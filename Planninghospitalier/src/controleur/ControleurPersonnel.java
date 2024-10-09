package controleur;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ControleurPersonnel {

	private Stage stage;
	private Scene scene;
	private Parent root;

	public void switchToCreneau(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("/vues/Creneau.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);

		stage.show();
		stage.centerOnScreen();
	}

	public void switchToPersonnel(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("/vues/Personnel.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);

		stage.show();
		stage.centerOnScreen();
	}

	public void switchToFonction(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("/vues/Fonction.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);

		stage.show();
		stage.centerOnScreen();
	}

	public void switchToSpecialite(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("/vues/Specialite.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);

		stage.show();
		stage.centerOnScreen();
	}

	public void switchToProbleme(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("/vues/Probleme.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);

		stage.show();
		stage.centerOnScreen();
	}

	public void switchToContrainte(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("/vues/Contrainte.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);

		stage.show();
		stage.centerOnScreen();
	}

	@FXML
	private void close(ActionEvent event) {
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

}
