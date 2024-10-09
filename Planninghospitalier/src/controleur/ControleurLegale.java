package controleur;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dao.ContrainteLegaleDAO;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import metier.ContrainteLegale;

/**
 * Cette classe est le contrôleur du fichier ContrainteLegale.fxml.
 * Elle est responsable de la gestion de l'interface utilisateur pour
 * les contraintes légales.
 */
public class ControleurLegale
    extends ContrainteLegaleDAO
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
   * légale dans la base de données.
   */
  private PreparedStatement stmt;

  /**
   * Le jeu de résultats de la dernière requête exécutée.
   */
  private ResultSet rs;

  /**
   * Le tableau qui affiche la liste des contraintes légales.
   */
  @FXML
  private TableView<ContrainteLegale> table;

  /**
   * La colonne pour l'identifiant de la contrainte légale.
   */
  @FXML
  private TableColumn<ContrainteLegale, Integer> idContrainteLegale;

  /**
   * La colonne pour le nom de la contrainte légale.
   */
  @FXML
  private TableColumn<ContrainteLegale, String> nom;

  /**
   * La colonne pour la durée de la contrainte légale.
   */
  @FXML
  private TableColumn<ContrainteLegale, Double> duree;

  /**
   * La colonne pour la temporalité de la contrainte légale.
   */
  @FXML
  private TableColumn<ContrainteLegale, String> temporalite;

  /**
   * La liste des contraintes légales.
   */
  ObservableList<ContrainteLegale> listC = FXCollections.observableArrayList();

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

    root = FXMLLoader.load(getClass().getResource("/vues/Specialite.fxml"));
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
   * Obtient une liste de toutes les contraintes légales de la base de données.
   *
   * @return [ObservableList] Un objet ObservableList de ContrainteLegale.
   */
  public ObservableList<ContrainteLegale> getDataContrainteSpe() {

    ObservableList<ContrainteLegale> list = FXCollections.observableArrayList();

    try {
      Connection conn = SingleConnection.getInstance();

      String requete = "SELECT * FROM contraintelegale";

      PreparedStatement ps = conn.prepareStatement(requete);
      ResultSet rs = ps.executeQuery();

      ContrainteLegaleDAO cld = new ContrainteLegaleDAO();
      ContrainteLegale cl = null;

      while (rs.next()) {

        cl = cld.read(rs.getInt(1));

        ContrainteLegale lc = new ContrainteLegale(
                rs.getInt(1),
                rs.getString(2),
                rs.getDouble(3),
                rs.getString(4));

        list.add(lc);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  /**
   * La méthode refreshTable() est chargée de rafraîchir la table
   * des contraintes de légale.
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
   * des contraintes légales.
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

      idContrainteLegale.setCellValueFactory(
         new PropertyValueFactory<>(
           "idContrainteLegale"));
      nom.setCellValueFactory(
         new PropertyValueFactory<>(
           "nomContrainteLegale"));
      duree.setCellValueFactory(
         new PropertyValueFactory<>(
           "duree"));
      temporalite.setCellValueFactory(
          new PropertyValueFactory<>(
           "temporalite"));

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
  void openAddWindow(ActionEvent event) {
    try {
      Parent parent = FXMLLoader.load(getClass().getResource(
          "/vues/addLegale.fxml"));
      Scene scene = new Scene(parent);
      Stage stage = new Stage();
      stage.setScene(scene);
      stage.initStyle(StageStyle.TRANSPARENT);
      stage.show();

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * Ouvre la fenêtre d'édition de la contrainte pour
   * la contrainte légale spécifiée.
   *
   * @param c [ContraintedeSpecialite] La contrainte légale à éditer.
   */
  private void openEditWindow(ContrainteLegale c) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(
         "/vues/editLegale.fxml"));
      Parent parent = loader.load();

      // Obtenez le contrôleur de la nouvelle fenêtre
      EditLegaleControleur editControler = loader.getController();

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
   * Il ouvre la fenêtre d'édition pour la contrainte légale sélectionnée.
   *
   *@param c La contrainte légale qui a été cliquée.
   **/
  private void handleRowClick(ContrainteLegale c) {
    ContrainteLegale cl = new ContrainteLegale(
        c.getIdContrainteLegale(),
        c.getNomContrainteLegale(),
        c.getDuree(),
        c.getTemporalite());

    openEditWindow(cl);
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

      idContrainteLegale.setCellValueFactory(
        new PropertyValueFactory<>(
          "idContrainteLegale"));
      nom.setCellValueFactory(
        new PropertyValueFactory<>(
          "nomContrainteLegale"));
      duree.setCellValueFactory(
        new PropertyValueFactory<>(
          "duree"));
      temporalite.setCellValueFactory(
        new PropertyValueFactory<>(
          "temporalite"));

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
