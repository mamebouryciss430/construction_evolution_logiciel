package org.atlanmod.risk;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import static org.atlanmod.risk.StringUtils.clean;

/**
 * This class maps the user's actions in RiskList objects to the data and methods in the
 * model.
 * @author Ted Mader
 * @version Alpha
 * @date 5/02/14
 **/


public class RiskListController implements ListSelectionListener {

    private final RiskModel model;
    private final BoardView view;

    public RiskListController(RiskModel model, BoardView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void valueChanged(ListSelectionEvent evt) {
        if (!evt.getValueIsAdjusting() && view.getCountryAIndex() != -1) {
            model.setCountryASelection(
                    clean(view.getCountryA())
            );
        }
    }
}
