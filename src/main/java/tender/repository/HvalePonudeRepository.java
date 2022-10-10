package tender.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tender.domain.HvalePonude;

/**
 * Spring Data SQL repository for the HvalePonude entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HvalePonudeRepository extends JpaRepository<HvalePonude, Long> {
    @Query(
        value = "SELECT \n" +
        "          specifikacije.id, \n" +
        "          specifikacije.broj_partije, \n" +
        "          specifikacije.inn, \n" +
        "          specifikacije.farmaceutski_oblik_lijeka, \n" +
        "          specifikacije.pakovanje, \n" +
        "          specifikacije.trazena_kolicina, \n" +
        "          specifikacije.procijenjena_vrijednost, \n" +
        "          specifikacije.sifra_postupka \n" +
        "        FROM specifikacije \n" +
        "        WHERE\n" +
        "          (\n" +
        "            (specifikacije.sifra_postupka =:sifra) AND\n" +
        "            (\n" +
        "              NOT (\n" +
        "                specifikacije.broj_partije IN\n" +
        "\t\t\t\t\t\t\t\t(SELECT\n" +
        "                    \n" +
        "                      view_vrednovanje.broj_partije\n" +
        "                      \n" +
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
        "                          )WHERE VIEW_vrednovanje.sifra_postupka=:sifra\n" +
        " \n" +
        "  )\n" +
        "              )\n" +
        "            )\n" +
        "          )",
        nativeQuery = true
    )
    List<HvalePonude> HvalePonude(@Param("sifra") Integer sifra);
}
