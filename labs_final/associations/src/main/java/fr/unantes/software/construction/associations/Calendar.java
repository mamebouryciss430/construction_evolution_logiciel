package fr.unantes.software.construction.associations;

/**
 * Created on 27/01/2018.
 *
 * @author sunye.
 */
public interface Calendar {

    String getName();

    void setName(String str);

    String getDescription();

    void setDescription(String str);

    MultipleReference<Event> events();

}
