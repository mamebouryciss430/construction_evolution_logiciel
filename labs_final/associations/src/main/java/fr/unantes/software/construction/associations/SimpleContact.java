package fr.unantes.software.construction.associations;

/**
 * Created on 27/01/2018.
 *
 * @author sunye.
 */
public class SimpleContact implements Contact{
    private String firstName;
    private String lastName;

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String str) {
        firstName = str;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String str) {
      lastName = str;
    }
}
