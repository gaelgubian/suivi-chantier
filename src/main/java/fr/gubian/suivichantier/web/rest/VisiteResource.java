package fr.gubian.suivichantier.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.gubian.suivichantier.domain.Visite;
import fr.gubian.suivichantier.service.VisiteService;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Visite.
 */
@RestController
@RequestMapping("/api")
public class VisiteResource {

    private final Logger log = LoggerFactory.getLogger(VisiteResource.class);

    private static final String ENTITY_NAME = "visite";

    private final VisiteService visiteService;

    public VisiteResource(VisiteService visiteService) {
        this.visiteService = visiteService;
    }

    /**
     * POST  /visites : Create a new visite.
     *
     * @param visite the visite to create
     * @return the ResponseEntity with status 201 (Created) and with body the new visite, or with status 400 (Bad Request) if the visite has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/visites")
    @Timed
    public ResponseEntity<Visite> createVisite(@RequestBody Visite visite) throws URISyntaxException {
        log.debug("REST request to save Visite : {}", visite);
        if (visite.getId() != null) {
            throw new BadRequestAlertException("A new visite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Visite result = visiteService.save(visite);
        return ResponseEntity.created(new URI("/api/visites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /visites : Updates an existing visite.
     *
     * @param visite the visite to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated visite,
     * or with status 400 (Bad Request) if the visite is not valid,
     * or with status 500 (Internal Server Error) if the visite couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/visites")
    @Timed
    public ResponseEntity<Visite> updateVisite(@RequestBody Visite visite) throws URISyntaxException {
        log.debug("REST request to update Visite : {}", visite);
        if (visite.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Visite result = visiteService.save(visite);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, visite.getId().toString()))
            .body(result);
    }

    /**
     * GET  /visites : get all the visites.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of visites in body
     */
    @GetMapping("/visites")
    @Timed
    public ResponseEntity<List<Visite>> getAllVisites(Pageable pageable) {
        log.debug("REST request to get a page of Visites");
        Page<Visite> page = visiteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/visites");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /visites/:id : get the "id" visite.
     *
     * @param id the id of the visite to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the visite, or with status 404 (Not Found)
     */
    @GetMapping("/visites/{id}")
    @Timed
    public ResponseEntity<Visite> getVisite(@PathVariable Long id) {
        log.debug("REST request to get Visite : {}", id);
        Optional<Visite> visite = visiteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(visite);
    }

    /**
     * DELETE  /visites/:id : delete the "id" visite.
     *
     * @param id the id of the visite to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/visites/{id}")
    @Timed
    public ResponseEntity<Void> deleteVisite(@PathVariable Long id) {
        log.debug("REST request to delete Visite : {}", id);
        visiteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
