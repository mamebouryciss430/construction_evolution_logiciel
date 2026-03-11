package org.atlanmod.risk;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * This class maps the user's actions in the org.atlanmod.risk.MenuDialog to the data and methods in the model.
 * @author Ted Mader
 * @version Alpha
 * @date 5/02/14
 **/
class MenuController implements ActionListener {

	private RiskModel model;
	private MenuDialog view;

	private JFileChooser fileChooser;
	private ObjectOutputStream objectWriter;
	private ObjectInputStream objectReader;
	private BufferedReader reader;

	public MenuController(RiskModel model, MenuDialog view) {

		this.model = model;
		this.view = view;
	}

	public void actionPerformed(ActionEvent evt) {

		String actionEvent = evt.getActionCommand();

		if (actionEvent.equals("returnBtn")) {
			view.dispose();

		} else if (actionEvent.equals("saveBtn")) {

			fileChooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Java-org.atlanmod.risk.Risk Save Files", "jrs");
			fileChooser.setFileFilter(filter);

			if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {

				try {
					objectWriter = new ObjectOutputStream(new FileOutputStream(fileChooser.getSelectedFile()));
					objectWriter.writeObject(model);
					objectWriter.close();

				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		} else if (actionEvent.equals("quitBtn")) {
			model.quitGame();

		} else {
			System.out.println("actionEvent not found: " + actionEvent);
		}
	}
}
