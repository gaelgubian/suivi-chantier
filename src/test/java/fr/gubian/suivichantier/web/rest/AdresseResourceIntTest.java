package fr.gubian.suivichantier.web.rest;

import fr.gubian.suivichantier.SuiviChantierApp;

import fr.gubian.suivichantier.domain.Adresse;
import fr.gubian.suivichantier.repository.AdresseRepository;
import fr.gubian.suivichantier.service.AdresseService;
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
 * Test class for the AdresseResource REST controller.
 *
 * @see AdresseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SuiviChantierApp.class)
public class AdresseResourceIntTest {

    private static final String DEFAULT_APPARTEMENT = "AAAAAAAAAA";
    private static final String UPDATED_APPARTEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_ENTREE = "AAAAAAAAAA";
    private static final String UPDATED_ENTREE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_VOIE = "AAAAAAAAAA";
    private static final String UPDATED_VOIE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLEMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_CODEPOSTAL = 1;
    private static final Integer UPDATED_CODEPOSTAL = 2;

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final String DEFAULT_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LATITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    @Autowired
    private AdresseRepository adresseRepository;
    
    @Autowired
    private AdresseService adresseService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAdresseMockMvc;

    private Adresse adresse;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdresseResource adresseResource = new AdresseResource(adresseService);
        this.restAdresseMockMvc = MockMvcBuilders.standaloneSetup(adresseResource)
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
    public static Adresse createEntity(EntityManager em) {
        Adresse adresse = new Adresse()
            .appartement(DEFAULT_APPARTEMENT)
            .entree(DEFAULT_ENTREE)
            .numero(DEFAULT_NUMERO)
            .voie(DEFAULT_VOIE)
            .complement(DEFAULT_COMPLEMENT)
            .codepostal(DEFAULT_CODEPOSTAL)
            .ville(DEFAULT_VILLE)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE);
        return adresse;
    }

    @Before
    public void initTest() {
        adresse = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdresse() throws Exception {
        int databaseSizeBeforeCreate = adresseRepository.findAll().size();

        // Create the Adresse
        restAdresseMockMvc.perform(post("/api/adresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adresse)))
            .andExpect(status().isCreated());

        // Validate the Adresse in the database
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeCreate + 1);
        Adresse testAdresse = adresseList.get(adresseList.size() - 1);
        assertThat(testAdresse.getAppartement()).isEqualTo(DEFAULT_APPARTEMENT);
        assertThat(testAdresse.getEntree()).isEqualTo(DEFAULT_ENTREE);
        assertThat(testAdresse.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testAdresse.getVoie()).isEqualTo(DEFAULT_VOIE);
        assertThat(testAdresse.getComplement()).isEqualTo(DEFAULT_COMPLEMENT);
        assertThat(testAdresse.getCodepostal()).isEqualTo(DEFAULT_CODEPOSTAL);
        assertThat(testAdresse.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testAdresse.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testAdresse.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
    }

    @Test
    @Transactional
    public void createAdresseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adresseRepository.findAll().size();

        // Create the Adresse with an existing ID
        adresse.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdresseMockMvc.perform(post("/api/adresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adresse)))
            .andExpect(status().isBadRequest());

        // Validate the Adresse in the database
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAdresses() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList
        restAdresseMockMvc.perform(get("/api/adresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adresse.getId().intValue())))
            .andExpect(jsonPath("$.[*].appartement").value(hasItem(DEFAULT_APPARTEMENT.toString())))
            .andExpect(jsonPath("$.[*].entree").value(hasItem(DEFAULT_ENTREE.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].voie").value(hasItem(DEFAULT_VOIE.toString())))
            .andExpect(jsonPath("$.[*].complement").value(hasItem(DEFAULT_COMPLEMENT.toString())))
            .andExpect(jsonPath("$.[*].codepostal").value(hasItem(DEFAULT_CODEPOSTAL)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.toString())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.toString())));
    }
    
    @Test
    @Transactional
    public void getAdresse() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get the adresse
        restAdresseMockMvc.perform(get("/api/adresses/{id}", adresse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adresse.getId().intValue()))
            .andExpect(jsonPath("$.appartement").value(DEFAULT_APPARTEMENT.toString()))
            .andExpect(jsonPath("$.entree").value(DEFAULT_ENTREE.toString()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()))
            .andExpect(jsonPath("$.voie").value(DEFAULT_VOIE.toString()))
            .andExpect(jsonPath("$.complement").value(DEFAULT_COMPLEMENT.toString()))
            .andExpect(jsonPath("$.codepostal").value(DEFAULT_CODEPOSTAL))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.toString()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAdresse() throws Exception {
        // Get the adresse
        restAdresseMockMvc.perform(get("/api/adresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdresse() throws Exception {
        // Initialize the database
        adresseService.save(adresse);

        int databaseSizeBeforeUpdate = adresseRepository.findAll().size();

        // Update the adresse
        Adresse updatedAdresse = adresseRepository.findById(adresse.getId()).get();
        // Disconnect from session so that the updates on updatedAdresse are not directly saved in db
        em.detach(updatedAdresse);
        updatedAdresse
            .appartement(UPDATED_APPARTEMENT)
            .entree(UPDATED_ENTREE)
            .numero(UPDATED_NUMERO)
            .voie(UPDATED_VOIE)
            .complement(UPDATED_COMPLEMENT)
            .codepostal(UPDATED_CODEPOSTAL)
            .ville(UPDATED_VILLE)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);

        restAdresseMockMvc.perform(put("/api/adresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAdresse)))
            .andExpect(status().isOk());

        // Validate the Adresse in the database
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeUpdate);
        Adresse testAdresse = adresseList.get(adresseList.size() - 1);
        assertThat(testAdresse.getAppartement()).isEqualTo(UPDATED_APPARTEMENT);
        assertThat(testAdresse.getEntree()).isEqualTo(UPDATED_ENTREE);
        assertThat(testAdresse.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testAdresse.getVoie()).isEqualTo(UPDATED_VOIE);
        assertThat(testAdresse.getComplement()).isEqualTo(UPDATED_COMPLEMENT);
        assertThat(testAdresse.getCodepostal()).isEqualTo(UPDATED_CODEPOSTAL);
        assertThat(testAdresse.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testAdresse.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testAdresse.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void updateNonExistingAdresse() throws Exception {
        int databaseSizeBeforeUpdate = adresseRepository.findAll().size();

        // Create the Adresse

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdresseMockMvc.perform(put("/api/adresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adresse)))
            .andExpect(status().isBadRequest());

        // Validate the Adresse in the database
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdresse() throws Exception {
        // Initialize the database
        adresseService.save(adresse);

        int databaseSizeBeforeDelete = adresseRepository.findAll().size();

        // Get the adresse
        restAdresseMockMvc.perform(delete("/api/adresses/{id}", adresse.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Adresse.class);
        Adresse adresse1 = new Adresse();
        adresse1.setId(1L);
        Adresse adresse2 = new Adresse();
        adresse2.setId(adresse1.getId());
        assertThat(adresse1).isEqualTo(adresse2);
        adresse2.setId(2L);
        assertThat(adresse1).isNotEqualTo(adresse2);
        adresse1.setId(null);
        assertThat(adresse1).isNotEqualTo(adresse2);
    }
}
