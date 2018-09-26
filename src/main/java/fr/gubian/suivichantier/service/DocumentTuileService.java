package fr.gubian.suivichantier.service;

import fr.gubian.suivichantier.domain.DocumentTuile;
import fr.gubian.suivichantier.repository.DocumentTuileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing DocumentTuile.
 */
@Service
@Transactional
public class DocumentTuileService {

    private final Logger log = LoggerFactory.getLogger(DocumentTuileService.class);

    private final DocumentTuileRepository documentTuileRepository;

    public DocumentTuileService(DocumentTuileRepository documentTuileRepository) {
        this.documentTuileRepository = documentTuileRepository;
    }

    /**
     * Save a documentTuile.
     *
     * @param documentTuile the entity to save
     * @return the persisted entity
     */
    public DocumentTuile save(DocumentTuile documentTuile) {
        log.debug("Request to save DocumentTuile : {}", documentTuile);        return documentTuileRepository.save(documentTuile);
    }

    /**
     * Get all the documentTuiles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DocumentTuile> findAll(Pageable pageable) {
        log.debug("Request to get all DocumentTuiles");
        return documentTuileRepository.findAll(pageable);
    }


    /**
     * Get one documentTuile by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DocumentTuile> findOne(Long id) {
        log.debug("Request to get DocumentTuile : {}", id);
        return documentTuileRepository.findById(id);
    }

    /**
     * Delete the documentTuile by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DocumentTuile : {}", id);
        documentTuileRepository.deleteById(id);
    }
}
