package controleur;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import dao.BesoinEnPersonnelDAO;
import dao.PersonnelDAO;
import dao.ViolationDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import metier.BesoinEnPersonnel;
import metier.ContrainteLegale;
import metier.Personnel;
import metier.Violation;

public class ControleurlistePersoParBesoin implements Initializable {
	
	private PersonnelDAO personnelDAO;
	
	private BesoinEnPersonnel besoinEnPersonnel;
	
	private BesoinEnPersonnelDAO besoinEnPersonnelDAO;
	
	private Personnel selectedPersonnel;
	
	@FXML
	private ListView<Personnel> listePersonnels = new ListView<Personnel>();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		  personnelDAO = new PersonnelDAO();
		  besoinEnPersonnelDAO = new BesoinEnPersonnelDAO();
		
	}
	
	public void setListe(List<Personnel> liste) {
	    this.listePersonnels.getItems().clear();
	    for(Personnel personnel : liste) {
	        listePersonnels.getItems().add(personnel);
	        listePersonnels.setOnMouseClicked(event -> {
	            System.out.println(listePersonnels.getSelectionModel().getSelectedItems());
	            selectedPersonnel =  listePersonnels.getSelectionModel().getSelectedItems().get(0);
	        });
	    }

	    System.out.println("Liste des personnels : "+liste.toString());
	}
	
	public void setBesoin(BesoinEnPersonnel besoinEnPersonnel) {
		this.besoinEnPersonnel = besoinEnPersonnel;
		this.getPersonnelByBesoinEnPersonnel(besoinEnPersonnel);
	}

	private void getPersonnelByBesoinEnPersonnel(BesoinEnPersonnel besoinEnPersonnel) {
		setListe(besoinEnPersonnelDAO.listerPersonnelParBesoin(besoinEnPersonnel.getIdBesoin()));
	}
	
	@FXML
	private void affetcerPerso(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vues/AjoutPersoAuBesoin.fxml"));
			Parent parent = loader.load();

			ControleurAjoutPeroAuBesoin controleurAjoutPeroAuBesoin = loader.getController();
			controleurAjoutPeroAuBesoin.setBesoin(besoinEnPersonnel);
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace(); // Print the exception to the console
			// Optionally show an alert with the error
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Error Loading the View");
			alert.setContentText("Cannot load the add requirement view. Please check the console for more details.");
			alert.showAndWait();
		}
	}
	
	public void refrechePerso() {
		this.getPersonnelByBesoinEnPersonnel(besoinEnPersonnel);
	}
	
	public void supprimerPerso(ActionEvent event) {
	    if (selectedPersonnel == null) {
	        showAlert(AlertType.ERROR, "Erreur", "Veuillez sélectionner un personnel à supprimer.");
	    } else {
	        try {
	            // Utilisation de la méthode vérifierSouschargeEtProposerAnnulation
	            if (besoinEnPersonnelDAO.verifierSouschargeEtProposerAnnulation(selectedPersonnel.getIdPersonnel(),
	                    besoinEnPersonnel.getIdBesoin())) {
	                // La suppression a été confirmée et réalisée avec succès
	                supprimerViolation(selectedPersonnel, besoinEnPersonnel);
	                refrechePerso();
	                showAlert(AlertType.INFORMATION, "Succès", "Le personnel a été supprimé avec succès.");
	            }
	        } catch (IllegalArgumentException e) {
	            // La suppression entraînerait une sous-charge
	            boolean userConfirmed = showSouschargeConfirmation(); // Affiche une alerte pour demander confirmation
	            if (userConfirmed) {
	                // Si l'utilisateur confirme, forcer la suppression malgré la sous-charge
	                if (besoinEnPersonnelDAO.annulerAffectationPersonnel(selectedPersonnel.getIdPersonnel(),
	                        besoinEnPersonnel.getIdBesoin())) {
		                supprimerViolation(selectedPersonnel, besoinEnPersonnel);
	                    refrechePerso();
	                    showAlert(AlertType.INFORMATION, "Succès", "Le personnel a été supprimé malgré la sous-charge.");
	                } else {
	                    showAlert(AlertType.ERROR, "Échec", "La suppression du personnel a échoué.");
	                }
	            }
	        }
	    }
	}
	
	private void supprimerViolation(Personnel selectedPersonnel, BesoinEnPersonnel besoinEnPersonnel) {
		ViolationDAO violationDAO = new ViolationDAO();
		List<Violation> violations = violationDAO.readAll().stream()
				.filter(v -> v.getPersonnel().getIdPersonnel() == selectedPersonnel.getIdPersonnel()
						&& v.getBesoin().getIdBesoin() == besoinEnPersonnel.getIdBesoin())
				.collect(Collectors.toList());
		for(Violation violation : violations) {
			violationDAO.delete(violation);
		}
	}

	private boolean showSouschargeConfirmation() {
	    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	    alert.setTitle("Confirmation de sous-charge");
	    alert.setHeaderText("Sous-charge détectée");
	    alert.setContentText("La suppression de ce personnel entraînera une sous-charge. Voulez-vous continuer ?");

	    Optional<ButtonType> result = alert.showAndWait();
	    return result.isPresent() && result.get() == ButtonType.OK;
	}

	
	private void showAlert(AlertType alertType, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	
	
	@FXML
	private void close(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}


}
