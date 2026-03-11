package fr.unantes.software.construction.associations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 27/01/2018.
 *
 * @author sunye.
 */
class SimpleCalendarTest {

    private Calendar calendar;

    @BeforeEach
    void setUp() {
        calendar = new SimpleCalendar();
    }

    @Test
    void testName() {
        String expected = "##@##";
        calendar.setName(new String(expected));

        assertThat(calendar.getName()).isEqualTo(expected);
    }

    @Test
    void testDescription() {
        String expected = "##@##";
        calendar.setDescription(new String(expected));

        assertThat(calendar.getDescription()).isEqualTo(expected);
    }

    @Test
    void testBidirectionalAdd() {
        Event[] events = {new SimpleEvent(Integer.valueOf(1)), new SimpleEvent(Integer.valueOf(2)),
                new SimpleEvent(Integer.valueOf(3)), new SimpleEvent(Integer.valueOf(4))};

        for (Event each : events) {
            calendar.events().add(each);
        }

        for (Event each : events) {
            assertThat(calendar.events().contains(each)).isTrue();
            assertThat(each.calendar().get()).isEqualTo(calendar);
        }
    }

    @Test
    void testCompleteHandshake() {
        Calendar cal2 = new SimpleCalendar();

        Event[] events = {new SimpleEvent(Integer.valueOf(1)), new SimpleEvent(Integer.valueOf(2)),
                new SimpleEvent(Integer.valueOf(3)), new SimpleEvent(Integer.valueOf(4))};

        for (Event each : events) {
            calendar.events().add(each);
        }

        for (Event each : events) {
            cal2.events().add(each);
        }

        for (Event each : events) {
            assertThat(calendar.events().contains(each)).isFalse();
            assertThat(each.calendar().get()).isEqualTo(cal2);
        }
    }

    @Test
    void testRemove() {
        Event[] events = {new SimpleEvent(Integer.valueOf(1)), new SimpleEvent(Integer.valueOf(2)),
                new SimpleEvent(Integer.valueOf(3)), new SimpleEvent(Integer.valueOf(4))};

        for (Event each : events) {
            calendar.events().add(each);
        }

        assertThat(calendar.events().size()).isEqualTo(events.length);

        for (Event each : events) {
            calendar.events().remove(each);
        }

        for (Event each : events) {
            assertThat(each.calendar().isSet()).isFalse();
        }

        assertThat(calendar.events().size()).isEqualTo(0);
    }

    @Test
    void testNoSideEffectAfterRemove() {
        Event event = new SimpleEvent(Integer.valueOf(1));
        Calendar other = new SimpleCalendar();

        calendar.events().add(event);
        assertThat(calendar.events().contains(event)).isTrue();

        other.events().remove(event);
        assertThat(calendar.events().contains(event)).isTrue();

    }

}