package fr.gubian.suivichantier.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.gubian.suivichantier.domain.DocumentTuile;
import fr.gubian.suivichantier.service.DocumentTuileService;
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
 * REST controller for managing DocumentTuile.
 */
@RestController
@RequestMapping("/api")
public class DocumentTuileResource {

    private final Logger log = LoggerFactory.getLogger(DocumentTuileResource.class);

    private static final String ENTITY_NAME = "documentTuile";

    private final DocumentTuileService documentTuileService;

    public DocumentTuileResource(DocumentTuileService documentTuileService) {
        this.documentTuileService = documentTuileService;
    }

    /**
     * POST  /document-tuiles : Create a new documentTuile.
     *
     * @param documentTuile the documentTuile to create
     * @return the ResponseEntity with status 201 (Created) and with body the new documentTuile, or with status 400 (Bad Request) if the documentTuile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/document-tuiles")
    @Timed
    public ResponseEntity<DocumentTuile> createDocumentTuile(@Valid @RequestBody DocumentTuile documentTuile) throws URISyntaxException {
        log.debug("REST request to save DocumentTuile : {}", documentTuile);
        if (documentTuile.getId() != null) {
            throw new BadRequestAlertException("A new documentTuile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentTuile result = documentTuileService.save(documentTuile);
        return ResponseEntity.created(new URI("/api/document-tuiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /document-tuiles : Updates an existing documentTuile.
     *
     * @param documentTuile the documentTuile to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated documentTuile,
     * or with status 400 (Bad Request) if the documentTuile is not valid,
     * or with status 500 (Internal Server Error) if the documentTuile couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/document-tuiles")
    @Timed
    public ResponseEntity<DocumentTuile> updateDocumentTuile(@Valid @RequestBody DocumentTuile documentTuile) throws URISyntaxException {
        log.debug("REST request to update DocumentTuile : {}", documentTuile);
        if (documentTuile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentTuile result = documentTuileService.save(documentTuile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, documentTuile.getId().toString()))
            .body(result);
    }

    /**
     * GET  /document-tuiles : get all the documentTuiles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of documentTuiles in body
     */
    @GetMapping("/document-tuiles")
    @Timed
    public ResponseEntity<List<DocumentTuile>> getAllDocumentTuiles(Pageable pageable) {
        log.debug("REST request to get a page of DocumentTuiles");
        Page<DocumentTuile> page = documentTuileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/document-tuiles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /document-tuiles/:id : get the "id" documentTuile.
     *
     * @param id the id of the documentTuile to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the documentTuile, or with status 404 (Not Found)
     */
    @GetMapping("/document-tuiles/{id}")
    @Timed
    public ResponseEntity<DocumentTuile> getDocumentTuile(@PathVariable Long id) {
        log.debug("REST request to get DocumentTuile : {}", id);
        Optional<DocumentTuile> documentTuile = documentTuileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentTuile);
    }

    /**
     * DELETE  /document-tuiles/:id : delete the "id" documentTuile.
     *
     * @param id the id of the documentTuile to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/document-tuiles/{id}")
    @Timed
    public ResponseEntity<Void> deleteDocumentTuile(@PathVariable Long id) {
        log.debug("REST request to delete DocumentTuile : {}", id);
        documentTuileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
