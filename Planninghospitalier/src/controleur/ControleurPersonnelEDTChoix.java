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
import java.util.ResourceBundle;

import dao.PersonnelDAO;
import dao.SingleConnection;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Callback;
import metier.Fonction;
import metier.Personnel;

public class ControleurPersonnelEDTChoix implements Initializable {

	@FXML
	private TableColumn<Personnel, String> fonctionfld;

	@FXML
	private TableColumn<Personnel, String> nomfld;

	@FXML
	private TableColumn<Personnel, String> prenomfld;

	@FXML
	private TableView<Personnel> tablepers;

	private PersonnelDAO personnelDAO;

	String query = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	Connection connect = null;

	Personnel personnel = null;

	ObservableList<Personnel> Personnellist = FXCollections.observableArrayList();

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		personnelDAO = new PersonnelDAO();

		loadDate();

		tablepers.setOnMouseClicked(event -> {
		    if (event.getClickCount() == 1) {
		        Personnel selectedPersonnel = tablepers.getSelectionModel().getSelectedItem();
		        if (selectedPersonnel != null) {
		            handleRowClick(selectedPersonnel);
		            close(event); // à combiner avec la methode qui ferme les fenetres dans handelRowClick
		        }
		    }
		});

	}

	private void handleRowClick(Personnel selectedpersonnel) {
		for (Window window : Window.getWindows()) {
            if (window instanceof Stage) {
                Stage stage3 = (Stage) window;
                stage3.close();
            }
        }
		openPersonnelEDT(selectedpersonnel);
	}

	private void openPersonnelEDT(Personnel selectedPersonnel) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vues/PersonnelEDT.fxml"));
			Parent parent = loader.load();

			ControleurPersonnelEDT personnelEDTControleur = loader.getController();
			personnelEDTControleur.setPersonnel(selectedPersonnel);

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
		fonctionfld.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Personnel, String>, javafx.beans.value.ObservableValue<String>>() {
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

			query = "SELECT p.idPersonnel, p.nomPersonnel, " + "p.prenomPersonnel, p.dateNaissance, "
					+ "p.tempsTravailMensuel, f.idFonction, " + "f.nomFonction " + "FROM personnel p "
					+ "JOIN fonction f ON p.idFonction = f.idFonction " + "";

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

	@FXML
	private void close(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

	private void close(MouseEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

}
