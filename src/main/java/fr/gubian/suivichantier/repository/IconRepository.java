package fr.gubian.suivichantier.repository;

import fr.gubian.suivichantier.domain.Icon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Icon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IconRepository extends JpaRepository<Icon, Long> {

}
