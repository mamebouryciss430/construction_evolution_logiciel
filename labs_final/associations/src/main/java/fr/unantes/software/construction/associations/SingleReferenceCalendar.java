package fr.unantes.software.construction.associations;

public class SingleReferenceCalendar implements SingleReference<Calendar> {
    private Calendar calendar;
    private final Event event;

    public SingleReferenceCalendar(Event event) {
        this.event = event;
    }

    @Override
    public void set(Calendar newValue) {
       this.basicSet(newValue);
       newValue.events().add(event);
    }

    @Override
    public Calendar get() {
        return calendar;
    }

    @Override
    public void unset() {
        if(this.calendar != null)
            this.calendar.events().basicRemove(event);

        this.basicUnSet();
    }

    @Override
    public boolean isSet() {
        return calendar!=null;
    }

    @Override
    public void basicSet(Calendar newValue) {
        this.unset();
        this.calendar = newValue;
    }

    @Override
    public void basicUnSet() {
         calendar = null;
    }
}
