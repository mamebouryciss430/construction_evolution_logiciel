package fr.unantes.software.construction.associations;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 27/01/2018.
 *
 * @author sunye.
 */
class SimpleTaskTest {

    private Task task1;

    @BeforeEach
    void setup() {
        task1 = new SimpleTask();
    }

    @Test
    void testTitle() {
        String expected = "My Task";
        task1.setTitle(new String(expected));

        assertThat(task1.getTitle()).isEqualTo(expected);
    }


    @Test
    void testEventSet() {
        Event evt1 = new SimpleEvent(Integer.valueOf(1));
        task1.event().set(evt1);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(evt1.task().get()).isEqualTo(task1);
        softly.assertThat(task1.event().get()).isEqualTo(evt1);
        softly.assertAll();
    }

    @Test
    void testHandshake() {
        Task task2 = new SimpleTask();
        Task task3 = new SimpleTask();
        Event evt1 = new SimpleEvent(Integer.valueOf(1));

        task2.event().set(evt1);
        task3.event().set(evt1);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(evt1.task().get()).isEqualTo(task3);
        softly.assertThat(task2.event().isSet()).isFalse();
        softly.assertAll();
    }

    @Test
    void testCompleteHandshake() {
        Task task2 = new SimpleTask();
        Event evt1 = new SimpleEvent(Integer.valueOf(1));
        Event evt2 = new SimpleEvent(Integer.valueOf(2));

        task1.event().set(evt1);
        task2.event().set(evt2);

        task2.event().set(evt1);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(evt2.task().isSet()).isFalse();
        softly.assertThat(task1.event().isSet()).isFalse();
        softly.assertAll();
    }

    @Test
    void testUnset() {
        Task task2 = new SimpleTask();
        Event evt1 = new SimpleEvent(Integer.valueOf(1));

        task2.event().set(evt1);
        task2.event().unset();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(task2.event().isSet()).isFalse();
        softly.assertThat(evt1.task().isSet()).isFalse();
        softly.assertAll();
    }

}