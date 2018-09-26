package fr.gubian.suivichantier.service;

import fr.gubian.suivichantier.domain.Signature;
import fr.gubian.suivichantier.repository.SignatureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Signature.
 */
@Service
@Transactional
public class SignatureService {

    private final Logger log = LoggerFactory.getLogger(SignatureService.class);

    private final SignatureRepository signatureRepository;

    public SignatureService(SignatureRepository signatureRepository) {
        this.signatureRepository = signatureRepository;
    }

    /**
     * Save a signature.
     *
     * @param signature the entity to save
     * @return the persisted entity
     */
    public Signature save(Signature signature) {
        log.debug("Request to save Signature : {}", signature);        return signatureRepository.save(signature);
    }

    /**
     * Get all the signatures.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Signature> findAll(Pageable pageable) {
        log.debug("Request to get all Signatures");
        return signatureRepository.findAll(pageable);
    }


    /**
     * Get one signature by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Signature> findOne(Long id) {
        log.debug("Request to get Signature : {}", id);
        return signatureRepository.findById(id);
    }

    /**
     * Delete the signature by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Signature : {}", id);
        signatureRepository.deleteById(id);
    }
}
