package fr.unantes.software.construction.associations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 27/01/2018.
 *
 * @author sunye.
 */
public class SimpleEvent implements Event {

    private Integer id;
    private  final Integer max_size= 5;
    private String name;
    private String location;
    private final int MAX_SIZE = 5;
    private final List<Integer> alarm = new ArrayList<>();
    private final MultipleReference<Contact> invitees= new MultipleReferenceContact();
    private final SingleReference<Task> task = new SingleReferenceTask(this);
    private final SingleReference<Calendar> calendar = new SingleReferenceCalendar(this);

    public SimpleEvent(Integer id) {
       this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getName() {

        return name;
    }

    @Override
    public void setName(String str) {
          name = str;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public void setLocation(String str) {
        location = str;
    }

    @Override
    public boolean addAlarm(Integer i) {
        if(alarm.size()>=MAX_SIZE)
            return false;
        return alarm.add(i);
    }

    @Override
    public Integer getAlarm(int i) {
        return alarm.get(i);
    }

    @Override
    public Integer removeAlarm(int i) {
        return alarm.remove(i);
    }

    @Override
    public SingleReference<Calendar> calendar() {
        return calendar;
    }

    @Override
    public SingleReference<Task> task() {
        return task;
    }

    @Override
    public MultipleReference<Contact> invitees() {
        return invitees;
    }

}
