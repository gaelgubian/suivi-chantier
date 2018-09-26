package fr.gubian.suivichantier.web.rest;

import fr.gubian.suivichantier.SuiviChantierApp;

import fr.gubian.suivichantier.domain.Visite;
import fr.gubian.suivichantier.repository.VisiteRepository;
import fr.gubian.suivichantier.service.VisiteService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static fr.gubian.suivichantier.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VisiteResource REST controller.
 *
 * @see VisiteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SuiviChantierApp.class)
public class VisiteResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private VisiteRepository visiteRepository;
    
    @Autowired
    private VisiteService visiteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVisiteMockMvc;

    private Visite visite;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VisiteResource visiteResource = new VisiteResource(visiteService);
        this.restVisiteMockMvc = MockMvcBuilders.standaloneSetup(visiteResource)
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
    public static Visite createEntity(EntityManager em) {
        Visite visite = new Visite()
            .label(DEFAULT_LABEL)
            .date(DEFAULT_DATE);
        return visite;
    }

    @Before
    public void initTest() {
        visite = createEntity(em);
    }

    @Test
    @Transactional
    public void createVisite() throws Exception {
        int databaseSizeBeforeCreate = visiteRepository.findAll().size();

        // Create the Visite
        restVisiteMockMvc.perform(post("/api/visites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visite)))
            .andExpect(status().isCreated());

        // Validate the Visite in the database
        List<Visite> visiteList = visiteRepository.findAll();
        assertThat(visiteList).hasSize(databaseSizeBeforeCreate + 1);
        Visite testVisite = visiteList.get(visiteList.size() - 1);
        assertThat(testVisite.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testVisite.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createVisiteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = visiteRepository.findAll().size();

        // Create the Visite with an existing ID
        visite.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVisiteMockMvc.perform(post("/api/visites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visite)))
            .andExpect(status().isBadRequest());

        // Validate the Visite in the database
        List<Visite> visiteList = visiteRepository.findAll();
        assertThat(visiteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVisites() throws Exception {
        // Initialize the database
        visiteRepository.saveAndFlush(visite);

        // Get all the visiteList
        restVisiteMockMvc.perform(get("/api/visites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visite.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getVisite() throws Exception {
        // Initialize the database
        visiteRepository.saveAndFlush(visite);

        // Get the visite
        restVisiteMockMvc.perform(get("/api/visites/{id}", visite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(visite.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVisite() throws Exception {
        // Get the visite
        restVisiteMockMvc.perform(get("/api/visites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVisite() throws Exception {
        // Initialize the database
        visiteService.save(visite);

        int databaseSizeBeforeUpdate = visiteRepository.findAll().size();

        // Update the visite
        Visite updatedVisite = visiteRepository.findById(visite.getId()).get();
        // Disconnect from session so that the updates on updatedVisite are not directly saved in db
        em.detach(updatedVisite);
        updatedVisite
            .label(UPDATED_LABEL)
            .date(UPDATED_DATE);

        restVisiteMockMvc.perform(put("/api/visites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVisite)))
            .andExpect(status().isOk());

        // Validate the Visite in the database
        List<Visite> visiteList = visiteRepository.findAll();
        assertThat(visiteList).hasSize(databaseSizeBeforeUpdate);
        Visite testVisite = visiteList.get(visiteList.size() - 1);
        assertThat(testVisite.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testVisite.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingVisite() throws Exception {
        int databaseSizeBeforeUpdate = visiteRepository.findAll().size();

        // Create the Visite

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVisiteMockMvc.perform(put("/api/visites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visite)))
            .andExpect(status().isBadRequest());

        // Validate the Visite in the database
        List<Visite> visiteList = visiteRepository.findAll();
        assertThat(visiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVisite() throws Exception {
        // Initialize the database
        visiteService.save(visite);

        int databaseSizeBeforeDelete = visiteRepository.findAll().size();

        // Get the visite
        restVisiteMockMvc.perform(delete("/api/visites/{id}", visite.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Visite> visiteList = visiteRepository.findAll();
        assertThat(visiteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Visite.class);
        Visite visite1 = new Visite();
        visite1.setId(1L);
        Visite visite2 = new Visite();
        visite2.setId(visite1.getId());
        assertThat(visite1).isEqualTo(visite2);
        visite2.setId(2L);
        assertThat(visite1).isNotEqualTo(visite2);
        visite1.setId(null);
        assertThat(visite1).isNotEqualTo(visite2);
    }
}
