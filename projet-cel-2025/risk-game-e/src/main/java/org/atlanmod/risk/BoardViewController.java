package org.atlanmod.risk;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import static org.atlanmod.risk.StringUtils.clean;

/**
 * This class maps the user's actions in the org.atlanmod.risk.BoardView to the data and methods in the model.
 * @author Ted Mader
 * @version Alpha
 * @date 5/02/14
 **/

public class BoardViewController implements ActionListener {

    private final RiskModel model;
    private final BoardView view;

    private static final Logger LOGGER = Logger.getLogger(BoardViewController.class.getName());


    public BoardViewController(RiskModel model, BoardView view) {
        this.model = model;
        this.view = view;
        model.startGame();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {

        String a = clean(view.getCountryA());
        String b = clean(view.getCountryB());

        switch (evt.getActionCommand()) {
            case "menuBtn":
                openMenu();
                break;
            case "turnInBtn":
                model.turnInCards(view.getCardsToRemove());
                break;
            case "reinforceBtn":
                model.reinforce(a);
                break;
            case "attackBtn":
                model.attack(a, b);
                break;
            case "fortifyBtn":
                model.fortify(a, b);
                break;
            case "endTurnBtn":
                model.nextPlayer();
                break;
            default:
                LOGGER.warning("Unknown action: " + evt.getActionCommand());

        }

    }

    private void openMenu() {
        MenuDialog dialog = new MenuDialog(view, true);
        dialog.addActionListeners(new MenuController(model, dialog));
        dialog.setVisible(true);
    }
}
