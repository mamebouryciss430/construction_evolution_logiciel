package fr.unantes.software.construction.associations;

import java.util.List;

public class SingleReferenceTask implements SingleReference<Task>{
    private Task task ;
    private final Event event;

    public SingleReferenceTask(Event event) {
        this.event = event;
    }

    @Override
    public void set(Task newValue) {
        this.basicSet(newValue);
        newValue.event().basicSet(event);
    }

    @Override
    public Task get() {
        return task;
    }

    @Override
    public void unset() {
        if(this.task != null)
           this.task.event().basicUnSet();

        this.basicUnSet();

    }

    @Override
    public boolean isSet() {
        return task!=null;
    }

    @Override
    public void basicSet(Task newValue) {
        this.unset();
        this.task = newValue;

    }

    @Override
    public void basicUnSet() {
        task=null;
    }
}
