create definer = um5vc0o9n32vfv2d@`%` view view_maximalni_bod as
select `wznmozuqssd1n6d0`.`view_vrednovanje`.`broj_partije`    AS `broj_partije`,
`wznmozuqssd1n6d0`.`view_vrednovanje`.`sifra_postupka`  AS `sifra_postupka`,
max(`wznmozuqssd1n6d0`.`view_vrednovanje`.`bod_ukupno`) AS `maximalni_bod`
from `wznmozuqssd1n6d0`.`view_vrednovanje`
group by `wznmozuqssd1n6d0`.`view_vrednovanje`.`broj_partije`, `wznmozuqssd1n6d0`.`view_vrednovanje`.`sifra_postupka`
order by `wznmozuqssd1n6d0`.`view_vrednovanje`.`broj_partije`;

create definer = um5vc0o9n32vfv2d@`%` view view_prvorangirani as
select `wznmozuqssd1n6d0`.`view_vrednovanje`.`id`                      AS `id`,
`wznmozuqssd1n6d0`.`view_vrednovanje`.`sifra_postupka`          AS `sifra_postupka`,
`wznmozuqssd1n6d0`.`view_vrednovanje`.`naziv_ponudjaca`         AS `naziv_ponudjaca`,
`wznmozuqssd1n6d0`.`view_vrednovanje`.`sifra_ponude`            AS `sifra_ponude`,
`wznmozuqssd1n6d0`.`view_vrednovanje`.`broj_partije`            AS `broj_partije`,
`wznmozuqssd1n6d0`.`view_vrednovanje`.`atc`                     AS `atc`,
`wznmozuqssd1n6d0`.`view_vrednovanje`.`trazena_kolicina`        AS `trazena_kolicina`,
`wznmozuqssd1n6d0`.`view_vrednovanje`.`procijenjena_vrijednost` AS `procijenjena_vrijednost`,
`wznmozuqssd1n6d0`.`view_vrednovanje`.`naziv_proizvodjaca`      AS `naziv_proizvodjaca`,
`wznmozuqssd1n6d0`.`view_vrednovanje`.`zasticeni_naziv`         AS `zasticeni_naziv`,
`wznmozuqssd1n6d0`.`view_vrednovanje`.`jedinicna_cijena`        AS `jedinicna_cijena`,
`wznmozuqssd1n6d0`.`view_vrednovanje`.`ponudjena_vrijednost`    AS `ponudjena_vrijednost`,
`wznmozuqssd1n6d0`.`view_vrednovanje`.`rok_isporuke`            AS `rok_isporuke`,
`wznmozuqssd1n6d0`.`view_vrednovanje`.`vrsta_postupka`          AS `vrsta_postupka`,
`wznmozuqssd1n6d0`.`view_vrednovanje`.`bod_cijena`              AS `bod_cijena`,
`wznmozuqssd1n6d0`.`view_vrednovanje`.`bod_rok`                 AS `bod_rok`,
`wznmozuqssd1n6d0`.`view_vrednovanje`.`bod_ukupno`              AS `bod_ukupno`
from (`wznmozuqssd1n6d0`.`view_vrednovanje` join `wznmozuqssd1n6d0`.`view_maximalni_bod`
on (((`wznmozuqssd1n6d0`.`view_vrednovanje`.`broj_partije` =
`wznmozuqssd1n6d0`.`view_maximalni_bod`.`broj_partije`) and
(`wznmozuqssd1n6d0`.`view_vrednovanje`.`sifra_postupka` =
`wznmozuqssd1n6d0`.`view_maximalni_bod`.`sifra_postupka`) and
(`wznmozuqssd1n6d0`.`view_vrednovanje`.`bod_ukupno` =
`wznmozuqssd1n6d0`.`view_maximalni_bod`.`maximalni_bod`))));

create definer = um5vc0o9n32vfv2d@`%` view view_vrednovanje as
select distinct `wznmozuqssd1n6d0`.`ponude`.`id`                             AS `id`,
`wznmozuqssd1n6d0`.`ponude`.`sifra_postupka`                 AS `sifra_postupka`,
`wznmozuqssd1n6d0`.`ponude`.`sifra_ponude`                   AS `sifra_ponude`,
`wznmozuqssd1n6d0`.`ponude`.`broj_partije`                   AS `broj_partije`,
`wznmozuqssd1n6d0`.`ponude`.`naziv_proizvodjaca`             AS `naziv_proizvodjaca`,
`wznmozuqssd1n6d0`.`ponude`.`zasticeni_naziv`                AS `zasticeni_naziv`,
`wznmozuqssd1n6d0`.`ponude`.`ponudjena_vrijednost`           AS `ponudjena_vrijednost`,
`wznmozuqssd1n6d0`.`ponude`.`rok_isporuke`                   AS `rok_isporuke`,
`wznmozuqssd1n6d0`.`ponude`.`jedinicna_cijena`               AS `jedinicna_cijena`,
`wznmozuqssd1n6d0`.`ponudjaci`.`naziv_ponudjaca`             AS `naziv_ponudjaca`,
`wznmozuqssd1n6d0`.`specifikacije`.`atc`                     AS `atc`,
`wznmozuqssd1n6d0`.`specifikacije`.`trazena_kolicina`        AS `trazena_kolicina`,
`wznmozuqssd1n6d0`.`specifikacije`.`procijenjena_vrijednost` AS `procijenjena_vrijednost`,
`wznmozuqssd1n6d0`.`postupci`.`vrsta_postupka`               AS `vrsta_postupka`,
((`wznmozuqssd1n6d0`.`postupci`.`kriterijum_cijena` *
min(`wznmozuqssd1n6d0`.`ponude`.`ponudjena_vrijednost`)
OVER (PARTITION BY `wznmozuqssd1n6d0`.`ponude`.`broj_partije`,`wznmozuqssd1n6d0`.`ponude`.`sifra_postupka` )) /
`wznmozuqssd1n6d0`.`ponude`.`ponudjena_vrijednost`)         AS `bod_cijena`,
((`wznmozuqssd1n6d0`.`postupci`.`drugi_kriterijum` * min(`wznmozuqssd1n6d0`.`ponude`.`rok_isporuke`)
OVER (PARTITION BY `wznmozuqssd1n6d0`.`ponude`.`broj_partije`,`wznmozuqssd1n6d0`.`ponude`.`sifra_postupka` )) /
`wznmozuqssd1n6d0`.`ponude`.`rok_isporuke`)                 AS `bod_rok`,
(((`wznmozuqssd1n6d0`.`postupci`.`kriterijum_cijena` *
min(`wznmozuqssd1n6d0`.`ponude`.`ponudjena_vrijednost`)
OVER (PARTITION BY `wznmozuqssd1n6d0`.`ponude`.`broj_partije`,`wznmozuqssd1n6d0`.`ponude`.`sifra_postupka` )) /
`wznmozuqssd1n6d0`.`ponude`.`ponudjena_vrijednost`) +
((`wznmozuqssd1n6d0`.`postupci`.`drugi_kriterijum` * min(`wznmozuqssd1n6d0`.`ponude`.`rok_isporuke`)
OVER (PARTITION BY `wznmozuqssd1n6d0`.`ponude`.`broj_partije`,`wznmozuqssd1n6d0`.`ponude`.`sifra_postupka` )) /
`wznmozuqssd1n6d0`.`ponude`.`rok_isporuke`))               AS `bod_ukupno`
from (((`wznmozuqssd1n6d0`.`ponude` join `wznmozuqssd1n6d0`.`postupci`
on ((`wznmozuqssd1n6d0`.`ponude`.`sifra_postupka` =
`wznmozuqssd1n6d0`.`postupci`.`sifra_postupka`))) join `wznmozuqssd1n6d0`.`ponudjaci`
on ((`wznmozuqssd1n6d0`.`ponude`.`sifra_ponudjaca` =
`wznmozuqssd1n6d0`.`ponudjaci`.`id`))) join `wznmozuqssd1n6d0`.`specifikacije`
on (((`wznmozuqssd1n6d0`.`ponude`.`sifra_postupka` = `wznmozuqssd1n6d0`.`specifikacije`.`sifra_postupka`) and
(`wznmozuqssd1n6d0`.`ponude`.`broj_partije` = `wznmozuqssd1n6d0`.`specifikacije`.`broj_partije`))));
