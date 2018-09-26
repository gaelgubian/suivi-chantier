package fr.gubian.suivichantier.service;

import fr.gubian.suivichantier.domain.Visite;
import fr.gubian.suivichantier.repository.VisiteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Visite.
 */
@Service
@Transactional
public class VisiteService {

    private final Logger log = LoggerFactory.getLogger(VisiteService.class);

    private final VisiteRepository visiteRepository;

    public VisiteService(VisiteRepository visiteRepository) {
        this.visiteRepository = visiteRepository;
    }

    /**
     * Save a visite.
     *
     * @param visite the entity to save
     * @return the persisted entity
     */
    public Visite save(Visite visite) {
        log.debug("Request to save Visite : {}", visite);        return visiteRepository.save(visite);
    }

    /**
     * Get all the visites.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Visite> findAll(Pageable pageable) {
        log.debug("Request to get all Visites");
        return visiteRepository.findAll(pageable);
    }


    /**
     * Get one visite by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Visite> findOne(Long id) {
        log.debug("Request to get Visite : {}", id);
        return visiteRepository.findById(id);
    }

    /**
     * Delete the visite by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Visite : {}", id);
        visiteRepository.deleteById(id);
    }
}
