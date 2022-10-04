package tender.web.rest;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.ResponseUtil;
import tender.domain.Prvorangirani;
import tender.repository.PrvorangiraniRepository;
import tender.service.PrvorangiraniQueryService;
import tender.service.PrvorangiraniService;
import tender.service.criteria.PrvorangiraniCriteria;

/**
 * REST controller for managing {@link tender.domain.Prvorangirani}.
 */
@RestController
@RequestMapping("/api")
public class PrvorangiraniResource {

    private final Logger log = LoggerFactory.getLogger(PrvorangiraniResource.class);

    private final PrvorangiraniService prvorangiraniService;

    private final PrvorangiraniRepository prvorangiraniRepository;

    private final PrvorangiraniQueryService prvorangiraniQueryService;

    public PrvorangiraniResource(
        PrvorangiraniService prvorangiraniService,
        PrvorangiraniRepository prvorangiraniRepository,
        PrvorangiraniQueryService prvorangiraniQueryService
    ) {
        this.prvorangiraniService = prvorangiraniService;
        this.prvorangiraniRepository = prvorangiraniRepository;
        this.prvorangiraniQueryService = prvorangiraniQueryService;
    }

    /**
     * {@code GET  /prvorangiranis} : get all the prvorangiranis.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prvorangiranis in body.
     */
    @GetMapping("/prvorangiranis")
    public ResponseEntity<List<Prvorangirani>> getAllPrvorangiranis(PrvorangiraniCriteria criteria) {
        log.debug("REST request to get Prvorangiranis by criteria: {}", criteria);
        List<Prvorangirani> entityList = prvorangiraniQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /prvorangiranis/count} : count all the prvorangiranis.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/prvorangiranis/count")
    public ResponseEntity<Long> countPrvorangiranis(PrvorangiraniCriteria criteria) {
        log.debug("REST request to count Prvorangiranis by criteria: {}", criteria);
        return ResponseEntity.ok().body(prvorangiraniQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /prvorangiranis/:id} : get the "id" prvorangirani.
     *
     * @param id the id of the prvorangirani to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prvorangirani, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prvorangiranis/{id}")
    public ResponseEntity<Prvorangirani> getPrvorangirani(@PathVariable Long id) {
        log.debug("REST request to get Prvorangirani : {}", id);
        Optional<Prvorangirani> prvorangirani = prvorangiraniService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prvorangirani);
    }

    @GetMapping("/prvorangirani")
    public List<Prvorangirani> getVrednovanjeNativePrvorangirani() {
        log.debug("REST request to get PonudePonudjaci : {}");
        List<Prvorangirani> prvorangirani = prvorangiraniRepository.findNativeAllPrvorangirani();
        return prvorangirani;
    }

    @GetMapping("/prvorangirani-postupci/{sifraPostupka}")
    public List<Prvorangirani> getPPrvorangiraniPostupak(@PathVariable Integer sifraPostupka) {
        log.debug("REST request to get Ponude : {}", sifraPostupka);
        List<Prvorangirani> prvorangirani = prvorangiraniRepository.findBySifraPostupkaListPrvorangirani(sifraPostupka);
        return prvorangirani;
    }
}
