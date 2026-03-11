package org.atlanmod.risk;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.DefaultListModel;

/**
 * Allows the creation of JLists with updating strings from the org.atlanmod.risk.RiskModel.
 * This class allows the creation of lists that stay updated to the lists of current 
 * player's hand, occupied countries, and countries adjacent to the selected, occupied 
 * country.
 * @author Ted Mader
 * @version Alpha
 * @date 5/02/14
 **/
public class RiskListModel extends DefaultListModel<String> implements PropertyChangeListener {


	private final String type;
    private final transient RiskModel model;

    public RiskListModel (RiskModel model, String type) {

        super();
        this.model = model;
		this.type = type;
		model.addPropertyChangeListener(this);
    }

	// Updates the state of the RiskList
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();

		if ("cards".equals(type) && "cards".equals(propName)) {
			refreshList(model.getCardsList());
		} else if ("countryA".equals(type) && "countryA".equals(propName)) {
			refreshList(model.getCountryAList());
		} else if ("countryB".equals(type) && "countryB".equals(propName)) {
			refreshList(model.getCountryBList());
		}
	}

	private void refreshList(java.util.List<String> list) {
		removeAllElements();
		for (String item : list) {
			addElement(item);
		}
	}
}