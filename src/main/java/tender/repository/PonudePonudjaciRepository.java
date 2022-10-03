package tender.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tender.domain.Ponude;
import tender.domain.PonudePonudjaci;

/**
 * Spring Data JPA repository for the PonudePonudjaci entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PonudePonudjaciRepository extends JpaRepository<PonudePonudjaci, Long>, JpaSpecificationExecutor<PonudePonudjaci> {
    @Query(
        value = "SELECT distinct `ponude`.*,`ponudjaci`.`naziv_ponudjaca` AS `naziv_ponudjaca`,`specifikacije`.*,`postupci`.`vrsta_postupka` AS `vrsta_postupka`,((`postupci`.`kriterijum_cijena` * min(`ponude`.`ponudjena_vrijednost`) OVER (PARTITION BY `ponude`.`broj_partije`,`ponude`.`sifra_postupka` ) ) / `ponude`.`ponudjena_vrijednost`) AS `bod_cijena`,((`postupci`.`drugi_kriterijum` * min(`ponude`.`rok_isporuke`) OVER (PARTITION BY `ponude`.`broj_partije`,`ponude`.`sifra_postupka` ) ) / `ponude`.`rok_isporuke`) AS `bod_rok`,(((`postupci`.`kriterijum_cijena` * min(`ponude`.`ponudjena_vrijednost`) OVER (PARTITION BY `ponude`.`broj_partije`,`ponude`.`sifra_postupka` ) ) / `ponude`.`ponudjena_vrijednost`) + ((`postupci`.`drugi_kriterijum` * min(`ponude`.`rok_isporuke`) OVER (PARTITION BY `ponude`.`broj_partije`,`ponude`.`sifra_postupka` ) ) / `ponude`.`rok_isporuke`)) AS `bod_ukupno` from (((`ponude` join `postupci` on((`ponude`.`sifra_postupka` = `postupci`.`sifra_postupka`))) join `ponudjaci` on((`ponude`.`sifra_ponudjaca` = `ponudjaci`.`id`))) join `specifikacije` on(((`ponude`.`sifra_postupka` = `specifikacije`.`sifra_postupka`) and (`ponude`.`broj_partije` = `specifikacije`.`broj_partije`))))",
        nativeQuery = true
    )
    List<PonudePonudjaci> findNativeAll();

    @Query(
        value = "SELECT distinct `ponude`.*,`ponudjaci`.`naziv_ponudjaca` AS `naziv_ponudjaca`,`specifikacije`.*,`postupci`.`vrsta_postupka` AS `vrsta_postupka`,((`postupci`.`kriterijum_cijena` * min(`ponude`.`ponudjena_vrijednost`) OVER (PARTITION BY `ponude`.`broj_partije`,`ponude`.`sifra_postupka` ) ) / `ponude`.`ponudjena_vrijednost`) AS `bod_cijena`,((`postupci`.`drugi_kriterijum` * min(`ponude`.`rok_isporuke`) OVER (PARTITION BY `ponude`.`broj_partije`,`ponude`.`sifra_postupka` ) ) / `ponude`.`rok_isporuke`) AS `bod_rok`,(((`postupci`.`kriterijum_cijena` * min(`ponude`.`ponudjena_vrijednost`) OVER (PARTITION BY `ponude`.`broj_partije`,`ponude`.`sifra_postupka` ) ) / `ponude`.`ponudjena_vrijednost`) + ((`postupci`.`drugi_kriterijum` * min(`ponude`.`rok_isporuke`) OVER (PARTITION BY `ponude`.`broj_partije`,`ponude`.`sifra_postupka` ) ) / `ponude`.`rok_isporuke`)) AS `bod_ukupno` from (((`ponude` join `postupci` on((`ponude`.`sifra_postupka` = `postupci`.`sifra_postupka`))) join `ponudjaci` on((`ponude`.`sifra_ponudjaca` = `ponudjaci`.`id`))) join `specifikacije` on(((`ponude`.`sifra_postupka` = `specifikacije`.`sifra_postupka`) and (`ponude`.`broj_partije` = `specifikacije`.`broj_partije`))))" +
        "where ponude.sifra_postupka=:sifraPostupka",
        nativeQuery = true
    )
    List<PonudePonudjaci> findBySifraPostupkaList(@Param("sifraPostupka") Integer sifraPostupka);
}
