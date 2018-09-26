package fr.gubian.suivichantier.web.rest;

import fr.gubian.suivichantier.SuiviChantierApp;

import fr.gubian.suivichantier.domain.ChantierIntervenant;
import fr.gubian.suivichantier.repository.ChantierIntervenantRepository;
import fr.gubian.suivichantier.service.ChantierIntervenantService;
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

import fr.gubian.suivichantier.domain.enumeration.Corpsmetier;
import fr.gubian.suivichantier.domain.enumeration.Role;
/**
 * Test class for the ChantierIntervenantResource REST controller.
 *
 * @see ChantierIntervenantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SuiviChantierApp.class)
public class ChantierIntervenantResourceIntTest {

    private static final Corpsmetier DEFAULT_CORPMETIER = Corpsmetier.TERRASSEMENT;
    private static final Corpsmetier UPDATED_CORPMETIER = Corpsmetier.GROS_OEUVRE;

    private static final Role DEFAULT_ROLE = Role.MOA;
    private static final Role UPDATED_ROLE = Role.MOE;

    @Autowired
    private ChantierIntervenantRepository chantierIntervenantRepository;
    
    @Autowired
    private ChantierIntervenantService chantierIntervenantService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restChantierIntervenantMockMvc;

    private ChantierIntervenant chantierIntervenant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChantierIntervenantResource chantierIntervenantResource = new ChantierIntervenantResource(chantierIntervenantService);
        this.restChantierIntervenantMockMvc = MockMvcBuilders.standaloneSetup(chantierIntervenantResource)
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
    public static ChantierIntervenant createEntity(EntityManager em) {
        ChantierIntervenant chantierIntervenant = new ChantierIntervenant()
            .corpmetier(DEFAULT_CORPMETIER)
            .role(DEFAULT_ROLE);
        return chantierIntervenant;
    }

    @Before
    public void initTest() {
        chantierIntervenant = createEntity(em);
    }

    @Test
    @Transactional
    public void createChantierIntervenant() throws Exception {
        int databaseSizeBeforeCreate = chantierIntervenantRepository.findAll().size();

        // Create the ChantierIntervenant
        restChantierIntervenantMockMvc.perform(post("/api/chantier-intervenants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chantierIntervenant)))
            .andExpect(status().isCreated());

        // Validate the ChantierIntervenant in the database
        List<ChantierIntervenant> chantierIntervenantList = chantierIntervenantRepository.findAll();
        assertThat(chantierIntervenantList).hasSize(databaseSizeBeforeCreate + 1);
        ChantierIntervenant testChantierIntervenant = chantierIntervenantList.get(chantierIntervenantList.size() - 1);
        assertThat(testChantierIntervenant.getCorpmetier()).isEqualTo(DEFAULT_CORPMETIER);
        assertThat(testChantierIntervenant.getRole()).isEqualTo(DEFAULT_ROLE);
    }

    @Test
    @Transactional
    public void createChantierIntervenantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chantierIntervenantRepository.findAll().size();

        // Create the ChantierIntervenant with an existing ID
        chantierIntervenant.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChantierIntervenantMockMvc.perform(post("/api/chantier-intervenants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chantierIntervenant)))
            .andExpect(status().isBadRequest());

        // Validate the ChantierIntervenant in the database
        List<ChantierIntervenant> chantierIntervenantList = chantierIntervenantRepository.findAll();
        assertThat(chantierIntervenantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllChantierIntervenants() throws Exception {
        // Initialize the database
        chantierIntervenantRepository.saveAndFlush(chantierIntervenant);

        // Get all the chantierIntervenantList
        restChantierIntervenantMockMvc.perform(get("/api/chantier-intervenants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chantierIntervenant.getId().intValue())))
            .andExpect(jsonPath("$.[*].corpmetier").value(hasItem(DEFAULT_CORPMETIER.toString())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())));
    }
    
    @Test
    @Transactional
    public void getChantierIntervenant() throws Exception {
        // Initialize the database
        chantierIntervenantRepository.saveAndFlush(chantierIntervenant);

        // Get the chantierIntervenant
        restChantierIntervenantMockMvc.perform(get("/api/chantier-intervenants/{id}", chantierIntervenant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chantierIntervenant.getId().intValue()))
            .andExpect(jsonPath("$.corpmetier").value(DEFAULT_CORPMETIER.toString()))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingChantierIntervenant() throws Exception {
        // Get the chantierIntervenant
        restChantierIntervenantMockMvc.perform(get("/api/chantier-intervenants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChantierIntervenant() throws Exception {
        // Initialize the database
        chantierIntervenantService.save(chantierIntervenant);

        int databaseSizeBeforeUpdate = chantierIntervenantRepository.findAll().size();

        // Update the chantierIntervenant
        ChantierIntervenant updatedChantierIntervenant = chantierIntervenantRepository.findById(chantierIntervenant.getId()).get();
        // Disconnect from session so that the updates on updatedChantierIntervenant are not directly saved in db
        em.detach(updatedChantierIntervenant);
        updatedChantierIntervenant
            .corpmetier(UPDATED_CORPMETIER)
            .role(UPDATED_ROLE);

        restChantierIntervenantMockMvc.perform(put("/api/chantier-intervenants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChantierIntervenant)))
            .andExpect(status().isOk());

        // Validate the ChantierIntervenant in the database
        List<ChantierIntervenant> chantierIntervenantList = chantierIntervenantRepository.findAll();
        assertThat(chantierIntervenantList).hasSize(databaseSizeBeforeUpdate);
        ChantierIntervenant testChantierIntervenant = chantierIntervenantList.get(chantierIntervenantList.size() - 1);
        assertThat(testChantierIntervenant.getCorpmetier()).isEqualTo(UPDATED_CORPMETIER);
        assertThat(testChantierIntervenant.getRole()).isEqualTo(UPDATED_ROLE);
    }

    @Test
    @Transactional
    public void updateNonExistingChantierIntervenant() throws Exception {
        int databaseSizeBeforeUpdate = chantierIntervenantRepository.findAll().size();

        // Create the ChantierIntervenant

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChantierIntervenantMockMvc.perform(put("/api/chantier-intervenants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chantierIntervenant)))
            .andExpect(status().isBadRequest());

        // Validate the ChantierIntervenant in the database
        List<ChantierIntervenant> chantierIntervenantList = chantierIntervenantRepository.findAll();
        assertThat(chantierIntervenantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChantierIntervenant() throws Exception {
        // Initialize the database
        chantierIntervenantService.save(chantierIntervenant);

        int databaseSizeBeforeDelete = chantierIntervenantRepository.findAll().size();

        // Get the chantierIntervenant
        restChantierIntervenantMockMvc.perform(delete("/api/chantier-intervenants/{id}", chantierIntervenant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ChantierIntervenant> chantierIntervenantList = chantierIntervenantRepository.findAll();
        assertThat(chantierIntervenantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChantierIntervenant.class);
        ChantierIntervenant chantierIntervenant1 = new ChantierIntervenant();
        chantierIntervenant1.setId(1L);
        ChantierIntervenant chantierIntervenant2 = new ChantierIntervenant();
        chantierIntervenant2.setId(chantierIntervenant1.getId());
        assertThat(chantierIntervenant1).isEqualTo(chantierIntervenant2);
        chantierIntervenant2.setId(2L);
        assertThat(chantierIntervenant1).isNotEqualTo(chantierIntervenant2);
        chantierIntervenant1.setId(null);
        assertThat(chantierIntervenant1).isNotEqualTo(chantierIntervenant2);
    }
}
