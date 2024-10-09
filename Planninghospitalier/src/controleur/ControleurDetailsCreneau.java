/**
 * @author 22208371 Cailleau Dylan - Ecriture de la classe et documentation
 */
package controleur;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import dao.BesoinEnPersonnelDAO;
import dao.PersonnelDAO;
import dao.SingleConnection;
import dao.ViolationDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import metier.BesoinEnPersonnel;
import metier.ContraintedeSpecialite;
import metier.Creneau;
import metier.Fonction;
import metier.Personnel;
import metier.Violation;

public class ControleurDetailsCreneau implements Initializable {

	@FXML
	private TextField idfld;

	@FXML
	private DatePicker datefld;

	@FXML
	private TextField heuredebfld;

	@FXML
	private TextField heurefinfld;

	@FXML
	private TableColumn<Personnel, String> fonctionfld;

	@FXML
	private TableColumn<Personnel, String> nomfld;

	@FXML
	private TableColumn<Personnel, String> prenomfld;

	@FXML
	private TableView<Personnel> tablepers;

	@FXML
	private ScrollPane paneViolations;

	private PersonnelDAO personnelDAO;

	String query = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	Connection connect = null;

	private int idC;

	Personnel personnel = null;

	ObservableList<Personnel> Personnellist = FXCollections.observableArrayList();

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
	    paneViolations.setId("paneViolations");
		personnelDAO = new PersonnelDAO();

		loadDate();
	}

	/**
	 * Cette méthode permet d'initialiser le scollPane à droite
	 * de la page afin avec les violations de contraintes mais
	 * aussi les violations de surcharge et souscharge.
	 */
	public void miseEnPlaceListeViolations() {
	    ViolationDAO violationDAO = new ViolationDAO();
	    ArrayList<Violation> listeV = violationDAO.readByIdCreneau(idC);
	    System.out.println("id creneau :"+idC);
	    System.out.println("Liste des violations "+listeV.toString());

        VBox vboxAllViolations = new VBox();
        vboxAllViolations.setId("listeViolations");
        VBox.setVgrow(vboxAllViolations, Priority.ALWAYS);
        vboxAllViolations.setSpacing(2);

        for (Violation violation : listeV) {
            String couleurFond;
            if (violation.getContrainte() instanceof ContraintedeSpecialite) {
                couleurFond = "#aaf7fc";
            }
            else {
                couleurFond = "#e77474";
            }
            HBox hboxRectangle = new HBox();
            hboxRectangle.setPadding(new Insets(5, 5, 5, 5));
            hboxRectangle.setId("rectangleViolation");
            hboxRectangle.setAlignment(Pos.CENTER_LEFT);
            hboxRectangle.setStyle("-fx-background-color :"+couleurFond);

            Label textV = new Label();
            String personnelProblematique = violationDAO.getPersonnelProblematique(violation);
            textV.setText(violation.getContrainte().getNom()+"\nPersonnel lié : "+personnelProblematique);
            textV.setAlignment(Pos.CENTER_LEFT);

            HBox boxBouton = new HBox();
            //boxBouton.setId("boxBouton");
            HBox.setHgrow(boxBouton, Priority.ALWAYS);
            boxBouton.setAlignment(Pos.CENTER_RIGHT);
            Button boutonGoToBesoin = new Button();
            boutonGoToBesoin.setText("Lieu violation");
            boutonGoToBesoin.setId("boutonGoToBesoin");
            boxBouton.getChildren().add(boutonGoToBesoin);

            hboxRectangle.getChildren().add(textV);
            hboxRectangle.getChildren().add(boxBouton);

            vboxAllViolations.getChildren().add(hboxRectangle);

            boutonGoToBesoin.setOnAction(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/vues/listePersoParBesoin2.fxml"));
                    Parent parent = loader.load();
                    ControleurlistePersoParBesoin controleurlistePersoParBesoin = loader.getController();
                    controleurlistePersoParBesoin.setBesoin(violation.getBesoin());
                    Scene scene = new Scene(parent);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.show();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IndexOutOfBoundsException e2) {
                }
            });
        }

        List<BesoinEnPersonnel> listeBesoins = (new BesoinEnPersonnelDAO()).readParCreneau(idC);
        for (BesoinEnPersonnel besoin : listeBesoins) {
            if ((new BesoinEnPersonnelDAO()).estSurCharge(besoin.getIdBesoin())) {
                HBox hboxRectangle = new HBox();
                hboxRectangle.setPadding(new Insets(5, 5, 5, 5));
                hboxRectangle.setId("rectangleViolation");
                hboxRectangle.setAlignment(Pos.CENTER_LEFT);
                Label textV = new Label();
                textV.setAlignment(Pos.CENTER_LEFT);
                hboxRectangle.setStyle("-fx-background-color : #fdb068");
                textV.setText("Surcharge de besoin");

                HBox boxBouton = new HBox();
                //boxBouton.setId("boxBouton");
                HBox.setHgrow(boxBouton, Priority.ALWAYS);
                boxBouton.setAlignment(Pos.CENTER_RIGHT);
                Button boutonGoToBesoin = new Button();
                boutonGoToBesoin.setText("Lieu violation");
                boutonGoToBesoin.setId("boutonGoToBesoin");
                boxBouton.getChildren().add(boutonGoToBesoin);

                hboxRectangle.getChildren().add(textV);
                hboxRectangle.getChildren().add(boxBouton);

                vboxAllViolations.getChildren().add(hboxRectangle);

                boutonGoToBesoin.setOnAction(event -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vues/listePersoParBesoin2.fxml"));
                        Parent parent = loader.load();
                        ControleurlistePersoParBesoin controleurlistePersoParBesoin = loader.getController();
                        controleurlistePersoParBesoin.setBesoin(besoin);
                        Scene scene = new Scene(parent);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.initStyle(StageStyle.TRANSPARENT);
                        stage.show();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IndexOutOfBoundsException e2) {
                    }
                });
            }
            else if ((new BesoinEnPersonnelDAO()).estSousChargeApresAnnulation(besoin.getIdBesoin())){
                HBox hboxRectangle = new HBox();
                hboxRectangle.setPadding(new Insets(5, 5, 5, 5));
                hboxRectangle.setId("rectangleViolation");
                hboxRectangle.setAlignment(Pos.CENTER_LEFT);
                Label textV = new Label();
                textV.setAlignment(Pos.CENTER_LEFT);
                hboxRectangle.setStyle("-fx-background-color : #70dba1");
                textV.setText("Sous-charge de besoin");

                HBox boxBouton = new HBox();
                //boxBouton.setId("boxBouton");
                HBox.setHgrow(boxBouton, Priority.ALWAYS);
                boxBouton.setAlignment(Pos.CENTER_RIGHT);
                Button boutonGoToBesoin = new Button();
                boutonGoToBesoin.setText("Lieu violation");
                boutonGoToBesoin.setId("boutonGoToBesoin");
                boxBouton.getChildren().add(boutonGoToBesoin);

                hboxRectangle.getChildren().add(textV);
                hboxRectangle.getChildren().add(boxBouton);

                vboxAllViolations.getChildren().add(hboxRectangle);

                boutonGoToBesoin.setOnAction(event -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vues/listePersoParBesoin2.fxml"));
                        Parent parent = loader.load();
                        ControleurlistePersoParBesoin controleurlistePersoParBesoin = loader.getController();
                        controleurlistePersoParBesoin.setBesoin(besoin);
                        Scene scene = new Scene(parent);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.initStyle(StageStyle.TRANSPARENT);
                        stage.show();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IndexOutOfBoundsException e2) {
                    }
                });
            }
        }
        paneViolations.setContent(vboxAllViolations);
        paneViolations.setFitToWidth(true);
	}

	private void loadDate() {

		try {
			connect = SingleConnection.getInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		refreshTable();

		nomfld.setCellValueFactory(new PropertyValueFactory<>("nomPersonnel"));
		prenomfld.setCellValueFactory(new PropertyValueFactory<>("prenomPersonnel"));
		fonctionfld.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Personnel, String>, javafx.beans.value.ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(TableColumn.CellDataFeatures<Personnel, String> param) {
	            return new SimpleStringProperty(param.getValue().getFonction().getNomFonction());
	        }
	    });

		tablepers.setItems(Personnellist);

	}

	@FXML
	private void refreshTable() {

		try {
			Personnellist.clear();

			query = "SELECT p.*, f.nomFonction "
					+ "FROM personnel p "
					+ "JOIN estaffecte e "
					+ "ON p.idPersonnel = e.idPersonnel "
					+ "JOIN besoinenpersonnel b "
					+ "ON e.idBesoin = b.idBesoin "
					+ "JOIN creneau c "
					+ "ON b.idCreneau = c.idCreneau "
					+ "JOIN fonction f ON p.idFonction = f.idFonction "
					+ "WHERE c.idCreneau = " + idC;
			preparedStatement = connect.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();



			while (resultSet.next()) {
				Fonction fonction = new Fonction(resultSet.getInt("idFonction"), resultSet.getString("nomFonction"));
				Personnellist.add(new Personnel(resultSet.getInt("idPersonnel"), resultSet.getString("nomPersonnel"),
						resultSet.getString("prenomPersonnel"), resultSet.getString("dateNaissance"),
						resultSet.getInt("tempsTravailMensuel"), fonction));
				tablepers.setItems(Personnellist);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setCreneauDetails(Creneau creneau) {

		idC = creneau.getIdC();
		idfld.setText(String.valueOf(creneau.getIdC()));
		datefld.setValue(creneau.getDate().toLocalDate());
		heuredebfld.setText(creneau.getHeureDeb().toString());
		heurefinfld.setText(creneau.getHeureFin().toString());
		refreshTable();
	}

	@FXML
	private void close(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

	@FXML
	private void clean() {
		datefld.setValue(null);
		heuredebfld.setText(null);
		heurefinfld.setText(null);
	}

	@FXML
	private void delete(ActionEvent event) {
		try {
			connect = SingleConnection.getInstance();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		idC = Integer.valueOf(idfld.getText());

		if (idC == 0) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Veuillez sélectionner un créneau à supprimer.");
			alert.showAndWait();
		} else {
			confirmDelete(); // Afficher une boîte de dialogue de confirmation avant la suppression
			close(event);

		}
	}

	private void confirmDelete() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vues/messageSuppression.fxml"));
			Parent root = loader.load();

			// Obtenez une référence au contrôleur du fichier FXML de confirmation
			ControleurMessageSuppression controleurMessageSuppression = loader.getController();

			Stage confirmStage = new Stage();
			confirmStage.initStyle(StageStyle.UNDECORATED);
			confirmStage.initModality(Modality.APPLICATION_MODAL);
			confirmStage.setScene(new Scene(root));

			// Obtenez une référence aux boutons de confirmation
			Button ouiButton = controleurMessageSuppression.getOuiButton();
			Button nonButton = controleurMessageSuppression.getNonButton();

			// Ajoutez les gestionnaires d'événements pour les boutons
			ouiButton.setOnAction(event -> {
				performDelete();
				confirmStage.close();
			});

			nonButton.setOnAction(event -> confirmStage.close());

			// Affichez la fenêtre de confirmation
			confirmStage.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void performDelete() {
		try {
			// Début de la transaction
			connect.setAutoCommit(false);

			// Supprimer les enregistrements dans la table nonrespectspe
			String deleteNonRespectSpeQuery = "DELETE FROM `nonrespectspe` WHERE idBesoin IN (SELECT idBesoin FROM `besoinenpersonnel` WHERE idCreneau = ?)";
			try (PreparedStatement deleteNonRespectSpeStatement = connect.prepareStatement(deleteNonRespectSpeQuery)) {
				deleteNonRespectSpeStatement.setInt(1, idC);
				deleteNonRespectSpeStatement.executeUpdate();
			}

			// Supprimer les enregistrements dans la table nonrespectlegal
			String deleteNonRespectLegalQuery = "DELETE FROM `nonrespectlegal` WHERE idBesoin IN (SELECT idBesoin FROM `besoinenpersonnel` WHERE idCreneau = ?)";
			try (PreparedStatement deleteNonRespectLegalStatement = connect
					.prepareStatement(deleteNonRespectLegalQuery)) {
				deleteNonRespectLegalStatement.setInt(1, idC);
				deleteNonRespectLegalStatement.executeUpdate();
			}

			// Supprimer les enregistrements dans la table estaffecte
			String deleteEstaffecteQuery = "DELETE FROM `estaffecte` WHERE idBesoin IN (SELECT idBesoin FROM `besoinenpersonnel` WHERE idCreneau = ?)";
			try (PreparedStatement deleteEstaffecteStatement = connect.prepareStatement(deleteEstaffecteQuery)) {
				deleteEstaffecteStatement.setInt(1, idC);
				deleteEstaffecteStatement.executeUpdate();
			}

			// Supprimer les enregistrements dans la table besoinenpersonnel
			String deleteBesoinQuery = "DELETE FROM `besoinenpersonnel` WHERE idCreneau = ?";
			try (PreparedStatement deleteBesoinStatement = connect.prepareStatement(deleteBesoinQuery)) {
				deleteBesoinStatement.setInt(1, idC);
				deleteBesoinStatement.executeUpdate();
			}

			// Supprimer le créneau
			String deleteCreneauQuery = "DELETE FROM `creneau` WHERE idCreneau = ?";
			try (PreparedStatement deleteCreneauStatement = connect.prepareStatement(deleteCreneauQuery)) {
				deleteCreneauStatement.setInt(1, idC);
				deleteCreneauStatement.executeUpdate();
			}

			// Commit de la transaction
			connect.commit();

			// Nettoyage des champs après la suppression
			clean();

		} catch (SQLException ex) {
			try {
				// En cas d'erreur, rollback de la transaction
				connect.rollback();
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
			}

			Logger.getLogger(AddCreneauControleur.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
				// Rétablir le mode de commit automatique par défaut
				connect.setAutoCommit(true);
			} catch (SQLException autoCommitEx) {
				autoCommitEx.printStackTrace();
			}
		}
	}

	@FXML
	private void save(ActionEvent event) {

		try {
			connect = SingleConnection.getInstance();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		idC = Integer.valueOf(idfld.getText());
		String date = String.valueOf(datefld.getValue());
		String heuredeb = heuredebfld.getText();
		String heurefin = heurefinfld.getText();

		if (idC == 0 || date.isEmpty() || heuredeb.isEmpty() || heurefin.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Merci de remplir tout les champs");
			alert.showAndWait();
		} else {
			update();
			clean();
			close(event);
		}

	}

	private void getQuery() {
		query = "UPDATE `creneau` SET " + "`dateCreneau`= ?," + "`horaireDeb`= ?," + "`horaireFin`= ? "
				+ "WHERE idCreneau = ?";
	}

	private void update() {
		try {
			getQuery();
			preparedStatement = connect.prepareStatement(query);
			preparedStatement.setString(1, String.valueOf(datefld.getValue()));
			preparedStatement.setString(2, heuredebfld.getText());
			preparedStatement.setString(3, heurefinfld.getText());
			preparedStatement.setInt(4, idC);
			System.out.print(preparedStatement.toString());
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(AddCreneauControleur.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	void setTextField(int id, LocalDate toLocalDate, String heuredeb, String heurefin) {

		idC = id;
		datefld.setValue(toLocalDate);
		heuredebfld.setText(heuredeb);
		heurefinfld.setText(heurefin);

	}

    /**
     * Gère l'action du bouton pour afficher les besoins en personnel d'un créneau spécifique.
     * Lorsque cette méthode est déclenchée par un événement, elle charge et affiche une nouvelle fenêtre
     * qui liste les besoins en personnel associés à un créneau donné.
     *
     * @param event L'événement qui a déclenché cette méthode, généralement le clic sur un bouton.
     */
    @FXML
    private void gestionBesoin(ActionEvent event) {
        try {
            // Charge le fichier FXML pour la vue des besoins en personnel
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vues/besoinPerso2.fxml"));
            Parent parent = loader.load();

            // Obtient le contrôleur associé à la vue des besoins en personnel
            ControleurBesoinsCreneau controleurBesoinsCreneau = loader.getController();

            // Passe l'ID du créneau actuel au contrôleur pour filtrer les besoins en personnel
            controleurBesoinsCreneau.setIdCreneau(idC);

            // Prépare et affiche la nouvelle scène
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
        } catch (IOException e) {
            // Journalise et affiche les détails en cas d'erreur lors du chargement de la vue
            Logger.getLogger(ControleurDetailsCreneau.class.getName()).log(Level.SEVERE,
                    "Erreur lors de la l'afficharge d'un besoin en personnel", e);
            e.printStackTrace();
        }
    }
}
