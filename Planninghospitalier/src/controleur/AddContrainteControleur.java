package controleur;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.ContrainteDeSpecialiteDAO;
import dao.SingleConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import metier.ContraintedeSpecialite;
import metier.Specialite;

/**
 * La classe `AddContrainteControleur` étend la classe
 * `ContrainteDeSpecialiteDAO` et implémente l'interface `Initialisable`.
 * Elle fournit des méthodes pour ajouter
 * une nouvelle contrainte à la base de données,
 * fermer la fenêtre et nettoyer les champs.
 */
public class AddContrainteControleur
    extends ContrainteDeSpecialiteDAO
    implements Initializable {

  /**
   * Connexion à la base de données.
   */
  Connection connect = null;
  /**
   * L'instruction préparée utilisée pour l'exécution des requêtes.
   */
  PreparedStatement stmt = null;
  /**
   * Le jeu de résultats de la dernière requête exécutée.
   */
  ResultSet rs = null;

  /**
   * Champ de texte pour saisir le nom de la contrainte de spécialité.
   */
  @FXML
  private TextField nomContrainteSpe;

  /**
   * Champ de texte pour saisir l'activation, 1 ou 0 pour vrai ou faux.
   */
  @FXML
  private TextField activation;

  /**
   * Champ de texte pour saisire la durée.
   */
  @FXML
  private TextField duree;

  /**
   * Champ de texte pour saisr la temporalité.
   */
  @FXML
  private TextField temporalite;

  /**
   * Champ de texte pour saisir l'identifiant de la spécialité associée.
   */
  @FXML
  private TextField idSpe;

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
    nomContrainteSpe.setText(null);
    activation.setText(null);
    duree.setText(null);
    temporalite.setText(null);
    idSpe.setText(null);
  }

  /**
   * Ajoute une nouvelle contrainte de spécialite à la base de données, à l'aide
   * des méthodes prédéfinies dans `ContrainteDeSpecialiteDAO`.
   *
   * @param event [Action] L'événement de l'action.
   */
  @FXML
  private void addContrainteSpe(ActionEvent event) {

      String nom = nomContrainteSpe.getText();
      int act = Integer.valueOf(activation.getText());
      double dur = Double.valueOf(duree.getText());
      String temp = temporalite.getText();
      int idS = Integer.valueOf(idSpe.getText());
      
      if (nom.isEmpty()
        ||
        Double.valueOf(duree.getText()).isNaN()
        ||
        temp.isEmpty()
        ||
        idSpe.getText().isEmpty()) {

         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setHeaderText(null);
         alert.setContentText("Merci de remplir correctement tout les champs."
           + " L'activation doit être 0 pour non activé et 1 pour activer.");
         alert.showAndWait();

        } else {
    try {
      Connection conn = SingleConnection.getInstance();

      String requete = "SELECT * FROM Specialite WHERE idSpe = ?";

      PreparedStatement ps = conn.prepareStatement(requete);
      ps.setInt(1, idS);
      ResultSet rs = ps.executeQuery();

      Specialite spe;

      if (rs.next()) {
        spe = new Specialite(rs.getInt("idSpe"), rs.getString("nomSpe"));

        ContraintedeSpecialite cd = new ContraintedeSpecialite(
          spe,
          act,
          nom,
          dur,
          temp);

        // Affiche une fenêtre de dialogue pour confirmer l'ajout
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation d'ajout");
        alert.setHeaderText("Voulez-vous vraiment ajouter"
          + " cette contrainte de spécialité ?");
        Optional<ButtonType> ouiBtn = alert.showAndWait();

        if (ouiBtn.get() == ButtonType.OK) {
          // Modifie la contrainte de spécialité
        create(cd);

        clean();
        
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert2.setTitle("Confirmation d'ajout");
        alert2.setHeaderText("La contrainte de spécialité a été ajoutée."
        		+ " Veuillez mettre à jour la table.");
        alert2.showAndWait();
        }
      }

    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
}
  }

  /**
   * Initialise la classe du contrôleur.
   *
   * @param url [UR] L'URL du fichier FXML.
   * @param rb [ResourceBundle] Le paquet de ressources utilisé à
   * des fins de localisation.
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
