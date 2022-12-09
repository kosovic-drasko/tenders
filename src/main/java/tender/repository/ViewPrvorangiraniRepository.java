package tender.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tender.domain.ViewPrvorangirani;
import tender.domain.ViewVrednovanje;

/**
 * Spring Data JPA repository for the ViewPrvorangirani entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ViewPrvorangiraniRepository extends JpaRepository<ViewPrvorangirani, Long>, JpaSpecificationExecutor<ViewPrvorangirani> {
    @Query("SELECT u FROM ViewPrvorangirani u WHERE u.sifraPostupka = :sifra")
    List<ViewPrvorangirani> findViewPrvorangiraniBySifraPostupka(@Param("sifra") Integer sifraPostupka);
}
