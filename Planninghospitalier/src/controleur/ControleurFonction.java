package controleur;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.FonctionDAO;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import metier.Fonction;
/**
 * Cette classe est le contrôleur associé à la vue de gestion des fonctions.
 * Elle gère l'affichage, la suppression,
 * la modification et l'ajout de fonctions.
 * @author 22101222 - Code - java doc - checkstyle.
 */
public class ControleurFonction implements Initializable {
    /**
     * Classe de gestion des fonctions, contenant les éléments nécessaires
     * à la manipulation de l'interface et des données.
     */
    private FonctionDAO fonctionDAO;
    /**
     * Référence à la scène principale de l'application.
     */
    private Stage stage;
    /**
     * Scène à afficher dans la fenêtre principale.
     */
    private Scene scene;
    /**
     * Élément racine de la hiérarchie des nœuds de l'interface graphique.
     */
    private Parent root;
    /**
     * Liste des objets représentant les fonctions à afficher dans l'interface.
     */
    @FXML
    private ArrayList<Fonction> listeFonction = new ArrayList<>();
    /**
     * Composant graphique permettant de faire défiler le contenu.
     */
    @FXML
    private ScrollPane paneFonction;
    /**
     * La méthode viewFonction est la méthode principale
     * permettant d'affiche la liste des fonctions,
     * de modifier, supprimer les fonctions.
     */
    public void viewFonction() {
        final int spacing = 10;
        ArrayList<Fonction> liste = fonctionDAO.readAll();
        VBox vboxFonctions = new VBox();
        vboxFonctions.setId("listeFonction");
        HBox.setHgrow(vboxFonctions, Priority.ALWAYS);
        vboxFonctions.setSpacing(spacing);
        for (Fonction fonction : liste) {
            HBox hboxFonction = new HBox();
            hboxFonction.setPadding(new Insets(spacing));
            hboxFonction.setId("rectangle");
            hboxFonction.setAlignment(Pos.CENTER);

            TextField textFonction = new TextField(
                     fonction.getNomFonction());
            textFonction.setStyle("-fx-font-weight: bold;");
            textFonction.setAlignment(Pos.CENTER);
            final int width = 200;
            textFonction.setMinWidth(width);
            textFonction.setMaxWidth(width);
            Button boutonSupprimer = new Button("Supprimer");
            boutonSupprimer.setStyle(
                    "-fx-background-color: #CC3333; -fx-text-fill: white;");
            boutonSupprimer.setOnAction(event -> {
                try {
                    deleteFonction(fonction);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            Button boutonModifier = new Button("Modifier");
            boutonModifier.setStyle(
                    "-fx-background-color: #6BA1E1; -fx-text-fill: white;");
            boutonModifier.setOnAction(event -> {
                updateFonction(fonction);
            });
            final int margin = 15;
            hboxFonction.getChildren().addAll(
                    textFonction, boutonSupprimer, boutonModifier);
            HBox.setMargin(
                    boutonSupprimer, new Insets(0, 0, 0, margin));
            HBox.setMargin(
                    boutonModifier, new Insets(0, 0, 0, margin));
            vboxFonctions.getChildren().add(hboxFonction);
            paneFonction.setContent(vboxFonctions);
            paneFonction.setFitToWidth(true);
            System.out.println(
                    "Liste des fonctions : " + liste.toString());
        }
    }
    /**
     * La méthode deleteFonction permet de supprime une fonction
     * après avoir demandé une confirmation à l'utilisateur.
     *
     * @param fonction : La fonction à supprimer
     * @throws IOException : En cas d'erreur d'entrée/sortie
     * lors de la suppression
     */
     private void deleteFonction(final Fonction fonction) throws IOException {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setHeaderText(null);
        confirmation.setContentText(
                "Voulez-vous vraiment supprimer cette fonction ?");

        Optional<ButtonType> choix = confirmation.showAndWait();
        if (choix.isPresent() && choix.get() == ButtonType.OK) {
            fonctionDAO.delete(fonction);
            listeFonction.remove(fonction);
            popUpConfirmation("Fonction supprimée avec succès !");
            viewFonction();
        }
    }
    /**
     * La méthode PopUpConfirmation crée
     * une boîte de dialogue de confirmation
     * avec le message spécifié et attend la réponse de l'utilisateur.
     *
     * @param message : Le message à afficher dans la boîte de dialogue
     * @return La réponse de l'utilisateur (Ok ou Annuler)
     */
     public Optional<ButtonType> popUpConfirmation(final String message) {
        Alert dialogConfirmation = new Alert(AlertType.CONFIRMATION);
        dialogConfirmation.setTitle("Validation !");
        dialogConfirmation.setHeaderText(null);
        dialogConfirmation.setContentText(message);

        return dialogConfirmation.showAndWait();
    }
     /**
      * Champ de texte pour saisir le nom de la fonction.
      */
    @FXML
    private TextField nomFonction;
    /**
     * Bouton pour ajouter une fonction.
     */
    @FXML
    private Button addButton;
    /**
     * La méthode addFonction permet de gérer l'ajout d'une nouvelle fonction.
     *
     * @param event : L'événement de clic sur le bouton d'ajout de fonction
     */
    @FXML
    private void addFonction(final ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Ajouter une fonction");
        dialog.setHeaderText(null);
        dialog.setContentText("Veuillez entrer le nom de la fonction :");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(newNomFonction -> {
            if (!newNomFonction.isEmpty()) {
                Fonction nouvelleFonction = new Fonction();
                nouvelleFonction.setNomFonction(newNomFonction);

                Fonction fonctionAjoutee = fonctionDAO.create(
                        nouvelleFonction);

                if (fonctionAjoutee != null) {
                    Alert successAlert = new Alert(
                            Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Ajout réussi");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText(
                            "La fonction a été ajoutée avec succès !");
                    successAlert.showAndWait();

                    viewFonction();
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erreur");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText(
                           "Erreur lors de l'ajout de la fonction.");
                    errorAlert.showAndWait();
                }
           } else {
                Alert emptyAlert = new Alert(Alert.AlertType.WARNING);
                emptyAlert.setTitle("Champ vide");
                emptyAlert.setHeaderText(null);
                emptyAlert.setContentText(
                        "Veuillez saisir un nom pour la fonction.");
                emptyAlert.showAndWait();
            }
       });
     }
    /**
     * La méthode updateFonction permet de
     * mettre à jour une fonction en modifiant son nom.
     *
     * @param fonction : La fonction à mettre à jour
     */
     private void updateFonction(final Fonction fonction) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Modifier la fonction");
            dialog.setHeaderText(null);
            dialog.setContentText(
                   "Entrez le nouveau nom de la fonction :");

            Optional<String> result = dialog.showAndWait();

            result.ifPresent(nouveauNom -> {
                 if (nouveauNom.isEmpty()) {
                    Alert emptyAlert = new Alert(Alert.AlertType.WARNING);
                    emptyAlert.setTitle("Champ vide");
                    emptyAlert.setHeaderText(null);
                    emptyAlert.setContentText(
                            "Veuillez saisir un nom pour la fonction.");
                    emptyAlert.showAndWait();
              } else {
                    fonction.setNomFonction(nouveauNom);
                    this.fonctionDAO.update(fonction);
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Mise à jour réussie");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText(
                            "La fonction a été mise à jour avec succès !");
                    successAlert.showAndWait();
                }
            });
            viewFonction();
    }
    /**
     * la méthode switchToCreneau permet de changer la scène
     * actuelle vers la page d'accueil principale.
     *
     * @param event : L'événement déclencheur
     * de l'action de changement de scène (un bouton)
     * @throws IOException : En cas d'erreur
     * lors du chargement de la vue Creneau.fxml
     */
     public void switchToCreneau(final ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource(
                "/vues/Creneau.fxml"));
        stage = (Stage) ((Node)
                 event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
        stage.centerOnScreen();
    }
    /**
     * la méthode switchToPersonnel permet de changer
     * la scène actuelle vers la vue Personnel.
     *
     * @param event : L'événement déclencheur
     * de l'action de changement de scène (un bouton)
     * @throws IOException : En cas d'erreur
     * lors du chargement de la vue Personnel.fxml
     */
     public void switchToPersonnel(ActionEvent event) throws IOException {

 		root = FXMLLoader.load(getClass().getResource("/vues/ListePersonnels2.fxml"));
 		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
 		scene = new Scene(root);
 		stage.setScene(scene);

 		stage.show();
 		stage.centerOnScreen();
 	}
    /**
     * la méthode switchToFonction permet
     * de changer la scène actuelle vers la vue Fonction.
     *
     * @param event : L'événement déclencheur de
     * l'action de changement de scène (un bouton)
     * @throws IOException : En cas d'erreur lors
     * du chargement de la vue afficheFonction.fxml
     */
     public void switchToFonction(final ActionEvent event) throws IOException {

        root = FXMLLoader.load(
                getClass().getResource("/vues/afficheFonction.fxml"));
        stage = (Stage) ((Node)
                event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
        stage.centerOnScreen();
     }
    /**
     * la méthode switchToSpecialite permet
     * de changer la scène actuelle vers la vue Spécialité.
     *
     * @param event : L'événement déclencheur de
     * l'action de changement de scène (un bouton)
     * @throws IOException : En cas d'erreur lors
     * du chargement de la vue ConsulterSpecialites.fxml
     */
     public void switchToSpecialite(
          final ActionEvent event) throws IOException {

        root = FXMLLoader.load(
                getClass().getResource("/vues/ConsulterSpecialites.fxml"));
        stage = (Stage) ((Node)
                event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
        stage.centerOnScreen();
    }
    /**
     * la méthode switchToProbleme permet de
     * changer la scène actuelle vers la vue Probleme.
     *
     * @param event : L'événement déclencheur
     * de l'action de changement de scène (un bouton)
     * @throws IOException : En cas d'erreur lors du
     * chargement de la vue Probleme.fxml
     */
    public void switchToProbleme(final ActionEvent event) throws IOException {

        root = FXMLLoader.load(
                getClass().getResource("/vues/Probleme.fxml"));
        stage = (Stage) ((Node)
                event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
        stage.centerOnScreen();
    }
    /**
     * la méthode switchToContrainte permet
     * de changer la scène actuelle vers la vue Contrainte.
     *
     * @param event : L'événement déclencheur
     * de l'action de changement de scène (un bouton)
     * @throws IOException : En cas d'erreur lors du
     * chargement de la vue Contrainte.fxml
     */
     public void switchToContrainte(
              final ActionEvent event) throws IOException {

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

         stage.show();
         stage.centerOnScreen();
     }
    /**
     * La méthode close permet de ferme la fenêtre courante.
     *
     * @param event : L'événement déclencheur.
     */
    @FXML
    private void close(final ActionEvent event) {
        this.stage = (Stage) ((Node)
                event.getSource()).getScene().getWindow();
        stage.close();
    }
    /**
     * La méthode min permet de minimise la fenêtre courante.
     *
     * @param event L'événement déclencheur.
     */
    @FXML
    private void min(final ActionEvent event) {
        this.stage = (Stage) ((Node)
                event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    /**
     * La méthode permet de maximise la fenêtre courante.
     *
     * @param event L'événement déclencheur.
     */
    @FXML
    private void max(final ActionEvent event) {
        this.stage = (Stage) ((Node)
                event.getSource()).getScene().getWindow();
        stage.setFullScreen(true);
    }
    /**
     * La méthode initialize permet d'initialiser
     * la classe ContrôleurFonction.
     * Appelle la méthode pour charger les fonctions
     * et afficher la vue des fonctions.
     *
     * @param arg0 URL du fichier FXML.
     * @param arg1 ResourceBundle utilisé pour localiser les éléments.
     */
    @Override
    public void initialize(final URL arg0, final ResourceBundle arg1) {
        fonctionDAO = new FonctionDAO();
        viewFonction();
    }
}
