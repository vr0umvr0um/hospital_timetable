package controleur;

import java.util.Optional;

import dao.ContrainteLegaleDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import metier.ContrainteLegale;

/**
 * Cette classe est le contrôleur du fichier EditLegale.fxml.
 * Elle est responsable de la gestion de l'interface utilisateur
 * pour l'édition des contraintes légales.
 */
public class EditLegaleControleur
    extends ContrainteLegaleDAO {

  /**
   * Le Champ de texte pour l'identifiant de la contrainte légale.
   */
  @FXML
  private TextField idContrainteLegale;

  /**
   * Le Champ de texte pour le nom de la contrainte légale.
   */
  @FXML
  private TextField nomContrainteLegale;

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
   * des valeurs de la contrainte légale spécifiée.
   *
   * @param c [ContrainteLégale] La contrainte légale.
   */
  public void setContrainte(ContrainteLegale c) {
    idContrainteLegale.setText(Integer.toString(c.getIdContrainteLegale()));
    idContrainteLegale.editableProperty().setValue(false);
    nomContrainteLegale.setText(c.getNomContrainteLegale());
    duree.setText(Double.toString(c.getDuree()));
    temporalite.setText(c.getTemporalite());
  }

  /**
  * Supprime la contrainte légale avec l'identifiant donné.
  *
  * @param event [ActionEvent] L'événement qui a déclenché l'appel de méthode.
  */
  @FXML
  void delContrainteLeg(ActionEvent event) {

    int idC = Integer.valueOf(idContrainteLegale.getText());
    String nom = nomContrainteLegale.getText();
    double dur = Double.valueOf(duree.getText());
    String temp = temporalite.getText();

    ContrainteLegale cl = new ContrainteLegale(idC, nom, dur, temp);

    // Affiche une fenêtre de dialogue pour confirmer la suppression
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation de suppression");
    alert.setHeaderText("Voulez-vous vraiment supprimer"
      + " cette contrainte légale ?");
    Optional<ButtonType> ouiBtn = alert.showAndWait();

    if (ouiBtn.get() == ButtonType.OK) {
      // Supprime la contrainte légale
      delete(cl);

      // Ferme la fenêtre
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.close();
      
      Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
      alert2.setTitle("Confirmation de suppression");
      alert2.setHeaderText("La contrainte légale a été supprimé."
      		+ " Veuillez mettre à jour la table.");
      alert2.showAndWait();
    }
  }

  /**
   * Modifie la contrainte légale avec l'identifiant donné.
   *
   * @param event [ActionEvent] L'événement qui a déclenché l'appel de méthode.
   */
  @FXML
  void editContrainteSpe(ActionEvent event) {

    int idC = Integer.valueOf(idContrainteLegale.getText());
    String nom = nomContrainteLegale.getText();
    double dur = Double.valueOf(duree.getText());
    String temp = temporalite.getText();

    ContrainteLegale cl = new ContrainteLegale(idC, nom, dur, temp);

    // Affiche une fenêtre de dialogue pour confirmer la modification
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation de modification");
    alert.setHeaderText("Voulez-vous vraiment modifier"
      + " cette contrainte légale ?");
    Optional<ButtonType> ouiBtn = alert.showAndWait();

    if (ouiBtn.get() == ButtonType.OK) {
      // Modifie la contrainte légale
    	
    update(cl);

    //Ferme la fenêtre
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.close();
    
    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
    alert2.setTitle("Confirmation de suppression");
    alert2.setHeaderText("La contrainte légale a été supprimé."
    		+ " Veuillez mettre à jour la table.");
    alert2.showAndWait();
    }
  }

}
