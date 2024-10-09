package controleur;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.sql.Date;
import dao.BesoinEnPersonnelDAO;
import dao.PersonnelDAO;
import dao.ViolationDAO;
import dao.ContrainteLegaleDAO;
import dao.ContrainteDeSpecialiteDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import metier.BesoinEnPersonnel;
import metier.Contrainte;
import metier.ContrainteLegale;
import metier.ContraintedeSpecialite;
import metier.Fonction;
import metier.Personnel;
import metier.Violation;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import java.util.Optional;

/**
 * Contrôleur gérant l'ajout de personnel à un besoin spécifique.
 * Cette classe est responsable de l'interface utilisateur permettant d'affecter
 * du personnel à un besoin en personnel.
 * 
 * Elle utilise les DAO pour accéder aux données et effectuer les opérations nécessaires.
 */
public class ControleurAjoutPeroAuBesoin implements Initializable {

	private PersonnelDAO personnelDAO;

	private BesoinEnPersonnel besoinEnPersonnel;

	private BesoinEnPersonnelDAO besoinEnPersonnelDAO;

	@FXML
	private ComboBox<Personnel> comboPersonnel;

	
	/**
     * Initialise le contrôleur. Cette méthode est appelée automatiquement
     * après que le fichier FXML a été chargé.
     *
     * @param arg0 L'URL utilisée pour résoudre les chemins relatifs pour
     *             l'objet racine, ou null si l'URL n'est pas connue.
     * @param arg1 Les ressources utilisées pour localiser l'objet racine, ou null si
     *             les ressources ne sont pas spécifiées.
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		personnelDAO = new PersonnelDAO();
		besoinEnPersonnelDAO = new BesoinEnPersonnelDAO();
	}

	/**
     * Définit le besoin en personnel pour lequel le personnel doit être ajouté.
     * Cette méthode est utilisée pour passer les détails du besoin en personnel
     * sélectionné à ce contrôleur.
     *
     * @param besoinEnPersonnel Le besoin en personnel à gérer.
     */
	public void setBesoin(BesoinEnPersonnel besoinEnPersonnel) {
		this.besoinEnPersonnel = besoinEnPersonnel;
		this.getPersonnelDisponible(besoinEnPersonnel);
	}

	/**
     * Remplit la ComboBox avec les personnels disponibles pour le besoin en personnel donné.
     * Cette méthode filtre et affiche les personnels qui peuvent être assignés au besoin
     * en personnel spécifié en fonction de leur fonction et spécialité.
     *
     * @param besoinEnPersonnel Le besoin en personnel pour lequel le personnel disponible doit être listé.
     */
	private void getPersonnelDisponible(BesoinEnPersonnel besoinEnPersonnel) {
		List<Personnel> personnels = this.besoinEnPersonnelDAO.listerPersonnelDisponiblePourBesoin(
				besoinEnPersonnel.getFonction().getIdFonction(),
				besoinEnPersonnel.getSpecialite() != null ? besoinEnPersonnel.getSpecialite().getIdSpecialite() : null);
		comboPersonnel.getItems().setAll(personnels);
	}

	@FXML
	private void close(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

	/**
     * Ajoute le personnel sélectionné au besoin en personnel spécifié.
     * Cette méthode est appelée lorsqu'un utilisateur confirme l'ajout d'un personnel à un besoin.
     * Elle vérifie les contraintes légales et de spécialité avant de procéder à l'affectation.
     *
     * @param event L'événement qui a déclenché l'appel de cette méthode.
     */
	@FXML
	private void ajouterPerso(ActionEvent event) {
		Personnel personnel = comboPersonnel.getValue();
		if (personnel == null) {
			showAlert(AlertType.ERROR, "Erreur de validation", "Veuillez sélectionner un personnel.");
			return;
		}

		// Création des instances nécessaires
		BesoinEnPersonnelDAO besoinDAO = new BesoinEnPersonnelDAO();
		ContrainteLegaleDAO contrainteLegaleDAO = new ContrainteLegaleDAO();
		ContrainteDeSpecialiteDAO contrainteDeSpecialiteDAO = new ContrainteDeSpecialiteDAO();

		// Obtenez les dates nécessaires pour les vérifications de contraintes
		// Assurez-vous que besoinEnPersonnel.getCreneau().getDate() retourne une
		// java.sql.Date
		Date dateDuJour = besoinEnPersonnel.getCreneau().getDate();
		Date[] weekDates = besoinDAO.getStartAndEndOfWeekDates(besoinEnPersonnel.getCreneau());
		Date[] monthDates = besoinDAO.getStartAndEndOfMonthDates(besoinEnPersonnel.getCreneau());

		// Vérifiez les contraintes légales et de spécialité
		ArrayList<Violation> violations = new ArrayList<>();
		violations.addAll(contrainteLegaleDAO.checker(personnel, weekDates[0], weekDates[1], monthDates[0],
				monthDates[1], dateDuJour, besoinEnPersonnel));
		violations.addAll(contrainteDeSpecialiteDAO.checker(personnel, weekDates[0],
		 weekDates[1], monthDates[0], monthDates[1], dateDuJour, besoinEnPersonnel));

		boolean proceedWithAssignment = true;
		if (!violations.isEmpty()) {
			// Afficher un pop-up pour notifier des violations
			proceedWithAssignment = showViolationsConfirmation(violations);
		}

		// Vérifier si l'utilisateur souhaite poursuivre malgré les violations
		if (!proceedWithAssignment)
			return;

		boolean affectationReussie = besoinDAO.affecterPersonnelAuBesoin(personnel.getIdPersonnel(),
				besoinEnPersonnel.getIdBesoin());
		if (affectationReussie) {
			showAlert(AlertType.INFORMATION, "Succès", "Le personnel a été ajouté avec succès.");
			// Ajouter les violations à la base de données
			ViolationDAO violationDAO = new ViolationDAO();
			if (!violations.isEmpty()) {
				for (Violation violation : violations) {
					violationDAO.create(violation);
				}
			}
			close(event); // Fermer la fenêtre actuelle
		} else

		{
			showAlert(AlertType.ERROR, "Échec", "L'ajout de personnel a échoué.");
		}
	}

	/**
     * Affiche une boîte de dialogue de confirmation lorsqu'une ou plusieurs violations sont détectées.
     * Cette méthode génère une alerte pour informer l'utilisateur des violations des contraintes
     * et demande confirmation avant de procéder à l'affectation.
     *
     * @param violations Liste des violations détectées.
     * @return true si l'utilisateur confirme vouloir continuer malgré les violations, false sinon.
     */
	private boolean showViolationsConfirmation(ArrayList<Violation> violations) {
		// Créer une alerte de confirmation
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Violations détectées");
		alert.setHeaderText("Des violations ont été détectées");

		// Construire le message d'alerte en listant les violations
		StringBuilder content = new StringBuilder("Les contraintes suivantes ne sont pas respectées : \n");
		for (Violation violation : violations) {
			content.append("- ").append(violation.getContrainte().getNom()).append("\n"); 
		}
		
		content.append("Voulez-vous continuer avec l'affectation ?");
		alert.setContentText(content.toString());

		// Ajouter les types de bouton Oui et Non
		ButtonType buttonTypeYes = new ButtonType("Oui", ButtonBar.ButtonData.YES);
		ButtonType buttonTypeNo = new ButtonType("Non", ButtonBar.ButtonData.NO);
		alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

		// Afficher l'alerte et attendre une réponse de l'utilisateur
		Optional<ButtonType> result = alert.showAndWait();

		// Vérifier la réponse de l'utilisateur et agir en conséquence
		return result.isPresent() && result.get() == buttonTypeYes;
	}

	// pas utilisé finalement 
	private boolean showSurchargeConfirmation() {
		// Créer une alerte de confirmation
		Alert surchargeAlert = new Alert(Alert.AlertType.CONFIRMATION);
		surchargeAlert.setTitle("Confirmation de Surcharge");
		surchargeAlert.setHeaderText("Surcharge détectée");
		surchargeAlert.setContentText(
				"Ce besoin a déjà atteint le nombre requis de personnel. Voulez-vous continuer avec l'affectation ?");

		// Ajouter les types de bouton Oui et Non
		ButtonType buttonTypeYes = new ButtonType("Oui", ButtonBar.ButtonData.YES);
		ButtonType buttonTypeNo = new ButtonType("Non", ButtonBar.ButtonData.NO);
		surchargeAlert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

		// Afficher l'alerte et attendre une réponse de l'utilisateur
		Optional<ButtonType> result = surchargeAlert.showAndWait();

		// Vérifier la réponse de l'utilisateur et agir en conséquence
		return result.isPresent() && result.get() == buttonTypeYes;
	}

	/**
     * Affiche une alerte à l'utilisateur.
     * Cette méthode est utilisée pour informer l'utilisateur des différentes situations,
     * comme les erreurs, les succès ou les confirmations.
     *
     * @param alertType Le type d'alerte (ex: ERROR, INFORMATION).
     * @param title     Le titre de l'alerte.
     * @param message   Le message à afficher dans l'alerte.
     */
	private void showAlert1(AlertType alertType, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private boolean validateInput() {
		return comboPersonnel.getValue() != null;
	}

	/**
     * Affiche une alerte à l'utilisateur.
     * Cette méthode est utilisée pour informer l'utilisateur des différentes situations,
     * comme les erreurs, les succès ou les confirmations.
     *
     * @param alertType Le type d'alerte (ex: ERROR, INFORMATION).
     * @param title     Le titre de l'alerte.
     * @param message   Le message à afficher dans l'alerte.
     */
	private void showAlert(AlertType alertType, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private void clearForm() {
		comboPersonnel.getSelectionModel().clearSelection();
	}

}
