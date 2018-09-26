package fr.gubian.suivichantier.service;

import fr.gubian.suivichantier.domain.Contact;
import fr.gubian.suivichantier.repository.ContactRepository;
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
 * Service Implementation for managing Contact.
 */
@Service
@Transactional
public class ContactService {

    private final Logger log = LoggerFactory.getLogger(ContactService.class);

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    /**
     * Save a contact.
     *
     * @param contact the entity to save
     * @return the persisted entity
     */
    public Contact save(Contact contact) {
        log.debug("Request to save Contact : {}", contact);        return contactRepository.save(contact);
    }

    /**
     * Get all the contacts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Contact> findAll(Pageable pageable) {
        log.debug("Request to get all Contacts");
        return contactRepository.findAll(pageable);
    }



    /**
     *  get all the contacts where ChantierIntervenant is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Contact> findAllWhereChantierIntervenantIsNull() {
        log.debug("Request to get all contacts where ChantierIntervenant is null");
        return StreamSupport
            .stream(contactRepository.findAll().spliterator(), false)
            .filter(contact -> contact.getChantierIntervenant() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one contact by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Contact> findOne(Long id) {
        log.debug("Request to get Contact : {}", id);
        return contactRepository.findById(id);
    }

    /**
     * Delete the contact by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Contact : {}", id);
        contactRepository.deleteById(id);
    }
}