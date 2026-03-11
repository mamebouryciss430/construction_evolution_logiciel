package fr.unantes.software.construction.associations;

/**
 * Created on 28/01/2018.
 *
 * @author sunye.
 */
public interface MultipleReference<T> {

    boolean add (T value);

    void remove(T value);

    boolean contains(T value);

    int size();

    void basicAdd(T value);

    void basicRemove(T value);
}
