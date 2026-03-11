package fr.unantes.software.construction.associations;

/**
 * Created on 28/01/2018.
 *
 * @author sunye.
 */
public interface SingleReference<T> {

    void set(T newValue);

    T get();

    void unset();

    boolean isSet();

    void basicSet(T newValue);
    
    void basicUnSet();
}
