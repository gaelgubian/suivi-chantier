package fr.gubian.suivichantier.web.rest;

import fr.gubian.suivichantier.SuiviChantierApp;

import fr.gubian.suivichantier.domain.Bien;
import fr.gubian.suivichantier.repository.BienRepository;
import fr.gubian.suivichantier.service.BienService;
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

import javax.persistence.EntityManager;
import java.util.List;


import static fr.gubian.suivichantier.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BienResource REST controller.
 *
 * @see BienResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SuiviChantierApp.class)
public class BienResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private BienRepository bienRepository;
    
    @Autowired
    private BienService bienService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBienMockMvc;

    private Bien bien;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BienResource bienResource = new BienResource(bienService);
        this.restBienMockMvc = MockMvcBuilders.standaloneSetup(bienResource)
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
    public static Bien createEntity(EntityManager em) {
        Bien bien = new Bien()
            .label(DEFAULT_LABEL)
            .description(DEFAULT_DESCRIPTION);
        return bien;
    }

    @Before
    public void initTest() {
        bien = createEntity(em);
    }

    @Test
    @Transactional
    public void createBien() throws Exception {
        int databaseSizeBeforeCreate = bienRepository.findAll().size();

        // Create the Bien
        restBienMockMvc.perform(post("/api/biens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bien)))
            .andExpect(status().isCreated());

        // Validate the Bien in the database
        List<Bien> bienList = bienRepository.findAll();
        assertThat(bienList).hasSize(databaseSizeBeforeCreate + 1);
        Bien testBien = bienList.get(bienList.size() - 1);
        assertThat(testBien.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testBien.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createBienWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bienRepository.findAll().size();

        // Create the Bien with an existing ID
        bien.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBienMockMvc.perform(post("/api/biens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bien)))
            .andExpect(status().isBadRequest());

        // Validate the Bien in the database
        List<Bien> bienList = bienRepository.findAll();
        assertThat(bienList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = bienRepository.findAll().size();
        // set the field null
        bien.setLabel(null);

        // Create the Bien, which fails.

        restBienMockMvc.perform(post("/api/biens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bien)))
            .andExpect(status().isBadRequest());

        List<Bien> bienList = bienRepository.findAll();
        assertThat(bienList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBiens() throws Exception {
        // Initialize the database
        bienRepository.saveAndFlush(bien);

        // Get all the bienList
        restBienMockMvc.perform(get("/api/biens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bien.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getBien() throws Exception {
        // Initialize the database
        bienRepository.saveAndFlush(bien);

        // Get the bien
        restBienMockMvc.perform(get("/api/biens/{id}", bien.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bien.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBien() throws Exception {
        // Get the bien
        restBienMockMvc.perform(get("/api/biens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBien() throws Exception {
        // Initialize the database
        bienService.save(bien);

        int databaseSizeBeforeUpdate = bienRepository.findAll().size();

        // Update the bien
        Bien updatedBien = bienRepository.findById(bien.getId()).get();
        // Disconnect from session so that the updates on updatedBien are not directly saved in db
        em.detach(updatedBien);
        updatedBien
            .label(UPDATED_LABEL)
            .description(UPDATED_DESCRIPTION);

        restBienMockMvc.perform(put("/api/biens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBien)))
            .andExpect(status().isOk());

        // Validate the Bien in the database
        List<Bien> bienList = bienRepository.findAll();
        assertThat(bienList).hasSize(databaseSizeBeforeUpdate);
        Bien testBien = bienList.get(bienList.size() - 1);
        assertThat(testBien.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testBien.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingBien() throws Exception {
        int databaseSizeBeforeUpdate = bienRepository.findAll().size();

        // Create the Bien

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBienMockMvc.perform(put("/api/biens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bien)))
            .andExpect(status().isBadRequest());

        // Validate the Bien in the database
        List<Bien> bienList = bienRepository.findAll();
        assertThat(bienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBien() throws Exception {
        // Initialize the database
        bienService.save(bien);

        int databaseSizeBeforeDelete = bienRepository.findAll().size();

        // Get the bien
        restBienMockMvc.perform(delete("/api/biens/{id}", bien.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Bien> bienList = bienRepository.findAll();
        assertThat(bienList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bien.class);
        Bien bien1 = new Bien();
        bien1.setId(1L);
        Bien bien2 = new Bien();
        bien2.setId(bien1.getId());
        assertThat(bien1).isEqualTo(bien2);
        bien2.setId(2L);
        assertThat(bien1).isNotEqualTo(bien2);
        bien1.setId(null);
        assertThat(bien1).isNotEqualTo(bien2);
    }
}
