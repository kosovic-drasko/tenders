package tender.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tender.domain.PonudePonudjaci;

/**
 * Spring Data JPA repository for the PonudePonudjaci entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PonudePonudjaciRepository extends JpaRepository<PonudePonudjaci, Long>, JpaSpecificationExecutor<PonudePonudjaci> {}
