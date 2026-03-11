package fr.unantes.software.construction.associations;

/**
 * Created on 27/01/2018.
 *
 * @author sunye.
 */
public class SimpleTask implements Task {
    private String title;
    private SingleReference<Event> event = new SingleReferenceEvent(this);
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String newTitle) {
        title = newTitle;
    }

    @Override
    public SingleReference<Event> event() {
        return event;
    }

}
