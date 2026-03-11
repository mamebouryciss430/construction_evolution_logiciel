package fr.unantes.software.construction.associations;

/**
 * Created on 27/01/2018.
 *
 * @author sunye.
 */
public interface Task {

    String getTitle();

    void setTitle(String newTitle);

    SingleReference<Event> event();
}
