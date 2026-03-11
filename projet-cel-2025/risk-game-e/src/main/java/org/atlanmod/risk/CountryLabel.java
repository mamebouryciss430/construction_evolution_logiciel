package org.atlanmod.risk;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JLabel;

/**
 * Allows the creation of JLists with updating strings from the org.atlanmod.risk.RiskModel.
 * This class allows the creation of lists that stay updated to the lists of current 
 * player's hand, occupied countries, and countries adjacent to the selected, occupied 
 * country.
 * @author Ted Mader
 * @version Alpha
 * @date 5/02/14
 **/
public class CountryLabel extends JLabel implements PropertyChangeListener {


	private final String name;

    private final transient Country country;
	
    public CountryLabel (RiskModel model, Country country) {
	
        super();
        this.country = country;
		name = country.getName();
		setText(name);
		model.addPropertyChangeListener(this);
    }


	// Updates the state of the RiskList
	public void propertyChange(PropertyChangeEvent evt) {

		if (!"countryA".equals(evt.getPropertyName())) {
			return;
		}

		updateLabel();
	}

	private void updateLabel() {
		if (country.getOccupant() == null) {
			setText(name);
		} else {
			setText(name + ": " + country.getArmies()
					+ ", " + country.getOccupant().getName());
		}
	}
}