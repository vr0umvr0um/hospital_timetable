package appli;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LancementAppli extends Application {

	@Override
	public void start(Stage primaryStage) {

		try {
			System.out.println("Lancement du Projet...");
			Parent root = FXMLLoader.load(getClass().getResource("/vues/Creneau.fxml"));
			Scene scene = new Scene(root);

			Image image = new Image("/vues/images/heart-pulse-solid.png"); // ajout d'une icone pour la barre de tâche
			primaryStage.getIcons().add(image);

			primaryStage.setTitle("Planning Hospitalier"); // ajout d'un titre pour la barre de tâche

			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.TRANSPARENT);

			primaryStage.show();
			primaryStage.centerOnScreen();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
