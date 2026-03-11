package org.atlanmod.risk;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class maps the user's actions in the org.atlanmod.risk.PlayerSettingsDialog to the data and methods in
 * the model.
 * @author Ted Mader
 * @version Alpha
 * @date 5/02/14
 **/
public class PlayerSettingsController implements ActionListener {

    private final RiskModel model;
    private final PlayerSettingsDialog view;
    private static final Logger LOGGER = Logger.getLogger(PlayerSettingsController.class.getName());


    public PlayerSettingsController(RiskModel model, PlayerSettingsDialog view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {

        if ("startBtn".equals(evt.getActionCommand())) {
            startGame();
        } else if ("backBtn".equals(evt.getActionCommand())) {
            view.dispose();
        }
    }

    private void startGame() {
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> types = new ArrayList<>();

        for (int i = 1; i <= model.getPlayerCount(); i++) {
            names.add(view.getPlayerTextField(i));
            types.add(view.getPlayerComboBox(i));
        }

        try {
            if (model.initializeGame(names, types)) {
                BoardView board =
                        new BoardView(view, true, model);
                board.addActionListeners(
                        new BoardViewController(model, board),
                        new RiskListController(model, board)
                );
                board.setVisible(true);
            }
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Fichier introuvable lors de l'initialisation de la partie", e);
        }
    }
}
