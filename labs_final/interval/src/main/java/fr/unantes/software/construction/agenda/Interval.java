package fr.unantes.software.construction.agenda;

/**
 * Created on 13/02/2018.
 *
 */
public class Interval<T extends Comparable> {
// le type T doit etre comparable
    private T begin;
    private T end;
    protected Interval(T begin, T end) {
        this.begin = begin;
        this.end = end;
    }

    public boolean includes(T value) {

        return (value.compareTo(this.begin) >= 0 && value.compareTo(this.end) <=0);
    }

    public boolean overlapsWith(Interval<T> other) {

        return this.includes(other.begin)|| this.includes(other.end);
    }

}

