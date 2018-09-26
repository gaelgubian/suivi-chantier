package fr.gubian.suivichantier.service;

import fr.gubian.suivichantier.domain.ChantierIntervenant;
import fr.gubian.suivichantier.repository.ChantierIntervenantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing ChantierIntervenant.
 */
@Service
@Transactional
public class ChantierIntervenantService {

    private final Logger log = LoggerFactory.getLogger(ChantierIntervenantService.class);

    private final ChantierIntervenantRepository chantierIntervenantRepository;

    public ChantierIntervenantService(ChantierIntervenantRepository chantierIntervenantRepository) {
        this.chantierIntervenantRepository = chantierIntervenantRepository;
    }

    /**
     * Save a chantierIntervenant.
     *
     * @param chantierIntervenant the entity to save
     * @return the persisted entity
     */
    public ChantierIntervenant save(ChantierIntervenant chantierIntervenant) {
        log.debug("Request to save ChantierIntervenant : {}", chantierIntervenant);        return chantierIntervenantRepository.save(chantierIntervenant);
    }

    /**
     * Get all the chantierIntervenants.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ChantierIntervenant> findAll(Pageable pageable) {
        log.debug("Request to get all ChantierIntervenants");
        return chantierIntervenantRepository.findAll(pageable);
    }



    /**
     *  get all the chantierIntervenants where Signature is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ChantierIntervenant> findAllWhereSignatureIsNull() {
        log.debug("Request to get all chantierIntervenants where Signature is null");
        return StreamSupport
            .stream(chantierIntervenantRepository.findAll().spliterator(), false)
            .filter(chantierIntervenant -> chantierIntervenant.getSignature() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one chantierIntervenant by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ChantierIntervenant> findOne(Long id) {
        log.debug("Request to get ChantierIntervenant : {}", id);
        return chantierIntervenantRepository.findById(id);
    }

    /**
     * Delete the chantierIntervenant by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ChantierIntervenant : {}", id);
        chantierIntervenantRepository.deleteById(id);
    }
}
