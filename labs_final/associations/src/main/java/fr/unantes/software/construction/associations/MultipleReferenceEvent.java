package fr.unantes.software.construction.associations;

import java.util.ArrayList;
import java.util.List;

public class MultipleReferenceEvent implements MultipleReference<Event>{

    private final List<Event> events = new ArrayList<>();
    private final int size = 4;
    @Override
    public boolean add(Event value) {
        if(events.size()>=size)
            return false ;
        return events.add(value);    }

    @Override
    public void remove(Event value) {
        events.remove(value);
    }

    @Override
    public boolean contains(Event value) {
        return events.contains(value);
    }

    @Override
    public int size() {
        return events.size();
    }

    @Override
    public void basicAdd(Event value) {
         this.add(value);
    }

    @Override
    public void basicRemove(Event value) {
       this.remove(value);
    }
}
