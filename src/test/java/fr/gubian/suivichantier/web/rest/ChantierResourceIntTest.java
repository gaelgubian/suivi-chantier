package fr.gubian.suivichantier.web.rest;

import fr.gubian.suivichantier.SuiviChantierApp;

import fr.gubian.suivichantier.domain.Chantier;
import fr.gubian.suivichantier.repository.ChantierRepository;
import fr.gubian.suivichantier.service.ChantierService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static fr.gubian.suivichantier.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ChantierResource REST controller.
 *
 * @see ChantierResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SuiviChantierApp.class)
public class ChantierResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ChantierRepository chantierRepository;
    
    @Autowired
    private ChantierService chantierService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restChantierMockMvc;

    private Chantier chantier;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChantierResource chantierResource = new ChantierResource(chantierService);
        this.restChantierMockMvc = MockMvcBuilders.standaloneSetup(chantierResource)
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
    public static Chantier createEntity(EntityManager em) {
        Chantier chantier = new Chantier()
            .label(DEFAULT_LABEL)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN);
        return chantier;
    }

    @Before
    public void initTest() {
        chantier = createEntity(em);
    }

    @Test
    @Transactional
    public void createChantier() throws Exception {
        int databaseSizeBeforeCreate = chantierRepository.findAll().size();

        // Create the Chantier
        restChantierMockMvc.perform(post("/api/chantiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chantier)))
            .andExpect(status().isCreated());

        // Validate the Chantier in the database
        List<Chantier> chantierList = chantierRepository.findAll();
        assertThat(chantierList).hasSize(databaseSizeBeforeCreate + 1);
        Chantier testChantier = chantierList.get(chantierList.size() - 1);
        assertThat(testChantier.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testChantier.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testChantier.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testChantier.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testChantier.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testChantier.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
    }

    @Test
    @Transactional
    public void createChantierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chantierRepository.findAll().size();

        // Create the Chantier with an existing ID
        chantier.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChantierMockMvc.perform(post("/api/chantiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chantier)))
            .andExpect(status().isBadRequest());

        // Validate the Chantier in the database
        List<Chantier> chantierList = chantierRepository.findAll();
        assertThat(chantierList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = chantierRepository.findAll().size();
        // set the field null
        chantier.setLabel(null);

        // Create the Chantier, which fails.

        restChantierMockMvc.perform(post("/api/chantiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chantier)))
            .andExpect(status().isBadRequest());

        List<Chantier> chantierList = chantierRepository.findAll();
        assertThat(chantierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChantiers() throws Exception {
        // Initialize the database
        chantierRepository.saveAndFlush(chantier);

        // Get all the chantierList
        restChantierMockMvc.perform(get("/api/chantiers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chantier.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())));
    }
    
    @Test
    @Transactional
    public void getChantier() throws Exception {
        // Initialize the database
        chantierRepository.saveAndFlush(chantier);

        // Get the chantier
        restChantierMockMvc.perform(get("/api/chantiers/{id}", chantier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chantier.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingChantier() throws Exception {
        // Get the chantier
        restChantierMockMvc.perform(get("/api/chantiers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChantier() throws Exception {
        // Initialize the database
        chantierService.save(chantier);

        int databaseSizeBeforeUpdate = chantierRepository.findAll().size();

        // Update the chantier
        Chantier updatedChantier = chantierRepository.findById(chantier.getId()).get();
        // Disconnect from session so that the updates on updatedChantier are not directly saved in db
        em.detach(updatedChantier);
        updatedChantier
            .label(UPDATED_LABEL)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN);

        restChantierMockMvc.perform(put("/api/chantiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChantier)))
            .andExpect(status().isOk());

        // Validate the Chantier in the database
        List<Chantier> chantierList = chantierRepository.findAll();
        assertThat(chantierList).hasSize(databaseSizeBeforeUpdate);
        Chantier testChantier = chantierList.get(chantierList.size() - 1);
        assertThat(testChantier.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testChantier.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testChantier.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testChantier.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testChantier.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testChantier.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void updateNonExistingChantier() throws Exception {
        int databaseSizeBeforeUpdate = chantierRepository.findAll().size();

        // Create the Chantier

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChantierMockMvc.perform(put("/api/chantiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chantier)))
            .andExpect(status().isBadRequest());

        // Validate the Chantier in the database
        List<Chantier> chantierList = chantierRepository.findAll();
        assertThat(chantierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChantier() throws Exception {
        // Initialize the database
        chantierService.save(chantier);

        int databaseSizeBeforeDelete = chantierRepository.findAll().size();

        // Get the chantier
        restChantierMockMvc.perform(delete("/api/chantiers/{id}", chantier.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Chantier> chantierList = chantierRepository.findAll();
        assertThat(chantierList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Chantier.class);
        Chantier chantier1 = new Chantier();
        chantier1.setId(1L);
        Chantier chantier2 = new Chantier();
        chantier2.setId(chantier1.getId());
        assertThat(chantier1).isEqualTo(chantier2);
        chantier2.setId(2L);
        assertThat(chantier1).isNotEqualTo(chantier2);
        chantier1.setId(null);
        assertThat(chantier1).isNotEqualTo(chantier2);
    }
}
