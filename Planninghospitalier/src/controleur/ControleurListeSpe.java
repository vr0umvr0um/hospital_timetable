package controleur;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.SpecialiteDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import metier.Specialite;

/**
 * @author 22203517t - Jules Forest
 */
public class ControleurListeSpe implements Initializable{

    // attributs nécessaires au fonctionnement du controleur pour switcher de pages.
    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * DAO de la classe métier Specialite.
     */
    private SpecialiteDAO speDAO;

    /**
     * Liste des spécialités à afficher.
     */
    @FXML
    private ArrayList<Specialite> listeSpe = new ArrayList<>() ;

    /**
     * ScrollPane servant à afficher les spécialités et les boutons associées.
     */
    @FXML
    private ScrollPane paneSpe;

    /**
     * Méthode appelée après que le contenu du FXML ai été injecté dans le contrôleur.
     * @param URL emplacement du fichier FXML source
     * @param RessourceBundle utilisé pour récupérer
     *       les textes internationalisés
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        speDAO = new SpecialiteDAO();
        miseEnPlaceListeSpe();
    }


    /**
     * Méthode qui permet de mettre en place la liste des spécialités.
     * Toutes les spécialités en base vont être récupérées et affichées
     * dans un TextField pour permettre les modifications avec en plus
     * un bouton pour supprimer et un bouton pour lancer la modification
     */
    public void miseEnPlaceListeSpe() {
        ArrayList<Specialite> liste = speDAO.readAll();
        VBox vboxAllSpe = new VBox();
        vboxAllSpe.setId("listeSpe");
        VBox.setVgrow(vboxAllSpe, Priority.ALWAYS);
        vboxAllSpe.setSpacing(2);

        for (Specialite spe : liste) {
            HBox hboxRectangle = new HBox();
            hboxRectangle.setPadding(new Insets(5, 5, 5, 5));
            hboxRectangle.setId("rectangle");
            hboxRectangle.setAlignment(Pos.CENTER);

            TextField textSpe = new TextField();
            textSpe.setText(spe.getNomSpecialite());
            textSpe.setTextFormatter(new TextFormatter<>(c ->
            c.getControlNewText().matches(".{0,50}") ? c : null));

            Button boutonSupp = new Button();
            boutonSupp.setText("Supprimer");
            boutonSupp.setId("boutonSuppSpe");

            Button boutonModif = new Button();
            boutonModif.setText("Modifier");
            boutonModif.setId("boutonModifSpe");

            hboxRectangle.getChildren().add(textSpe);
            hboxRectangle.getChildren().add(boutonSupp);
            hboxRectangle.getChildren().add(boutonModif);

            vboxAllSpe.getChildren().add(hboxRectangle);

            boutonSupp.setOnAction(event -> {
                try {
                    this.supprimerSpe(spe);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IndexOutOfBoundsException e2) {
                }
            });

            boutonModif.setOnAction(event -> {
                if (textSpe.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Merci de remplir le nom de la spécialité");
                    alert.showAndWait();
                } else {
                    spe.setNomSpecialite(textSpe.getText());
                    this.modifSpe(spe);
                }
            });
        }
        paneSpe.setContent(vboxAllSpe);
        paneSpe.setFitToWidth(true);
    }

    public void switchToCreneau(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("/vues/Creneau.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);

		stage.show();
		stage.centerOnScreen();
	}

	public void switchToPersonnelEDT(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("/vues/PersonnelEDTChoix.fxml"));
		stage = new Stage();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.initStyle(StageStyle.TRANSPARENT);

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

        stage.show();
        stage.centerOnScreen();
    }

	public void switchToFonction(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("/vues/afficheFonction.fxml"));
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
     * Méthode qui permet d'aller sur la page de consultation
     * des specialites (ConsulterSpecialites.fxml).
     * @param event
     * @throws IOException
     */
    @FXML
    public void switchToSpecialite(ActionEvent event)
            throws IOException {

        root = FXMLLoader.load(getClass().getResource("/vues/ConsulterSpecialites.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
        stage.centerOnScreen();
    }

	@FXML
	private void min(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}

	@FXML
	private void max(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setFullScreen(true);
	}

    /**
     * Méthode qui permet de fermer la fenetre
     * @param event
     */
    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Méthode qui permet le retour sur la page Creneau.fxml,
     * soit les EDT.
     * @param event
     */
    @FXML
    private void retour(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/vues/Creneau.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Méthode qui permet de supprimer une spécialite.
     * La méthode va créer un pop-up pour valider la suppression.
     * @param spe - spécialité à supprimer
     * @throws IOException
     */
    private void supprimerSpe(Specialite spe)
            throws IOException {
        String message = "Etes-vous sur de vouloir supprimer cette spécialité ?";

        Optional<ButtonType> answer = creerPopUpValidation(message);
        if (answer.get() == ButtonType.OK) {
            speDAO.delete(spe);
            listeSpe.remove(spe);
            creerPopUpSucces("Suppression réussie !");
        }
        this.refresh();
    }

    /**
     * Méthode qui va permettre de créer un pop-up de confirmation.
     * @param message - message à faire afficher par le pop-up
     * @return Optional<ButtonType> - réponse au pop-up
     */
    public Optional<ButtonType> creerPopUpValidation(String message) {
        Alert dialogC = new Alert(AlertType.CONFIRMATION);
        dialogC.setTitle("VALIDATION");
        dialogC.setHeaderText(null);
        dialogC.setContentText(message);
        Optional<ButtonType> answer = dialogC.showAndWait();
        return answer;
    }

    /**
     * Méthode qui permet d'afficher un pop-up d'affirmation.
     * @param message - message à faire afficher par le pop-up
     */
    public void creerPopUpSucces(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Méthode qui gère l'ajout d'une nouvelle spécialité en base.
     * La méthode va appeler un pop-up qui va demander le nom
     * de la nouvelle spécialité, le cadre confirme et la spécialité
     * est ainsi créée en base.
     */
    public void ajoutSpecialite() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Ajouter une spécialité");
        dialog.setHeaderText(null);
        dialog.setContentText("Choisissez un nom à la spécialité :");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            Specialite newSpe = speDAO.create(new Specialite(0, result.get()));
            this.listeSpe.add(newSpe);
        }
        this.refresh();
    }

    /**
     * Méthode qui permet de modifier une spécialité.
     * La méthode appelle creerPopUpSucces() en cas de succes
     * @param spe
     */
    public void modifSpe(Specialite spe) {
        this.speDAO.update(spe);
        creerPopUpSucces("Modification réussie !");
    }

    /**
     * Méthode qui permet de lancer un refresh de la page.
     */
    private void refresh() {
        // TODO Auto-generated method stub
        miseEnPlaceListeSpe();
    }
}
