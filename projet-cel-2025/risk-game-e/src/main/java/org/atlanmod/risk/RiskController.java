package org.atlanmod.risk;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.logging.Logger;

/**
 * This class maps the user's actions in the org.atlanmod.risk.RiskView to the data and methods in the model.
 * @author Ted Mader
 * @version Alpha
 * @date 5/02/14
 **/

public class RiskController implements ActionListener {

	private final RiskModel model;
	private final RiskView view;
	private static final Logger LOGGER = Logger.getLogger(RiskController.class.getName());


	public RiskController(RiskModel model, RiskView view) {
		this.model = model;
		this.view = view;
		view.riskViewActionListeners(this);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		switch (evt.getActionCommand()) {

			case "newGameBtn":
				openPlayerCountDialog();
				break;

			case "loadGameBtn":
				loadGame();
				break;

			case "quitBtn":
				model.quitGame();
				break;

			default:
				LOGGER.warning("Unknown action: " + evt.getActionCommand());

		}

	}

	/* ===== Méthodes privées ===== */

	private void openPlayerCountDialog() {
		PlayerCountDialog dialog = new PlayerCountDialog(view, true);
		dialog.addActionListeners(new PlayerCountController(model, dialog));
		dialog.setVisible(true);
	}

	private void loadGame() {
		JFileChooser chooser = createFileChooser();

		if (chooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
			RiskModel loadedModel = readModel(chooser.getSelectedFile());
			if (loadedModel != null) {
				openBoardView(loadedModel);
			}
		}
	}

	private JFileChooser createFileChooser() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(
				new FileNameExtensionFilter("Risk Save Files", "jrs")
		);
		return chooser;
	}

	private RiskModel readModel(File file) {
		try (ObjectInputStream in =
					 new ObjectInputStream(new FileInputStream(file))) {
			return (RiskModel) in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(
					view,
					"Erreur lors du chargement",
					"Erreur",
					JOptionPane.ERROR_MESSAGE
			);
			return null;
		}
	}

	private void openBoardView(RiskModel model) {
		BoardView boardView = new BoardView(view, true, model);
		boardView.addActionListeners(
				new BoardViewController(model, boardView),
				new RiskListController(model, boardView)
		);
		boardView.setVisible(true);
	}
}

