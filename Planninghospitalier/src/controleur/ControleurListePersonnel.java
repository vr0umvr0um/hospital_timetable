package controleur;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import metier.Personnel;

/**
 * @author 22203517t - Jules Forest
 */
public class ControleurListePersonnel
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
	 * Champ de saisie d'un id pour rechercher un personnel.
	 */
	@FXML
	private TextField champRecherche;

	/**
	 * ListView représentant une liste de personnels.
	 */
	@FXML
	private ListView<Personnel> listePersonnels = new ListView<>();

	/**
     * Méthode appelée après que le contenu du FXML ai été injecté dans le contrôleur.
     * @param URL emplacement du fichier FXML source
     * @param RessourceBundle utilisé pour récupérer
     *       les textes internationalisés
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
	    personnelDAO = new PersonnelDAO();
	    ArrayList<Personnel> liste = personnelDAO.readAll();
	    setListe(liste);
	}

	/**
	 * Méthode qui permet remplacer la listeView des Personnels par une nouvelle.
	 * @param liste - liste de Personnels que l'on va afficher
	 */
	public void setListe(ArrayList<Personnel> liste) {
	    // on vide la listeView
	    this.listePersonnels.getItems().clear();
	    // pour chaque Personnel de liste, on va créer une cellule dans la listeView
	    for (Personnel personnel : liste) {
	        listePersonnels.getItems().add(personnel);
	        listePersonnels.setOnMouseClicked(event -> {
	            try {
	                this.switchToFichePersonnel(event, listePersonnels
	                        .getSelectionModel().getSelectedItems().get(0));
	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            } catch (IndexOutOfBoundsException e2) {
	            }
	        });
	    }
	}

	/**
	 * Méthode qui permet d'aller sur la page FichePersonnel2,
	 * soit la fiche d'un Personnel.
	 * @param event
	 * @param personnel - le personnel dont on veut la fiche
	 * @throws IOException
	 */
	private void switchToFichePersonnel(MouseEvent event, Personnel personnel)
	        throws IOException {
        // TODO Auto-generated method stub
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vues/FichePersonnel2.fxml"));
            Parent parent = loader.load();

            // Ob obtient le contrôleur de la nouvelle fenêtre
            ControleurFichePersonnel controleurFichePersonnel = loader.getController();

            // On passe les détails du personnel au contrôleur de la nouvelle fenêtre
            controleurFichePersonnel.setPersonnel(personnel);

            stage = new Stage();
            scene = new Scene(parent);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
	 * Permet de switch vers la page Creneau en récupérant la page actuelle et en la
	 * remplaçant
	 * 
	 * @param event Action event généralement un bouton qui indique la pression de
	 *              ce dernier
	 * @throws IOException renvoit un message en cas d'erreur
	 */
	public void switchToCreneau(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("/vues/Creneau.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		;
		stage.show();
		stage.centerOnScreen();
	}

	/**
	 * Permet de switch vers la page PersonnelEDTChoix en récupérant la page
	 * actuelle et en la remplaçant
	 * 
	 * @param event Action event généralement un bouton qui indique la pression de
	 *              ce dernier
	 * @throws IOException renvoit un message en cas d'erreur
	 */

	public void switchToPersonnelEDT(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("/vues/PersonnelEDTChoix.fxml"));
		stage = new Stage();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.initStyle(StageStyle.TRANSPARENT);

		stage.show();
		stage.centerOnScreen();
	}

	/**
	 * Permet de switch vers la page ListePersonnels2 en récupérant la page actuelle
	 * et en la remplaçant
	 * 
	 * @param event Action event généralement un bouton qui indique la pression de
	 *              ce dernier
	 * @throws IOException renvoit un message en cas d'erreur
	 */

	public void switchToPersonnel(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("/vues/ListePersonnels2.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		;
		stage.show();
		stage.centerOnScreen();
	}

	/**
	 * la méthode switchToAddCreneau permet de changer la scène actuelle vers la vue
	 * addCreneau.
	 *
	 * @param event : L'événement déclencheur de l'action de changement de scène (un
	 *              bouton)
	 * @throws IOException : En cas d'erreur lors du chargement de la vue
	 *                     addCreneau.fxml
	 */
	public void switchToAddCreneau(final ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("/vues/addCreneau.fxml"));
		stage = new Stage();
		scene = new Scene(root);
		stage.setScene(scene);

		stage.show();
		stage.centerOnScreen();
	}

	/**
	 * Permet de switch vers la page afficheFonction en récupérant la page actuelle
	 * et en la remplaçant
	 * 
	 * @param event Action event généralement un bouton qui indique la pression de
	 *              ce dernier
	 * @throws IOException renvoit un message en cas d'erreur
	 */

	public void switchToFonction(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("/vues/afficheFonction.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		;
		stage.show();
		stage.centerOnScreen();
	}

	/**
	 * Permet de switch vers la page ConsulterSpecialites en récupérant la page
	 * actuelle et en la remplaçant
	 * 
	 * @param event Action event généralement un bouton qui indique la pression de
	 *              ce dernier
	 * @throws IOException renvoit un message en cas d'erreur
	 */
	public void switchToSpecialite(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("/vues/ConsulterSpecialites.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		;
		stage.show();
		stage.centerOnScreen();
	}

	/**
	 * Permet de switch vers la page Probleme en récupérant la page actuelle et en
	 * la remplaçant
	 * 
	 * @param event Action event généralement un bouton qui indique la pression de
	 *              ce dernier
	 * @throws IOException renvoit un message en cas d'erreur
	 */
	public void switchToProbleme(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("/vues/Probleme.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		;
		stage.show();
		stage.centerOnScreen();
	}

	/**
	 * Permet de switch vers la page Contrainte en récupérant la page actuelle et en
	 * la remplaçant
	 * 
	 * @param event Action event généralement un bouton qui indique la pression de
	 *              ce dernier
	 * @throws IOException renvoit un message en cas d'erreur
	 */
	public void switchToContrainte(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("/vues/Contrainte.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		;
		stage.show();
		stage.centerOnScreen();
	}

	/**
     * Méthode qui permet d'aller sur la page AjoutPersonnel2,
     * soit la page d'ajout d'un personnel.
  	 * @param event
	 * @throws IOException
	 */
	public void switchToAjoutPersonnel(ActionEvent event)
	        throws IOException {
        root = FXMLLoader.load(getClass().getResource("/vues/AjoutPersonnel2.fxml"));
        stage = new Stage();
        scene = new Scene(root);
        stage.setScene(scene);

        stage.centerOnScreen();
        stage.show();
	}
	
	

	/**
	 * Méthode qui permet de fermer la page.
	 * @param event
	 */
	@FXML
	private void close(ActionEvent event) {
	    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    stage.close();
	}

	/**
	 * Méthode qui permet de retourner à la page Creneau,
	 * soit l'affichage des EDT.
	 * @param event
	 */
	@FXML
	private void retour(ActionEvent event) {
	    try {
            root = FXMLLoader.load(getClass().getResource("/vues/Creneau.fxml"));
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
	 * Méthode qui permet de gérer la recherche d'un personnel via son id.
	 * En cas de problème de saisie, elle renverra des pop-up d'erreur
	 * @param event
	 */
	@FXML
	private void recherchePersonnelViaID(ActionEvent event) {
	    try {
	        // teste si le champRecherche est vide,
	        // si oui renvoie tout les personnels
	        // sinon lance la recherche via ce qui a été saisi
	        if (champRecherche.getText().isEmpty()) {
	            setListe(personnelDAO.readAll());
	        } else {
	            ArrayList<Personnel> newListe = new ArrayList<>();
	            Personnel p = personnelDAO.read(Integer.parseInt(champRecherche.getText()));
	            if (p != null) {
	                newListe.add(p);
	                setListe(newListe);
	            } else {
	                Alert alert = new Alert(Alert.AlertType.ERROR);
	                alert.setHeaderText(null);
	                alert.setContentText("Personnel introuvable !");
	                alert.showAndWait();
	            }
	        }
	    } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Veuillez rentrer un id de personnel !");
            alert.showAndWait();
	    }

	}
}
