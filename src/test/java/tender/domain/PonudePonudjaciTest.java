package tender.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tender.web.rest.TestUtil;

class PonudePonudjaciTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PonudePonudjaci.class);
        PonudePonudjaci ponudePonudjaci1 = new PonudePonudjaci();
        ponudePonudjaci1.setId(1L);
        PonudePonudjaci ponudePonudjaci2 = new PonudePonudjaci();
        ponudePonudjaci2.setId(ponudePonudjaci1.getId());
        assertThat(ponudePonudjaci1).isEqualTo(ponudePonudjaci2);
        ponudePonudjaci2.setId(2L);
        assertThat(ponudePonudjaci1).isNotEqualTo(ponudePonudjaci2);
        ponudePonudjaci1.setId(null);
        assertThat(ponudePonudjaci1).isNotEqualTo(ponudePonudjaci2);
    }
}
