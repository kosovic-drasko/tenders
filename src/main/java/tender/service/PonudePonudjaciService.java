package tender.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tender.domain.PonudePonudjaci;
import tender.repository.PonudePonudjaciRepository;

/**
 * Service Implementation for managing {@link PonudePonudjaci}.
 */
@Service
@Transactional
public class PonudePonudjaciService {

    private final Logger log = LoggerFactory.getLogger(PonudePonudjaciService.class);

    private final PonudePonudjaciRepository ponudePonudjaciRepository;

    public PonudePonudjaciService(PonudePonudjaciRepository ponudePonudjaciRepository) {
        this.ponudePonudjaciRepository = ponudePonudjaciRepository;
    }

    /**
     * Save a ponudePonudjaci.
     *
     * @param ponudePonudjaci the entity to save.
     * @return the persisted entity.
     */
    public PonudePonudjaci save(PonudePonudjaci ponudePonudjaci) {
        log.debug("Request to save PonudePonudjaci : {}", ponudePonudjaci);
        return ponudePonudjaciRepository.save(ponudePonudjaci);
    }

    /**
     * Update a ponudePonudjaci.
     *
     * @param ponudePonudjaci the entity to save.
     * @return the persisted entity.
     */
    public PonudePonudjaci update(PonudePonudjaci ponudePonudjaci) {
        log.debug("Request to update PonudePonudjaci : {}", ponudePonudjaci);
        return ponudePonudjaciRepository.save(ponudePonudjaci);
    }

    /**
     * Partially update a ponudePonudjaci.
     *
     * @param ponudePonudjaci the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PonudePonudjaci> partialUpdate(PonudePonudjaci ponudePonudjaci) {
        log.debug("Request to partially update PonudePonudjaci : {}", ponudePonudjaci);

        return ponudePonudjaciRepository
            .findById(ponudePonudjaci.getId())
            .map(existingPonudePonudjaci -> {
                if (ponudePonudjaci.getSifraPostupka() != null) {
                    existingPonudePonudjaci.setSifraPostupka(ponudePonudjaci.getSifraPostupka());
                }
                if (ponudePonudjaci.getSifraPonude() != null) {
                    existingPonudePonudjaci.setSifraPonude(ponudePonudjaci.getSifraPonude());
                }
                if (ponudePonudjaci.getBrojPartije() != null) {
                    existingPonudePonudjaci.setBrojPartije(ponudePonudjaci.getBrojPartije());
                }
                if (ponudePonudjaci.getNazivProizvodjaca() != null) {
                    existingPonudePonudjaci.setNazivProizvodjaca(ponudePonudjaci.getNazivProizvodjaca());
                }
                if (ponudePonudjaci.getNazivPonudjaca() != null) {
                    existingPonudePonudjaci.setNazivPonudjaca(ponudePonudjaci.getNazivPonudjaca());
                }
                if (ponudePonudjaci.getZasticeniNaziv() != null) {
                    existingPonudePonudjaci.setZasticeniNaziv(ponudePonudjaci.getZasticeniNaziv());
                }
                if (ponudePonudjaci.getPonudjenaVrijednost() != null) {
                    existingPonudePonudjaci.setPonudjenaVrijednost(ponudePonudjaci.getPonudjenaVrijednost());
                }
                if (ponudePonudjaci.getRokIsporuke() != null) {
                    existingPonudePonudjaci.setRokIsporuke(ponudePonudjaci.getRokIsporuke());
                }
                if (ponudePonudjaci.getJedinicnaCijena() != null) {
                    existingPonudePonudjaci.setJedinicnaCijena(ponudePonudjaci.getJedinicnaCijena());
                }
                if (ponudePonudjaci.getSelected() != null) {
                    existingPonudePonudjaci.setSelected(ponudePonudjaci.getSelected());
                }

                return existingPonudePonudjaci;
            })
            .map(ponudePonudjaciRepository::save);
    }

    /**
     * Get all the ponudePonudjacis.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PonudePonudjaci> findAll() {
        log.debug("Request to get all PonudePonudjacis");
        return ponudePonudjaciRepository.findAll();
    }

    /**
     * Get one ponudePonudjaci by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PonudePonudjaci> findOne(Long id) {
        log.debug("Request to get PonudePonudjaci : {}", id);
        return ponudePonudjaciRepository.findById(id);
    }

    /**
     * Delete the ponudePonudjaci by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PonudePonudjaci : {}", id);
        ponudePonudjaciRepository.deleteById(id);
    }
}
