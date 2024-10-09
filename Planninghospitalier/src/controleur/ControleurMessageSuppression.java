package controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ControleurMessageSuppression {

	@FXML
	private Button Oui;

	@FXML
	private Button Non;

	public Button getOuiButton() {
		return Oui;
	}

	public Button getNonButton() {
		return Non;
	}
}
