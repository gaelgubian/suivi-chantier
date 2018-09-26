package fr.gubian.suivichantier.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.gubian.suivichantier.domain.Icon;
import fr.gubian.suivichantier.service.IconService;
import fr.gubian.suivichantier.web.rest.errors.BadRequestAlertException;
import fr.gubian.suivichantier.web.rest.util.HeaderUtil;
import fr.gubian.suivichantier.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Icon.
 */
@RestController
@RequestMapping("/api")
public class IconResource {

    private final Logger log = LoggerFactory.getLogger(IconResource.class);

    private static final String ENTITY_NAME = "icon";

    private final IconService iconService;

    public IconResource(IconService iconService) {
        this.iconService = iconService;
    }

    /**
     * POST  /icons : Create a new icon.
     *
     * @param icon the icon to create
     * @return the ResponseEntity with status 201 (Created) and with body the new icon, or with status 400 (Bad Request) if the icon has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/icons")
    @Timed
    public ResponseEntity<Icon> createIcon(@Valid @RequestBody Icon icon) throws URISyntaxException {
        log.debug("REST request to save Icon : {}", icon);
        if (icon.getId() != null) {
            throw new BadRequestAlertException("A new icon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Icon result = iconService.save(icon);
        return ResponseEntity.created(new URI("/api/icons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /icons : Updates an existing icon.
     *
     * @param icon the icon to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated icon,
     * or with status 400 (Bad Request) if the icon is not valid,
     * or with status 500 (Internal Server Error) if the icon couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/icons")
    @Timed
    public ResponseEntity<Icon> updateIcon(@Valid @RequestBody Icon icon) throws URISyntaxException {
        log.debug("REST request to update Icon : {}", icon);
        if (icon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Icon result = iconService.save(icon);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, icon.getId().toString()))
            .body(result);
    }

    /**
     * GET  /icons : get all the icons.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of icons in body
     */
    @GetMapping("/icons")
    @Timed
    public ResponseEntity<List<Icon>> getAllIcons(Pageable pageable) {
        log.debug("REST request to get a page of Icons");
        Page<Icon> page = iconService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/icons");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /icons/:id : get the "id" icon.
     *
     * @param id the id of the icon to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the icon, or with status 404 (Not Found)
     */
    @GetMapping("/icons/{id}")
    @Timed
    public ResponseEntity<Icon> getIcon(@PathVariable Long id) {
        log.debug("REST request to get Icon : {}", id);
        Optional<Icon> icon = iconService.findOne(id);
        return ResponseUtil.wrapOrNotFound(icon);
    }

    /**
     * DELETE  /icons/:id : delete the "id" icon.
     *
     * @param id the id of the icon to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/icons/{id}")
    @Timed
    public ResponseEntity<Void> deleteIcon(@PathVariable Long id) {
        log.debug("REST request to delete Icon : {}", id);
        iconService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
