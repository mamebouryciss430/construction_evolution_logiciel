package fr.unantes.software.construction.attributes;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


/**
 * Created on 21/01/2018.
 *
 * @author NaoMod team.
 */
class SimpleEventTest {

    private LocalDateTime start;
    private LocalDateTime end;

    @BeforeEach
    void setup() {
        start = LocalDateTime.of(2018, 1, 21, 12, 0);
        end = LocalDateTime.of(2018, 1, 22, 12, 0);
    }

    @Test
    void testConstructor() {
        Throwable thrown = catchThrowable(() -> {
            new SimpleEvent(12, "Lab", end, start);
        });

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getId() {
        Event evt = new SimpleEvent(12, "Lab", start, end);

        assertThat(evt.getId()).isEqualTo(12);
    }

    @Test
    void getName() {
        Event evt = new SimpleEvent(12, "Lab", start, end);

        assertThat(evt.getName()).isEqualTo("Lab");
    }

    @Test
    void setName() {
        Event evt = new SimpleEvent(12, "Lab", start, end);
        evt.setName("Other");

        assertThat(evt.getName()).isEqualTo("Other");
    }

    @Test
    void testLocation() {
        Event evt = new SimpleEvent(12, "Lab", start, end);
        evt.setLocation("Nantes");

        assertThat(evt.getLocation()).isEqualTo("Nantes");
    }

    @Test
    void testAlarm() {
        Event evt = new SimpleEvent(12, "Lab", start, end);
        evt.addAlarm(AlarmKind.Message);
        evt.addAlarm(AlarmKind.Audio);
        evt.addAlarm(AlarmKind.Email
        );

        // Use SoftAssertions
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(evt.getAlarm(0)).isEqualTo(AlarmKind.Message);
        softly.assertThat(evt.getAlarm(1)).isEqualTo(AlarmKind.Audio);
        softly.assertThat(evt.getAlarm(2)).isEqualTo(AlarmKind.Email);
        softly.assertAll();
    }

    @Test
    void testAlarmExceeding() {
        Event evt = new SimpleEvent(12, "Lab", start, end);
        for (int i = 0; i < 5; i++) {
            evt.addAlarm(AlarmKind.Email);
        }

        assertThat(evt.addAlarm(AlarmKind.Audio)).isFalse();
    }


    @Test
    void removeAlarm() {
        Event evt = new SimpleEvent(12, "Lab", start, end);
        evt.addAlarm(AlarmKind.Message);
        evt.addAlarm(AlarmKind.Audio);
        evt.addAlarm(AlarmKind.Email);

        evt.removeAlarm(1);

        assertThat(evt.getAlarm(1)).isEqualTo(AlarmKind.Email);
    }
}
