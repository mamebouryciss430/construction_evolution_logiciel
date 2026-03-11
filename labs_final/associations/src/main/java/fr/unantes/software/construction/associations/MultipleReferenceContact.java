package fr.unantes.software.construction.associations;

import java.util.ArrayList;
import java.util.List;

public class MultipleReferenceContact implements MultipleReference<Contact>{
    private final List<Contact> contacts = new ArrayList<>();
    @Override
    public boolean add(Contact value) {
      return contacts.add(value);
    }

    @Override
    public void remove(Contact value) {
      contacts.remove(value);
    }

    @Override
    public boolean contains(Contact value) {
        return contacts.contains(value);
    }

    @Override
    public int size() {
        return contacts.size();
    }

    @Override
    public void basicAdd(Contact value) {
        this.add(value);
    }

    @Override
    public void basicRemove(Contact value) {
       this.remove(value);
    }
}
