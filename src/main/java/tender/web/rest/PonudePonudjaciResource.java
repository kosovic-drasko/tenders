package tender.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import tender.domain.Ponude;
import tender.domain.PonudePonudjaci;
import tender.repository.PonudePonudjaciRepository;
import tender.service.PonudePonudjaciQueryService;
import tender.service.PonudePonudjaciService;
import tender.service.criteria.PonudePonudjaciCriteria;
import tender.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tender.domain.PonudePonudjaci}.
 */
@RestController
@RequestMapping("/api")
public class PonudePonudjaciResource {

    private final Logger log = LoggerFactory.getLogger(PonudePonudjaciResource.class);

    private static final String ENTITY_NAME = "ponudePonudjaci";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PonudePonudjaciService ponudePonudjaciService;

    private final PonudePonudjaciRepository ponudePonudjaciRepository;

    private final PonudePonudjaciQueryService ponudePonudjaciQueryService;

    public PonudePonudjaciResource(
        PonudePonudjaciService ponudePonudjaciService,
        PonudePonudjaciRepository ponudePonudjaciRepository,
        PonudePonudjaciQueryService ponudePonudjaciQueryService
    ) {
        this.ponudePonudjaciService = ponudePonudjaciService;
        this.ponudePonudjaciRepository = ponudePonudjaciRepository;
        this.ponudePonudjaciQueryService = ponudePonudjaciQueryService;
    }

    /**
     * {@code POST  /ponude-ponudjacis} : Create a new ponudePonudjaci.
     *
     * @param ponudePonudjaci the ponudePonudjaci to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ponudePonudjaci, or with status {@code 400 (Bad Request)} if the ponudePonudjaci has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ponude-ponudjacis")
    public ResponseEntity<PonudePonudjaci> createPonudePonudjaci(@Valid @RequestBody PonudePonudjaci ponudePonudjaci)
        throws URISyntaxException {
        log.debug("REST request to save PonudePonudjaci : {}", ponudePonudjaci);
        if (ponudePonudjaci.getId() != null) {
            throw new BadRequestAlertException("A new ponudePonudjaci cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PonudePonudjaci result = ponudePonudjaciService.save(ponudePonudjaci);
        return ResponseEntity
            .created(new URI("/api/ponude-ponudjacis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ponude-ponudjacis/:id} : Updates an existing ponudePonudjaci.
     *
     * @param id the id of the ponudePonudjaci to save.
     * @param ponudePonudjaci the ponudePonudjaci to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ponudePonudjaci,
     * or with status {@code 400 (Bad Request)} if the ponudePonudjaci is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ponudePonudjaci couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ponude-ponudjacis/{id}")
    public ResponseEntity<PonudePonudjaci> updatePonudePonudjaci(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PonudePonudjaci ponudePonudjaci
    ) throws URISyntaxException {
        log.debug("REST request to update PonudePonudjaci : {}, {}", id, ponudePonudjaci);
        if (ponudePonudjaci.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ponudePonudjaci.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ponudePonudjaciRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PonudePonudjaci result = ponudePonudjaciService.update(ponudePonudjaci);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ponudePonudjaci.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ponude-ponudjacis/:id} : Partial updates given fields of an existing ponudePonudjaci, field will ignore if it is null
     *
     * @param id the id of the ponudePonudjaci to save.
     * @param ponudePonudjaci the ponudePonudjaci to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ponudePonudjaci,
     * or with status {@code 400 (Bad Request)} if the ponudePonudjaci is not valid,
     * or with status {@code 404 (Not Found)} if the ponudePonudjaci is not found,
     * or with status {@code 500 (Internal Server Error)} if the ponudePonudjaci couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ponude-ponudjacis/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PonudePonudjaci> partialUpdatePonudePonudjaci(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PonudePonudjaci ponudePonudjaci
    ) throws URISyntaxException {
        log.debug("REST request to partial update PonudePonudjaci partially : {}, {}", id, ponudePonudjaci);
        if (ponudePonudjaci.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ponudePonudjaci.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ponudePonudjaciRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PonudePonudjaci> result = ponudePonudjaciService.partialUpdate(ponudePonudjaci);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ponudePonudjaci.getId().toString())
        );
    }

    /**
     * {@code GET  /ponude-ponudjacis} : get all the ponudePonudjacis.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ponudePonudjacis in body.
     */
    @GetMapping("/ponude-ponudjacis")
    public ResponseEntity<List<PonudePonudjaci>> getAllPonudePonudjacis(PonudePonudjaciCriteria criteria) {
        log.debug("REST request to get PonudePonudjacis by criteria: {}", criteria);
        List<PonudePonudjaci> entityList = ponudePonudjaciQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ponude-ponudjacis/count} : count all the ponudePonudjacis.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ponude-ponudjacis/count")
    public ResponseEntity<Long> countPonudePonudjacis(PonudePonudjaciCriteria criteria) {
        log.debug("REST request to count PonudePonudjacis by criteria: {}", criteria);
        return ResponseEntity.ok().body(ponudePonudjaciQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ponude-ponudjacis/:id} : get the "id" ponudePonudjaci.
     *
     * @param id the id of the ponudePonudjaci to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ponudePonudjaci, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ponude-ponudjacis/{id}")
    public ResponseEntity<PonudePonudjaci> getPonudePonudjaci(@PathVariable Long id) {
        log.debug("REST request to get PonudePonudjaci : {}", id);
        Optional<PonudePonudjaci> ponudePonudjaci = ponudePonudjaciService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ponudePonudjaci);
    }

    /**
     * {@code DELETE  /ponude-ponudjacis/:id} : delete the "id" ponudePonudjaci.
     *
     * @param id the id of the ponudePonudjaci to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ponude-ponudjacis/{id}")
    public ResponseEntity<Void> deletePonudePonudjaci(@PathVariable Long id) {
        log.debug("REST request to delete PonudePonudjaci : {}", id);
        ponudePonudjaciService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/ponude-ponudjaci")
    public List<PonudePonudjaci> getPonudePonudjaciNative() {
        log.debug("REST request to get PonudePonudjaci : {}");
        List<PonudePonudjaci> ponudePonudjaci = ponudePonudjaciRepository.findNativeAll();
        return ponudePonudjaci;
    }

    @GetMapping("/ponude-ponudjaci-postupak/{sifraPostupka}")
    public List<PonudePonudjaci> getPonudePonudjaciPostupak(@PathVariable Integer sifraPostupka) {
        log.debug("REST request to get Ponude : {}", sifraPostupka);
        List<PonudePonudjaci> ponude_ponudjaci = ponudePonudjaciRepository.findBySifraPostupkaList(sifraPostupka);
        return ponude_ponudjaci;
    }

    @GetMapping("/ponude-ponudjaci-ponude/{sifraPonude}")
    public List<PonudePonudjaci> getPonudePonudjaciPonude(@PathVariable Integer sifraPonude) {
        log.debug("REST request to get Ponude : {}", sifraPonude);
        List<PonudePonudjaci> ponude_ponudjaci = ponudePonudjaciRepository.findBySifraPonudeList(sifraPonude);
        return ponude_ponudjaci;
    }

    @GetMapping("/ponude-ponudjaci-postupci/{sifra}")
    public ResponseEntity<?> getPonudePonudjaciPostuci(@PathVariable Integer sifra) {
        Optional<? extends List<?>> ponude = Optional.ofNullable(ponudePonudjaciRepository.findBySifraPostupkaPonudjaci(sifra));
        return ResponseUtil.wrapOrNotFound(ponude);
    }
}
