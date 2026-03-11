package fr.unantes.software.construction.date;

import org.jetbrains.annotations.Contract;

import static org.atlanmod.commons.Guards.*;

/**
 * Created on 28/11/2017.
 *
 * @author AtlanMod team.
 */
public class Date {

    private int day;
    private int month;
    private int year;

    /**
     *
     * @param d an {@link int} between 1 and 31
     * @param m an {@link int} between 1 and 12
     * @param y an {@link int} greater than 1582.
     */
    public Date(int d, int m, int y) {
        checkArgument(isValidDate(d, m, y), "Invalid Date");

        day = d;
        month = m;
        year = y;
    }

    /**
     * Returns the day of week for this date.
     *
     * @return 0 for  Saturday, 1 for Sunday, etc.
     */
    @Contract(pure = true)
    public int dayOfWeek() {
        /*
         * Here we use Zeller's algorithm to calculate the day of the week.
         */
        checkArgument(isValid(),"Valid");
        checkArgument(year >1582,
                "Year %s is not valid. Only the Gregorian calendar is taken into account", year);

        int hundreds = this.year / 100;
        int centuryYear = this.year % 100;
        int m = this.month;
        int result;

        // march = 3; jan = 13, feb = 14 of previous year.
        if (m < 3) {
            m += 12;
            if (centuryYear > 0) {
                centuryYear -= 1;
            } else {
                centuryYear = 99;
                hundreds -= 1;
            }
        }

        // Zeller formula.
        result = ((this.day + ((26 * (m + 1)) / 10) +
                centuryYear + (centuryYear / 4) + (hundreds / 4)
                - (2 * hundreds)) % 7);

        // remainder to modulo
        if (result < 0) {
            result = result + 7;
        }

        assert result >=0 && result < 7 :"Valid result";

        return result;
    }

    @Contract(pure = true)
    private boolean isValidDate(int d, int m, int y) {
        return d >= 1 && d <= 31 &&
                m >= 1 && m <= 12 &&
                y > 1582;
    }


    private boolean isValid() {
        return this.isValidDate(day, month, year);
    }

    /**
     * Returns true if the argument is a lean year
     * @param year a valid year
     * @return a boolean
     */
    public static boolean isLeapYear(int year) {
        return (year % 400 == 0) || ((year % 4 == 0) && !(year % 100 == 0));
    }

    @Contract(pure = true)
    public int day() {
        return day;
    }

    @Contract(pure = true)
    public int month() {
        return month;
    }

    @Contract(pure = true)
    public int year() {
        return year;
    }
}