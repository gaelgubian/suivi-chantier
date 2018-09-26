package fr.gubian.suivichantier.service;

import fr.gubian.suivichantier.domain.Icon;
import fr.gubian.suivichantier.repository.IconRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Icon.
 */
@Service
@Transactional
public class IconService {

    private final Logger log = LoggerFactory.getLogger(IconService.class);

    private final IconRepository iconRepository;

    public IconService(IconRepository iconRepository) {
        this.iconRepository = iconRepository;
    }

    /**
     * Save a icon.
     *
     * @param icon the entity to save
     * @return the persisted entity
     */
    public Icon save(Icon icon) {
        log.debug("Request to save Icon : {}", icon);        return iconRepository.save(icon);
    }

    /**
     * Get all the icons.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Icon> findAll(Pageable pageable) {
        log.debug("Request to get all Icons");
        return iconRepository.findAll(pageable);
    }


    /**
     * Get one icon by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Icon> findOne(Long id) {
        log.debug("Request to get Icon : {}", id);
        return iconRepository.findById(id);
    }

    /**
     * Delete the icon by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Icon : {}", id);
        iconRepository.deleteById(id);
    }
}
