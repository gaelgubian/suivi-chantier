package fr.gubian.suivichantier.service;

import fr.gubian.suivichantier.domain.Chantier;
import fr.gubian.suivichantier.repository.ChantierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Chantier.
 */
@Service
@Transactional
public class ChantierService {

    private final Logger log = LoggerFactory.getLogger(ChantierService.class);

    private final ChantierRepository chantierRepository;

    public ChantierService(ChantierRepository chantierRepository) {
        this.chantierRepository = chantierRepository;
    }

    /**
     * Save a chantier.
     *
     * @param chantier the entity to save
     * @return the persisted entity
     */
    public Chantier save(Chantier chantier) {
        log.debug("Request to save Chantier : {}", chantier);        return chantierRepository.save(chantier);
    }

    /**
     * Get all the chantiers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Chantier> findAll(Pageable pageable) {
        log.debug("Request to get all Chantiers");
        return chantierRepository.findAll(pageable);
    }


    /**
     * Get one chantier by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Chantier> findOne(Long id) {
        log.debug("Request to get Chantier : {}", id);
        return chantierRepository.findById(id);
    }

    /**
     * Delete the chantier by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Chantier : {}", id);
        chantierRepository.deleteById(id);
    }
}
