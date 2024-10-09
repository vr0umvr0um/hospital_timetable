package controleur;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dao.ContrainteDeSpecialiteDAO;
import dao.SingleConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import metier.ContraintedeSpecialite;
import metier.Specialite;

/**
 * Cette classe est le contrôleur du fichier Contrainte.fxml.
 * Elle est responsable de la gestion de l'interface utilisateur pour
 * les contraintes de spécialité.
 */
public class ControleurContrainte
    extends ContrainteDeSpecialiteDAO
    implements Initializable {

  /**
   * La scène qui contient l'interface utilisateur.
   */
  private Stage stage;

  /**
   * La scène qui contient l'interface utilisateur.
   */
  private Scene scene;

  /**
   * Le nœud racine de l'interface utilisateur.
   */
  private Parent root;

  /**
   * La connexion à la base de données.
   */
  private Connection connect;

  /**
   * L'objet PreparedStatement pour récupérer les contraintes
   * de spécialité dans la base de données.
   */
  private PreparedStatement stmt;


  /**
   * Le jeu de résultats de la dernière requête exécutée.
   */
  private ResultSet rs;

  /**
   * Le tableau qui affiche la liste des contraintes de spécialité.
   */
  @FXML
  private TableView<ContraintedeSpecialite> table;

  /**
   * La colonne pour l'identifiant de la contrainte de spécialité.
   */
  @FXML
  private TableColumn<ContraintedeSpecialite, Integer> idCDS;

  /**
   * La colonne pour le nom de la contrainte de spécialité.
   */
  @FXML
  private TableColumn<ContraintedeSpecialite, String> nomCDS;

  /**
   * La colonne pour l'état d'activation de la contrainte de spécialité.
   */
  @FXML
  private TableColumn<ContraintedeSpecialite, Integer> activation;

  /**
   * La colonne pour la durée de la contrainte de spécialité.
   */
  @FXML
  private TableColumn<ContraintedeSpecialite, Double> duree;

  /**
   * La colonne pour la temporalité de la contrainte de spécialité.
   */
  @FXML
  private TableColumn<ContraintedeSpecialite, String> temporalite;

  /**
   * La colonne pour l'identifiant de la spécialité associée
   * à la contrainte de spécialité.
   */
  @FXML
  private TableColumn<ContraintedeSpecialite, Integer> idS;

  /**
   * La colonne pour le nom de la spécialité associée
   * à la contrainte de spécialité.
   */
  @FXML
  private TableColumn<ContraintedeSpecialite, String> nomS;

  /**
   * La liste des contraintes de spécialité.
   */
  ObservableList<ContraintedeSpecialite> listC = FXCollections.observableArrayList();

  /**
   * Passe au fichier Creneau.fxml.
   *
   * @param event [Event] L'événement qui a déclenché l'appel de la méthode.
   * @throws IOException Si une erreur d'E/S se produit.
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
   * Passe au fichier PersonnelEDTChoix.fxml.
   *
   * @param event [Event] L'événement qui a déclenché l'appel de la méthode.
   * @throws IOException Si une erreur d'E/S se produit.
   */
  public void switchToPersonnelEDT(ActionEvent event) throws IOException {

    root = FXMLLoader.load(getClass().getResource(
        "/vues/PersonnelEDTChoix.fxml"));
    stage = new Stage();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.initStyle(StageStyle.TRANSPARENT);

    stage.show();
    stage.centerOnScreen();
  }

  /**
   * Passe au fichier listePersonnel2.fxml.
   *
   * @param event [Event] L'événement qui a déclenché l'appel de la méthode.
   * @throws IOException Si une erreur d'E/S se produit.
   */
  public void switchToPersonnel(ActionEvent event)
      throws IOException {

    root = FXMLLoader.load(getClass().getResource(
        "/vues/listePersonnels2.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    stage.centerOnScreen();
  }

  /**
   * Passe au fichier afficheFonction.fxml.
   *
   * @param event [Event] L'événement qui a déclenché l'appel de la méthode.
   * @throws IOException Si une erreur d'E/S se produit.
   */
  public void switchToFonction(ActionEvent event)
      throws IOException {

    root = FXMLLoader.load(getClass().getResource(
        "/vues/afficheFonction.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    stage.centerOnScreen();
  }

  /**
   * Passe au fichier Specialite.fxml.
   *
   * @param event [Event] L'événement qui a déclenché l'appel de la méthode.
   * @throws IOException Si une erreur d'E/S se produit.
   */
  public void switchToSpecialite(ActionEvent event)
      throws IOException {

    root = FXMLLoader.load(getClass().getResource(
        "/vues/ConsulterSpecialites.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    stage.centerOnScreen();
  }

  /**
   * Passe au fichier Probleme.fxml.
   *
   * @param event [Event] L'événement qui a déclenché l'appel de la méthode.
   * @throws IOException Si une erreur d'E/S se produit.
   */
  public void switchToProbleme(ActionEvent event)
      throws IOException {

    root = FXMLLoader.load(getClass().getResource("/vues/Probleme.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    stage.centerOnScreen();
  }

  /**
   * Passe au fichier Contrainte.fxml.
   *
   * @param event [Event] L'événement qui a déclenché l'appel de la méthode.
   * @throws IOException Si une erreur d'E/S se produit.
   */
  public void switchToContrainte(ActionEvent event)
      throws IOException {

    root = FXMLLoader.load(getClass().getResource("/vues/Contrainte.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);

    stage.show();
    stage.centerOnScreen();
  }

  /**
   * Passe au fichier Contrainte.fxml.
   *
   * @param event [Event] L'événement qui a déclenché l'appel de la méthode.
   * @throws IOException Si une erreur d'E/S se produit.
   */
  public void switchToContrainteSpe(ActionEvent event)
      throws IOException {

    root = FXMLLoader.load(getClass().getResource("/vues/Contrainte.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    stage.centerOnScreen();
  }

  /**
   * Passe au fichier ContrainteLegale.fxml.
   *
   * @param event [Event] L'événement qui a déclenché l'appel de la méthode.
   * @throws IOException Si une erreur d'E/S se produit.
   */
  public void switchToContrainteLegale(ActionEvent event)
      throws IOException {

    root = FXMLLoader.load(getClass().getResource(
        "/vues/ContrainteLegale.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    stage.centerOnScreen();

  }

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
     * la méthode switchToAddCreneau permet de
     * changer la scène actuelle vers la vue addCreneau.
     *
     * @param event : L'événement déclencheur de
     * l'action de changement de scène (un bouton)
     * @throws IOException : En cas d'erreur
     * lors du chargement de la vue addCreneau.fxml
     */
     public void switchToAddCreneau(
             final ActionEvent event) throws IOException {

        root = FXMLLoader.load(
                getClass().getResource("/vues/addCreneau.fxml"));
        stage = new Stage();
        scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
        stage.centerOnScreen();
    }

  /**
  * Obtient une liste de toutes les contraintes de spécialité
  * de la base de données.
  *
  * @return [ObservableList] Un objet ObservableList de ContraintedeSpecialite.
  */
  public ObservableList<ContraintedeSpecialite> getDataContrainteSpe() {

    ObservableList<ContraintedeSpecialite> list = FXCollections.observableArrayList();

    try {
      Connection conn = SingleConnection.getInstance();

      String requete = "SELECT * ";
      requete += "FROM contraintedespe INNER JOIN specialite ";
      requete += "ON contraintedespe.idSpe = specialite.idSpe ";

      PreparedStatement ps = conn.prepareStatement(requete);
      ResultSet rs = ps.executeQuery();

      ContrainteDeSpecialiteDAO cdsd = new ContrainteDeSpecialiteDAO();
      ContraintedeSpecialite cds = null;
      Specialite spe = null;

      while (rs.next()) {
        cds = read(rs.getInt(1));

        spe = new Specialite(rs.getInt("idSpe"), rs.getString("nomSpe"));
        ContraintedeSpecialite sdc = new ContraintedeSpecialite(
            rs.getInt("idContrainteSpe"),
            spe,
            rs.getInt("activation"),
            rs.getString("nomContrainteSpe"),
            rs.getDouble("duree"),
            rs.getString("temporalite"));
        list.add(sdc);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  /**
 * La méthode refreshTable() est chargée de rafraîchir la table
 * des contraintes de spécialité.
 * Pour ce faire, elle récupère les données les plus récentes
 * de la base de données
 * et les affecte à la table.
 */
  @FXML
  private void refreshTable() {
    try {
      connect = SingleConnection.getInstance();
    } catch (ClassNotFoundException e) {

      e.printStackTrace();
    } catch (SQLException e) {

      e.printStackTrace();
    }

  }

  /**
   * La méthode refreshTable() est chargée de rafraîchir la table
   * des contraintes de spécialité.
   * Pour ce faire, elle récupère les données les plus récentes
   * de la base de données
   * et les affecte à la table.
   * Elle fonctionne sur demande.
   *
   * @param event [Event] L'événement qui a déclenché l'appel de la méthode.
   */
  @FXML
  void refreshTableOnAction(ActionEvent event) {
    try {
      connect = SingleConnection.getInstance();

      idCDS.setCellValueFactory(
         new PropertyValueFactory<>(
           "idContrainteSpe"));
      nomCDS.setCellValueFactory(
         new PropertyValueFactory<>(
           "nomContrainteSpe"));
      activation.setCellValueFactory(
         new PropertyValueFactory<>(
           "activation"));
      duree.setCellValueFactory(
         new PropertyValueFactory<>(
           "duree"));
      temporalite.setCellValueFactory(
         new PropertyValueFactory<>(
           "temporalite"));
      idS.setCellValueFactory(
         new PropertyValueFactory<>(
           "idSpe"));
      nomS.setCellValueFactory(
         new PropertyValueFactory<>(
           "nomSpe"));

      listC = getDataContrainteSpe();

      table.setItems(listC);

      refreshTable(); // Call the refreshTable method
    } catch (ClassNotFoundException e) {

      e.printStackTrace();
    } catch (SQLException e) {

      e.printStackTrace();
    }

  }

  /**
   * Ouvre la fenêtre d'ajout de contrainte.
   *
   * @param event [ActionEvent] L'événement qui a déclenché l'appel de méthode.
   */
  @FXML
  private void openAddWindow(ActionEvent event) {

    try {
      Parent parent = FXMLLoader.load(getClass().getResource(
        "/vues/addContrainte.fxml"));
      Scene scene = new Scene(parent);
      Stage stage = new Stage();
      stage.setScene(scene);
      stage.initStyle(StageStyle.TRANSPARENT);
      stage.show();

      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Information d'ajout");
      alert.setHeaderText("L'activation doit être 0 pour non activé et 1 pour activer.");
      alert.showAndWait();
      
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
  * Ouvre la fenêtre d'édition de la contrainte pour
  * la contrainte de spécialité spécifiée.
  *
  * @param c [ContraintedeSpecialite] La contrainte de spécialité à éditer.
  */
  private void openEditWindow(ContraintedeSpecialite c) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(
        "/vues/editContrainte.fxml"));
      Parent parent = loader.load();

      // Obtenez le contrôleur de la nouvelle fenêtre
      EditContrainteControleur editControler = loader.getController();

      // Passez les détails du créneau au contrôleur de la nouvelle fenêtre
      editControler.setContrainte(c);

      // Créez une nouvelle scène et affichez la fenêtre
      Scene scene = new Scene(parent);
      Stage stage = new Stage();
      stage.setScene(scene);
      stage.initStyle(StageStyle.TRANSPARENT);
      stage.show();

    } catch (IOException e) {
      e.printStackTrace();
      // Gérez l'exception d'I/O
    }
  }

  /**
  * Gère le clic sur une ligne du tableau.
  * Il ouvre la fenêtre d'édition pour la contrainte de spécialité sélectionnée.
  *
  *@param c La contrainte de spécialité qui a été cliquée.
  **/
  private void handleRowClick(ContraintedeSpecialite c) {
    Specialite spe = new Specialite(c.getIdSpe(), c.getNomSpe());
    ContraintedeSpecialite cds = new ContraintedeSpecialite(
        c.getIdContrainteSpe(),
        spe,
        c.getActivation(),
        c.getNomContrainteSpe(),
        c.getDuree(),
        c.getTemporalite());

    openEditWindow(cds);
  }

  /**
   * Initialise la classe du contrôleur.
   *
   * @param url [URL] L'URL du fichier FXML.
   * @param rb [ResourceBundle] Le paquet de ressources utilisé
   * à des fins de localisation.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    try {
      connect = SingleConnection.getInstance();

      idCDS.setCellValueFactory(
        new PropertyValueFactory<>(
                  "idContrainteSpe"));
      nomCDS.setCellValueFactory(
        new PropertyValueFactory<>(
          "nomContrainteSpe"));
      activation.setCellValueFactory(
        new PropertyValueFactory<>(
          "activation"));
      duree.setCellValueFactory(
        new PropertyValueFactory<>(
          "duree"));
      temporalite.setCellValueFactory(
         new PropertyValueFactory<>(
           "temporalite"));
      idS.setCellValueFactory(
        new PropertyValueFactory<>(
           "idSpe"));
      nomS.setCellValueFactory(
        new PropertyValueFactory<>(
          "nomSpe"));

      listC = getDataContrainteSpe();

      table.setItems(listC);

      refreshTable(); // Call the refreshTable method

      // Ajouter un écouteur d'événements pour le clic sur une ligne
      table.getSelectionModel()
          .selectedItemProperty().addListener(
             (obs, oldSelection, newSelection) -> {

            if (newSelection != null) {
              // Appel de la méthode pour gérer le clic sur la ligne
              handleRowClick(newSelection);
            }
          });

    } catch (ClassNotFoundException e) {
      e.printStackTrace();

    } catch (SQLException e) {
      e.printStackTrace();

    }
  }

}
