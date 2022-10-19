package tender.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tender.domain.PonudePonudjaci;
import tender.domain.Prvorangirani;
import tender.domain.Vrednovanje;

/**
 * Spring Data JPA repository for the Prvorangirani entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrvorangiraniRepository extends JpaRepository<Prvorangirani, Long>, JpaSpecificationExecutor<Prvorangirani> {
    @Query(
        value = "SELECT \n" +
        "                 *\n" +
        "                  FROM ((SELECT DISTINCT    ponude.id,\n" +
        "                                     ponude.sifra_postupka,\n" +
        "                                     ponude.sifra_ponude,\n" +
        "                                     ponude.broj_partije,\n" +
        "                                     ponude.naziv_proizvodjaca,\n" +
        "                                     ponude.zasticeni_naziv,\n" +
        "                                     ponude.ponudjena_vrijednost,\n" +
        "                                     ponude.rok_isporuke,\n" +
        "                                     ponude.jedinicna_cijena,\n" +
        "                                     ponudjaci.naziv_ponudjaca,\n" +
        "                                     specifikacije.atc,\n" +
        "                                     specifikacije.trazena_kolicina,\n" +
        "                                     specifikacije.procijenjena_vrijednost,\n" +
        "                                     postupci.vrsta_postupka,\n" +
        "                                     (((postupci.kriterijum_cijena) * min(ponude.ponudjena_vrijednost) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.ponudjena_vrijednost) AS bod_cijena,\n" +
        "          ((postupci.drugi_kriterijum * min(ponude.rok_isporuke) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.rok_isporuke) AS bod_rok,\n" +
        "          ((((postupci.kriterijum_cijena) * min(ponude.ponudjena_vrijednost) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.ponudjena_vrijednost) + (((postupci.drugi_kriterijum * min(ponude.rok_isporuke) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.rok_isporuke))) AS bod_ukupno\n" +
        "                  FROM (((ponude\n" +
        "                      JOIN postupci ON ((ponude.sifra_postupka = postupci.sifra_postupka)))\n" +
        "                      JOIN ponudjaci ON ((ponude.sifra_ponudjaca = ponudjaci.id)))\n" +
        "                      JOIN specifikacije ON (((ponude.sifra_postupka = specifikacije.sifra_postupka) AND (ponude.broj_partije = specifikacije.broj_partije)))))) as view_vrednovanje\n" +
        "                      JOIN ( SELECT\n" +
        "                  view_vrednovanje.broj_partije,\n" +
        "                  view_vrednovanje.sifra_postupka,\n" +
        "                  max(view_vrednovanje.bod_ukupno) AS maximalni_bod\n" +
        "                  FROM (SELECT DISTINCT    ponude.id,\n" +
        "                                     ponude.sifra_postupka,\n" +
        "                                     ponude.sifra_ponude,\n" +
        "                                     ponude.broj_partije,\n" +
        "                                     ponude.naziv_proizvodjaca,\n" +
        "                                     ponude.zasticeni_naziv,\n" +
        "                                     ponude.ponudjena_vrijednost,\n" +
        "                                     ponude.rok_isporuke,\n" +
        "                                     ponude.jedinicna_cijena,\n" +
        "                                     ponudjaci.naziv_ponudjaca,\n" +
        "                                     specifikacije.atc,\n" +
        "                                     specifikacije.trazena_kolicina,\n" +
        "                                     specifikacije.procijenjena_vrijednost,\n" +
        "                                     postupci.vrsta_postupka,\n" +
        "                                     (((postupci.kriterijum_cijena) * min(ponude.ponudjena_vrijednost) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.ponudjena_vrijednost) AS bod_cijena,\n" +
        "          ((postupci.drugi_kriterijum * min(ponude.rok_isporuke) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.rok_isporuke) AS bod_rok,\n" +
        "          ((((postupci.kriterijum_cijena) * min(ponude.ponudjena_vrijednost) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.ponudjena_vrijednost) + (((postupci.drugi_kriterijum * min(ponude.rok_isporuke) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.rok_isporuke))) AS bod_ukupno\n" +
        "                  FROM (((ponude\n" +
        "                      JOIN postupci ON ((ponude.sifra_postupka = postupci.sifra_postupka)))\n" +
        "                      JOIN ponudjaci ON ((ponude.sifra_ponudjaca = ponudjaci.id)))\n" +
        "                      JOIN specifikacije ON (((ponude.sifra_postupka = specifikacije.sifra_postupka) AND (ponude.broj_partije = specifikacije.broj_partije)))))as view_vrednovanje\n" +
        "                  GROUP BY\n" +
        "                  view_vrednovanje.broj_partije,\n" +
        "                  view_vrednovanje.sifra_postupka\n" +
        "                  ORDER BY\n" +
        "                  view_vrednovanje.broj_partije) AS view_maximalni_bod ON\n" +
        "                          (\n" +
        "                              (\n" +
        "                                      (view_vrednovanje.broj_partije = view_maximalni_bod.broj_partije) AND\n" +
        "                                      (view_vrednovanje.sifra_postupka = view_maximalni_bod.sifra_postupka) AND\n" +
        "                                      (view_vrednovanje.bod_ukupno = view_maximalni_bod.maximalni_bod)\n" +
        "                              )\n" +
        "                          )",
        nativeQuery = true
    )
    List<Prvorangirani> findNativeAllPrvorangirani();

    @Query(
        value = "SELECT \n" +
        "                 *\n" +
        "                  FROM ((SELECT DISTINCT    ponude.id,\n" +
        "                                     ponude.sifra_postupka,\n" +
        "                                     ponude.sifra_ponude,\n" +
        "                                     ponude.broj_partije,\n" +
        "                                     ponude.naziv_proizvodjaca,\n" +
        "                                     ponude.zasticeni_naziv,\n" +
        "                                     ponude.ponudjena_vrijednost,\n" +
        "                                     ponude.rok_isporuke,\n" +
        "                                     ponude.jedinicna_cijena,\n" +
        "                                     ponudjaci.naziv_ponudjaca,\n" +
        "                                     specifikacije.atc,\n" +
        "                                     specifikacije.trazena_kolicina,\n" +
        "                                     specifikacije.procijenjena_vrijednost,\n" +
        "                                     postupci.vrsta_postupka,\n" +
        "                                     (((postupci.kriterijum_cijena) * min(ponude.ponudjena_vrijednost) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.ponudjena_vrijednost) AS bod_cijena,\n" +
        "          ((postupci.drugi_kriterijum * min(ponude.rok_isporuke) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.rok_isporuke) AS bod_rok,\n" +
        "          ((((postupci.kriterijum_cijena) * min(ponude.ponudjena_vrijednost) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.ponudjena_vrijednost) + (((postupci.drugi_kriterijum * min(ponude.rok_isporuke) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.rok_isporuke))) AS bod_ukupno\n" +
        "                  FROM (((ponude\n" +
        "                      JOIN postupci ON ((ponude.sifra_postupka = postupci.sifra_postupka)))\n" +
        "                      JOIN ponudjaci ON ((ponude.sifra_ponudjaca = ponudjaci.id)))\n" +
        "                      JOIN specifikacije ON (((ponude.sifra_postupka = specifikacije.sifra_postupka) AND (ponude.broj_partije = specifikacije.broj_partije)))))) as view_vrednovanje\n" +
        "                      JOIN ( SELECT\n" +
        "                  view_vrednovanje.broj_partije,\n" +
        "                  view_vrednovanje.sifra_postupka,\n" +
        "                  max(view_vrednovanje.bod_ukupno) AS maximalni_bod\n" +
        "                  FROM (SELECT DISTINCT    ponude.id,\n" +
        "                                     ponude.sifra_postupka,\n" +
        "                                     ponude.sifra_ponude,\n" +
        "                                     ponude.broj_partije,\n" +
        "                                     ponude.naziv_proizvodjaca,\n" +
        "                                     ponude.zasticeni_naziv,\n" +
        "                                     ponude.ponudjena_vrijednost,\n" +
        "                                     ponude.rok_isporuke,\n" +
        "                                     ponude.jedinicna_cijena,\n" +
        "                                     ponudjaci.naziv_ponudjaca,\n" +
        "                                     specifikacije.atc,\n" +
        "                                     specifikacije.trazena_kolicina,\n" +
        "                                     specifikacije.procijenjena_vrijednost,\n" +
        "                                     postupci.vrsta_postupka,\n" +
        "                                     (((postupci.kriterijum_cijena) * min(ponude.ponudjena_vrijednost) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.ponudjena_vrijednost) AS bod_cijena,\n" +
        "          ((postupci.drugi_kriterijum * min(ponude.rok_isporuke) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.rok_isporuke) AS bod_rok,\n" +
        "          ((((postupci.kriterijum_cijena) * min(ponude.ponudjena_vrijednost) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.ponudjena_vrijednost) + (((postupci.drugi_kriterijum * min(ponude.rok_isporuke) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.rok_isporuke))) AS bod_ukupno\n" +
        "                  FROM (((ponude\n" +
        "                      JOIN postupci ON ((ponude.sifra_postupka = postupci.sifra_postupka)))\n" +
        "                      JOIN ponudjaci ON ((ponude.sifra_ponudjaca = ponudjaci.id)))\n" +
        "                      JOIN specifikacije ON (((ponude.sifra_postupka = specifikacije.sifra_postupka) AND (ponude.broj_partije = specifikacije.broj_partije)))))as view_vrednovanje\n" +
        "                  GROUP BY\n" +
        "                  view_vrednovanje.broj_partije,\n" +
        "                  view_vrednovanje.sifra_postupka\n" +
        "                  ORDER BY\n" +
        "                  view_vrednovanje.broj_partije) AS view_maximalni_bod ON\n" +
        "                          (\n" +
        "                              (\n" +
        "                                      (view_vrednovanje.broj_partije = view_maximalni_bod.broj_partije) AND\n" +
        "                                      (view_vrednovanje.sifra_postupka = view_maximalni_bod.sifra_postupka) AND\n" +
        "                                      (view_vrednovanje.bod_ukupno = view_maximalni_bod.maximalni_bod)\n" +
        "                              )\n" +
        "                          ) where view_vrednovanje.sifra_postupka=:sifraPostupka",
        nativeQuery = true
    )
    List<Prvorangirani> findBySifraPostupkaListPrvorangirani(@Param("sifraPostupka") Integer sifraPostupka);

    @Query(
        value = "SELECT\n" +
        "                view_vrednovanje.id,\n" +
        "                view_vrednovanje.sifra_postupka,\n" +
        "                view_vrednovanje.naziv_ponudjaca,\n" +
        "                view_vrednovanje.sifra_ponude,\n" +
        "                view_vrednovanje.broj_partije,\n" +
        "                view_vrednovanje.atc,\n" +
        "                view_vrednovanje.trazena_kolicina,\n" +
        "                view_vrednovanje.procijenjena_vrijednost,\n" +
        "                view_vrednovanje.naziv_proizvodjaca,\n" +
        "                view_vrednovanje.zasticeni_naziv,\n" +
        "                view_vrednovanje.jedinicna_cijena,\n" +
        "                view_vrednovanje.ponudjena_vrijednost,\n" +
        "                view_vrednovanje.rok_isporuke,\n" +
        "                view_vrednovanje.vrsta_postupka,\n" +
        "                view_vrednovanje.bod_cijena,\n" +
        "                view_vrednovanje.bod_rok,\n" +
        "                view_vrednovanje.bod_ukupno\n" +
        "            FROM ((SELECT DISTINCT    ponude.id,\n" +
        "                               ponude.sifra_postupka,\n" +
        "                               ponude.sifra_ponude,\n" +
        "                               ponude.broj_partije,\n" +
        "                               ponude.naziv_proizvodjaca,\n" +
        "                               ponude.zasticeni_naziv,\n" +
        "                               ponude.ponudjena_vrijednost,\n" +
        "                               ponude.rok_isporuke,\n" +
        "                               ponude.jedinicna_cijena,\n" +
        "                               ponudjaci.naziv_ponudjaca,\n" +
        "                               specifikacije.atc,\n" +
        "                               specifikacije.trazena_kolicina,\n" +
        "                               specifikacije.procijenjena_vrijednost,\n" +
        "                               postupci.vrsta_postupka,\n" +
        "                               (((postupci.kriterijum_cijena) * min(ponude.ponudjena_vrijednost) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.ponudjena_vrijednost) AS bod_cijena,\n" +
        "    ((postupci.drugi_kriterijum * min(ponude.rok_isporuke) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.rok_isporuke) AS bod_rok,\n" +
        "    ((((postupci.kriterijum_cijena) * min(ponude.ponudjena_vrijednost) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.ponudjena_vrijednost) + (((postupci.drugi_kriterijum * min(ponude.rok_isporuke) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.rok_isporuke))) AS bod_ukupno\n" +
        "            FROM (((ponude\n" +
        "                JOIN postupci ON ((ponude.sifra_postupka = postupci.sifra_postupka)))\n" +
        "                JOIN ponudjaci ON ((ponude.sifra_ponudjaca = ponudjaci.id)))\n" +
        "                JOIN specifikacije ON (((ponude.sifra_postupka = specifikacije.sifra_postupka) AND (ponude.broj_partije = specifikacije.broj_partije)))))) as view_vrednovanje\n" +
        "                JOIN ( SELECT\n" +
        "            view_vrednovanje.broj_partije,\n" +
        "            view_vrednovanje.sifra_postupka,\n" +
        "            max(view_vrednovanje.bod_ukupno) AS maximalni_bod\n" +
        "            FROM (SELECT DISTINCT    ponude.id,\n" +
        "                               ponude.sifra_postupka,\n" +
        "                               ponude.sifra_ponude,\n" +
        "                               ponude.broj_partije,\n" +
        "                               ponude.naziv_proizvodjaca,\n" +
        "                               ponude.zasticeni_naziv,\n" +
        "                               ponude.ponudjena_vrijednost,\n" +
        "                               ponude.rok_isporuke,\n" +
        "                               ponude.jedinicna_cijena,\n" +
        "                               ponudjaci.naziv_ponudjaca,\n" +
        "                               specifikacije.atc,\n" +
        "                               specifikacije.trazena_kolicina,\n" +
        "                               specifikacije.procijenjena_vrijednost,\n" +
        "                               postupci.vrsta_postupka,\n" +
        "                               (((postupci.kriterijum_cijena) * min(ponude.ponudjena_vrijednost) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.ponudjena_vrijednost) AS bod_cijena,\n" +
        "    ((postupci.drugi_kriterijum * min(ponude.rok_isporuke) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.rok_isporuke) AS bod_rok,\n" +
        "    ((((postupci.kriterijum_cijena) * min(ponude.ponudjena_vrijednost) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.ponudjena_vrijednost) + (((postupci.drugi_kriterijum * min(ponude.rok_isporuke) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.rok_isporuke))) AS bod_ukupno\n" +
        "            FROM (((ponude\n" +
        "                JOIN postupci ON ((ponude.sifra_postupka = postupci.sifra_postupka)))\n" +
        "                JOIN ponudjaci ON ((ponude.sifra_ponudjaca = ponudjaci.id)))\n" +
        "                JOIN specifikacije ON (((ponude.sifra_postupka = specifikacije.sifra_postupka) AND (ponude.broj_partije = specifikacije.broj_partije)))))as view_vrednovanje\n" +
        "            GROUP BY\n" +
        "            view_vrednovanje.broj_partije,\n" +
        "            view_vrednovanje.sifra_postupka\n" +
        "            ORDER BY\n" +
        "            view_vrednovanje.broj_partije) AS view_maximalni_bod ON\n" +
        "                    (\n" +
        "                        (\n" +
        "                                (view_vrednovanje.broj_partije = view_maximalni_bod.broj_partije) AND\n" +
        "                                (view_vrednovanje.sifra_postupka = view_maximalni_bod.sifra_postupka) AND\n" +
        "                                (view_vrednovanje.bod_ukupno = view_maximalni_bod.maximalni_bod)\n " +
        "                            )\n" +
        "                        ) where view_vrednovanje.sifra_ponude like CONCAT('%', :sifraPonude, '%')",
        nativeQuery = true
    )
    List<Prvorangirani> findBySifraPonudeListPrvorangirani(@Param("sifraPonude") Integer sifraPonude);

    @Query(
        value = "SELECT\n" +
        "                      view_vrednovanje.id,\n" +
        "                      view_vrednovanje.sifra_postupka,\n" +
        "                      view_vrednovanje.naziv_ponudjaca,\n" +
        "                      view_vrednovanje.sifra_ponude,\n" +
        "                      view_vrednovanje.broj_partije,\n" +
        "                      view_vrednovanje.atc,\n" +
        "                      view_vrednovanje.trazena_kolicina,\n" +
        "                      view_vrednovanje.procijenjena_vrijednost,\n" +
        "                      view_vrednovanje.naziv_proizvodjaca,\n" +
        "                      view_vrednovanje.zasticeni_naziv,\n" +
        "                      view_vrednovanje.jedinicna_cijena,\n" +
        "                      view_vrednovanje.ponudjena_vrijednost,\n" +
        "                      view_vrednovanje.rok_isporuke,\n" +
        "                      view_vrednovanje.vrsta_postupka,\n" +
        "                      view_vrednovanje.bod_cijena,\n" +
        "                      view_vrednovanje.bod_rok,\n" +
        "                      view_vrednovanje.bod_ukupno\n" +
        "                  FROM ((SELECT DISTINCT    ponude.id,\n" +
        "                                     ponude.sifra_postupka,\n" +
        "                                     ponude.sifra_ponude,\n" +
        "                                     ponude.broj_partije,\n" +
        "                                     ponude.naziv_proizvodjaca,\n" +
        "                                     ponude.zasticeni_naziv,\n" +
        "                                     ponude.ponudjena_vrijednost,\n" +
        "                                     ponude.rok_isporuke,\n" +
        "                                     ponude.jedinicna_cijena,\n" +
        "                                     ponudjaci.naziv_ponudjaca,\n" +
        "                                     specifikacije.atc,\n" +
        "                                     specifikacije.trazena_kolicina,\n" +
        "                                     specifikacije.procijenjena_vrijednost,\n" +
        "                                     postupci.vrsta_postupka,\n" +
        "                                     (((postupci.kriterijum_cijena) * min(ponude.ponudjena_vrijednost) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.ponudjena_vrijednost) AS bod_cijena,\n" +
        "          ((postupci.drugi_kriterijum * min(ponude.rok_isporuke) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.rok_isporuke) AS bod_rok,\n" +
        "          ((((postupci.kriterijum_cijena) * min(ponude.ponudjena_vrijednost) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.ponudjena_vrijednost) + (((postupci.drugi_kriterijum * min(ponude.rok_isporuke) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.rok_isporuke))) AS bod_ukupno\n" +
        "                  FROM (((ponude\n" +
        "                      JOIN postupci ON ((ponude.sifra_postupka = postupci.sifra_postupka)))\n" +
        "                      JOIN ponudjaci ON ((ponude.sifra_ponudjaca = ponudjaci.id)))\n" +
        "                      JOIN specifikacije ON (((ponude.sifra_postupka = specifikacije.sifra_postupka) AND (ponude.broj_partije = specifikacije.broj_partije)))))) as view_vrednovanje\n" +
        "                      JOIN ( SELECT\n" +
        "                  view_vrednovanje.broj_partije,\n" +
        "                  view_vrednovanje.sifra_postupka,\n" +
        "                  max(view_vrednovanje.bod_ukupno) AS maximalni_bod\n" +
        "                  FROM (SELECT DISTINCT    ponude.id,\n" +
        "                                     ponude.sifra_postupka,\n" +
        "                                     ponude.sifra_ponude,\n" +
        "                                     ponude.broj_partije,\n" +
        "                                     ponude.naziv_proizvodjaca,\n" +
        "                                     ponude.zasticeni_naziv,\n" +
        "                                     ponude.ponudjena_vrijednost,\n" +
        "                                     ponude.rok_isporuke,\n" +
        "                                     ponude.jedinicna_cijena,\n" +
        "                                     ponudjaci.naziv_ponudjaca,\n" +
        "                                     specifikacije.atc,\n" +
        "                                     specifikacije.trazena_kolicina,\n" +
        "                                     specifikacije.procijenjena_vrijednost,\n" +
        "                                     postupci.vrsta_postupka,\n" +
        "                                     (((postupci.kriterijum_cijena) * min(ponude.ponudjena_vrijednost) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.ponudjena_vrijednost) AS bod_cijena,\n" +
        "          ((postupci.drugi_kriterijum * min(ponude.rok_isporuke) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.rok_isporuke) AS bod_rok,\n" +
        "          ((((postupci.kriterijum_cijena) * min(ponude.ponudjena_vrijednost) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.ponudjena_vrijednost) + (((postupci.drugi_kriterijum * min(ponude.rok_isporuke) OVER (PARTITION BY ponude.broj_partije, ponude.sifra_postupka)) / ponude.rok_isporuke))) AS bod_ukupno\n" +
        "                  FROM (((ponude\n" +
        "                      JOIN postupci ON ((ponude.sifra_postupka = postupci.sifra_postupka)))\n" +
        "                      JOIN ponudjaci ON ((ponude.sifra_ponudjaca = ponudjaci.id)))\n" +
        "                      JOIN specifikacije ON (((ponude.sifra_postupka = specifikacije.sifra_postupka) AND (ponude.broj_partije = specifikacije.broj_partije)))))as view_vrednovanje\n" +
        "                  GROUP BY\n" +
        "                  view_vrednovanje.broj_partije,\n" +
        "                  view_vrednovanje.sifra_postupka\n" +
        "                  ORDER BY\n" +
        "                  view_vrednovanje.broj_partije) AS view_maximalni_bod ON\n" +
        "                          (\n" +
        "                              (\n" +
        "                                      (view_vrednovanje.broj_partije = view_maximalni_bod.broj_partije) AND\n" +
        "                                      (view_vrednovanje.sifra_postupka = view_maximalni_bod.sifra_postupka) AND\n" +
        "                                      (view_vrednovanje.bod_ukupno = view_maximalni_bod.maximalni_bod)\n" +
        "                              )\n" +
        "                          ) WHERE view_vrednovanje.sifra_postupka=:sifra\n" +
        "  \n" +
        "  ",
        nativeQuery = true
    )
    List<Prvorangirani> findBySifraPostupkaPonudjaci(@Param("sifra") Integer sifra);
}
