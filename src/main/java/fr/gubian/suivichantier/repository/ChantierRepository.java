package fr.gubian.suivichantier.repository;

import fr.gubian.suivichantier.domain.Chantier;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Chantier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChantierRepository extends JpaRepository<Chantier, Long> {

}
