package fr.gubian.suivichantier.web.rest;

import fr.gubian.suivichantier.SuiviChantierApp;

import fr.gubian.suivichantier.domain.DocumentTuile;
import fr.gubian.suivichantier.repository.DocumentTuileRepository;
import fr.gubian.suivichantier.service.DocumentTuileService;
import fr.gubian.suivichantier.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;


import static fr.gubian.suivichantier.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DocumentTuileResource REST controller.
 *
 * @see DocumentTuileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SuiviChantierApp.class)
public class DocumentTuileResourceIntTest {

    private static final byte[] DEFAULT_CONTENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CONTENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTENT_CONTENT_TYPE = "image/png";

    @Autowired
    private DocumentTuileRepository documentTuileRepository;
    
    @Autowired
    private DocumentTuileService documentTuileService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDocumentTuileMockMvc;

    private DocumentTuile documentTuile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocumentTuileResource documentTuileResource = new DocumentTuileResource(documentTuileService);
        this.restDocumentTuileMockMvc = MockMvcBuilders.standaloneSetup(documentTuileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentTuile createEntity(EntityManager em) {
        DocumentTuile documentTuile = new DocumentTuile()
            .content(DEFAULT_CONTENT)
            .contentContentType(DEFAULT_CONTENT_CONTENT_TYPE);
        return documentTuile;
    }

    @Before
    public void initTest() {
        documentTuile = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumentTuile() throws Exception {
        int databaseSizeBeforeCreate = documentTuileRepository.findAll().size();

        // Create the DocumentTuile
        restDocumentTuileMockMvc.perform(post("/api/document-tuiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentTuile)))
            .andExpect(status().isCreated());

        // Validate the DocumentTuile in the database
        List<DocumentTuile> documentTuileList = documentTuileRepository.findAll();
        assertThat(documentTuileList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentTuile testDocumentTuile = documentTuileList.get(documentTuileList.size() - 1);
        assertThat(testDocumentTuile.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testDocumentTuile.getContentContentType()).isEqualTo(DEFAULT_CONTENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createDocumentTuileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentTuileRepository.findAll().size();

        // Create the DocumentTuile with an existing ID
        documentTuile.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentTuileMockMvc.perform(post("/api/document-tuiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentTuile)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentTuile in the database
        List<DocumentTuile> documentTuileList = documentTuileRepository.findAll();
        assertThat(documentTuileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDocumentTuiles() throws Exception {
        // Initialize the database
        documentTuileRepository.saveAndFlush(documentTuile);

        // Get all the documentTuileList
        restDocumentTuileMockMvc.perform(get("/api/document-tuiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentTuile.getId().intValue())))
            .andExpect(jsonPath("$.[*].contentContentType").value(hasItem(DEFAULT_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT))));
    }
    
    @Test
    @Transactional
    public void getDocumentTuile() throws Exception {
        // Initialize the database
        documentTuileRepository.saveAndFlush(documentTuile);

        // Get the documentTuile
        restDocumentTuileMockMvc.perform(get("/api/document-tuiles/{id}", documentTuile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(documentTuile.getId().intValue()))
            .andExpect(jsonPath("$.contentContentType").value(DEFAULT_CONTENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.content").value(Base64Utils.encodeToString(DEFAULT_CONTENT)));
    }

    @Test
    @Transactional
    public void getNonExistingDocumentTuile() throws Exception {
        // Get the documentTuile
        restDocumentTuileMockMvc.perform(get("/api/document-tuiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumentTuile() throws Exception {
        // Initialize the database
        documentTuileService.save(documentTuile);

        int databaseSizeBeforeUpdate = documentTuileRepository.findAll().size();

        // Update the documentTuile
        DocumentTuile updatedDocumentTuile = documentTuileRepository.findById(documentTuile.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentTuile are not directly saved in db
        em.detach(updatedDocumentTuile);
        updatedDocumentTuile
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE);

        restDocumentTuileMockMvc.perform(put("/api/document-tuiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDocumentTuile)))
            .andExpect(status().isOk());

        // Validate the DocumentTuile in the database
        List<DocumentTuile> documentTuileList = documentTuileRepository.findAll();
        assertThat(documentTuileList).hasSize(databaseSizeBeforeUpdate);
        DocumentTuile testDocumentTuile = documentTuileList.get(documentTuileList.size() - 1);
        assertThat(testDocumentTuile.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testDocumentTuile.getContentContentType()).isEqualTo(UPDATED_CONTENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumentTuile() throws Exception {
        int databaseSizeBeforeUpdate = documentTuileRepository.findAll().size();

        // Create the DocumentTuile

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentTuileMockMvc.perform(put("/api/document-tuiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentTuile)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentTuile in the database
        List<DocumentTuile> documentTuileList = documentTuileRepository.findAll();
        assertThat(documentTuileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocumentTuile() throws Exception {
        // Initialize the database
        documentTuileService.save(documentTuile);

        int databaseSizeBeforeDelete = documentTuileRepository.findAll().size();

        // Get the documentTuile
        restDocumentTuileMockMvc.perform(delete("/api/document-tuiles/{id}", documentTuile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DocumentTuile> documentTuileList = documentTuileRepository.findAll();
        assertThat(documentTuileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentTuile.class);
        DocumentTuile documentTuile1 = new DocumentTuile();
        documentTuile1.setId(1L);
        DocumentTuile documentTuile2 = new DocumentTuile();
        documentTuile2.setId(documentTuile1.getId());
        assertThat(documentTuile1).isEqualTo(documentTuile2);
        documentTuile2.setId(2L);
        assertThat(documentTuile1).isNotEqualTo(documentTuile2);
        documentTuile1.setId(null);
        assertThat(documentTuile1).isNotEqualTo(documentTuile2);
    }
}
