package tender.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tender.domain.PonudePonudjaci;
import tender.domain.Vrednovanje;

/**
 * Spring Data JPA repository for the Vrednovanje entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VrednovanjeRepository extends JpaRepository<Vrednovanje, Long>, JpaSpecificationExecutor<Vrednovanje> {
    @Query(
        value = "SELECT distinct `ponude`.`id` AS `id`,`ponude`.`sifra_postupka` AS `sifra_postupka`,`ponude`.`sifra_ponude` AS `sifra_ponude`,`ponude`.`broj_partije` AS `broj_partije`,`ponude`.`naziv_proizvodjaca` AS `naziv_proizvodjaca`,`ponude`.`zasticeni_naziv` AS `zasticeni_naziv`,`ponude`.`karakteristika` AS `karakteristika`,`ponude`.`ponudjena_vrijednost` AS `ponudjena_vrijednost`,`ponude`.`rok_isporuke` AS `rok_isporuke`,`ponude`.`jedinicna_cijena` AS `jedinicna_cijena`,`ponudjaci`.`naziv_ponudjaca` AS `naziv_ponudjaca`,`specifikacije`.`atc` AS `atc`,`specifikacije`.`trazena_kolicina` AS `trazena_kolicina`,`specifikacije`.`procijenjena_vrijednost` AS `procijenjena_vrijednost`,`postupci`.`vrsta_postupka` AS `vrsta_postupka`,((`postupci`.`kriterijum_cijena` * min(`ponude`.`ponudjena_vrijednost`) OVER (PARTITION BY `ponude`.`broj_partije`,`ponude`.`sifra_postupka` ) ) / `ponude`.`ponudjena_vrijednost`) AS `bod_cijena`,((`postupci`.`drugi_kriterijum` * min(`ponude`.`rok_isporuke`) OVER (PARTITION BY `ponude`.`broj_partije`,`ponude`.`sifra_postupka` ) ) / `ponude`.`rok_isporuke`) AS `bod_rok`,(((`postupci`.`kriterijum_cijena` * min(`ponude`.`ponudjena_vrijednost`) OVER (PARTITION BY `ponude`.`broj_partije`,`ponude`.`sifra_postupka` ) ) / `ponude`.`ponudjena_vrijednost`) + ((`postupci`.`drugi_kriterijum` * min(`ponude`.`rok_isporuke`) OVER (PARTITION BY `ponude`.`broj_partije`,`ponude`.`sifra_postupka` ) ) / `ponude`.`rok_isporuke`)) AS `bod_ukupno` from (((`ponude` join `postupci` on((`ponude`.`sifra_postupka` = `postupci`.`sifra_postupka`))) join `ponudjaci` on((`ponude`.`sifra_ponudjaca` = `ponudjaci`.`id`))) join `specifikacije` on(((`ponude`.`sifra_postupka` = `specifikacije`.`sifra_postupka`) and (`ponude`.`broj_partije` = `specifikacije`.`broj_partije`))))",
        nativeQuery = true
    )
    List<Vrednovanje> findNativeAllVrednovanje();

    @Query(
        value = "SELECT r.*,\n" +
        " AVG(r.bod_ukupno) OVER(PARTITION BY r.broj_partije ORDER BY  r.bod_ukupno DESC)\n" +
        "from( SELECT `ponude`.`id` AS `id`,`ponude`.`sifra_postupka` AS `sifra_postupka`,`ponude`.`sifra_ponude` AS `sifra_ponude`,`ponude`.`broj_partije` AS `broj_partije`,`ponude`.`naziv_proizvodjaca` AS `naziv_proizvodjaca`,`ponude`.`zasticeni_naziv` AS `zasticeni_naziv`,`ponude`.`karakteristika` AS `karakteristika`,`ponude`.`ponudjena_vrijednost` AS `ponudjena_vrijednost`,`ponude`.`rok_isporuke` AS `rok_isporuke`,`ponude`.`jedinicna_cijena` AS `jedinicna_cijena`,`ponudjaci`.`naziv_ponudjaca` AS `naziv_ponudjaca`,`specifikacije`.`atc` AS `atc`,`specifikacije`.`trazena_kolicina` AS `trazena_kolicina`,`specifikacije`.`procijenjena_vrijednost` AS `procijenjena_vrijednost`,`postupci`.`vrsta_postupka` AS `vrsta_postupka`,((`postupci`.`kriterijum_cijena` * min(`ponude`.`ponudjena_vrijednost`) OVER (PARTITION BY `ponude`.`broj_partije`,`ponude`.`sifra_postupka` ) ) / `ponude`.`ponudjena_vrijednost`) AS `bod_cijena`,((`postupci`.`drugi_kriterijum` * min(`ponude`.`rok_isporuke`) OVER (PARTITION BY `ponude`.`broj_partije`,`ponude`.`sifra_postupka` ) ) / `ponude`.`rok_isporuke`) AS `bod_rok`,(((`postupci`.`kriterijum_cijena` * min(`ponude`.`ponudjena_vrijednost`) OVER (PARTITION BY `ponude`.`broj_partije`,`ponude`.`sifra_postupka` ) ) / `ponude`.`ponudjena_vrijednost`) + ((`postupci`.`drugi_kriterijum` * min(`ponude`.`rok_isporuke`) OVER (PARTITION BY `ponude`.`broj_partije`,`ponude`.`sifra_postupka` ) ) / `ponude`.`rok_isporuke`)) AS `bod_ukupno` from (((`ponude` join `postupci` on((`ponude`.`sifra_postupka` = `postupci`.`sifra_postupka`))) join `ponudjaci` on((`ponude`.`sifra_ponudjaca` = `ponudjaci`.`id`))) join `specifikacije` on(((`ponude`.`sifra_postupka` = `specifikacije`.`sifra_postupka`) and (`ponude`.`broj_partije` = `specifikacije`.`broj_partije`))))where ponude.sifra_postupka=:sifraPostupka GROUP BY ponude.id) r",
        nativeQuery = true
    )
    List<Vrednovanje> findBySifraPostupkaList(@Param("sifraPostupka") Integer sifraPostupka);

    @Query(
        value = "SELECT distinct `ponude`.`id` AS `id`,`ponude`.`sifra_postupka` AS `sifra_postupka`,`ponude`.`sifra_ponude` AS `sifra_ponude`,`ponude`.`broj_partije` AS `broj_partije`,`ponude`.`naziv_proizvodjaca` AS `naziv_proizvodjaca`,`ponude`.`zasticeni_naziv` AS `zasticeni_naziv`,`ponude`.`karakteristika` AS `karakteristika`,`ponude`.`ponudjena_vrijednost` AS `ponudjena_vrijednost`,`ponude`.`rok_isporuke` AS `rok_isporuke`,`ponude`.`jedinicna_cijena` AS `jedinicna_cijena`,`ponudjaci`.`naziv_ponudjaca` AS `naziv_ponudjaca`,`specifikacije`.`atc` AS `atc`,`specifikacije`.`trazena_kolicina` AS `trazena_kolicina`,`specifikacije`.`procijenjena_vrijednost` AS `procijenjena_vrijednost`,`postupci`.`vrsta_postupka` AS `vrsta_postupka`,((`postupci`.`kriterijum_cijena` * min(`ponude`.`ponudjena_vrijednost`) OVER (PARTITION BY `ponude`.`broj_partije`,`ponude`.`sifra_postupka` ) ) / `ponude`.`ponudjena_vrijednost`) AS `bod_cijena`,((`postupci`.`drugi_kriterijum` * min(`ponude`.`rok_isporuke`) OVER (PARTITION BY `ponude`.`broj_partije`,`ponude`.`sifra_postupka` ) ) / `ponude`.`rok_isporuke`) AS `bod_rok`,(((`postupci`.`kriterijum_cijena` * min(`ponude`.`ponudjena_vrijednost`) OVER (PARTITION BY `ponude`.`broj_partije`,`ponude`.`sifra_postupka` ) ) / `ponude`.`ponudjena_vrijednost`) + ((`postupci`.`drugi_kriterijum` * min(`ponude`.`rok_isporuke`) OVER (PARTITION BY `ponude`.`broj_partije`,`ponude`.`sifra_postupka` ) ) / `ponude`.`rok_isporuke`)) AS `bod_ukupno` from (((`ponude` join `postupci` on((`ponude`.`sifra_postupka` = `postupci`.`sifra_postupka`))) join `ponudjaci` on((`ponude`.`sifra_ponudjaca` = `ponudjaci`.`id`))) join `specifikacije` on(((`ponude`.`sifra_postupka` = `specifikacije`.`sifra_postupka`) and (`ponude`.`broj_partije` = `specifikacije`.`broj_partije`))))where ponude.sifra_ponude like CONCAT('%', :sifraPonude, '%')",
        nativeQuery = true
    )
    List<Vrednovanje> findBySifraPonudeList(@Param("sifraPonude") Integer sifraPonude);

    @Query(
        value = "SELECT  * FROM (SELECT distinct ponude.*,`naziv_ponudjaca` AS `naziv_ponudjaca`,`specifikacije`.`atc` AS `atc`,`specifikacije`.`trazena_kolicina` AS `trazena_kolicina`,`specifikacije`.`procijenjena_vrijednost` AS `procijenjena_vrijednost`,`postupci`.`vrsta_postupka` AS `vrsta_postupka`,((`postupci`.`kriterijum_cijena` * min(`ponude`.`ponudjena_vrijednost`) OVER (PARTITION BY `ponude`.`broj_partije`,`ponude`.`sifra_postupka` ) ) / `ponude`.`ponudjena_vrijednost`) AS `bod_cijena`,((`postupci`.`drugi_kriterijum` * min(`ponude`.`rok_isporuke`) OVER (PARTITION BY `ponude`.`broj_partije`,`ponude`.`sifra_postupka` ) ) / `ponude`.`rok_isporuke`) AS `bod_rok`,(((`postupci`.`kriterijum_cijena` * min(`ponude`.`ponudjena_vrijednost`) OVER (PARTITION BY `ponude`.`broj_partije`,`ponude`.`sifra_postupka` ) ) / `ponude`.`ponudjena_vrijednost`) + ((`postupci`.`drugi_kriterijum` * min(`ponude`.`rok_isporuke`) OVER (PARTITION BY `ponude`.`broj_partije`,`ponude`.`sifra_postupka` ) ) / `ponude`.`rok_isporuke`)) AS `bod_ukupno`,ROW_NUMBER() over(partition BY ponude.sifra_ponude\n" +
        " ORDER BY  ponude.id DESC)rn from (((`ponude` join `postupci` on((`ponude`.`sifra_postupka` = `postupci`.`sifra_postupka`))) join `ponudjaci` on((`ponude`.`sifra_ponudjaca` = `ponudjaci`.`id`))) join `specifikacije` on(((`ponude`.`sifra_postupka` = `specifikacije`.`sifra_postupka`)\n" +
        " and (`ponude`.`broj_partije` = `specifikacije`.`broj_partije`\n" +
        "      )))) WHERE ponude.sifra_postupka=:sifra)r WHERE rn=1",
        nativeQuery = true
    )
    List<Vrednovanje> findBySifraPostupkaPonudjaci(@Param("sifra") Integer sifra);
}
