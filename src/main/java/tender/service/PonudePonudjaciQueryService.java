package tender.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;
import tender.domain.*; // for static metamodels
import tender.domain.PonudePonudjaci;
import tender.repository.PonudePonudjaciRepository;
import tender.service.criteria.PonudePonudjaciCriteria;

/**
 * Service for executing complex queries for {@link PonudePonudjaci} entities in the database.
 * The main input is a {@link PonudePonudjaciCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PonudePonudjaci} or a {@link Page} of {@link PonudePonudjaci} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PonudePonudjaciQueryService extends QueryService<PonudePonudjaci> {

    private final Logger log = LoggerFactory.getLogger(PonudePonudjaciQueryService.class);

    private final PonudePonudjaciRepository ponudePonudjaciRepository;

    public PonudePonudjaciQueryService(PonudePonudjaciRepository ponudePonudjaciRepository) {
        this.ponudePonudjaciRepository = ponudePonudjaciRepository;
    }

    /**
     * Return a {@link List} of {@link PonudePonudjaci} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PonudePonudjaci> findByCriteria(PonudePonudjaciCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PonudePonudjaci> specification = createSpecification(criteria);
        return ponudePonudjaciRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PonudePonudjaci} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PonudePonudjaci> findByCriteria(PonudePonudjaciCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PonudePonudjaci> specification = createSpecification(criteria);
        return ponudePonudjaciRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PonudePonudjaciCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PonudePonudjaci> specification = createSpecification(criteria);
        return ponudePonudjaciRepository.count(specification);
    }

    /**
     * Function to convert {@link PonudePonudjaciCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PonudePonudjaci> createSpecification(PonudePonudjaciCriteria criteria) {
        Specification<PonudePonudjaci> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PonudePonudjaci_.id));
            }
            if (criteria.getSifraPostupka() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSifraPostupka(), PonudePonudjaci_.sifraPostupka));
            }
            if (criteria.getSifraPonude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSifraPonude(), PonudePonudjaci_.sifraPonude));
            }
            if (criteria.getBrojPartije() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBrojPartije(), PonudePonudjaci_.brojPartije));
            }
            if (criteria.getNazivProizvodjaca() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getNazivProizvodjaca(), PonudePonudjaci_.nazivProizvodjaca));
            }
            if (criteria.getNazivPonudjaca() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNazivPonudjaca(), PonudePonudjaci_.nazivPonudjaca));
            }
            if (criteria.getZasticeniNaziv() != null) {
                specification = specification.and(buildStringSpecification(criteria.getZasticeniNaziv(), PonudePonudjaci_.zasticeniNaziv));
            }
            if (criteria.getPonudjenaVrijednost() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPonudjenaVrijednost(), PonudePonudjaci_.ponudjenaVrijednost));
            }
            if (criteria.getRokIsporuke() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRokIsporuke(), PonudePonudjaci_.rokIsporuke));
            }
            if (criteria.getJedinicnaCijena() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJedinicnaCijena(), PonudePonudjaci_.jedinicnaCijena));
            }
            if (criteria.getSelected() != null) {
                specification = specification.and(buildSpecification(criteria.getSelected(), PonudePonudjaci_.selected));
            }
        }
        return specification;
    }
}
