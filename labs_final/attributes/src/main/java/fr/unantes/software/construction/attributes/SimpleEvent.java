package fr.unantes.software.construction.attributes;

import java.time.LocalDateTime;

/**
 * Created on 21/01/2018.
 *
 */
public class SimpleEvent implements Event {

    
    /**
     * Creates a new event. Throws a IllegalArgumentException if the arguments are not valid:
     * null name or a end date that precedes the start date.
     *
     * @param id this event unique identifier
     * @param name this event name
     * @param start this event start date
     * @param end this event end date
     */
    public SimpleEvent(Integer id, String name, LocalDateTime start, LocalDateTime end) {

    }

    @Override
    public Integer getId() {
        //TODO
        return null;
    }

    @Override
    public String getName() {
        //TODO
        return null;
    }

    @Override
    public void setName(String str) {
        //TODO
    }

    @Override
    public String getLocation() {
        //TODO
        return null;
    }

    @Override
    public void setLocation(String str) {
        //TODO
    }

    @Override
    public boolean addAlarm(AlarmKind kind) {
        return false;
        //TODO
    }

    @Override
    public AlarmKind getAlarm(int position) {
        return null;
        //TODO
    }

    @Override
    public AlarmKind removeAlarm(int position) {
        //TODO
        return null;
    }

    LocalDateTime getStart() {
        //TODO
        return null;
    }

    void setStart(LocalDateTime cal) {
        //TODO
    }

    LocalDateTime getEnd() {
        //TODO
        return null;
    }

    void setEnd(LocalDateTime cal) {
        //TODO
    }
}
