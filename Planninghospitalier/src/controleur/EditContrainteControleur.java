package controleur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import dao.ContrainteDeSpecialiteDAO;
import dao.SingleConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import metier.ContraintedeSpecialite;
import metier.Specialite;

/**
 * Cette classe est le contrôleur du fichier EditContrainte.fxml.
 * Elle est responsable de la gestion de l'interface utilisateur
 * pour l'édition des contraintes de spécialité.
 */
public class EditContrainteControleur
    extends ContrainteDeSpecialiteDAO {

  /**
   * Le Champ de texte pour l'identifiant de la contrainte de spécialité.
   */
  @FXML
  private TextField idCS;

  /**
   * Le Champ de texte pour le nom de la contrainte de spécialité.
   */
  @FXML
  private TextField nomContrainteSpe;

  /**
   * Le Champ de texte pour l'état d'activation de la contrainte de spécialité.
   */
  @FXML
  private TextField activation;

  /**
   * Le Champ de texte pour la durée de la contrainte de spécialité.
   */
  @FXML
  private TextField duree;

  /**
   * Le Champ de texte pour la temporalité de la contrainte de spécialité.
   */
  @FXML
  private TextField temporalite;

  /**
   * Champ de texte pour l'identifiant de spécialité associée à la contrainte.
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
    Stage stage = (Stage) ((Node) event.getSource())
      .getScene()
      .getWindow();
    stage.setIconified(true);
  }

/**
 * Agrandit à la taille de l'écran.
 *
 * @param event [ActionEvent] L'événement de l'action.
 */
  @FXML
  private void max(ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource())
        .getScene()
        .getWindow();
    stage.setFullScreen(true);
  }

  /**
   * Définit les valeurs des champs de texte en fonction
   * des valeurs de la contrainte de spécialité spécifiée.
   *
   * @param c [ContraintedeSpecialite] La contrainte de spécialité.
   */
  public void setContrainte(ContraintedeSpecialite c) {

    idCS.setText(Integer.toString(c.getIdContrainteSpe()));
    idCS.editableProperty().setValue(false);
    nomContrainteSpe.setText(c.getNomContrainteSpe());
    activation.setText(Integer.toString(c.getActivation()));
    duree.setText(Double.toString(c.getDuree()));
    temporalite.setText(c.getTemporalite());
    idSpe.setText(Integer.toString(c.getIdSpe()));

  }

  /**
  * Supprime la contrainte de spécialité avec l'identifiant donné.
  *
  * @param event [ActionEvent] L'événement qui a déclenché l'appel de méthode.
  */
  @FXML
  private void delContrainteSpe(ActionEvent event) {

    try {
      Connection conn = SingleConnection.getInstance();

      int idC = Integer.valueOf(idCS.getText());
      String nom = nomContrainteSpe.getText();
      int act = Integer.valueOf(activation.getText());
      double dur = Double.valueOf(duree.getText());
      String temp = temporalite.getText();
      int idS = Integer.valueOf(idSpe.getText());

      // Affiche une fenêtre de dialogue pour confirmer la suppression
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Confirmation de suppression");
      alert.setHeaderText("Voulez-vous vraiment supprimer"
        + " cette contrainte de spécialité ?");
      Optional<ButtonType> ouiBtn = alert.showAndWait();

      if (ouiBtn.get() == ButtonType.OK) {

        // Supprime la contrainte légale
        String requete = "SELECT * FROM Specialite WHERE idSpe = ?";

        PreparedStatement ps = conn.prepareStatement(requete);
        ps.setInt(1, idS);
        ResultSet rs = ps.executeQuery();

        Specialite spe;

        if (rs.next()) {
          spe = new Specialite(rs.getInt("idSpe"), rs.getString("nomSpe"));

          ContraintedeSpecialite cd = new ContraintedeSpecialite(
            idC,
            spe,
            act,
            nom,
            dur,
            temp);

          delete(cd);

          // Ferme la fenêtre
          Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
          stage.close();
          
          Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
          alert2.setTitle("Confirmation de suppression");
          alert2.setHeaderText("La contrainte de spécialité a été supprimé."
          		+ " Veuillez mettre à jour la table.");
          alert2.showAndWait();
          
        }
      }

    } catch (ClassNotFoundException e) {
      e.printStackTrace();

    } catch (SQLException e) {
      e.printStackTrace();

    }

  }

  /**
  * Modifie la contrainte de spécialité avec l'identifiant donné.
  *
  * @param event [ActionEvent] L'événement qui a déclenché l'appel de méthode.
  */
  @FXML
  private void editContrainteSpe(ActionEvent event) {

    try {
      Connection conn = SingleConnection.getInstance();

      int idC = Integer.valueOf(idCS.getText());
      String nom = nomContrainteSpe.getText();
      int act = Integer.valueOf(activation.getText());
      double dur = Double.valueOf(duree.getText());
      String temp = temporalite.getText();
      int idS = Integer.valueOf(idSpe.getText());

      String requete = "SELECT * FROM Specialite WHERE idSpe = ?";

      PreparedStatement ps = conn.prepareStatement(requete);
      ps.setInt(1, idS);
      ResultSet rs = ps.executeQuery();

      Specialite spe;

      if (rs.next()) {
        spe = new Specialite(rs.getInt("idSpe"), rs.getString("nomSpe"));

        ContraintedeSpecialite cd = new ContraintedeSpecialite(
            idC,
            spe,
            act,
            nom,
            dur,
            temp);

        // Affiche une fenêtre de dialogue pour confirmer la modification
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de modification");
        alert.setHeaderText("Voulez-vous vraiment modifier"
          + " cette contrainte de spécialité ?");
        Optional<ButtonType> ouiBtn = alert.showAndWait();

        if (ouiBtn.get() == ButtonType.OK) {
          // Modifie la contrainte de spécialité
        update(cd);

        // Ferme la fenêtre
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert2.setTitle("Confirmation de suppression");
        alert2.setHeaderText("La contrainte de spécialité a été modifié."
        		+ " Veuillez mettre à jour la table.");
        alert2.showAndWait();
        
        }
      }

    } catch (ClassNotFoundException e) {
      e.printStackTrace();

    } catch (SQLException e) {
      e.printStackTrace();

    }
  }
}
