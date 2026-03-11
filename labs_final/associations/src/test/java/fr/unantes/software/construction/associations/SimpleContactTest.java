package fr.unantes.software.construction.associations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 27/01/2018.
 *
 * @author sunye.
 */
class SimpleContactTest {
    private Contact ctc1;

    @BeforeEach
    void setup() {
        ctc1 = new SimpleContact();
    }

    @Test
    void testFirstName() {
        String expected = "##@##";
        ctc1.setFirstName(expected);

        assertThat(ctc1.getFirstName()).isEqualTo(expected);
    }

    @Test
    void testLastName() {
        String expected = "##@##";
        ctc1.setLastName(expected);

        assertThat(ctc1.getLastName()).isEqualTo(expected);
    }
}