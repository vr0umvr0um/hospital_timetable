/**
 * @author 22106979 Antivackis Vanessa - Documentation
 * @author 22208371 Cailleau Dylan - Ecriture de la classe et documentation
 */

package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import metier.Creneau;

public class CreneauDAO extends DAO<Creneau> {

	String query = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	Creneau creneau = null;

	ArrayList<Creneau> creneauTab = new ArrayList<>();

	/**
	 * Requête SQL qui renvoit un créneau en fonction de son id passé en paramètre
	 *
	 * @param idC
	 * @return créneau
	 */
	public Creneau getCreneau(int idC) {
		try {

			query = "SELECT * FROM `creneau` WHERE idCreneau = " + idC;
			preparedStatement = connect.prepareStatement(query);
			resultSet = preparedStatement.executeQuery(query);

			while (resultSet.next()) {
				creneau = new Creneau(resultSet.getInt(1), String.valueOf(resultSet.getDate(2)),
						String.valueOf(resultSet.getTime(3)), String.valueOf(resultSet.getTime(4)));
			}
		} catch (SQLException ex) {
			Logger.getLogger(CreneauDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return creneau;
	}

	/**
	 * Requête SQL qui renvoit tout les créneaux de la BD
	 *
	 * @return ArrayList créneau
	 */
	public ArrayList<Creneau> getAllCreneau() {

		ArrayList<Creneau> CreneauTab = new ArrayList<>();
		try {

			query = "SELECT * FROM `creneau`";
			preparedStatement = connect.prepareStatement(query);
			resultSet = preparedStatement.executeQuery(query);

			while (resultSet.next()) {
				creneau = new Creneau(resultSet.getInt(1), String.valueOf(resultSet.getDate(2)),
						String.valueOf(resultSet.getTime(3)), String.valueOf(resultSet.getTime(4)));
				CreneauTab.add(creneau);
			}
		} catch (SQLException ex) {
			Logger.getLogger(CreneauDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return CreneauTab;
	}

	/**
	 * Requête SQL qui renvoit tout les créneaux de la BD correspondant à une date donnée
	 * @param date Date
	 * @return ArrayList créneau
	 */
	public ArrayList<Creneau> getAllCreneauDate(Timestamp date) {
	    ArrayList<Creneau> CreneauTab = new ArrayList<>();
	    try {
	        query = "SELECT * FROM `creneau` WHERE `dateCreneau` = '" + date + "'";
	        preparedStatement = connect.prepareStatement(query);
	        resultSet = preparedStatement.executeQuery(query);

	        while (resultSet.next()) {
	            creneau = new Creneau(resultSet.getInt(1), String.valueOf(resultSet.getDate(2)),
	                    String.valueOf(resultSet.getTime(3)), String.valueOf(resultSet.getTime(4)));
	            CreneauTab.add(creneau);
	        }
	    } catch (SQLException ex) {
	        Logger.getLogger(CreneauDAO.class.getName()).log(Level.SEVERE, null, ex);
	    }
	    return CreneauTab;
	}


	/**
	 * Requête SQL qui renvoit tout les créneaux de la BD correspondant à une date et un id donnée
	 * @param date Date
	 * @return ArrayList créneau
	 */
	public ArrayList<Creneau> getAllCreneauDateID(Timestamp date, int id) {
	    ArrayList<Creneau> CreneauTab = new ArrayList<>();
	    try {
	    	query = "SELECT * FROM creneau c " +
	                "JOIN estaffecte e ON c.idCreneau = e.idBesoin " +
	                "WHERE e.idPersonnel = ? AND c.dateCreneau = ?";

	        preparedStatement = connect.prepareStatement(query);
	        preparedStatement.setInt(1, id);
	        preparedStatement.setDate(2, new java.sql.Date(date.getTime()));

	        resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	            creneau = new Creneau(resultSet.getInt(1), String.valueOf(resultSet.getDate(2)),
	                    String.valueOf(resultSet.getTime(3)), String.valueOf(resultSet.getTime(4)));
	            CreneauTab.add(creneau);
	        }
	    } catch (SQLException ex) {
	        Logger.getLogger(CreneauDAO.class.getName()).log(Level.SEVERE, null, ex);
	    }
	    return CreneauTab;
	}

	@Override
	public Creneau create(Creneau obj) {
		try {

			query = "INSERT INTO `creneau`(" + "`dateCreneau`, " + "`horaireDeb`, " + "`horaireFin`) " + "VALUES ("
					+ obj.getDate() + "," + obj.getHeureDeb() + "," + obj.getHeureFin() + ")";
			preparedStatement = connect.prepareStatement(query);
			preparedStatement.executeUpdate();

		} catch (SQLException ex) {
			System.err.println("Echec de l'ajout du personnel");
			Logger.getLogger(CreneauDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return obj;
	}

	@Override
	public Creneau update(Creneau obj) {
		try {

			query = "UPDATE `creneau` SET `dateCreneau`=" + obj.getDate() + "," + " `horaireDeb`=" + obj.getHeureDeb()
					+ "," + " `horaireFin`=" + obj.getHeureFin() + " WHERE `idCreneau`= " + obj.getIdC();
			preparedStatement = connect.prepareStatement(query);
			preparedStatement.executeUpdate();

		} catch (SQLException ex) {
			System.err.println("Echec de l'ajout du personnel");
			Logger.getLogger(CreneauDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return obj;

	}

	@Override
	public void delete(Creneau obj) {
		try {

			query = "DELETE FROM `creneau` WHERE idCreneau = " + obj.getIdC();
			preparedStatement = connect.prepareStatement(query);
			preparedStatement.executeUpdate();

		} catch (SQLException ex) {
			Logger.getLogger(CreneauDAO.class.getName()).log(Level.SEVERE, null, ex);
		}

	}
}
