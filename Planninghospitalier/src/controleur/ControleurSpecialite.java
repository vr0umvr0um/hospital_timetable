package controleur;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ControleurSpecialite {

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

		root = FXMLLoader.load(getClass().getResource("/vues/ListePersonnels2.fxml"));
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

	/**
     * la méthode switchToAddCreneau permet de
     * changer la scène actuelle vers la vue addCreneau.
     *
     * @param event : L'événement déclencheur de
     * l'action de changement de scène (un bouton)
     * @throws IOException : En cas d'erreur
     * lors du chargement de la vue addCreneau.fxml
     */
     public void switchToAddCreneau(
             final ActionEvent event) throws IOException {

        root = FXMLLoader.load(
                getClass().getResource("/vues/addCreneau.fxml"));
        stage = new Stage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);

        stage.show();
        stage.centerOnScreen();
    }

	@FXML
	private void close(ActionEvent event) {
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}
}
