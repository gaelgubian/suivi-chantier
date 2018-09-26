package fr.gubian.suivichantier.web.rest;

import fr.gubian.suivichantier.SuiviChantierApp;

import fr.gubian.suivichantier.domain.Signature;
import fr.gubian.suivichantier.repository.SignatureRepository;
import fr.gubian.suivichantier.service.SignatureService;
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
 * Test class for the SignatureResource REST controller.
 *
 * @see SignatureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SuiviChantierApp.class)
public class SignatureResourceIntTest {

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private SignatureRepository signatureRepository;
    
    @Autowired
    private SignatureService signatureService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSignatureMockMvc;

    private Signature signature;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SignatureResource signatureResource = new SignatureResource(signatureService);
        this.restSignatureMockMvc = MockMvcBuilders.standaloneSetup(signatureResource)
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
    public static Signature createEntity(EntityManager em) {
        Signature signature = new Signature()
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return signature;
    }

    @Before
    public void initTest() {
        signature = createEntity(em);
    }

    @Test
    @Transactional
    public void createSignature() throws Exception {
        int databaseSizeBeforeCreate = signatureRepository.findAll().size();

        // Create the Signature
        restSignatureMockMvc.perform(post("/api/signatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(signature)))
            .andExpect(status().isCreated());

        // Validate the Signature in the database
        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeCreate + 1);
        Signature testSignature = signatureList.get(signatureList.size() - 1);
        assertThat(testSignature.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testSignature.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createSignatureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = signatureRepository.findAll().size();

        // Create the Signature with an existing ID
        signature.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSignatureMockMvc.perform(post("/api/signatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(signature)))
            .andExpect(status().isBadRequest());

        // Validate the Signature in the database
        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSignatures() throws Exception {
        // Initialize the database
        signatureRepository.saveAndFlush(signature);

        // Get all the signatureList
        restSignatureMockMvc.perform(get("/api/signatures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(signature.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }
    
    @Test
    @Transactional
    public void getSignature() throws Exception {
        // Initialize the database
        signatureRepository.saveAndFlush(signature);

        // Get the signature
        restSignatureMockMvc.perform(get("/api/signatures/{id}", signature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(signature.getId().intValue()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    public void getNonExistingSignature() throws Exception {
        // Get the signature
        restSignatureMockMvc.perform(get("/api/signatures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSignature() throws Exception {
        // Initialize the database
        signatureService.save(signature);

        int databaseSizeBeforeUpdate = signatureRepository.findAll().size();

        // Update the signature
        Signature updatedSignature = signatureRepository.findById(signature.getId()).get();
        // Disconnect from session so that the updates on updatedSignature are not directly saved in db
        em.detach(updatedSignature);
        updatedSignature
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restSignatureMockMvc.perform(put("/api/signatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSignature)))
            .andExpect(status().isOk());

        // Validate the Signature in the database
        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeUpdate);
        Signature testSignature = signatureList.get(signatureList.size() - 1);
        assertThat(testSignature.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testSignature.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingSignature() throws Exception {
        int databaseSizeBeforeUpdate = signatureRepository.findAll().size();

        // Create the Signature

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSignatureMockMvc.perform(put("/api/signatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(signature)))
            .andExpect(status().isBadRequest());

        // Validate the Signature in the database
        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSignature() throws Exception {
        // Initialize the database
        signatureService.save(signature);

        int databaseSizeBeforeDelete = signatureRepository.findAll().size();

        // Get the signature
        restSignatureMockMvc.perform(delete("/api/signatures/{id}", signature.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Signature.class);
        Signature signature1 = new Signature();
        signature1.setId(1L);
        Signature signature2 = new Signature();
        signature2.setId(signature1.getId());
        assertThat(signature1).isEqualTo(signature2);
        signature2.setId(2L);
        assertThat(signature1).isNotEqualTo(signature2);
        signature1.setId(null);
        assertThat(signature1).isNotEqualTo(signature2);
    }
}
