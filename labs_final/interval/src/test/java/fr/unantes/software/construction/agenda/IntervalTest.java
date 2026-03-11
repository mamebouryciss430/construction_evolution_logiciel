package fr.unantes.software.construction.agenda;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created on 21/02/2018.
 *
 */
class IntervalTest {

    /**
     * Checks if the Interval::includes() methods returns true for values inside the interval.
     * Uses the inner boundaries as test data, as well as some values between them.
     */
    @ParameterizedTest()
    @CsvSource({"2","3","4"})
    //@ValueSource(ints = {2,4})
    void testIncludes(Integer a) {
        Interval<Integer> i = new Interval<Integer>(2,4);
        assertThat(i.includes(a)).isTrue();
    }

    /**
     *  Checks if the Interval::includes() methods returns false for values outside the interval.
     *  Uses the outer boundaries as test data, as well as Integer.MIN_VALUE and
     *  Integer.MAX_VALUE
     *
     */
    @ParameterizedTest()
    @CsvSource({"0","1","5"})
    void testNotIncludes(Integer a) {
        Interval<Integer> i = new Interval<Integer>(2,4);
        assertThat(i.includes(a)).isFalse();
    }

    /**
     * Given the intervals [a,b] and [c,d], checks if the Interval::overlapsWith()
     * method returns true for the case where:
     * [a,b] contains c.
     */
    @ParameterizedTest()
    @CsvSource({"2,6,5,9"})
    void testOverlapsWithOtherFirst(Integer a, Integer b, Integer c, Integer d) {
        Interval<Integer> i1 = new Interval<Integer>(a,b);
        Interval<Integer> i2 = new Interval<Integer>(c,d);
        assertThat(i1.overlapsWith(i2)).isTrue();

    }

    /**
     * Given the intervals [a,b] and [c,d], checks if the Interval::overlapsWith()
     * method returns true for the case where:
     * [a,b] contains d.
     */
    @ParameterizedTest()
    @CsvSource({"2,6,1,5"})
    void testOverlapsWithOtherLast(Integer a, Integer b, Integer c, Integer d) {
        Interval<Integer> i1 = new Interval<Integer>(a,b);
        Interval<Integer> i2 = new Interval<Integer>(c,d);
        assertThat(i1.overlapsWith(i2)).isTrue();
    }

    /**
     * Given the intervals [a,b] and [c,d], checks if the Interval::overlapsWith()
     * method returns true for the case where:
     * [a,b] contains c and d.
     */
    @ParameterizedTest()
    @CsvSource({"1,4,2,3"})
    void testOverlapsWithOtherBoth(Integer a, Integer b, Integer c, Integer d) {
        Interval<Integer> i1 = new Interval<Integer>(a,b);
        Interval<Integer> i2 = new Interval<Integer>(c,d);

        assertThat(i1.overlapsWith(i2)).isTrue();
    }

    /**
     * Given the intervals [a,b] and [c,d], checks if the Interval::overlapsWith()
     * is commutative: if [a,b] overlaps with [c,d], then [c,d] also overlaps with
     * [a,b].
     */
    @ParameterizedTest()
    @CsvSource({"2,6,1,4"})
    void testOverlapsWithIsCommutative(Integer a, Integer b, Integer c, Integer d) {
        Interval<Integer> i1 = new Interval<Integer>(a,b);
        Interval<Integer> i2 = new Interval<Integer>(c,d);

        assertThat(i1.overlapsWith(i2)).isTrue();
        assertThat(i2.overlapsWith(i1)).isTrue();
    }

    /**
     * Given the intervals [a,b] and [c,d], checks if the Interval::overlapsWith()
     * method returns false for the case where:
     * c < a and d < a.
     */
    @ParameterizedTest()
    @CsvSource({"5,8,1,4"})
    void testNotOverlapsWithInferiorValues(Integer a, Integer b, Integer c, Integer d) {
        Interval<Integer> i1 = new Interval<Integer>(a,b);
        Interval<Integer> i2 = new Interval<Integer>(c,d);

        assertThat(i1.overlapsWith(i2)).isFalse();
    }

    /**
     * Given the intervals [a,b] and [c,d], checks if the Interval::overlapsWith()
     * method returns false for the case where:
     * c > b and d > b.
     */
    @ParameterizedTest()
    @CsvSource({"1,4,5,8"})
    void testNotOverlapsWithSuperiorValues(Integer a, Integer b, Integer c, Integer d) {
        Interval<Integer> i1 = new Interval<Integer>(a,b);
        Interval<Integer> i2 = new Interval<Integer>(c,d);

        assertThat(i1.overlapsWith(i2)).isFalse();
    }
}