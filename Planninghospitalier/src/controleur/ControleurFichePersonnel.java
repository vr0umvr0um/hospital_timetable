package controleur;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.PersonnelDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import metier.Personnel;
import metier.Specialite;

/**
 * @author 22203517t - Jules Forest
 */
public class ControleurFichePersonnel
    implements Initializable {

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

    /**
     * Zone d'affichage du nom et du prenom en même temps.
     */
    @FXML
    private Label nomPrenom;

    /**
     * Zone d'affichage de l'initial du prenom du personnel.
     */
    @FXML
    private Label initiale;

    /**
     * Zone d'affichage du nom.
     */
    @FXML
    private Label nom;

    /**
     * Zone d'affichage du prenom.
     */
    @FXML
    private Label prenom;

    /**
     * Zone d'affichage de la date de naissance
     */
    @FXML
    private Label dateNaissance;

    /**
     * Zone d'affichage de la fonction
     */
    @FXML
    private Label fonction;

    /**
     * Liste déroulante des spécialités du personnel
     */
    @FXML
    private ComboBox<Specialite> spe;

    /**
     * Zone d'affichage du temps de travail mensuel
     */
    @FXML
    private Label ttm;

    /**
     * Zone d'affichage de l'id du personnel
     */
    @FXML
    private Label id;

    /**
     * Méthode appelée après que le contenu du FXML ai été injecté dans le contrôleur.
     * @param URL emplacement du fichier FXML source
     * @param RessourceBundle utilisé pour récupérer
     *       les textes internationalisés
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        personnelDAO = new PersonnelDAO();
    }

    /**
     * Méthode qui permet de set le personnel dont on veut la fiche.
     * La méthode va aussi se charger de remplir toutes les zones d'affichage.
     * @param personnel - personnel dont on veut la fiche
     */
    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
        this.spe.getItems().clear();
        nomPrenom.setText(personnel.getNomPersonnel() + " " + personnel.getPrenomPersonnel());
        initiale.setText(personnel.getPrenomPersonnel().substring(0, 1).toUpperCase());
        nom.setText("Nom : " + personnel.getNomPersonnel());
        prenom.setText("Prenom : " + personnel.getPrenomPersonnel());
        dateNaissance.setText("Date de naissance : " + personnel.getDateNaissance());
	    fonction.setText("Fonction : " + personnel.getFonction().toString());
        spe.setPromptText("Spécialités du personnel");
        spe.setVisibleRowCount(5);
        spe.getItems().addAll(personnel.getSpecialites());
	    ttm.setText("Temps de travail mensuel : " + String.valueOf(personnel.getTempsTravailMensuel()));
	    id.setText("Identifiant : " + String.valueOf(personnel.getIdPersonnel()));
	}
    @FXML
    private void openPersonnelEDT(ActionEvent event) {
    	try {
			close(event);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vues/PersonnelEDT.fxml"));
			Parent parent = loader.load();

			ControleurPersonnelEDT personnelEDTControleur = loader.getController();
			personnelEDTControleur.setPersonnel(personnel);
			
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			
			Image image = new Image("/vues/images/heart-pulse-solid.png");
			stage.getIcons().add(image);
			
			stage.setScene(scene);
			stage.initStyle(StageStyle.TRANSPARENT);
			
			
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
			// Gérez l'exception d'I/O
		}
	}

	/**
	 * Méthode pour aller à la page des EDT (Creneau.fxml).
	 * @param event
	 * @throws IOException
	 */
	public void switchToCreneau(ActionEvent event)
	        throws IOException {

		root = FXMLLoader.load(getClass().getResource("/vues/Creneau.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);

		stage.show();
		stage.centerOnScreen();
	}

	/**
	 * Méthode qui permet d'aller sur la page de modification d'un personnel.
	 * @param event
	 * @throws IOException
	 */
	public void switchToModifPersonnel(ActionEvent event)
	        throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vues/ModifPersonnel.fxml"));
            Parent parent = loader.load();

            // On passe le contrôleur de la nouvelle fenêtre
            ControleurModifPersonnel controleurModifPersonnel = loader.getController();

            // On passe les détails du personnel au contrôleur de la nouvelle fenêtre
            controleurModifPersonnel.setPersonnel(personnel);

            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
            this.refresh();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	/**
	 * Méthode qui permet de fermer la fenetre.
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void close(ActionEvent event)
	        throws IOException {
	    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    stage.close();
	}
	
	/**
	 * Permet de réduire la fenêtre et de la mettre dans la barre des tâches
	 * 
	 * @param event Action event généralement un bouton qui indique la pression de
	 *              ce dernier
	 */
	@FXML
	private void min(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}

	/**
	 * Permet d'agrandir l'appplication pour qu'elle soit afficher sur tout l'écran
	 * 
	 * @param event Action event généralement un bouton qui indique la pression de
	 *              ce dernier
	 */
	@FXML
	private void max(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setFullScreen(true);
	}

	/**
	 * Méthode qui permet de revenir sur la liste des personnels (ListePersonnels.fxml).
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void retour(ActionEvent event)
	        throws IOException {
	    try {
	        root = FXMLLoader.load(getClass().getResource("/vues/ListePersonnels2.fxml"));
	        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        scene = new Scene(root);
	        stage.setScene(scene);

	        stage.show();
	        stage.centerOnScreen();
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	}

	/**
	 * Méthode qui permet de gérer la suppression d'un personnel.
	 * La méthode va créer un pop-up pour valider la suppression
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void supprimerPersonnel(ActionEvent event)
	        throws IOException {
	    String message = "Etes-vous sur de vouloir supprimer ce personnel ?";
	    if (personnelDAO.isAffected(this.personnel.getIdPersonnel())) {
	        message = "Etes-vous sur de vouloir supprimer ce personnel ?\n"
	                + "ATTENTION : CE PERSONNEL EST AFFECTE "
	                + "SUR DES CRENEAUX !";
	    }

        Optional<ButtonType> answer = creerPopUpValidation(message);
        if (answer.get() == ButtonType.OK) {
            personnelDAO.delete(personnel);
            creerPopUpSucces("Suppression réussie !");
            this.retour(event);
        }
	}

	/**
	 * Méthode qui va créer un pop-up de validation.
	 * @param message - message à afficher dans le pop-up
	 * @return Optional<ButtonType> réponse du pop-up
	 */
    public Optional<ButtonType> creerPopUpValidation(String message) {
        Alert dialogC = new Alert(AlertType.CONFIRMATION);
        dialogC.setTitle("VALIDATION");
        dialogC.setHeaderText(null);
        dialogC.setContentText(message);
        Optional<ButtonType> answer = dialogC.showAndWait();
        return answer;
    }

    /**
     * Méthode qui crée et affiche un pop-up d'information
     * @param message - message du pop-up
     */
    public void creerPopUpSucces(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Méthode qui va refresh la page avec les valeurs les plus récentes
     */
    public void refresh() {
        this.setPersonnel(personnelDAO.read(personnel.getIdPersonnel()));
    }
}
