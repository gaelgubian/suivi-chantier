package fr.gubian.suivichantier.web.rest;

import fr.gubian.suivichantier.SuiviChantierApp;

import fr.gubian.suivichantier.domain.Icon;
import fr.gubian.suivichantier.repository.IconRepository;
import fr.gubian.suivichantier.service.IconService;
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
 * Test class for the IconResource REST controller.
 *
 * @see IconResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SuiviChantierApp.class)
public class IconResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private IconRepository iconRepository;
    
    @Autowired
    private IconService iconService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIconMockMvc;

    private Icon icon;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IconResource iconResource = new IconResource(iconService);
        this.restIconMockMvc = MockMvcBuilders.standaloneSetup(iconResource)
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
    public static Icon createEntity(EntityManager em) {
        Icon icon = new Icon()
            .label(DEFAULT_LABEL)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return icon;
    }

    @Before
    public void initTest() {
        icon = createEntity(em);
    }

    @Test
    @Transactional
    public void createIcon() throws Exception {
        int databaseSizeBeforeCreate = iconRepository.findAll().size();

        // Create the Icon
        restIconMockMvc.perform(post("/api/icons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(icon)))
            .andExpect(status().isCreated());

        // Validate the Icon in the database
        List<Icon> iconList = iconRepository.findAll();
        assertThat(iconList).hasSize(databaseSizeBeforeCreate + 1);
        Icon testIcon = iconList.get(iconList.size() - 1);
        assertThat(testIcon.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testIcon.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testIcon.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createIconWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = iconRepository.findAll().size();

        // Create the Icon with an existing ID
        icon.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIconMockMvc.perform(post("/api/icons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(icon)))
            .andExpect(status().isBadRequest());

        // Validate the Icon in the database
        List<Icon> iconList = iconRepository.findAll();
        assertThat(iconList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllIcons() throws Exception {
        // Initialize the database
        iconRepository.saveAndFlush(icon);

        // Get all the iconList
        restIconMockMvc.perform(get("/api/icons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(icon.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }
    
    @Test
    @Transactional
    public void getIcon() throws Exception {
        // Initialize the database
        iconRepository.saveAndFlush(icon);

        // Get the icon
        restIconMockMvc.perform(get("/api/icons/{id}", icon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(icon.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    public void getNonExistingIcon() throws Exception {
        // Get the icon
        restIconMockMvc.perform(get("/api/icons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIcon() throws Exception {
        // Initialize the database
        iconService.save(icon);

        int databaseSizeBeforeUpdate = iconRepository.findAll().size();

        // Update the icon
        Icon updatedIcon = iconRepository.findById(icon.getId()).get();
        // Disconnect from session so that the updates on updatedIcon are not directly saved in db
        em.detach(updatedIcon);
        updatedIcon
            .label(UPDATED_LABEL)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restIconMockMvc.perform(put("/api/icons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIcon)))
            .andExpect(status().isOk());

        // Validate the Icon in the database
        List<Icon> iconList = iconRepository.findAll();
        assertThat(iconList).hasSize(databaseSizeBeforeUpdate);
        Icon testIcon = iconList.get(iconList.size() - 1);
        assertThat(testIcon.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testIcon.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testIcon.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingIcon() throws Exception {
        int databaseSizeBeforeUpdate = iconRepository.findAll().size();

        // Create the Icon

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIconMockMvc.perform(put("/api/icons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(icon)))
            .andExpect(status().isBadRequest());

        // Validate the Icon in the database
        List<Icon> iconList = iconRepository.findAll();
        assertThat(iconList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIcon() throws Exception {
        // Initialize the database
        iconService.save(icon);

        int databaseSizeBeforeDelete = iconRepository.findAll().size();

        // Get the icon
        restIconMockMvc.perform(delete("/api/icons/{id}", icon.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Icon> iconList = iconRepository.findAll();
        assertThat(iconList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Icon.class);
        Icon icon1 = new Icon();
        icon1.setId(1L);
        Icon icon2 = new Icon();
        icon2.setId(icon1.getId());
        assertThat(icon1).isEqualTo(icon2);
        icon2.setId(2L);
        assertThat(icon1).isNotEqualTo(icon2);
        icon1.setId(null);
        assertThat(icon1).isNotEqualTo(icon2);
    }
}
