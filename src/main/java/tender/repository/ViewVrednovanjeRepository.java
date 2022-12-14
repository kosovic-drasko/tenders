package tender.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tender.domain.ViewVrednovanje;

/**
 * Spring Data JPA repository for the ViewVrednovanje entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ViewVrednovanjeRepository extends JpaRepository<ViewVrednovanje, Long>, JpaSpecificationExecutor<ViewVrednovanje> {
    //    @Query("SELECT u FROM ViewVrednovanje u WHERE u.sifraPostupka = :sifra")
    //    List<ViewVrednovanje> findViewVrednovanjeBySifraPostupka(@Param("sifra") Integer sifraPostupka);
    //
    //    @Query("SELECT u FROM ViewVrednovanje u WHERE u.sifraPonude = :sifra")
    //    List<ViewVrednovanje> findViewVrednovanjeBySifraPonude(@Param("sifra") Integer sifraPonude);

    //    @Query("SELECT u FROM ViewVrednovanje u WHERE u.sifraPonude = :sifra")
    //    List<ViewVrednovanje> findViewVrednovanjeBySifraPonude(@Param("sifra") Integer sifraPonde);
}
