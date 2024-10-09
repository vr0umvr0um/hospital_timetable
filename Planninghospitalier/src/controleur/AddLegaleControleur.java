package controleur;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.ContrainteLegaleDAO;
import dao.SingleConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import metier.ContrainteLegale;

/**
 * La classe `AddLegaleControleur` étend la classe
 * `ContrainteLegaleDAO` et implémente l'interface `Initialisable`.
 * Elle fournit des méthodes pour ajouter une nouvelle contrainte
 * à la base de données,
 * fermer la fenêtre et nettoyer les champs.
 */
public class AddLegaleControleur
    extends ContrainteLegaleDAO
    implements Initializable {

  /**
   * Champ de texte pour saisir le nom de la contrainte légale.
   */
  @FXML
  private TextField nomContrainteLegale;

  /**
   * Champ de texte pour saisir la durée.
   */
  @FXML
  private TextField duree;

  /**
   * Champ de texte pour saisir la temporalité.
   */
  @FXML
  private TextField temporalite;

  /**
   * Ferme la fenêtre.
   *
   * @param event [ActionEvent] L'événement de l'action.
   */
  @FXML
  private void close(ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource())
        .getScene()
        .getWindow();
    stage.close();
  }

 /**
  * Réduit la fenêtre dans la barre de tâche.
  *
 * @param event [ActionEvent] L'événement de l'action.
  */
  @FXML
  private void min(ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setIconified(true);
  }

/**
 * Agrandit à la taille de l'écran.
 *
 * @param event [ActionEvent] L'événement de l'action.
 */
  @FXML
  private void max(ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setFullScreen(true);
  }

  /**
   * Nettoie les champs en définissant leur texte à null.
   */
  @FXML
  private void clean() {
    nomContrainteLegale.setText(null);
    duree.setText(null);
    temporalite.setText(null);
  }

  /**
   * Ajoute une nouvelle contrainte légale à la base de données, à l'aide
   * des méthodes prédéfinies dans `ContrainteLégaleDAO`.
   *
   * @param event [Action] L'événement de l'action.
   */
  @FXML
  private void addContrainteLegale(ActionEvent event) {

    String nom = nomContrainteLegale.getText();
    double dur = Double.valueOf(duree.getText());
    String temp = temporalite.getText();

    if (
      nom.isEmpty()
      ||
      Double.valueOf(duree.getText()).isNaN()
      ||
      temp.isEmpty()) {

      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setHeaderText(null);
      alert.setContentText("Merci de remplir tout les champs");
      alert.showAndWait();

    } else {

      ContrainteLegale cl = new ContrainteLegale(nom, dur, temp);
   
      // Affiche une fenêtre de dialogue pour confirmer l'ajout
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Confirmation d'ajout");
      alert.setHeaderText("Voulez-vous vraiment ajouter"
        + " cette contrainte légale ?");
      Optional<ButtonType> ouiBtn = alert.showAndWait();

      if (ouiBtn.get() == ButtonType.OK) {
        // Modifie la contrainte de spécialité
      create(cl);

      clean();
      
      Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
      alert2.setTitle("Confirmation d'ajout");
      alert2.setHeaderText("La contrainte légale a été ajoutée."
      		+ " Veuillez mettre à jour la table.");
      alert2.showAndWait();
      }
      create(cl);

      clean();
    }


  }

  /**
   * Initialise la classe du contrôleur.
   *
   * @param url [UR] L'URL du fichier FXML.
   * @param rb [ResourceBundle] Le paquet de ressources utilisé
   * à des fins de localisation.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    try {
      connect = SingleConnection.getInstance();

    } catch (ClassNotFoundException e) {

      e.printStackTrace();
    } catch (SQLException e) {

      e.printStackTrace();
    }
  }
}
