package fr.gubian.suivichantier.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.gubian.suivichantier.domain.Bien;
import fr.gubian.suivichantier.service.BienService;
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
 * REST controller for managing Bien.
 */
@RestController
@RequestMapping("/api")
public class BienResource {

    private final Logger log = LoggerFactory.getLogger(BienResource.class);

    private static final String ENTITY_NAME = "bien";

    private final BienService bienService;

    public BienResource(BienService bienService) {
        this.bienService = bienService;
    }

    /**
     * POST  /biens : Create a new bien.
     *
     * @param bien the bien to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bien, or with status 400 (Bad Request) if the bien has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/biens")
    @Timed
    public ResponseEntity<Bien> createBien(@Valid @RequestBody Bien bien) throws URISyntaxException {
        log.debug("REST request to save Bien : {}", bien);
        if (bien.getId() != null) {
            throw new BadRequestAlertException("A new bien cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bien result = bienService.save(bien);
        return ResponseEntity.created(new URI("/api/biens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /biens : Updates an existing bien.
     *
     * @param bien the bien to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bien,
     * or with status 400 (Bad Request) if the bien is not valid,
     * or with status 500 (Internal Server Error) if the bien couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/biens")
    @Timed
    public ResponseEntity<Bien> updateBien(@Valid @RequestBody Bien bien) throws URISyntaxException {
        log.debug("REST request to update Bien : {}", bien);
        if (bien.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Bien result = bienService.save(bien);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bien.getId().toString()))
            .body(result);
    }

    /**
     * GET  /biens : get all the biens.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of biens in body
     */
    @GetMapping("/biens")
    @Timed
    public ResponseEntity<List<Bien>> getAllBiens(Pageable pageable) {
        log.debug("REST request to get a page of Biens");
        Page<Bien> page = bienService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/biens");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /biens/:id : get the "id" bien.
     *
     * @param id the id of the bien to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bien, or with status 404 (Not Found)
     */
    @GetMapping("/biens/{id}")
    @Timed
    public ResponseEntity<Bien> getBien(@PathVariable Long id) {
        log.debug("REST request to get Bien : {}", id);
        Optional<Bien> bien = bienService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bien);
    }

    /**
     * DELETE  /biens/:id : delete the "id" bien.
     *
     * @param id the id of the bien to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/biens/{id}")
    @Timed
    public ResponseEntity<Void> deleteBien(@PathVariable Long id) {
        log.debug("REST request to delete Bien : {}", id);
        bienService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
