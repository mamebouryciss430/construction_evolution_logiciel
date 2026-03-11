package fr.unantes.software.construction.associations;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 27/01/2018.
 *
 * @author ardourel;
 */
class SimpleEventTest {

    Event evt1;

    @BeforeEach
    void setUp() {
        evt1 = new SimpleEvent(Integer.valueOf(1));
    }

    @Test
    void testId() {
        assertThat(evt1.getId()).isEqualTo(Integer.valueOf(1));
    }

    @Test
    void testName() {
        String expected = "Alice";
        evt1.setName(new String(expected));

        assertThat(evt1.getName()).isEqualTo(expected);
    }

    @Test
    void testLocation() {
        String expected = "Nantes";
        evt1.setLocation(new String(expected));

        assertThat(evt1.getLocation()).isEqualTo(expected);
    }

    @Test
    void testAlarm() {
        Event evt = new SimpleEvent(12);
        evt.addAlarm(Integer.valueOf(42));
        evt.addAlarm(Integer.valueOf(13));
        evt.addAlarm(Integer.valueOf(99));


        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(evt.getAlarm(0)).isEqualTo(Integer.valueOf(42));
        softly.assertThat(evt.getAlarm(1)).isEqualTo(Integer.valueOf(13));
        softly.assertThat(evt.getAlarm(2)).isEqualTo(Integer.valueOf(99));
        softly.assertAll();
    }

    @Test
    void testAlarmExceeding() {
        Event evt = new SimpleEvent(12);
        for (int i = 0; i < 5; i++) {
            evt.addAlarm(Integer.valueOf(i));
        }

        assertThat(evt.addAlarm(Integer.valueOf(42))).isFalse();
    }


    @Test
    void removeAlarm() {
        Event evt = new SimpleEvent(12);
        evt.addAlarm(Integer.valueOf(42));
        evt.addAlarm(Integer.valueOf(13));
        evt.addAlarm(Integer.valueOf(99));

        evt.removeAlarm(1);

        assertThat(evt.getAlarm(1)).isEqualTo(Integer.valueOf(99));
    }

    @Test
    void testAddContacts() {
        Contact[] contacts = {new SimpleContact(), new SimpleContact(),
                new SimpleContact(), new SimpleContact()};

        for (Contact each : contacts) {
            evt1.invitees().add(each);
        }

        for (Contact each : contacts) {
            assertThat(evt1.invitees().contains(each)).isTrue();
        }

        for (Contact each : contacts) {
            evt1.invitees().remove(each);
        }

        assertThat(evt1.invitees().size()).isZero();

    }

    @Test
    void testSetTask() {
        Task task1 = new SimpleTask();

        evt1.task().set(task1);

        assertThat(task1.event().get()).isEqualTo(evt1);
    }

    @Test
    void testTaskCompleteHandshake() {
        Event evt2 = new SimpleEvent(Integer.valueOf(2));
        Task task1 = new SimpleTask();
        Task task2 = new SimpleTask();

        evt1.task().set(task1);
        evt2.task().set(task2);

        evt1.task().set(task2);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(task1.event().isSet()).isFalse();
        softly.assertThat(evt2.task().isSet()).isFalse();
        softly.assertThat(task2.event().get()).isEqualTo(evt1);
        softly.assertAll();
    }

    @Test
    void testSetCalendar() {
        Calendar cal1 = new SimpleCalendar();
        Calendar cal2 = new SimpleCalendar();

        evt1.calendar().set(cal1);
        assertThat(cal1.events().contains(evt1)).isTrue();

        evt1.calendar().set(cal2);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(cal1.events().contains(evt1)).isFalse();
        softly.assertThat(cal2.events().contains(evt1)).isTrue();
        softly.assertThat(evt1.calendar().get()).isEqualTo(cal2);
        softly.assertAll();
    }
}