package controleur;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import dao.SingleConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import metier.Creneau;

public class ControleurAddCreneauToPersonnel implements Initializable {

	@FXML
	private TableColumn<Creneau, Date> date;

	@FXML
	private TableColumn<Creneau, Time> heureDeb;

	@FXML
	private TableColumn<Creneau, Time> heureFin;

	@FXML
	private TableColumn<Creneau, Integer> idC;

	@FXML
	private TableView<Creneau> table;

	@FXML
	private DatePicker datefld;

	@FXML
	private TextField heuredebfld;

	@FXML
	private TextField heurefinfld;

	String query = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	Connection connect = null;
	private boolean update;
	int idPersonnel;

	ObservableList<Creneau> Creneaulist = FXCollections.observableArrayList();

	@Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

		loadDate();

		// Ajouter un écouteur d'événements pour le clic sur une ligne
		table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				// Appel de la méthode pour gérer le clic sur la ligne
				handleRowClick(newSelection);
			}
		});
	}

	private void handleRowClick(Creneau selectedCreneau) {
		// Après avoir ouvert la nouvelle fenêtre, affectez directement le personnel au
		// créneau
		getQuery();
		insert(selectedCreneau);
	}

	private void loadDate() {

		try {
			connect = SingleConnection.getInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		refreshTable();

		idC.setCellValueFactory(new PropertyValueFactory<>("idC"));
		date.setCellValueFactory(new PropertyValueFactory<>("date"));
		heureDeb.setCellValueFactory(new PropertyValueFactory<>("heureDeb"));
		heureFin.setCellValueFactory(new PropertyValueFactory<>("heureFin"));

		table.setItems(Creneaulist);

	}

	@FXML
	private void refreshTable() {

		try {
			Creneaulist.clear();

			query = "select * from `creneau`";
			preparedStatement = connect.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Creneaulist.add(new Creneau(resultSet.getInt("idCreneau"), resultSet.getString("dateCreneau"),
						resultSet.getString("horaireDeb"), resultSet.getString("horaireFin")));
				table.setItems(Creneaulist);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void getQuery() {
		if (!update) {
			query = "INSERT INTO creneau (dateCreneau, horaireDeb, horaireFin) VALUES (?, ?, ?);"
					+ "SELECT @lastCreneauID := LAST_INSERT_ID();" + "SELECT @idPersCreateur := ?;"
					+ "SELECT @idSpe := (SELECT idSpe FROM sespecialise WHERE idPersonnel = @idPersCreateur LIMIT 1);"
					+ "SELECT @idFonc := (SELECT idFonction FROM personnel WHERE idPersonnel = @idPersCreateur LIMIT 1);"
					+ "INSERT INTO besoinenpersonnel (nbPersonnes, idSpe, idFonction, idCreneau) "
					+ "VALUES ((SELECT nbPersonnes + 1 FROM besoinenpersonnel WHERE idCreneau = @lastCreneauID LIMIT 1), @idSpe, @idFonc, @lastCreneauID);"
					+ "INSERT INTO estaffecte (idPersonnel, idBesoin) VALUES (@idPersCreateur, (SELECT @lastCreneauID))";
		} else {
			// La logique de mise à jour peut être ajoutée ici au besoin
			query = "UPDATE `creneau` SET " + "`dateCreneau`= ?," + "`horaireDeb`= ?," + "`horaireFin`= ? "
					+ "WHERE idCreneau = ?";
		}
	}

	private void insert(Creneau selectedCreneau) {
		try {
			System.out.println("insert");
			preparedStatement = connect.prepareStatement(query);
			preparedStatement.setString(1, String.valueOf(selectedCreneau.getDate()));
			preparedStatement.setString(2, String.valueOf(selectedCreneau.getHeureDeb()));
			preparedStatement.setString(3, String.valueOf(selectedCreneau.getHeureFin()));
			preparedStatement.setInt(4, idPersonnel);

			preparedStatement.execute();
			refreshTable();
		} catch (SQLException ex) {
			Logger.getLogger(AddCreneauControleur.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	void setIdPersonnel(int id) {
		idPersonnel = id;
	}

	void setUpdate(boolean b) {
		this.update = b;
	}

	@FXML
	private void close(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}
}
