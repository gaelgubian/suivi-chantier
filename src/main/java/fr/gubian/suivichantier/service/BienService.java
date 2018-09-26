package fr.gubian.suivichantier.service;

import fr.gubian.suivichantier.domain.Bien;
import fr.gubian.suivichantier.repository.BienRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Bien.
 */
@Service
@Transactional
public class BienService {

    private final Logger log = LoggerFactory.getLogger(BienService.class);

    private final BienRepository bienRepository;

    public BienService(BienRepository bienRepository) {
        this.bienRepository = bienRepository;
    }

    /**
     * Save a bien.
     *
     * @param bien the entity to save
     * @return the persisted entity
     */
    public Bien save(Bien bien) {
        log.debug("Request to save Bien : {}", bien);        return bienRepository.save(bien);
    }

    /**
     * Get all the biens.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Bien> findAll(Pageable pageable) {
        log.debug("Request to get all Biens");
        return bienRepository.findAll(pageable);
    }


    /**
     * Get one bien by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Bien> findOne(Long id) {
        log.debug("Request to get Bien : {}", id);
        return bienRepository.findById(id);
    }

    /**
     * Delete the bien by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Bien : {}", id);
        bienRepository.deleteById(id);
    }
}
