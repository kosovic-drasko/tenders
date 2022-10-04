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
        value = "\tSELECT distinct \n" +
        "\t  ponude.id,\n" +
        "\t  ponude.sifra_postupka,\n" +
        "\t  ponude.sifra_ponude,\n" +
        "\t  ponude.broj_partije,\n" +
        "\t  ponude.naziv_proizvodjaca,\n" +
        "\t  ponudjaci.naziv_ponudjaca,\n" +
        "\t  ponude.zasticeni_naziv,\n" +
        "\t  ponude.ponudjena_vrijednost,\n" +
        "\t  ponude.rok_isporuke,\n" +
        "\t  ponude.jedinicna_cijena,\n" +
        "\t  ponude.selected,\n" +
        "\t  ponude.sifra_ponudjaca,\n" +
        "\t  ponude.created_by,\n" +
        "\t  ponude.created_date,\n" +
        "\t  ponude.last_modified_by,\n" +
        "\t  ponude.last_modified_date,\n" +
        "\t  specifikacije.trazena_kolicina,\n" +
        "\t  postupci.vrsta_postupka\n" +
        "\tFROM ponude\n" +
        "\t  INNER JOIN ponudjaci\n" +
        "\t    ON ponude.sifra_ponudjaca = ponudjaci.id\n" +
        "\t  INNER JOIN specifikacije\n" +
        "\t    ON ponude.sifra_postupka = specifikacije.sifra_postupka\n" +
        "\t  INNER JOIN postupci\n" +
        "\t    ON specifikacije.sifra_postupka = postupci.sifra_postupka;",
        nativeQuery = true
    )
    List<PonudePonudjaci> findNativeAll();

    @Query(
        value = "\tSELECT\n" +
        "\t  ponude.id,\n" +
        "\t  ponude.sifra_postupka,\n" +
        "\t  ponude.sifra_ponude,\n" +
        "\t  ponude.broj_partije,\n" +
        "\t  ponude.naziv_proizvodjaca,\n" +
        "\t  ponudjaci.naziv_ponudjaca,\n" +
        "\t  ponude.zasticeni_naziv,\n" +
        "\t  ponude.ponudjena_vrijednost,\n" +
        "\t  ponude.rok_isporuke,\n" +
        "\t  ponude.jedinicna_cijena,\n" +
        "\t  ponude.selected,\n" +
        "\t  ponude.sifra_ponudjaca,\n" +
        "\t  ponude.created_by,\n" +
        "\t  ponude.created_date,\n" +
        "\t  ponude.last_modified_by,\n" +
        "\t  ponude.last_modified_date,\n" +
        "\t  specifikacije.trazena_kolicina,\n" +
        "\t  postupci.vrsta_postupka\n" +
        "\tFROM ponude\n" +
        "\t  INNER JOIN ponudjaci\n" +
        "\t    ON ponude.sifra_ponudjaca = ponudjaci.id\n" +
        "\t  INNER JOIN specifikacije\n" +
        "\t    ON ponude.sifra_postupka = specifikacije.sifra_postupka\n" +
        "\t  INNER JOIN postupci\n" +
        "\t    ON specifikacije.sifra_postupka = postupci.sifra_postupka where ponude.sifra_postupka=:sifraPostupka",
        nativeQuery = true
    )
    List<PonudePonudjaci> findBySifraPostupkaList(@Param("sifraPostupka") Integer sifraPostupka);

    @Query(
        value = "SELECT\n" +
        "\t  ponude.id,\n" +
        "\t  ponude.sifra_postupka,\n" +
        "\t  ponude.sifra_ponude,\n" +
        "\t  ponude.broj_partije,\n" +
        "\t  ponude.naziv_proizvodjaca,\n" +
        "\t  ponudjaci.naziv_ponudjaca,\n" +
        "\t  ponude.zasticeni_naziv,\n" +
        "\t  ponude.ponudjena_vrijednost,\n" +
        "\t  ponude.rok_isporuke,\n" +
        "\t  ponude.jedinicna_cijena,\n" +
        "\t  ponude.selected,\n" +
        "\t  ponude.sifra_ponudjaca,\n" +
        "\t  ponude.created_by,\n" +
        "\t  ponude.created_date,\n" +
        "\t  ponude.last_modified_by,\n" +
        "\t  ponude.last_modified_date,\n" +
        "\t  specifikacije.trazena_kolicina,\n" +
        "\t  postupci.vrsta_postupka\n" +
        "\tFROM ponude\n" +
        "\t  INNER JOIN ponudjaci\n" +
        "\t    ON ponude.sifra_ponudjaca = ponudjaci.id\n" +
        "\t  INNER JOIN specifikacije\n" +
        "\t    ON ponude.sifra_postupka = specifikacije.sifra_postupka\n" +
        "\t  INNER JOIN postupci\n" +
        "\t    ON specifikacije.sifra_postupka = postupci.sifra_postupka where ponude.sifra_ponude like CONCAT('%', :sifraPonude, '%')",
        nativeQuery = true
    )
    List<PonudePonudjaci> findBySifraPonudeList(@Param("sifraPonude") Integer sifraPonude);
}
