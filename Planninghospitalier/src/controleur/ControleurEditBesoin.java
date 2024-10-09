package controleur;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.BesoinEnPersonnelDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import metier.BesoinEnPersonnel;

public class ControleurEditBesoin implements Initializable {

	private BesoinEnPersonnel selectedBesoinEnPersonnel;


	private BesoinEnPersonnelDAO besoinEnPersonnelDAO;

    @FXML
    private TextField txtEffectif;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		besoinEnPersonnelDAO = new BesoinEnPersonnelDAO();
	}

	public void setSelectedBesoinEnPersonnel(BesoinEnPersonnel selectedBesoinEnPersonnel) {
		this.selectedBesoinEnPersonnel = selectedBesoinEnPersonnel;
	}


	@FXML
	private void modifierBesoin(ActionEvent event) {
	    // Valider l'entrée
	    int nouvelEffectif;
	    try {
	        nouvelEffectif = Integer.parseInt(txtEffectif.getText());
	    } catch (NumberFormatException e) {
	        showAlert(AlertType.ERROR, "Erreur de saisie", "L'effectif doit être un nombre valide.");
	        return;
	    }

	    // Vérifier que l'effectif n'est ni négatif ni égal à 0
	    if (nouvelEffectif <= 0) {
	        showAlert(AlertType.ERROR, "Erreur d'effectif", "L'effectif doit être supérieur à 0.");
	        return;
	    }

	    int effectifActuel = selectedBesoinEnPersonnel.getNbPersonnes();
	    int nbPersonnelAffecte = besoinEnPersonnelDAO.getNombrePersonnelParBesoin(selectedBesoinEnPersonnel.getIdBesoin());

	    // Gérer la surcharge
	    if (nouvelEffectif < nbPersonnelAffecte) {
	        boolean confirmation = showSurchargeConfirmation();
	        if (!confirmation) {
	            return; // L'utilisateur a annulé la modification
	        }
	    }

	    // Gérer la souscharge
	    if (nouvelEffectif > nbPersonnelAffecte) {
	        showAlert(AlertType.INFORMATION, "Souscharge", "Attention, vous avez moins de personnel affecté que l'effectif requis !");
	    }

	    // Mise à jour de l'effectif si tout est correct
	    this.selectedBesoinEnPersonnel.setNbPersonnes(nouvelEffectif);
	    BesoinEnPersonnel besoinModifie = besoinEnPersonnelDAO.update(this.selectedBesoinEnPersonnel);
	    if (besoinModifie != null) {
	        showAlert(AlertType.INFORMATION, "Succès", "Le besoin en personnel a été modifié avec succès.");
	        clearForm();
	        close(event);
	    } else {
	        showAlert(AlertType.ERROR, "Échec", "La modification du besoin en personnel a échoué.");
	    }
	}

	private boolean showSurchargeConfirmation() {
	    // Créer une alerte de confirmation
	    Alert surchargeAlert = new Alert(Alert.AlertType.CONFIRMATION);
	    surchargeAlert.setTitle("Confirmation de Surcharge");
	    surchargeAlert.setHeaderText("Surcharge détectée");
	    surchargeAlert.setContentText("Ce besoin a déjà atteint ou dépassé le nombre requis de personnel. Voulez-vous continuer avec cette modification qui réduit l'effectif ?");

	    // Ajouter les types de bouton Oui et Non
	    ButtonType buttonTypeYes = new ButtonType("Oui", ButtonBar.ButtonData.YES);
	    ButtonType buttonTypeNo = new ButtonType("Non", ButtonBar.ButtonData.NO);
	    surchargeAlert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

	    // Afficher l'alerte et attendre une réponse de l'utilisateur
	    Optional<ButtonType> result = surchargeAlert.showAndWait();

	    // Vérifier la réponse de l'utilisateur et agir en conséquence
	    return result.isPresent() && result.get() == buttonTypeYes;
	}


    private void clearForm() {
        txtEffectif.clear();
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private boolean validateInput() {
        return !txtEffectif.getText().isEmpty();
    }


	@FXML
	private void close(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

}
