package fr.unantes.software.construction.associations;

/**
 * Created on 27/01/2018.
 *
 * @author sunye.
 */
public class SimpleCalendar implements Calendar {
    MultipleReference<Event> events = new MultipleReferenceEvent();
    private String name;
    private String desciption;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String str) {
       name = str;
    }

    @Override
    public String getDescription() {
        return desciption;
    }

    @Override
    public void setDescription(String str) {
        desciption = str;
    }

    @Override
    public MultipleReference<Event> events() {
        return events;
    }

}
