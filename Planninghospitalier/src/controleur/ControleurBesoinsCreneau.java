package controleur;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import dao.BesoinEnPersonnelDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import metier.BesoinEnPersonnel;
/**
 * @author Meriem Bennama : code - doc
 * @author 22101222 Mohamed Arwa - un peu de doc & checkstyle
 */
public class ControleurBesoinsCreneau implements Initializable {
    /**
     * ListView affichant les besoins en personnel.
     */
    @FXML
    private ListView<BesoinEnPersonnel> liseBesoins = new ListView<>();

    /**
     * DAO (Data Access Object) pour la gestion des besoins en personnel.
     */
    private BesoinEnPersonnelDAO besoinEnPersonnelDAO;

    /**
     * Besoin en personnel sélectionné.
     */
    private BesoinEnPersonnel selectedBesoinEnPersonnel;

    /**
     * Identifiant du créneau lié aux besoins en personnel.
     */
    private int idC;

    /**
     * Stage utilisé pour la fenêtre JavaFX.
     */
    private Stage stage;

    /**
     * Scene utilisée pour la fenêtre JavaFX.
     */
    private Scene scene;

    /**
     * Root Parent pour la fenêtre JavaFX.
     */
    private Parent root;

    /**
     * Initialise le contrôleur de la classe.
     * Cette méthode est automatiquement appelée
     * après que le fichier FXML a été chargé.
     * Initialise les composants nécessaires
     * et les dépendances du contrôleur.
     *
     * @param url L'emplacement utilisé pour résoudre les chemins
     * relatifs pour l'objet racine,
     * ou null si l'emplacement n'est pas connu.
     * @param rb Les ressources utilisées pour localiser l'objet racine,
     * ou null si les ressources localisées ne sont pas utilisées.
     */
    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
        besoinEnPersonnelDAO = new BesoinEnPersonnelDAO();
    }

    /**
     * Ferme la fenêtre actuelle.
     * Cette méthode est appelée lorsque l'utilisateur
     * interagit avec le composant d'interface
     * utilisateur pour fermer la fenêtre.
     *
     * @param event L'événement de clic qui a
     * déclenché la méthode. Utilisé pour obtenir
     * la fenêtre source qui doit être fermée.
     */
    @FXML
    private void close(final ActionEvent event) {
        Stage stage = (Stage) ((Node)
               event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Définit l'ID du créneau actuel et
     * affiche les besoins associés à ce créneau
     * en appelant la méthode `afficherBesoinsParCreneau`.
     *
     * @param idC L'ID du créneau à afficher.
     * Utilisé pour charger et afficher les besoins
     * spécifiques à ce créneau.
     */
     public void setIdCreneau(int idC) {
        this.idC = idC;
        this.afficherBesoinsParCreneau(idC);
     }

    /**
     * Rafraîchit l'affichage des besoins pour le créneau actuel.
     * Cette méthode est utile pour mettre à jour
     * l'affichage après une modification, un ajout ou une
     * suppression de besoins.
     */
     public void refrecheBesoin() {
         this.afficherBesoinsParCreneau(idC);
	}


	/**
	 * Affiche les besoins en personnel associés à un créneau spécifique.
	 * Cette méthode récupère une liste de besoins
	 * en personnel pour un ID de créneau donné et les
	 * affiche dans un composant ListView.
	 * <p>
	 * Elle initialise également un écouteur
	 * d'événements pour les clics sur les éléments
	 * de la liste, permettant de sélectionner un
	 * besoin en personnel spécifique pour
	 * d'autres actions.
	 *
	 * @param idCreneau L'identifiant du créneau pour
	 * lequel les besoins en personnel doivent être affichés.
	 */
	@FXML
	public void afficherBesoinsParCreneau(int idCreneau) {
	    List<BesoinEnPersonnel> besoins =
	    		besoinEnPersonnelDAO.readParCreneau(idCreneau);
	    Logger.getLogger(ControleurBesoinsCreneau.class.getName()).log(
	    		Level.SEVERE, "listes Besoins est par id : "
	    + idCreneau + " : " + besoins);
	    this.liseBesoins.getItems().clear();
	    for (BesoinEnPersonnel besoin : besoins) {
	        this.liseBesoins.getItems().add(besoin);
	        liseBesoins.setOnMouseClicked(event -> {
	            System.out.println(liseBesoins.getSelectionModel().
	            		getSelectedItems());
	            selectedBesoinEnPersonnel = liseBesoins.getSelectionModel().
	            		getSelectedItems().get(0);
	        });
	    }
	    Logger.getLogger(ControleurBesoinsCreneau.class.getName()).log(
	    		Level.SEVERE, "listes Besoins est par id : "
	    + idCreneau + " : " + this.liseBesoins.getItems());
	}

	/**
	 * Bascule vers la vue d'ajout d'un nouveau besoin en personnel.
	 * Cette méthode charge une nouvelle scène à partir
	 * du fichier FXML spécifié pour l'ajout de besoins en
	 * personnel et l'affiche dans une nouvelle fenêtre.
	 * Elle passe également l'ID du
	 * créneau actuel au contrôleur de la nouvelle vue.
	 * <p>
	 * En cas d'échec du chargement de la vue,
	 * une alerte est affichée à l'utilisateur
	 * indiquant l'erreur et suggérant de vérifier la
	 * console pour plus de détails.
	 *
	 * @param event L'événement ActionEvent
	 * déclenché par l'interaction de l'utilisateur.
	 */
	public void switchToAjoutBesoin(ActionEvent event) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource(
	        		"/vues/AjoutBesoin.fxml"));
	        Parent parent = loader.load();

	        ControleurAjoutBesoin controleurAjoutBesoin =
	        		loader.getController();
	        controleurAjoutBesoin.setIdCreneau(idC);
	        Scene scene = new Scene(parent);
	        Stage stage = new Stage();
	        stage.setScene(scene);
	        stage.initStyle(StageStyle.TRANSPARENT);
	        stage.show();
	    } catch (Exception e) {
	        e.printStackTrace();
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Error Dialog");
	        alert.setHeaderText("Error Loading the View");
	        alert.setContentText("Cannot load the add requirement view. "
	        		+ "Please check the console for more details.");
	        alert.showAndWait();
	    }
	}


	/**
	 * Permet de modifier l'effectif d'un besoin en personnel sélectionné.
	 * Cette méthode charge la vue de modification pour
	 * le besoin en personnel sélectionné et l'affiche
	 * dans une nouvelle fenêtre.
	 * Elle passe également le besoin en personnel sélectionné
	 * au contrôleur de la nouvelle vue.
	 * <p>
	 * Si aucun besoin n'est sélectionné,
	 * une alerte est affichée pour informer
	 * l'utilisateur de la nécessité de choisir un besoin à modifier.
	 * En cas d'erreur lors du chargement de la vue,
	 * une exception IOException est capturée et la trace
	 * de la pile est imprimée dans la console.
	 *
	 * @param event L'événement ActionEvent
	 * déclenché par le clic sur le bouton de modification.
	 */
	@FXML
	private void modifierEffectif(ActionEvent event) {
	    try {
	        if (selectedBesoinEnPersonnel == null) {
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setHeaderText(null);
	            alert.setContentText(
	            		"Veuillez sélectionner un besoin à modifier.");
	            alert.showAndWait();
	        } else {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource(
	            		"/vues/editBesoin.fxml"));
	            Parent parent = loader.load();
	            ControleurEditBesoin controleurEditBesoin =
	            		loader.getController();
	            controleurEditBesoin.setSelectedBesoinEnPersonnel(
	            		selectedBesoinEnPersonnel);
	            Scene scene = new Scene(parent);
	            Stage stage = new Stage();
	            stage.setScene(scene);
	            stage.initStyle(StageStyle.TRANSPARENT);
	            stage.show();
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	/**
	 * Gère l'action de visualiser le personnel
	 * affecté à un besoin en personnel sélectionné.
	 * Cette méthode charge la vue de la liste du personnel
	 * affecté à un besoin en personnel
	 * spécifique et l'affiche dans une nouvelle fenêtre.
	 * Le besoin en personnel sélectionné
	 * est passé au contrôleur de la nouvelle vue pour afficher
	 * les informations pertinentes.
	 * <p>
	 * Si aucun besoin en personnel n'est sélectionné,
	 * une alerte est affichée pour informer
	 * l'utilisateur de la nécessité de choisir un besoin pour
	 * voir le personnel affecté. En cas
	 * d'erreur lors du chargement de la vue,
	 * une exception IOException est capturée et la trace
	 * de la pile est imprimée dans la console.
	 *
	 * @param event L'événement ActionEvent
	 * déclenché par le clic sur le bouton de visualisation
	 *              du personnel.
	 */
	@FXML
	private void getPerso(ActionEvent event) {
	    try {
	        if (selectedBesoinEnPersonnel == null) {
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setHeaderText(null);
	            alert.setContentText(
	            		"Veuillez sélectionner un besoin à gérer.");
	            alert.showAndWait();
	        } else {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource(
	            		"/vues/listePersoParBesoin2.fxml"));
	            Parent parent = loader.load();
	            ControleurlistePersoParBesoin
	            controleurlistePersoParBesoin = loader.getController();
	            controleurlistePersoParBesoin.setBesoin(
	            		selectedBesoinEnPersonnel);
	            Scene scene = new Scene(parent);
	            Stage stage = new Stage();
	            stage.setScene(scene);
	            stage.initStyle(StageStyle.TRANSPARENT);
	            stage.show();
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


	/**
	 * Gère l'action de supprimer un besoin en personnel sélectionné.
	 * Cette méthode affiche une boîte de dialogue de confirmation
	 * avant de procéder à la suppression.
	 * Si aucun besoin en personnel n'est sélectionné,
	 * une alerte est affichée pour informer l'utilisateur
	 * de la nécessité de choisir un besoin à supprimer.
	 * <p>
	 * En cas de confirmation de suppression par l'utilisateur,
	 * le besoin en personnel sélectionné est
	 * supprimé de la base de données.
	 * La liste des besoins est ensuite actualisée
	 * pour refléter cette suppression.
	 * La fenêtre de dialogue est fermée après l'action,
	 * que l'utilisateur choisisse de supprimer ou non le besoin.
	 *
	 * @param event L'événement ActionEvent déclenché
	 * par le clic sur le bouton de suppression.
	 */
	@FXML
	public void supprimerBesoin (ActionEvent event) {
	    if (selectedBesoinEnPersonnel == null) {
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setHeaderText(null);
            alert.setContentText(
                 "Veuillez sélectionner un besoin à supprimer.");
	        alert.showAndWait();
	    } else {
	        // Alert pour confirmer la suppression
	        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
	        confirmAlert.setHeaderText("Confirmation de suppression");
            confirmAlert.setContentText(
                    "Êtes-vous sûr de vouloir "
                       + "supprimer le besoin sélectionné?");

	        Optional<ButtonType> result = confirmAlert.showAndWait();
	        if (result.isPresent() && result.get() == ButtonType.OK) {
	            // Si l'utilisateur confirme, supprimez le besoin
	            besoinEnPersonnelDAO.delete(selectedBesoinEnPersonnel);
	            selectedBesoinEnPersonnel = null;
	            this.afficherBesoinsParCreneau(this.idC);
	        }
	        // Fermeture dialogue
	        close(event);
	    }
	}

}
