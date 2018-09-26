package fr.gubian.suivichantier.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.gubian.suivichantier.domain.ChantierIntervenant;
import fr.gubian.suivichantier.service.ChantierIntervenantService;
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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing ChantierIntervenant.
 */
@RestController
@RequestMapping("/api")
public class ChantierIntervenantResource {

    private final Logger log = LoggerFactory.getLogger(ChantierIntervenantResource.class);

    private static final String ENTITY_NAME = "chantierIntervenant";

    private final ChantierIntervenantService chantierIntervenantService;

    public ChantierIntervenantResource(ChantierIntervenantService chantierIntervenantService) {
        this.chantierIntervenantService = chantierIntervenantService;
    }

    /**
     * POST  /chantier-intervenants : Create a new chantierIntervenant.
     *
     * @param chantierIntervenant the chantierIntervenant to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chantierIntervenant, or with status 400 (Bad Request) if the chantierIntervenant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chantier-intervenants")
    @Timed
    public ResponseEntity<ChantierIntervenant> createChantierIntervenant(@RequestBody ChantierIntervenant chantierIntervenant) throws URISyntaxException {
        log.debug("REST request to save ChantierIntervenant : {}", chantierIntervenant);
        if (chantierIntervenant.getId() != null) {
            throw new BadRequestAlertException("A new chantierIntervenant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChantierIntervenant result = chantierIntervenantService.save(chantierIntervenant);
        return ResponseEntity.created(new URI("/api/chantier-intervenants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /chantier-intervenants : Updates an existing chantierIntervenant.
     *
     * @param chantierIntervenant the chantierIntervenant to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chantierIntervenant,
     * or with status 400 (Bad Request) if the chantierIntervenant is not valid,
     * or with status 500 (Internal Server Error) if the chantierIntervenant couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chantier-intervenants")
    @Timed
    public ResponseEntity<ChantierIntervenant> updateChantierIntervenant(@RequestBody ChantierIntervenant chantierIntervenant) throws URISyntaxException {
        log.debug("REST request to update ChantierIntervenant : {}", chantierIntervenant);
        if (chantierIntervenant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChantierIntervenant result = chantierIntervenantService.save(chantierIntervenant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, chantierIntervenant.getId().toString()))
            .body(result);
    }

    /**
     * GET  /chantier-intervenants : get all the chantierIntervenants.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of chantierIntervenants in body
     */
    @GetMapping("/chantier-intervenants")
    @Timed
    public ResponseEntity<List<ChantierIntervenant>> getAllChantierIntervenants(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("signature-is-null".equals(filter)) {
            log.debug("REST request to get all ChantierIntervenants where signature is null");
            return new ResponseEntity<>(chantierIntervenantService.findAllWhereSignatureIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of ChantierIntervenants");
        Page<ChantierIntervenant> page = chantierIntervenantService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/chantier-intervenants");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /chantier-intervenants/:id : get the "id" chantierIntervenant.
     *
     * @param id the id of the chantierIntervenant to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chantierIntervenant, or with status 404 (Not Found)
     */
    @GetMapping("/chantier-intervenants/{id}")
    @Timed
    public ResponseEntity<ChantierIntervenant> getChantierIntervenant(@PathVariable Long id) {
        log.debug("REST request to get ChantierIntervenant : {}", id);
        Optional<ChantierIntervenant> chantierIntervenant = chantierIntervenantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chantierIntervenant);
    }

    /**
     * DELETE  /chantier-intervenants/:id : delete the "id" chantierIntervenant.
     *
     * @param id the id of the chantierIntervenant to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chantier-intervenants/{id}")
    @Timed
    public ResponseEntity<Void> deleteChantierIntervenant(@PathVariable Long id) {
        log.debug("REST request to delete ChantierIntervenant : {}", id);
        chantierIntervenantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
