package org.atlanmod.risk;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

/**
 * This class maps the user's actions in the org.atlanmod.risk.PlayerCountDialog to the data and methods in
 * the model.
 * @author Ted Mader
 * @version Alpha
 * @date 5/02/14
 **/
public class PlayerCountController implements ActionListener {

    private final RiskModel model;
    private final PlayerCountDialog view;
    private static final Logger LOGGER = Logger.getLogger(PlayerCountController.class.getName());

    public PlayerCountController(RiskModel model, PlayerCountDialog view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        switch (evt.getActionCommand()) {
            case "threePlayersBtn":
                startGame(3);
                break;
            case "fourPlayersBtn":
                startGame(4);
                break;
            case "fivePlayersBtn":
                startGame(5);
                break;
            case "sixPlayersBtn":
                startGame(6);
                break;
            case "backBtn":
                view.dispose();
                break;
            default:
                LOGGER.info("Unknown action");
        }

    }

    private void startGame(int count) {
        model.setPlayerCount(count);

        PlayerSettingsDialog dialog =
                new PlayerSettingsDialog(view, true, count);
        dialog.addActionListeners(
                new PlayerSettingsController(model, dialog)
        );
        dialog.setVisible(true);
    }
}
