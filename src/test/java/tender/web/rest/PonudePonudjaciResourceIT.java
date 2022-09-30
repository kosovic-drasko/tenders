package tender.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tender.IntegrationTest;
import tender.domain.PonudePonudjaci;
import tender.repository.PonudePonudjaciRepository;
import tender.service.criteria.PonudePonudjaciCriteria;

/**
 * Integration tests for the {@link PonudePonudjaciResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PonudePonudjaciResourceIT {

    private static final Integer DEFAULT_SIFRA_POSTUPKA = 1;
    private static final Integer UPDATED_SIFRA_POSTUPKA = 2;
    private static final Integer SMALLER_SIFRA_POSTUPKA = 1 - 1;

    private static final Integer DEFAULT_SIFRA_PONUDE = 1;
    private static final Integer UPDATED_SIFRA_PONUDE = 2;
    private static final Integer SMALLER_SIFRA_PONUDE = 1 - 1;

    private static final Integer DEFAULT_BROJ_PARTIJE = 1;
    private static final Integer UPDATED_BROJ_PARTIJE = 2;
    private static final Integer SMALLER_BROJ_PARTIJE = 1 - 1;

    private static final String DEFAULT_NAZIV_PROIZVODJACA = "AAAAAAAAAA";
    private static final String UPDATED_NAZIV_PROIZVODJACA = "BBBBBBBBBB";

    private static final String DEFAULT_NAZIV_PONUDJACA = "AAAAAAAAAA";
    private static final String UPDATED_NAZIV_PONUDJACA = "BBBBBBBBBB";

    private static final String DEFAULT_ZASTICENI_NAZIV = "AAAAAAAAAA";
    private static final String UPDATED_ZASTICENI_NAZIV = "BBBBBBBBBB";

    private static final Double DEFAULT_PONUDJENA_VRIJEDNOST = 1D;
    private static final Double UPDATED_PONUDJENA_VRIJEDNOST = 2D;
    private static final Double SMALLER_PONUDJENA_VRIJEDNOST = 1D - 1D;

    private static final Integer DEFAULT_ROK_ISPORUKE = 1;
    private static final Integer UPDATED_ROK_ISPORUKE = 2;
    private static final Integer SMALLER_ROK_ISPORUKE = 1 - 1;

    private static final Double DEFAULT_JEDINICNA_CIJENA = 1D;
    private static final Double UPDATED_JEDINICNA_CIJENA = 2D;
    private static final Double SMALLER_JEDINICNA_CIJENA = 1D - 1D;

    private static final Boolean DEFAULT_SELECTED = false;
    private static final Boolean UPDATED_SELECTED = true;

    private static final String ENTITY_API_URL = "/api/ponude-ponudjacis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PonudePonudjaciRepository ponudePonudjaciRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPonudePonudjaciMockMvc;

    private PonudePonudjaci ponudePonudjaci;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PonudePonudjaci createEntity(EntityManager em) {
        PonudePonudjaci ponudePonudjaci = new PonudePonudjaci()
            .sifraPostupka(DEFAULT_SIFRA_POSTUPKA)
            .sifraPonude(DEFAULT_SIFRA_PONUDE)
            .brojPartije(DEFAULT_BROJ_PARTIJE)
            .nazivProizvodjaca(DEFAULT_NAZIV_PROIZVODJACA)
            .nazivPonudjaca(DEFAULT_NAZIV_PONUDJACA)
            .zasticeniNaziv(DEFAULT_ZASTICENI_NAZIV)
            .ponudjenaVrijednost(DEFAULT_PONUDJENA_VRIJEDNOST)
            .rokIsporuke(DEFAULT_ROK_ISPORUKE)
            .jedinicnaCijena(DEFAULT_JEDINICNA_CIJENA)
            .selected(DEFAULT_SELECTED);
        return ponudePonudjaci;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PonudePonudjaci createUpdatedEntity(EntityManager em) {
        PonudePonudjaci ponudePonudjaci = new PonudePonudjaci()
            .sifraPostupka(UPDATED_SIFRA_POSTUPKA)
            .sifraPonude(UPDATED_SIFRA_PONUDE)
            .brojPartije(UPDATED_BROJ_PARTIJE)
            .nazivProizvodjaca(UPDATED_NAZIV_PROIZVODJACA)
            .nazivPonudjaca(UPDATED_NAZIV_PONUDJACA)
            .zasticeniNaziv(UPDATED_ZASTICENI_NAZIV)
            .ponudjenaVrijednost(UPDATED_PONUDJENA_VRIJEDNOST)
            .rokIsporuke(UPDATED_ROK_ISPORUKE)
            .jedinicnaCijena(UPDATED_JEDINICNA_CIJENA)
            .selected(UPDATED_SELECTED);
        return ponudePonudjaci;
    }

    @BeforeEach
    public void initTest() {
        ponudePonudjaci = createEntity(em);
    }

    @Test
    @Transactional
    void createPonudePonudjaci() throws Exception {
        int databaseSizeBeforeCreate = ponudePonudjaciRepository.findAll().size();
        // Create the PonudePonudjaci
        restPonudePonudjaciMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ponudePonudjaci))
            )
            .andExpect(status().isCreated());

        // Validate the PonudePonudjaci in the database
        List<PonudePonudjaci> ponudePonudjaciList = ponudePonudjaciRepository.findAll();
        assertThat(ponudePonudjaciList).hasSize(databaseSizeBeforeCreate + 1);
        PonudePonudjaci testPonudePonudjaci = ponudePonudjaciList.get(ponudePonudjaciList.size() - 1);
        assertThat(testPonudePonudjaci.getSifraPostupka()).isEqualTo(DEFAULT_SIFRA_POSTUPKA);
        assertThat(testPonudePonudjaci.getSifraPonude()).isEqualTo(DEFAULT_SIFRA_PONUDE);
        assertThat(testPonudePonudjaci.getBrojPartije()).isEqualTo(DEFAULT_BROJ_PARTIJE);
        assertThat(testPonudePonudjaci.getNazivProizvodjaca()).isEqualTo(DEFAULT_NAZIV_PROIZVODJACA);
        assertThat(testPonudePonudjaci.getNazivPonudjaca()).isEqualTo(DEFAULT_NAZIV_PONUDJACA);
        assertThat(testPonudePonudjaci.getZasticeniNaziv()).isEqualTo(DEFAULT_ZASTICENI_NAZIV);
        assertThat(testPonudePonudjaci.getPonudjenaVrijednost()).isEqualTo(DEFAULT_PONUDJENA_VRIJEDNOST);
        assertThat(testPonudePonudjaci.getRokIsporuke()).isEqualTo(DEFAULT_ROK_ISPORUKE);
        assertThat(testPonudePonudjaci.getJedinicnaCijena()).isEqualTo(DEFAULT_JEDINICNA_CIJENA);
        assertThat(testPonudePonudjaci.getSelected()).isEqualTo(DEFAULT_SELECTED);
    }

    @Test
    @Transactional
    void createPonudePonudjaciWithExistingId() throws Exception {
        // Create the PonudePonudjaci with an existing ID
        ponudePonudjaci.setId(1L);

        int databaseSizeBeforeCreate = ponudePonudjaciRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPonudePonudjaciMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ponudePonudjaci))
            )
            .andExpect(status().isBadRequest());

        // Validate the PonudePonudjaci in the database
        List<PonudePonudjaci> ponudePonudjaciList = ponudePonudjaciRepository.findAll();
        assertThat(ponudePonudjaciList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSifraPostupkaIsRequired() throws Exception {
        int databaseSizeBeforeTest = ponudePonudjaciRepository.findAll().size();
        // set the field null
        ponudePonudjaci.setSifraPostupka(null);

        // Create the PonudePonudjaci, which fails.

        restPonudePonudjaciMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ponudePonudjaci))
            )
            .andExpect(status().isBadRequest());

        List<PonudePonudjaci> ponudePonudjaciList = ponudePonudjaciRepository.findAll();
        assertThat(ponudePonudjaciList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSifraPonudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ponudePonudjaciRepository.findAll().size();
        // set the field null
        ponudePonudjaci.setSifraPonude(null);

        // Create the PonudePonudjaci, which fails.

        restPonudePonudjaciMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ponudePonudjaci))
            )
            .andExpect(status().isBadRequest());

        List<PonudePonudjaci> ponudePonudjaciList = ponudePonudjaciRepository.findAll();
        assertThat(ponudePonudjaciList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBrojPartijeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ponudePonudjaciRepository.findAll().size();
        // set the field null
        ponudePonudjaci.setBrojPartije(null);

        // Create the PonudePonudjaci, which fails.

        restPonudePonudjaciMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ponudePonudjaci))
            )
            .andExpect(status().isBadRequest());

        List<PonudePonudjaci> ponudePonudjaciList = ponudePonudjaciRepository.findAll();
        assertThat(ponudePonudjaciList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPonudjenaVrijednostIsRequired() throws Exception {
        int databaseSizeBeforeTest = ponudePonudjaciRepository.findAll().size();
        // set the field null
        ponudePonudjaci.setPonudjenaVrijednost(null);

        // Create the PonudePonudjaci, which fails.

        restPonudePonudjaciMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ponudePonudjaci))
            )
            .andExpect(status().isBadRequest());

        List<PonudePonudjaci> ponudePonudjaciList = ponudePonudjaciRepository.findAll();
        assertThat(ponudePonudjaciList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacis() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList
        restPonudePonudjaciMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ponudePonudjaci.getId().intValue())))
            .andExpect(jsonPath("$.[*].sifraPostupka").value(hasItem(DEFAULT_SIFRA_POSTUPKA)))
            .andExpect(jsonPath("$.[*].sifraPonude").value(hasItem(DEFAULT_SIFRA_PONUDE)))
            .andExpect(jsonPath("$.[*].brojPartije").value(hasItem(DEFAULT_BROJ_PARTIJE)))
            .andExpect(jsonPath("$.[*].nazivProizvodjaca").value(hasItem(DEFAULT_NAZIV_PROIZVODJACA)))
            .andExpect(jsonPath("$.[*].nazivPonudjaca").value(hasItem(DEFAULT_NAZIV_PONUDJACA)))
            .andExpect(jsonPath("$.[*].zasticeniNaziv").value(hasItem(DEFAULT_ZASTICENI_NAZIV)))
            .andExpect(jsonPath("$.[*].ponudjenaVrijednost").value(hasItem(DEFAULT_PONUDJENA_VRIJEDNOST.doubleValue())))
            .andExpect(jsonPath("$.[*].rokIsporuke").value(hasItem(DEFAULT_ROK_ISPORUKE)))
            .andExpect(jsonPath("$.[*].jedinicnaCijena").value(hasItem(DEFAULT_JEDINICNA_CIJENA.doubleValue())))
            .andExpect(jsonPath("$.[*].selected").value(hasItem(DEFAULT_SELECTED.booleanValue())));
    }

    @Test
    @Transactional
    void getPonudePonudjaci() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get the ponudePonudjaci
        restPonudePonudjaciMockMvc
            .perform(get(ENTITY_API_URL_ID, ponudePonudjaci.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ponudePonudjaci.getId().intValue()))
            .andExpect(jsonPath("$.sifraPostupka").value(DEFAULT_SIFRA_POSTUPKA))
            .andExpect(jsonPath("$.sifraPonude").value(DEFAULT_SIFRA_PONUDE))
            .andExpect(jsonPath("$.brojPartije").value(DEFAULT_BROJ_PARTIJE))
            .andExpect(jsonPath("$.nazivProizvodjaca").value(DEFAULT_NAZIV_PROIZVODJACA))
            .andExpect(jsonPath("$.nazivPonudjaca").value(DEFAULT_NAZIV_PONUDJACA))
            .andExpect(jsonPath("$.zasticeniNaziv").value(DEFAULT_ZASTICENI_NAZIV))
            .andExpect(jsonPath("$.ponudjenaVrijednost").value(DEFAULT_PONUDJENA_VRIJEDNOST.doubleValue()))
            .andExpect(jsonPath("$.rokIsporuke").value(DEFAULT_ROK_ISPORUKE))
            .andExpect(jsonPath("$.jedinicnaCijena").value(DEFAULT_JEDINICNA_CIJENA.doubleValue()))
            .andExpect(jsonPath("$.selected").value(DEFAULT_SELECTED.booleanValue()));
    }

    @Test
    @Transactional
    void getPonudePonudjacisByIdFiltering() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        Long id = ponudePonudjaci.getId();

        defaultPonudePonudjaciShouldBeFound("id.equals=" + id);
        defaultPonudePonudjaciShouldNotBeFound("id.notEquals=" + id);

        defaultPonudePonudjaciShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPonudePonudjaciShouldNotBeFound("id.greaterThan=" + id);

        defaultPonudePonudjaciShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPonudePonudjaciShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisBySifraPostupkaIsEqualToSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where sifraPostupka equals to DEFAULT_SIFRA_POSTUPKA
        defaultPonudePonudjaciShouldBeFound("sifraPostupka.equals=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the ponudePonudjaciList where sifraPostupka equals to UPDATED_SIFRA_POSTUPKA
        defaultPonudePonudjaciShouldNotBeFound("sifraPostupka.equals=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisBySifraPostupkaIsInShouldWork() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where sifraPostupka in DEFAULT_SIFRA_POSTUPKA or UPDATED_SIFRA_POSTUPKA
        defaultPonudePonudjaciShouldBeFound("sifraPostupka.in=" + DEFAULT_SIFRA_POSTUPKA + "," + UPDATED_SIFRA_POSTUPKA);

        // Get all the ponudePonudjaciList where sifraPostupka equals to UPDATED_SIFRA_POSTUPKA
        defaultPonudePonudjaciShouldNotBeFound("sifraPostupka.in=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisBySifraPostupkaIsNullOrNotNull() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where sifraPostupka is not null
        defaultPonudePonudjaciShouldBeFound("sifraPostupka.specified=true");

        // Get all the ponudePonudjaciList where sifraPostupka is null
        defaultPonudePonudjaciShouldNotBeFound("sifraPostupka.specified=false");
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisBySifraPostupkaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where sifraPostupka is greater than or equal to DEFAULT_SIFRA_POSTUPKA
        defaultPonudePonudjaciShouldBeFound("sifraPostupka.greaterThanOrEqual=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the ponudePonudjaciList where sifraPostupka is greater than or equal to UPDATED_SIFRA_POSTUPKA
        defaultPonudePonudjaciShouldNotBeFound("sifraPostupka.greaterThanOrEqual=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisBySifraPostupkaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where sifraPostupka is less than or equal to DEFAULT_SIFRA_POSTUPKA
        defaultPonudePonudjaciShouldBeFound("sifraPostupka.lessThanOrEqual=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the ponudePonudjaciList where sifraPostupka is less than or equal to SMALLER_SIFRA_POSTUPKA
        defaultPonudePonudjaciShouldNotBeFound("sifraPostupka.lessThanOrEqual=" + SMALLER_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisBySifraPostupkaIsLessThanSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where sifraPostupka is less than DEFAULT_SIFRA_POSTUPKA
        defaultPonudePonudjaciShouldNotBeFound("sifraPostupka.lessThan=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the ponudePonudjaciList where sifraPostupka is less than UPDATED_SIFRA_POSTUPKA
        defaultPonudePonudjaciShouldBeFound("sifraPostupka.lessThan=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisBySifraPostupkaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where sifraPostupka is greater than DEFAULT_SIFRA_POSTUPKA
        defaultPonudePonudjaciShouldNotBeFound("sifraPostupka.greaterThan=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the ponudePonudjaciList where sifraPostupka is greater than SMALLER_SIFRA_POSTUPKA
        defaultPonudePonudjaciShouldBeFound("sifraPostupka.greaterThan=" + SMALLER_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisBySifraPonudeIsEqualToSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where sifraPonude equals to DEFAULT_SIFRA_PONUDE
        defaultPonudePonudjaciShouldBeFound("sifraPonude.equals=" + DEFAULT_SIFRA_PONUDE);

        // Get all the ponudePonudjaciList where sifraPonude equals to UPDATED_SIFRA_PONUDE
        defaultPonudePonudjaciShouldNotBeFound("sifraPonude.equals=" + UPDATED_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisBySifraPonudeIsInShouldWork() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where sifraPonude in DEFAULT_SIFRA_PONUDE or UPDATED_SIFRA_PONUDE
        defaultPonudePonudjaciShouldBeFound("sifraPonude.in=" + DEFAULT_SIFRA_PONUDE + "," + UPDATED_SIFRA_PONUDE);

        // Get all the ponudePonudjaciList where sifraPonude equals to UPDATED_SIFRA_PONUDE
        defaultPonudePonudjaciShouldNotBeFound("sifraPonude.in=" + UPDATED_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisBySifraPonudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where sifraPonude is not null
        defaultPonudePonudjaciShouldBeFound("sifraPonude.specified=true");

        // Get all the ponudePonudjaciList where sifraPonude is null
        defaultPonudePonudjaciShouldNotBeFound("sifraPonude.specified=false");
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisBySifraPonudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where sifraPonude is greater than or equal to DEFAULT_SIFRA_PONUDE
        defaultPonudePonudjaciShouldBeFound("sifraPonude.greaterThanOrEqual=" + DEFAULT_SIFRA_PONUDE);

        // Get all the ponudePonudjaciList where sifraPonude is greater than or equal to UPDATED_SIFRA_PONUDE
        defaultPonudePonudjaciShouldNotBeFound("sifraPonude.greaterThanOrEqual=" + UPDATED_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisBySifraPonudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where sifraPonude is less than or equal to DEFAULT_SIFRA_PONUDE
        defaultPonudePonudjaciShouldBeFound("sifraPonude.lessThanOrEqual=" + DEFAULT_SIFRA_PONUDE);

        // Get all the ponudePonudjaciList where sifraPonude is less than or equal to SMALLER_SIFRA_PONUDE
        defaultPonudePonudjaciShouldNotBeFound("sifraPonude.lessThanOrEqual=" + SMALLER_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisBySifraPonudeIsLessThanSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where sifraPonude is less than DEFAULT_SIFRA_PONUDE
        defaultPonudePonudjaciShouldNotBeFound("sifraPonude.lessThan=" + DEFAULT_SIFRA_PONUDE);

        // Get all the ponudePonudjaciList where sifraPonude is less than UPDATED_SIFRA_PONUDE
        defaultPonudePonudjaciShouldBeFound("sifraPonude.lessThan=" + UPDATED_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisBySifraPonudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where sifraPonude is greater than DEFAULT_SIFRA_PONUDE
        defaultPonudePonudjaciShouldNotBeFound("sifraPonude.greaterThan=" + DEFAULT_SIFRA_PONUDE);

        // Get all the ponudePonudjaciList where sifraPonude is greater than SMALLER_SIFRA_PONUDE
        defaultPonudePonudjaciShouldBeFound("sifraPonude.greaterThan=" + SMALLER_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByBrojPartijeIsEqualToSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where brojPartije equals to DEFAULT_BROJ_PARTIJE
        defaultPonudePonudjaciShouldBeFound("brojPartije.equals=" + DEFAULT_BROJ_PARTIJE);

        // Get all the ponudePonudjaciList where brojPartije equals to UPDATED_BROJ_PARTIJE
        defaultPonudePonudjaciShouldNotBeFound("brojPartije.equals=" + UPDATED_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByBrojPartijeIsInShouldWork() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where brojPartije in DEFAULT_BROJ_PARTIJE or UPDATED_BROJ_PARTIJE
        defaultPonudePonudjaciShouldBeFound("brojPartije.in=" + DEFAULT_BROJ_PARTIJE + "," + UPDATED_BROJ_PARTIJE);

        // Get all the ponudePonudjaciList where brojPartije equals to UPDATED_BROJ_PARTIJE
        defaultPonudePonudjaciShouldNotBeFound("brojPartije.in=" + UPDATED_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByBrojPartijeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where brojPartije is not null
        defaultPonudePonudjaciShouldBeFound("brojPartije.specified=true");

        // Get all the ponudePonudjaciList where brojPartije is null
        defaultPonudePonudjaciShouldNotBeFound("brojPartije.specified=false");
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByBrojPartijeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where brojPartije is greater than or equal to DEFAULT_BROJ_PARTIJE
        defaultPonudePonudjaciShouldBeFound("brojPartije.greaterThanOrEqual=" + DEFAULT_BROJ_PARTIJE);

        // Get all the ponudePonudjaciList where brojPartije is greater than or equal to UPDATED_BROJ_PARTIJE
        defaultPonudePonudjaciShouldNotBeFound("brojPartije.greaterThanOrEqual=" + UPDATED_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByBrojPartijeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where brojPartije is less than or equal to DEFAULT_BROJ_PARTIJE
        defaultPonudePonudjaciShouldBeFound("brojPartije.lessThanOrEqual=" + DEFAULT_BROJ_PARTIJE);

        // Get all the ponudePonudjaciList where brojPartije is less than or equal to SMALLER_BROJ_PARTIJE
        defaultPonudePonudjaciShouldNotBeFound("brojPartije.lessThanOrEqual=" + SMALLER_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByBrojPartijeIsLessThanSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where brojPartije is less than DEFAULT_BROJ_PARTIJE
        defaultPonudePonudjaciShouldNotBeFound("brojPartije.lessThan=" + DEFAULT_BROJ_PARTIJE);

        // Get all the ponudePonudjaciList where brojPartije is less than UPDATED_BROJ_PARTIJE
        defaultPonudePonudjaciShouldBeFound("brojPartije.lessThan=" + UPDATED_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByBrojPartijeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where brojPartije is greater than DEFAULT_BROJ_PARTIJE
        defaultPonudePonudjaciShouldNotBeFound("brojPartije.greaterThan=" + DEFAULT_BROJ_PARTIJE);

        // Get all the ponudePonudjaciList where brojPartije is greater than SMALLER_BROJ_PARTIJE
        defaultPonudePonudjaciShouldBeFound("brojPartije.greaterThan=" + SMALLER_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByNazivProizvodjacaIsEqualToSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where nazivProizvodjaca equals to DEFAULT_NAZIV_PROIZVODJACA
        defaultPonudePonudjaciShouldBeFound("nazivProizvodjaca.equals=" + DEFAULT_NAZIV_PROIZVODJACA);

        // Get all the ponudePonudjaciList where nazivProizvodjaca equals to UPDATED_NAZIV_PROIZVODJACA
        defaultPonudePonudjaciShouldNotBeFound("nazivProizvodjaca.equals=" + UPDATED_NAZIV_PROIZVODJACA);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByNazivProizvodjacaIsInShouldWork() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where nazivProizvodjaca in DEFAULT_NAZIV_PROIZVODJACA or UPDATED_NAZIV_PROIZVODJACA
        defaultPonudePonudjaciShouldBeFound("nazivProizvodjaca.in=" + DEFAULT_NAZIV_PROIZVODJACA + "," + UPDATED_NAZIV_PROIZVODJACA);

        // Get all the ponudePonudjaciList where nazivProizvodjaca equals to UPDATED_NAZIV_PROIZVODJACA
        defaultPonudePonudjaciShouldNotBeFound("nazivProizvodjaca.in=" + UPDATED_NAZIV_PROIZVODJACA);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByNazivProizvodjacaIsNullOrNotNull() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where nazivProizvodjaca is not null
        defaultPonudePonudjaciShouldBeFound("nazivProizvodjaca.specified=true");

        // Get all the ponudePonudjaciList where nazivProizvodjaca is null
        defaultPonudePonudjaciShouldNotBeFound("nazivProizvodjaca.specified=false");
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByNazivProizvodjacaContainsSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where nazivProizvodjaca contains DEFAULT_NAZIV_PROIZVODJACA
        defaultPonudePonudjaciShouldBeFound("nazivProizvodjaca.contains=" + DEFAULT_NAZIV_PROIZVODJACA);

        // Get all the ponudePonudjaciList where nazivProizvodjaca contains UPDATED_NAZIV_PROIZVODJACA
        defaultPonudePonudjaciShouldNotBeFound("nazivProizvodjaca.contains=" + UPDATED_NAZIV_PROIZVODJACA);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByNazivProizvodjacaNotContainsSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where nazivProizvodjaca does not contain DEFAULT_NAZIV_PROIZVODJACA
        defaultPonudePonudjaciShouldNotBeFound("nazivProizvodjaca.doesNotContain=" + DEFAULT_NAZIV_PROIZVODJACA);

        // Get all the ponudePonudjaciList where nazivProizvodjaca does not contain UPDATED_NAZIV_PROIZVODJACA
        defaultPonudePonudjaciShouldBeFound("nazivProizvodjaca.doesNotContain=" + UPDATED_NAZIV_PROIZVODJACA);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByNazivPonudjacaIsEqualToSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where nazivPonudjaca equals to DEFAULT_NAZIV_PONUDJACA
        defaultPonudePonudjaciShouldBeFound("nazivPonudjaca.equals=" + DEFAULT_NAZIV_PONUDJACA);

        // Get all the ponudePonudjaciList where nazivPonudjaca equals to UPDATED_NAZIV_PONUDJACA
        defaultPonudePonudjaciShouldNotBeFound("nazivPonudjaca.equals=" + UPDATED_NAZIV_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByNazivPonudjacaIsInShouldWork() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where nazivPonudjaca in DEFAULT_NAZIV_PONUDJACA or UPDATED_NAZIV_PONUDJACA
        defaultPonudePonudjaciShouldBeFound("nazivPonudjaca.in=" + DEFAULT_NAZIV_PONUDJACA + "," + UPDATED_NAZIV_PONUDJACA);

        // Get all the ponudePonudjaciList where nazivPonudjaca equals to UPDATED_NAZIV_PONUDJACA
        defaultPonudePonudjaciShouldNotBeFound("nazivPonudjaca.in=" + UPDATED_NAZIV_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByNazivPonudjacaIsNullOrNotNull() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where nazivPonudjaca is not null
        defaultPonudePonudjaciShouldBeFound("nazivPonudjaca.specified=true");

        // Get all the ponudePonudjaciList where nazivPonudjaca is null
        defaultPonudePonudjaciShouldNotBeFound("nazivPonudjaca.specified=false");
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByNazivPonudjacaContainsSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where nazivPonudjaca contains DEFAULT_NAZIV_PONUDJACA
        defaultPonudePonudjaciShouldBeFound("nazivPonudjaca.contains=" + DEFAULT_NAZIV_PONUDJACA);

        // Get all the ponudePonudjaciList where nazivPonudjaca contains UPDATED_NAZIV_PONUDJACA
        defaultPonudePonudjaciShouldNotBeFound("nazivPonudjaca.contains=" + UPDATED_NAZIV_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByNazivPonudjacaNotContainsSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where nazivPonudjaca does not contain DEFAULT_NAZIV_PONUDJACA
        defaultPonudePonudjaciShouldNotBeFound("nazivPonudjaca.doesNotContain=" + DEFAULT_NAZIV_PONUDJACA);

        // Get all the ponudePonudjaciList where nazivPonudjaca does not contain UPDATED_NAZIV_PONUDJACA
        defaultPonudePonudjaciShouldBeFound("nazivPonudjaca.doesNotContain=" + UPDATED_NAZIV_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByZasticeniNazivIsEqualToSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where zasticeniNaziv equals to DEFAULT_ZASTICENI_NAZIV
        defaultPonudePonudjaciShouldBeFound("zasticeniNaziv.equals=" + DEFAULT_ZASTICENI_NAZIV);

        // Get all the ponudePonudjaciList where zasticeniNaziv equals to UPDATED_ZASTICENI_NAZIV
        defaultPonudePonudjaciShouldNotBeFound("zasticeniNaziv.equals=" + UPDATED_ZASTICENI_NAZIV);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByZasticeniNazivIsInShouldWork() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where zasticeniNaziv in DEFAULT_ZASTICENI_NAZIV or UPDATED_ZASTICENI_NAZIV
        defaultPonudePonudjaciShouldBeFound("zasticeniNaziv.in=" + DEFAULT_ZASTICENI_NAZIV + "," + UPDATED_ZASTICENI_NAZIV);

        // Get all the ponudePonudjaciList where zasticeniNaziv equals to UPDATED_ZASTICENI_NAZIV
        defaultPonudePonudjaciShouldNotBeFound("zasticeniNaziv.in=" + UPDATED_ZASTICENI_NAZIV);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByZasticeniNazivIsNullOrNotNull() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where zasticeniNaziv is not null
        defaultPonudePonudjaciShouldBeFound("zasticeniNaziv.specified=true");

        // Get all the ponudePonudjaciList where zasticeniNaziv is null
        defaultPonudePonudjaciShouldNotBeFound("zasticeniNaziv.specified=false");
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByZasticeniNazivContainsSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where zasticeniNaziv contains DEFAULT_ZASTICENI_NAZIV
        defaultPonudePonudjaciShouldBeFound("zasticeniNaziv.contains=" + DEFAULT_ZASTICENI_NAZIV);

        // Get all the ponudePonudjaciList where zasticeniNaziv contains UPDATED_ZASTICENI_NAZIV
        defaultPonudePonudjaciShouldNotBeFound("zasticeniNaziv.contains=" + UPDATED_ZASTICENI_NAZIV);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByZasticeniNazivNotContainsSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where zasticeniNaziv does not contain DEFAULT_ZASTICENI_NAZIV
        defaultPonudePonudjaciShouldNotBeFound("zasticeniNaziv.doesNotContain=" + DEFAULT_ZASTICENI_NAZIV);

        // Get all the ponudePonudjaciList where zasticeniNaziv does not contain UPDATED_ZASTICENI_NAZIV
        defaultPonudePonudjaciShouldBeFound("zasticeniNaziv.doesNotContain=" + UPDATED_ZASTICENI_NAZIV);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByPonudjenaVrijednostIsEqualToSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where ponudjenaVrijednost equals to DEFAULT_PONUDJENA_VRIJEDNOST
        defaultPonudePonudjaciShouldBeFound("ponudjenaVrijednost.equals=" + DEFAULT_PONUDJENA_VRIJEDNOST);

        // Get all the ponudePonudjaciList where ponudjenaVrijednost equals to UPDATED_PONUDJENA_VRIJEDNOST
        defaultPonudePonudjaciShouldNotBeFound("ponudjenaVrijednost.equals=" + UPDATED_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByPonudjenaVrijednostIsInShouldWork() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where ponudjenaVrijednost in DEFAULT_PONUDJENA_VRIJEDNOST or UPDATED_PONUDJENA_VRIJEDNOST
        defaultPonudePonudjaciShouldBeFound("ponudjenaVrijednost.in=" + DEFAULT_PONUDJENA_VRIJEDNOST + "," + UPDATED_PONUDJENA_VRIJEDNOST);

        // Get all the ponudePonudjaciList where ponudjenaVrijednost equals to UPDATED_PONUDJENA_VRIJEDNOST
        defaultPonudePonudjaciShouldNotBeFound("ponudjenaVrijednost.in=" + UPDATED_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByPonudjenaVrijednostIsNullOrNotNull() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where ponudjenaVrijednost is not null
        defaultPonudePonudjaciShouldBeFound("ponudjenaVrijednost.specified=true");

        // Get all the ponudePonudjaciList where ponudjenaVrijednost is null
        defaultPonudePonudjaciShouldNotBeFound("ponudjenaVrijednost.specified=false");
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByPonudjenaVrijednostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where ponudjenaVrijednost is greater than or equal to DEFAULT_PONUDJENA_VRIJEDNOST
        defaultPonudePonudjaciShouldBeFound("ponudjenaVrijednost.greaterThanOrEqual=" + DEFAULT_PONUDJENA_VRIJEDNOST);

        // Get all the ponudePonudjaciList where ponudjenaVrijednost is greater than or equal to UPDATED_PONUDJENA_VRIJEDNOST
        defaultPonudePonudjaciShouldNotBeFound("ponudjenaVrijednost.greaterThanOrEqual=" + UPDATED_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByPonudjenaVrijednostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where ponudjenaVrijednost is less than or equal to DEFAULT_PONUDJENA_VRIJEDNOST
        defaultPonudePonudjaciShouldBeFound("ponudjenaVrijednost.lessThanOrEqual=" + DEFAULT_PONUDJENA_VRIJEDNOST);

        // Get all the ponudePonudjaciList where ponudjenaVrijednost is less than or equal to SMALLER_PONUDJENA_VRIJEDNOST
        defaultPonudePonudjaciShouldNotBeFound("ponudjenaVrijednost.lessThanOrEqual=" + SMALLER_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByPonudjenaVrijednostIsLessThanSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where ponudjenaVrijednost is less than DEFAULT_PONUDJENA_VRIJEDNOST
        defaultPonudePonudjaciShouldNotBeFound("ponudjenaVrijednost.lessThan=" + DEFAULT_PONUDJENA_VRIJEDNOST);

        // Get all the ponudePonudjaciList where ponudjenaVrijednost is less than UPDATED_PONUDJENA_VRIJEDNOST
        defaultPonudePonudjaciShouldBeFound("ponudjenaVrijednost.lessThan=" + UPDATED_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByPonudjenaVrijednostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where ponudjenaVrijednost is greater than DEFAULT_PONUDJENA_VRIJEDNOST
        defaultPonudePonudjaciShouldNotBeFound("ponudjenaVrijednost.greaterThan=" + DEFAULT_PONUDJENA_VRIJEDNOST);

        // Get all the ponudePonudjaciList where ponudjenaVrijednost is greater than SMALLER_PONUDJENA_VRIJEDNOST
        defaultPonudePonudjaciShouldBeFound("ponudjenaVrijednost.greaterThan=" + SMALLER_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByRokIsporukeIsEqualToSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where rokIsporuke equals to DEFAULT_ROK_ISPORUKE
        defaultPonudePonudjaciShouldBeFound("rokIsporuke.equals=" + DEFAULT_ROK_ISPORUKE);

        // Get all the ponudePonudjaciList where rokIsporuke equals to UPDATED_ROK_ISPORUKE
        defaultPonudePonudjaciShouldNotBeFound("rokIsporuke.equals=" + UPDATED_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByRokIsporukeIsInShouldWork() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where rokIsporuke in DEFAULT_ROK_ISPORUKE or UPDATED_ROK_ISPORUKE
        defaultPonudePonudjaciShouldBeFound("rokIsporuke.in=" + DEFAULT_ROK_ISPORUKE + "," + UPDATED_ROK_ISPORUKE);

        // Get all the ponudePonudjaciList where rokIsporuke equals to UPDATED_ROK_ISPORUKE
        defaultPonudePonudjaciShouldNotBeFound("rokIsporuke.in=" + UPDATED_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByRokIsporukeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where rokIsporuke is not null
        defaultPonudePonudjaciShouldBeFound("rokIsporuke.specified=true");

        // Get all the ponudePonudjaciList where rokIsporuke is null
        defaultPonudePonudjaciShouldNotBeFound("rokIsporuke.specified=false");
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByRokIsporukeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where rokIsporuke is greater than or equal to DEFAULT_ROK_ISPORUKE
        defaultPonudePonudjaciShouldBeFound("rokIsporuke.greaterThanOrEqual=" + DEFAULT_ROK_ISPORUKE);

        // Get all the ponudePonudjaciList where rokIsporuke is greater than or equal to UPDATED_ROK_ISPORUKE
        defaultPonudePonudjaciShouldNotBeFound("rokIsporuke.greaterThanOrEqual=" + UPDATED_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByRokIsporukeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where rokIsporuke is less than or equal to DEFAULT_ROK_ISPORUKE
        defaultPonudePonudjaciShouldBeFound("rokIsporuke.lessThanOrEqual=" + DEFAULT_ROK_ISPORUKE);

        // Get all the ponudePonudjaciList where rokIsporuke is less than or equal to SMALLER_ROK_ISPORUKE
        defaultPonudePonudjaciShouldNotBeFound("rokIsporuke.lessThanOrEqual=" + SMALLER_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByRokIsporukeIsLessThanSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where rokIsporuke is less than DEFAULT_ROK_ISPORUKE
        defaultPonudePonudjaciShouldNotBeFound("rokIsporuke.lessThan=" + DEFAULT_ROK_ISPORUKE);

        // Get all the ponudePonudjaciList where rokIsporuke is less than UPDATED_ROK_ISPORUKE
        defaultPonudePonudjaciShouldBeFound("rokIsporuke.lessThan=" + UPDATED_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByRokIsporukeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where rokIsporuke is greater than DEFAULT_ROK_ISPORUKE
        defaultPonudePonudjaciShouldNotBeFound("rokIsporuke.greaterThan=" + DEFAULT_ROK_ISPORUKE);

        // Get all the ponudePonudjaciList where rokIsporuke is greater than SMALLER_ROK_ISPORUKE
        defaultPonudePonudjaciShouldBeFound("rokIsporuke.greaterThan=" + SMALLER_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByJedinicnaCijenaIsEqualToSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where jedinicnaCijena equals to DEFAULT_JEDINICNA_CIJENA
        defaultPonudePonudjaciShouldBeFound("jedinicnaCijena.equals=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the ponudePonudjaciList where jedinicnaCijena equals to UPDATED_JEDINICNA_CIJENA
        defaultPonudePonudjaciShouldNotBeFound("jedinicnaCijena.equals=" + UPDATED_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByJedinicnaCijenaIsInShouldWork() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where jedinicnaCijena in DEFAULT_JEDINICNA_CIJENA or UPDATED_JEDINICNA_CIJENA
        defaultPonudePonudjaciShouldBeFound("jedinicnaCijena.in=" + DEFAULT_JEDINICNA_CIJENA + "," + UPDATED_JEDINICNA_CIJENA);

        // Get all the ponudePonudjaciList where jedinicnaCijena equals to UPDATED_JEDINICNA_CIJENA
        defaultPonudePonudjaciShouldNotBeFound("jedinicnaCijena.in=" + UPDATED_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByJedinicnaCijenaIsNullOrNotNull() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where jedinicnaCijena is not null
        defaultPonudePonudjaciShouldBeFound("jedinicnaCijena.specified=true");

        // Get all the ponudePonudjaciList where jedinicnaCijena is null
        defaultPonudePonudjaciShouldNotBeFound("jedinicnaCijena.specified=false");
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByJedinicnaCijenaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where jedinicnaCijena is greater than or equal to DEFAULT_JEDINICNA_CIJENA
        defaultPonudePonudjaciShouldBeFound("jedinicnaCijena.greaterThanOrEqual=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the ponudePonudjaciList where jedinicnaCijena is greater than or equal to UPDATED_JEDINICNA_CIJENA
        defaultPonudePonudjaciShouldNotBeFound("jedinicnaCijena.greaterThanOrEqual=" + UPDATED_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByJedinicnaCijenaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where jedinicnaCijena is less than or equal to DEFAULT_JEDINICNA_CIJENA
        defaultPonudePonudjaciShouldBeFound("jedinicnaCijena.lessThanOrEqual=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the ponudePonudjaciList where jedinicnaCijena is less than or equal to SMALLER_JEDINICNA_CIJENA
        defaultPonudePonudjaciShouldNotBeFound("jedinicnaCijena.lessThanOrEqual=" + SMALLER_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByJedinicnaCijenaIsLessThanSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where jedinicnaCijena is less than DEFAULT_JEDINICNA_CIJENA
        defaultPonudePonudjaciShouldNotBeFound("jedinicnaCijena.lessThan=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the ponudePonudjaciList where jedinicnaCijena is less than UPDATED_JEDINICNA_CIJENA
        defaultPonudePonudjaciShouldBeFound("jedinicnaCijena.lessThan=" + UPDATED_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisByJedinicnaCijenaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where jedinicnaCijena is greater than DEFAULT_JEDINICNA_CIJENA
        defaultPonudePonudjaciShouldNotBeFound("jedinicnaCijena.greaterThan=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the ponudePonudjaciList where jedinicnaCijena is greater than SMALLER_JEDINICNA_CIJENA
        defaultPonudePonudjaciShouldBeFound("jedinicnaCijena.greaterThan=" + SMALLER_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisBySelectedIsEqualToSomething() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where selected equals to DEFAULT_SELECTED
        defaultPonudePonudjaciShouldBeFound("selected.equals=" + DEFAULT_SELECTED);

        // Get all the ponudePonudjaciList where selected equals to UPDATED_SELECTED
        defaultPonudePonudjaciShouldNotBeFound("selected.equals=" + UPDATED_SELECTED);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisBySelectedIsInShouldWork() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where selected in DEFAULT_SELECTED or UPDATED_SELECTED
        defaultPonudePonudjaciShouldBeFound("selected.in=" + DEFAULT_SELECTED + "," + UPDATED_SELECTED);

        // Get all the ponudePonudjaciList where selected equals to UPDATED_SELECTED
        defaultPonudePonudjaciShouldNotBeFound("selected.in=" + UPDATED_SELECTED);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacisBySelectedIsNullOrNotNull() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList where selected is not null
        defaultPonudePonudjaciShouldBeFound("selected.specified=true");

        // Get all the ponudePonudjaciList where selected is null
        defaultPonudePonudjaciShouldNotBeFound("selected.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPonudePonudjaciShouldBeFound(String filter) throws Exception {
        restPonudePonudjaciMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ponudePonudjaci.getId().intValue())))
            .andExpect(jsonPath("$.[*].sifraPostupka").value(hasItem(DEFAULT_SIFRA_POSTUPKA)))
            .andExpect(jsonPath("$.[*].sifraPonude").value(hasItem(DEFAULT_SIFRA_PONUDE)))
            .andExpect(jsonPath("$.[*].brojPartije").value(hasItem(DEFAULT_BROJ_PARTIJE)))
            .andExpect(jsonPath("$.[*].nazivProizvodjaca").value(hasItem(DEFAULT_NAZIV_PROIZVODJACA)))
            .andExpect(jsonPath("$.[*].nazivPonudjaca").value(hasItem(DEFAULT_NAZIV_PONUDJACA)))
            .andExpect(jsonPath("$.[*].zasticeniNaziv").value(hasItem(DEFAULT_ZASTICENI_NAZIV)))
            .andExpect(jsonPath("$.[*].ponudjenaVrijednost").value(hasItem(DEFAULT_PONUDJENA_VRIJEDNOST.doubleValue())))
            .andExpect(jsonPath("$.[*].rokIsporuke").value(hasItem(DEFAULT_ROK_ISPORUKE)))
            .andExpect(jsonPath("$.[*].jedinicnaCijena").value(hasItem(DEFAULT_JEDINICNA_CIJENA.doubleValue())))
            .andExpect(jsonPath("$.[*].selected").value(hasItem(DEFAULT_SELECTED.booleanValue())));

        // Check, that the count call also returns 1
        restPonudePonudjaciMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPonudePonudjaciShouldNotBeFound(String filter) throws Exception {
        restPonudePonudjaciMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPonudePonudjaciMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPonudePonudjaci() throws Exception {
        // Get the ponudePonudjaci
        restPonudePonudjaciMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPonudePonudjaci() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        int databaseSizeBeforeUpdate = ponudePonudjaciRepository.findAll().size();

        // Update the ponudePonudjaci
        PonudePonudjaci updatedPonudePonudjaci = ponudePonudjaciRepository.findById(ponudePonudjaci.getId()).get();
        // Disconnect from session so that the updates on updatedPonudePonudjaci are not directly saved in db
        em.detach(updatedPonudePonudjaci);
        updatedPonudePonudjaci
            .sifraPostupka(UPDATED_SIFRA_POSTUPKA)
            .sifraPonude(UPDATED_SIFRA_PONUDE)
            .brojPartije(UPDATED_BROJ_PARTIJE)
            .nazivProizvodjaca(UPDATED_NAZIV_PROIZVODJACA)
            .nazivPonudjaca(UPDATED_NAZIV_PONUDJACA)
            .zasticeniNaziv(UPDATED_ZASTICENI_NAZIV)
            .ponudjenaVrijednost(UPDATED_PONUDJENA_VRIJEDNOST)
            .rokIsporuke(UPDATED_ROK_ISPORUKE)
            .jedinicnaCijena(UPDATED_JEDINICNA_CIJENA)
            .selected(UPDATED_SELECTED);

        restPonudePonudjaciMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPonudePonudjaci.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPonudePonudjaci))
            )
            .andExpect(status().isOk());

        // Validate the PonudePonudjaci in the database
        List<PonudePonudjaci> ponudePonudjaciList = ponudePonudjaciRepository.findAll();
        assertThat(ponudePonudjaciList).hasSize(databaseSizeBeforeUpdate);
        PonudePonudjaci testPonudePonudjaci = ponudePonudjaciList.get(ponudePonudjaciList.size() - 1);
        assertThat(testPonudePonudjaci.getSifraPostupka()).isEqualTo(UPDATED_SIFRA_POSTUPKA);
        assertThat(testPonudePonudjaci.getSifraPonude()).isEqualTo(UPDATED_SIFRA_PONUDE);
        assertThat(testPonudePonudjaci.getBrojPartije()).isEqualTo(UPDATED_BROJ_PARTIJE);
        assertThat(testPonudePonudjaci.getNazivProizvodjaca()).isEqualTo(UPDATED_NAZIV_PROIZVODJACA);
        assertThat(testPonudePonudjaci.getNazivPonudjaca()).isEqualTo(UPDATED_NAZIV_PONUDJACA);
        assertThat(testPonudePonudjaci.getZasticeniNaziv()).isEqualTo(UPDATED_ZASTICENI_NAZIV);
        assertThat(testPonudePonudjaci.getPonudjenaVrijednost()).isEqualTo(UPDATED_PONUDJENA_VRIJEDNOST);
        assertThat(testPonudePonudjaci.getRokIsporuke()).isEqualTo(UPDATED_ROK_ISPORUKE);
        assertThat(testPonudePonudjaci.getJedinicnaCijena()).isEqualTo(UPDATED_JEDINICNA_CIJENA);
        assertThat(testPonudePonudjaci.getSelected()).isEqualTo(UPDATED_SELECTED);
    }

    @Test
    @Transactional
    void putNonExistingPonudePonudjaci() throws Exception {
        int databaseSizeBeforeUpdate = ponudePonudjaciRepository.findAll().size();
        ponudePonudjaci.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPonudePonudjaciMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ponudePonudjaci.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ponudePonudjaci))
            )
            .andExpect(status().isBadRequest());

        // Validate the PonudePonudjaci in the database
        List<PonudePonudjaci> ponudePonudjaciList = ponudePonudjaciRepository.findAll();
        assertThat(ponudePonudjaciList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPonudePonudjaci() throws Exception {
        int databaseSizeBeforeUpdate = ponudePonudjaciRepository.findAll().size();
        ponudePonudjaci.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPonudePonudjaciMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ponudePonudjaci))
            )
            .andExpect(status().isBadRequest());

        // Validate the PonudePonudjaci in the database
        List<PonudePonudjaci> ponudePonudjaciList = ponudePonudjaciRepository.findAll();
        assertThat(ponudePonudjaciList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPonudePonudjaci() throws Exception {
        int databaseSizeBeforeUpdate = ponudePonudjaciRepository.findAll().size();
        ponudePonudjaci.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPonudePonudjaciMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ponudePonudjaci))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PonudePonudjaci in the database
        List<PonudePonudjaci> ponudePonudjaciList = ponudePonudjaciRepository.findAll();
        assertThat(ponudePonudjaciList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePonudePonudjaciWithPatch() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        int databaseSizeBeforeUpdate = ponudePonudjaciRepository.findAll().size();

        // Update the ponudePonudjaci using partial update
        PonudePonudjaci partialUpdatedPonudePonudjaci = new PonudePonudjaci();
        partialUpdatedPonudePonudjaci.setId(ponudePonudjaci.getId());

        partialUpdatedPonudePonudjaci
            .sifraPonude(UPDATED_SIFRA_PONUDE)
            .nazivProizvodjaca(UPDATED_NAZIV_PROIZVODJACA)
            .nazivPonudjaca(UPDATED_NAZIV_PONUDJACA)
            .zasticeniNaziv(UPDATED_ZASTICENI_NAZIV)
            .ponudjenaVrijednost(UPDATED_PONUDJENA_VRIJEDNOST)
            .jedinicnaCijena(UPDATED_JEDINICNA_CIJENA)
            .selected(UPDATED_SELECTED);

        restPonudePonudjaciMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPonudePonudjaci.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPonudePonudjaci))
            )
            .andExpect(status().isOk());

        // Validate the PonudePonudjaci in the database
        List<PonudePonudjaci> ponudePonudjaciList = ponudePonudjaciRepository.findAll();
        assertThat(ponudePonudjaciList).hasSize(databaseSizeBeforeUpdate);
        PonudePonudjaci testPonudePonudjaci = ponudePonudjaciList.get(ponudePonudjaciList.size() - 1);
        assertThat(testPonudePonudjaci.getSifraPostupka()).isEqualTo(DEFAULT_SIFRA_POSTUPKA);
        assertThat(testPonudePonudjaci.getSifraPonude()).isEqualTo(UPDATED_SIFRA_PONUDE);
        assertThat(testPonudePonudjaci.getBrojPartije()).isEqualTo(DEFAULT_BROJ_PARTIJE);
        assertThat(testPonudePonudjaci.getNazivProizvodjaca()).isEqualTo(UPDATED_NAZIV_PROIZVODJACA);
        assertThat(testPonudePonudjaci.getNazivPonudjaca()).isEqualTo(UPDATED_NAZIV_PONUDJACA);
        assertThat(testPonudePonudjaci.getZasticeniNaziv()).isEqualTo(UPDATED_ZASTICENI_NAZIV);
        assertThat(testPonudePonudjaci.getPonudjenaVrijednost()).isEqualTo(UPDATED_PONUDJENA_VRIJEDNOST);
        assertThat(testPonudePonudjaci.getRokIsporuke()).isEqualTo(DEFAULT_ROK_ISPORUKE);
        assertThat(testPonudePonudjaci.getJedinicnaCijena()).isEqualTo(UPDATED_JEDINICNA_CIJENA);
        assertThat(testPonudePonudjaci.getSelected()).isEqualTo(UPDATED_SELECTED);
    }

    @Test
    @Transactional
    void fullUpdatePonudePonudjaciWithPatch() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        int databaseSizeBeforeUpdate = ponudePonudjaciRepository.findAll().size();

        // Update the ponudePonudjaci using partial update
        PonudePonudjaci partialUpdatedPonudePonudjaci = new PonudePonudjaci();
        partialUpdatedPonudePonudjaci.setId(ponudePonudjaci.getId());

        partialUpdatedPonudePonudjaci
            .sifraPostupka(UPDATED_SIFRA_POSTUPKA)
            .sifraPonude(UPDATED_SIFRA_PONUDE)
            .brojPartije(UPDATED_BROJ_PARTIJE)
            .nazivProizvodjaca(UPDATED_NAZIV_PROIZVODJACA)
            .nazivPonudjaca(UPDATED_NAZIV_PONUDJACA)
            .zasticeniNaziv(UPDATED_ZASTICENI_NAZIV)
            .ponudjenaVrijednost(UPDATED_PONUDJENA_VRIJEDNOST)
            .rokIsporuke(UPDATED_ROK_ISPORUKE)
            .jedinicnaCijena(UPDATED_JEDINICNA_CIJENA)
            .selected(UPDATED_SELECTED);

        restPonudePonudjaciMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPonudePonudjaci.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPonudePonudjaci))
            )
            .andExpect(status().isOk());

        // Validate the PonudePonudjaci in the database
        List<PonudePonudjaci> ponudePonudjaciList = ponudePonudjaciRepository.findAll();
        assertThat(ponudePonudjaciList).hasSize(databaseSizeBeforeUpdate);
        PonudePonudjaci testPonudePonudjaci = ponudePonudjaciList.get(ponudePonudjaciList.size() - 1);
        assertThat(testPonudePonudjaci.getSifraPostupka()).isEqualTo(UPDATED_SIFRA_POSTUPKA);
        assertThat(testPonudePonudjaci.getSifraPonude()).isEqualTo(UPDATED_SIFRA_PONUDE);
        assertThat(testPonudePonudjaci.getBrojPartije()).isEqualTo(UPDATED_BROJ_PARTIJE);
        assertThat(testPonudePonudjaci.getNazivProizvodjaca()).isEqualTo(UPDATED_NAZIV_PROIZVODJACA);
        assertThat(testPonudePonudjaci.getNazivPonudjaca()).isEqualTo(UPDATED_NAZIV_PONUDJACA);
        assertThat(testPonudePonudjaci.getZasticeniNaziv()).isEqualTo(UPDATED_ZASTICENI_NAZIV);
        assertThat(testPonudePonudjaci.getPonudjenaVrijednost()).isEqualTo(UPDATED_PONUDJENA_VRIJEDNOST);
        assertThat(testPonudePonudjaci.getRokIsporuke()).isEqualTo(UPDATED_ROK_ISPORUKE);
        assertThat(testPonudePonudjaci.getJedinicnaCijena()).isEqualTo(UPDATED_JEDINICNA_CIJENA);
        assertThat(testPonudePonudjaci.getSelected()).isEqualTo(UPDATED_SELECTED);
    }

    @Test
    @Transactional
    void patchNonExistingPonudePonudjaci() throws Exception {
        int databaseSizeBeforeUpdate = ponudePonudjaciRepository.findAll().size();
        ponudePonudjaci.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPonudePonudjaciMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ponudePonudjaci.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ponudePonudjaci))
            )
            .andExpect(status().isBadRequest());

        // Validate the PonudePonudjaci in the database
        List<PonudePonudjaci> ponudePonudjaciList = ponudePonudjaciRepository.findAll();
        assertThat(ponudePonudjaciList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPonudePonudjaci() throws Exception {
        int databaseSizeBeforeUpdate = ponudePonudjaciRepository.findAll().size();
        ponudePonudjaci.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPonudePonudjaciMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ponudePonudjaci))
            )
            .andExpect(status().isBadRequest());

        // Validate the PonudePonudjaci in the database
        List<PonudePonudjaci> ponudePonudjaciList = ponudePonudjaciRepository.findAll();
        assertThat(ponudePonudjaciList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPonudePonudjaci() throws Exception {
        int databaseSizeBeforeUpdate = ponudePonudjaciRepository.findAll().size();
        ponudePonudjaci.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPonudePonudjaciMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ponudePonudjaci))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PonudePonudjaci in the database
        List<PonudePonudjaci> ponudePonudjaciList = ponudePonudjaciRepository.findAll();
        assertThat(ponudePonudjaciList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePonudePonudjaci() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        int databaseSizeBeforeDelete = ponudePonudjaciRepository.findAll().size();

        // Delete the ponudePonudjaci
        restPonudePonudjaciMockMvc
            .perform(delete(ENTITY_API_URL_ID, ponudePonudjaci.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PonudePonudjaci> ponudePonudjaciList = ponudePonudjaciRepository.findAll();
        assertThat(ponudePonudjaciList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
