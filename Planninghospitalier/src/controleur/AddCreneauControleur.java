/**
 * @author 22208371 Cailleau Dylan - Ecriture de la classe et documentation
 */

package controleur;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import dao.SingleConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Cette classe permet d'ajouter un créneau à la base de donnée en prenant les
 * paramètres suivants :
 *
 * @param idc         int est l'id du créneau
 * @param update      boolean qui vérifie si une insertion à déjà été faite
 * @param datefld     DatePicker récupère la date inseré
 * @param heuredebfld TextField récupère l'heure de début inseré
 * @param heurefinfld TextField récupère l'heure de fin inseré
 */

public class AddCreneauControleur implements Initializable {

	String query = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	Connection connect = null;
	private boolean update;
	int idC;

	@FXML
	private DatePicker datefld;

	@FXML
	private TextField heuredebfld;

	@FXML
	private TextField heurefinfld;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	/**
	 * Cette méthode vide les champs des TextField/DatePicker
	 */
	@FXML
	private void clean() {
		datefld.setValue(null);
		heuredebfld.setText(null);
		heurefinfld.setText(null);
	}

	/**
	 * Cette méthode va récupéré les argument insérés dans les champs pour ensuite
	 * les inséré dans la base de donnée
	 *
	 * @param event
	 */

	@FXML
	private void save(ActionEvent event) {

		try {
			connect = SingleConnection.getInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String date = String.valueOf(datefld.getValue());
		String heuredeb = heuredebfld.getText();
		String heurefin = heurefinfld.getText();

		if (date.isEmpty() || heuredeb.isEmpty() || heurefin.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Merci de remplir tout les champs");
			alert.showAndWait();
		} else {
			getQuery();
			insert();
			clean();
		}
	}

	/**
	 * Cette méthode prépare la requête sql pour pouvoir créer un nouveau créneau
	 */

	private void getQuery() {

		if (!update) {
			query = "INSERT INTO `creneau`(`dateCreneau`,`horaireDeb`, `horaireFin`) " + "VALUES (?, ?, ?)";
		} else {
			query = "UPDATE `creneau` SET " + "`dateCreneau`= ?," + "`horaireDeb`= ?," + "`horaireFin`= ? "
					+ "WHERE idCreneau = " + idC;
		}
	}

	/**
	 * insert établit un connection avec la base de donnée pour ensuite y executer
	 * la requête sql générée par getQuery()
	 */
	private void insert() {
		try {

			preparedStatement = connect.prepareStatement(query);
			preparedStatement.setString(1, String.valueOf(datefld.getValue()));
			preparedStatement.setString(2, heuredebfld.getText());
			preparedStatement.setString(3, heurefinfld.getText());
			preparedStatement.execute();

		} catch (SQLException ex) {
			Logger.getLogger(AddCreneauControleur.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Cette méthode initialise les arguments du créneau à partir des éléments
	 * passés en paramètre
	 *
	 * @param id
	 * @param toLocalDate
	 * @param heuredeb
	 * @param heurefin
	 */

	void setTextField(int id, LocalDate toLocalDate, String heuredeb, String heurefin) {

		idC = id;
		datefld.setValue(toLocalDate);
		heuredebfld.setText(heuredeb);
		heurefinfld.setText(heurefin);

	}

	/**
	 * Change l'attribut update en fonction du boolean b
	 *
	 * @param b
	 */

	void setUpdate(boolean b) {
		this.update = b;
	}

	/**
	 * Permet de fermer la fenêtre actuellement ouverte
	 *
	 * @param event
	 */

	@FXML
	private void close(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

}
