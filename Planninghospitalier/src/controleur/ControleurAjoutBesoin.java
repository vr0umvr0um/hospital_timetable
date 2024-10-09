package controleur;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dao.BesoinEnPersonnelDAO;
import dao.CreneauDAO;
import dao.FonctionDAO;
import dao.SpecialiteDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import metier.BesoinEnPersonnel;
import metier.Creneau;
import metier.Fonction;
import metier.Specialite;




public class ControleurAjoutBesoin implements Initializable {

    private int idCreneau;  // ID du créneau reçu du contrôleur précédent


    @FXML
    private ComboBox<Fonction> comboFonction;

    @FXML
    private ComboBox<Specialite> comboSpecialite;

    @FXML
    private TextField txtEffectif;

    private FonctionDAO fonctionDAO;
    private SpecialiteDAO specialiteDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fonctionDAO = new FonctionDAO();
        specialiteDAO = new SpecialiteDAO();
        loadFonctions();
        loadSpecialites();
    }

    private void loadFonctions() {
        List<Fonction> fonctions = fonctionDAO.readAll();
        comboFonction.getItems().setAll(fonctions);
    }

    private void loadSpecialites() {
        List<Specialite> specialites = specialiteDAO.readAll();
        comboSpecialite.getItems().setAll(specialites);
    }

    public void setIdCreneau(int id) {
        this.idCreneau = id;
        // Affichage dans la console pour le débogage, à retirer en production
        System.out.println("ID du créneau reçu: " + idCreneau);
    }

    @FXML
    private void ajouterBesoin(ActionEvent event) {
        if (validateInput()) {
            Fonction fonction = comboFonction.getValue();
            Specialite specialite = comboSpecialite.getValue(); // Peut être null
            int effectif;
            try {
                effectif = Integer.parseInt(txtEffectif.getText());

                // Vérifier que l'effectif est strictement supérieur à 0
                if (effectif <= 0) {
                    showAlert(AlertType.ERROR, "Erreur d'effectif", "L'effectif doit être supérieur à 0.");
                    return;
                }

            } catch (NumberFormatException e) {
                showAlert(AlertType.ERROR, "Erreur de saisie", "L'effectif doit être un nombre valide.");
                return;
            }

            // Utilisation de CreneauDAO pour obtenir l'objet Creneau basé sur l'ID
            CreneauDAO creneauDAO = new CreneauDAO();
            Creneau creneau = creneauDAO.getCreneau(idCreneau);

            if(creneau == null) {
                showAlert(AlertType.ERROR, "Erreur", "Le créneau spécifié n'existe pas.");
                return;
            }

            // Création de l'objet BesoinEnPersonnel avec le constructeur qui prend des paramètres
            BesoinEnPersonnel besoinEnPersonnel = new BesoinEnPersonnel(0, effectif, creneau, specialite, fonction);

            // Utilisation de la méthode create du DAO
            BesoinEnPersonnelDAO besoinEnPersonnelDAO = new BesoinEnPersonnelDAO();
            BesoinEnPersonnel besoinCree = besoinEnPersonnelDAO.create(besoinEnPersonnel);

            if (besoinCree != null) {
                showAlert(AlertType.INFORMATION, "Succès", "Le besoin en personnel a été ajouté avec succès.");
                clearForm();
                close(event);
            } else {
                showAlert(AlertType.ERROR, "Échec", "L'ajout du besoin en personnel a échoué.");
            }
        } else {
            showAlert(AlertType.ERROR, "Erreur de validation", "Veuillez remplir tous les champs obligatoires.");
        }
    }



    private boolean validateInput() {
        return comboFonction.getValue() != null && !txtEffectif.getText().isEmpty();
    }



    private void clearForm() {
        comboFonction.getSelectionModel().clearSelection();
        comboSpecialite.getSelectionModel().clearSelection();
        txtEffectif.clear();
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
        // Cast l'événement source en Node et le récupérer dans une variable de scène
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close(); // Ferme la fenêtre
    }



}



