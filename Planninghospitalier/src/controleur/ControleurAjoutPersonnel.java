package controleur;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.FonctionDAO;
import dao.PersonnelDAO;
import dao.SpecialiteDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import metier.Fonction;
import metier.Personnel;
import metier.Specialite;

/**
 * @author 22203517t - Jules Forest
 */
public class ControleurAjoutPersonnel
    implements Initializable {

    /**
     * attributs nécessaires au fonctionnement du controleur pour switcher de page.
     */
    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * DAO de la classe métier Personnel
     */
    private PersonnelDAO personnelDAO;

    @FXML
    /**
     * Champ de saisie du nom du personnel
     */
    private TextField champNom;

    @FXML
    /**
     * Champ de saisie du prénom du personnel
     */
    private TextField champPrenom;

    @FXML
    /**
     * Champ de choix de la date de naissance du personnel
     */
    private DatePicker champDateNaissance;

    @FXML
    /**
     * Champ de saisie du temps de travail mensuel du personnel
     */
    private TextField champTTM;

    @FXML
    /**
     * Champ de choix de la fonction du personnel
     */
    private ComboBox<Fonction> champFonction = new ComboBox<>();

    /**
     * Champ de choix des spécialités du personnel.
     */
    @FXML
    private ComboBox<Specialite> champSpecialite = new ComboBox<>();

    /**
     * Méthode appelée après que le contenu du FXML ai été injecté dans le contrôleur.
     * @param URL emplacement du fichier FXML source
     * @param RessourceBundle utilisé pour récupérer
     *       les textes internationalisés
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.personnelDAO = new PersonnelDAO();
        FonctionDAO fonctionDAO = new FonctionDAO();

        //initialisation de la liste des fonctions
        ArrayList<Fonction> listeFonctions = fonctionDAO.readAll();
        //initialisation de la liste des spécialités
        ArrayList<Specialite> listeSpe = new ArrayList<>();

        //limitation du nombre de caractères
        champNom.setTextFormatter(new TextFormatter<>(c ->
            c.getControlNewText().matches(".{0,50}") ? c : null));
        champPrenom.setTextFormatter(new TextFormatter<>(c ->
            c.getControlNewText().matches(".{0,50}") ? c : null));
        champTTM.setTextFormatter(new TextFormatter<>(c ->
            c.getControlNewText().matches(".{0,4}") ? c : null));

        champFonction.setPromptText("Choisir une fonction");
        champFonction.setVisibleRowCount(5);
        champFonction.getItems().addAll(listeFonctions);

        //la liste est vide car elle ne représente que les spécialités
        //qui vont être ajoutées au Personnel
        champSpecialite.setPromptText("Spécialités du personnel");
        champSpecialite.setVisibleRowCount(5);
        champSpecialite.getItems().addAll(listeSpe);

        champDateNaissance.setEditable(false);
    }

    /**
     * Méthode qui permet de switch sur la page de la liste des personnels (ListePersonnels).
     * @param event
     * @throws IOException
     */
    @FXML
    private void switchToListePersonnel(ActionEvent event)
            throws IOException {
        root = FXMLLoader.load(getClass().getResource("/vues/ListePersonnels2.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
        stage.centerOnScreen();
    }

    /**
     * Méthode qui permet d'ajouter un personnel via la récupération de tout les champs
     * de la page AjoutPersonnel.
     * @param event
     * @throws IOException
     */
    public void ajouterPersonnel(ActionEvent event)
            throws IOException {
        try {
            String nom = champNom.getText().trim();
            String prenom = champPrenom.getText().trim();
            String ttm = champTTM.getText().trim();
            if (nom.isEmpty() || prenom.isEmpty() || champDateNaissance.getValue() == null
                    || ttm.isEmpty() || champFonction.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Merci de remplir tout les champs");
                alert.showAndWait();
            } else {
                ArrayList<Specialite> listeSpe = new ArrayList<>();
                listeSpe.addAll(champSpecialite.getItems());

                Personnel newPersonnel = new Personnel(0, champNom.getText(), champPrenom.getText(),
                        String.valueOf(champDateNaissance.getValue()), Integer.parseInt(champTTM.getText()),
                        champFonction.getValue(), listeSpe, null);

                personnelDAO.create(newPersonnel);
                this.clean();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Ajout réussi !");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Veuillez n'utiliser que des chiffres pour le temps de travail mensuel.");
            alert.showAndWait();
        }
    }

    @FXML
    /**
     * Méthode qui permet de fermer la page
     * @param event
     */
    private void close(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Méthode qui permet de vider tout les champs de la page AjoutPersonnel
     */
    private void clean() {
        champNom.setText(null);
        champPrenom.setText(null);
        champDateNaissance.setValue(null);
        champTTM.setText(null);
        champFonction.setValue(null);
        champSpecialite.setValue(null);
    }

    /**
     * Méthode qui permet d'ajouter une spécialité au champ de saisie des spécialités
     * Elle va appeler un pop-up qui permettra de choisir une spécialité parmi toutes celles.
     * enregistrées en bdd afin de l'ajouter
     */
    @FXML
    private void ajouterSpe() {
        ArrayList<Specialite> specialitesProposees = (new SpecialiteDAO()).readAll();
        ArrayList<Specialite> speTampon = new ArrayList<>();

        // optimisable
        for (Specialite spe : specialitesProposees) {
            for (Specialite spe2 : champSpecialite.getItems()) {
                if (spe.getIdSpecialite() == spe2.getIdSpecialite()) {
                    speTampon.add(spe);
                }
            }
        }
        specialitesProposees.removeAll(speTampon);

        ChoiceDialog<Specialite> dialog = new ChoiceDialog<>(null, specialitesProposees);
        dialog.setTitle("Ajout d'une spécialité");
        dialog.setHeaderText("Choisissez une spécialité à ajouter");
        dialog.setContentText("Votre choix :");

        Optional<Specialite> result = dialog.showAndWait();
        if (result.isPresent()){
            champSpecialite.getItems().add(result.get());
        }
    }

    @FXML
    /**
     * Méthode qui permet d'enlever une méthode du champ de saisie des spécialités
     * Elle va appeler un pop-up qui permettra de choisir une spécialité parmi
     * toutes celles du champ de saisie des spécialites afin de la supprimer de celui-ci
     */
    private void enleverSpe() {
        ChoiceDialog<Specialite> dialog = new ChoiceDialog<>(
                champSpecialite.getItems().get(0), champSpecialite.getItems());
        dialog.setTitle("Retrait d'une spécialité");
        dialog.setHeaderText("Choisissez une spécialité à enlever");
        dialog.setContentText("Votre choix :");

        Optional<Specialite> result = dialog.showAndWait();
        if (result.isPresent()) {
            champSpecialite.getItems().remove(result.get());
        }

    }
}
