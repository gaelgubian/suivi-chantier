package fr.gubian.suivichantier.service;

import fr.gubian.suivichantier.domain.Adresse;
import fr.gubian.suivichantier.repository.AdresseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Adresse.
 */
@Service
@Transactional
public class AdresseService {

    private final Logger log = LoggerFactory.getLogger(AdresseService.class);

    private final AdresseRepository adresseRepository;

    public AdresseService(AdresseRepository adresseRepository) {
        this.adresseRepository = adresseRepository;
    }

    /**
     * Save a adresse.
     *
     * @param adresse the entity to save
     * @return the persisted entity
     */
    public Adresse save(Adresse adresse) {
        log.debug("Request to save Adresse : {}", adresse);        return adresseRepository.save(adresse);
    }

    /**
     * Get all the adresses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Adresse> findAll(Pageable pageable) {
        log.debug("Request to get all Adresses");
        return adresseRepository.findAll(pageable);
    }


    /**
     * Get one adresse by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Adresse> findOne(Long id) {
        log.debug("Request to get Adresse : {}", id);
        return adresseRepository.findById(id);
    }

    /**
     * Delete the adresse by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Adresse : {}", id);
        adresseRepository.deleteById(id);
    }
}
