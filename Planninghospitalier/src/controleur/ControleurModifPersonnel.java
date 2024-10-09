package controleur;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.FonctionDAO;
import dao.PersonnelDAO;
import dao.SpecialiteDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

/*
 * @author 22203517t - Jules Forest
 */
public class ControleurModifPersonnel implements Initializable{

    /**
     * attributs nécessaires au fonctionnement du controleur pour switcher de pages.
     */
    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * DAO de la classe métier Personnel.
     */
    private PersonnelDAO personnelDAO;

    /**
     * Personnel dont on va afficher la fiche.
     */
    private Personnel personnel;

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
    }

    /**
     * Méthode qui permet de set le personnel que l'on veut modifier.
     * La méthode va aussi se charger de remplir toutes les zones de saisie.
     * @param personnel - personnel que l'on veut modifier
     */
    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
        FonctionDAO fonctionDAO = new FonctionDAO();
        SpecialiteDAO speDAO = new SpecialiteDAO();
        ArrayList<Fonction> listeFonctions = fonctionDAO.readAll();
        ArrayList<Specialite> listeSpe = personnel.getSpecialites();

        //limitation du nombre de caractères
        champNom.setTextFormatter(new TextFormatter<>(c ->
            c.getControlNewText().matches(".{0,50}") ? c : null));
        champPrenom.setTextFormatter(new TextFormatter<>(c ->
            c.getControlNewText().matches(".{0,50}") ? c : null));
        champTTM.setTextFormatter(new TextFormatter<>(c ->
            c.getControlNewText().matches(".{0,4}") ? c : null));

        champNom.setText(personnel.getNomPersonnel());
        champPrenom.setText(personnel.getPrenomPersonnel());
        champDateNaissance.setValue(LocalDate.parse(personnel.getDateNaissance()));
        champDateNaissance.setEditable(false);
        champTTM.setText(String.valueOf(personnel.getTempsTravailMensuel()));

        champFonction.setPromptText(personnel.getFonction().toString());
        champFonction.setValue(personnel.getFonction());
        champFonction.setVisibleRowCount(5);
        champFonction.getItems().addAll(listeFonctions);

        champSpecialite.setPromptText("Liste des spécialités");
        champSpecialite.setVisibleRowCount(5);
        champSpecialite.getItems().addAll(listeSpe);
    }

    /**
     * Méthode qui permet de gérer la modification d'un personnel.
     * La méthode va récupérer le contenu des champs de saisie et
     * faire la modification du personnel en fonction
     * @param event
     * @throws IOException
     */
    public void modifPersonnel(ActionEvent event)
            throws IOException {

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

            Personnel newPersonnel = new Personnel(
                    personnel.getIdPersonnel(),
                    champNom.getText(),
                    champPrenom.getText(),
                    String.valueOf(champDateNaissance.getValue()),
                    Integer.parseInt(champTTM.getText()),
                    champFonction.getValue(), listeSpe, personnel.getViolations());

            personnelDAO.update(newPersonnel);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Modification réussie !");
            alert.showAndWait();
        }
        this.close(event);
    }

    /**
     * Méthode qui permet de fermer la fenetre
     * @param event
     */
    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Méthode qui permet de gérer l'ajout d'une spécialité
     * La méthode va créer un pop-up qui propose toutes les spécialités
     * en base, le cadre choisi et la spécialité est ajouté a champSpe
     *
     */
    @FXML
    private void ajouterSpe() {
        ArrayList<Specialite> specialitesProposees = (new SpecialiteDAO()).readAll();
        ArrayList<Specialite> speTampon = new ArrayList<>();
        //optimisable
        // Ne filtre que les spe que le personnel n'a pas
        for (Specialite spe : specialitesProposees) {
            for (Specialite spe2 : personnel.getSpecialites()) {
                if (spe.getIdSpecialite() == spe2.getIdSpecialite()) {
                    speTampon.add(spe);
                }
            }
        }
        specialitesProposees.removeAll(speTampon);

        //création du pop-up et affichage
        ChoiceDialog<Specialite> dialog = new ChoiceDialog<>(null, specialitesProposees);
        dialog.setTitle("Ajout d'une spécialité");
        dialog.setHeaderText("Choisissez une spécialité à ajouter");
        dialog.setContentText("Votre choix :");
        Optional<Specialite> result = dialog.showAndWait();

        // traitemet de la réponse
        if (result.isPresent()){
            champSpecialite.getItems().add(result.get());
            personnel.addSpecialite(result.get());
        }
    }

    /** Méthode qui permet de gérer la suppression d'une spécialité
    * La méthode va créer un pop-up qui propose toutes les spécialités
    * du personnel, le cadre choisi et la spécialité est supprimé de champSpe
    */
    @FXML
    private void enleverSpe() {
        // création et affichage du pop-up
        ChoiceDialog<Specialite> dialog = new ChoiceDialog<>(
                personnel.getSpecialites().get(0), champSpecialite.getItems());
        dialog.setTitle("Retrait d'une spécialité");
        dialog.setHeaderText("Choisissez une spécialité à enlever");
        dialog.setContentText("Votre choix :");
        Optional<Specialite> result = dialog.showAndWait();

        // traitement de la réponse
        if (result.isPresent()){
            champSpecialite.getItems().remove(result.get());
        }
    }
}
