package fr.unantes.software.construction.associations;

public class SingleReferenceEvent implements SingleReference<Event>{

    private Event event;
    private final Task task;

    public SingleReferenceEvent(Task task){
        this.task = task;
    }

    @Override
    public void set(Event newValue) {
      this.basicSet(newValue);
      newValue.task().basicSet(task);
    }

    @Override
    public Event get() {
        return event;
    }

    @Override
    public void unset() {

        if(this.event != null)
            this.event.task().basicUnSet();

        this.basicUnSet();

    }

    @Override
    public boolean isSet() {
        return event!=null;
    }

    @Override
    public void basicSet(Event newValue) {
        this.unset();
        this.event = newValue;

    }

    @Override
    public void basicUnSet() {
        event = null;
    }
}
