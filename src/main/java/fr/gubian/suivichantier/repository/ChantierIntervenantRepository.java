package fr.gubian.suivichantier.repository;

import fr.gubian.suivichantier.domain.ChantierIntervenant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ChantierIntervenant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChantierIntervenantRepository extends JpaRepository<ChantierIntervenant, Long> {

}
