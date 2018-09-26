package fr.gubian.suivichantier.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.gubian.suivichantier.domain.Chantier;
import fr.gubian.suivichantier.service.ChantierService;
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
 * REST controller for managing Chantier.
 */
@RestController
@RequestMapping("/api")
public class ChantierResource {

    private final Logger log = LoggerFactory.getLogger(ChantierResource.class);

    private static final String ENTITY_NAME = "chantier";

    private final ChantierService chantierService;

    public ChantierResource(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    /**
     * POST  /chantiers : Create a new chantier.
     *
     * @param chantier the chantier to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chantier, or with status 400 (Bad Request) if the chantier has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chantiers")
    @Timed
    public ResponseEntity<Chantier> createChantier(@Valid @RequestBody Chantier chantier) throws URISyntaxException {
        log.debug("REST request to save Chantier : {}", chantier);
        if (chantier.getId() != null) {
            throw new BadRequestAlertException("A new chantier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Chantier result = chantierService.save(chantier);
        return ResponseEntity.created(new URI("/api/chantiers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /chantiers : Updates an existing chantier.
     *
     * @param chantier the chantier to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chantier,
     * or with status 400 (Bad Request) if the chantier is not valid,
     * or with status 500 (Internal Server Error) if the chantier couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chantiers")
    @Timed
    public ResponseEntity<Chantier> updateChantier(@Valid @RequestBody Chantier chantier) throws URISyntaxException {
        log.debug("REST request to update Chantier : {}", chantier);
        if (chantier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Chantier result = chantierService.save(chantier);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, chantier.getId().toString()))
            .body(result);
    }

    /**
     * GET  /chantiers : get all the chantiers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of chantiers in body
     */
    @GetMapping("/chantiers")
    @Timed
    public ResponseEntity<List<Chantier>> getAllChantiers(Pageable pageable) {
        log.debug("REST request to get a page of Chantiers");
        Page<Chantier> page = chantierService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/chantiers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /chantiers/:id : get the "id" chantier.
     *
     * @param id the id of the chantier to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chantier, or with status 404 (Not Found)
     */
    @GetMapping("/chantiers/{id}")
    @Timed
    public ResponseEntity<Chantier> getChantier(@PathVariable Long id) {
        log.debug("REST request to get Chantier : {}", id);
        Optional<Chantier> chantier = chantierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chantier);
    }

    /**
     * DELETE  /chantiers/:id : delete the "id" chantier.
     *
     * @param id the id of the chantier to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chantiers/{id}")
    @Timed
    public ResponseEntity<Void> deleteChantier(@PathVariable Long id) {
        log.debug("REST request to delete Chantier : {}", id);
        chantierService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
