package fr.gubian.suivichantier.repository;

import fr.gubian.suivichantier.domain.DocumentTuile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DocumentTuile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentTuileRepository extends JpaRepository<DocumentTuile, Long> {

}
