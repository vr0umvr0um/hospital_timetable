/**
 * La classe 'ContrainteDeSpecialiteDAO' fournit des méthodes pour gérer
 * les objets 'ContraintedeSpecialite' dans une base de données.
 *
 * @author 22106979 - Documentation & Implémentation
 */

package dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;

import metier.BesoinEnPersonnel;
import metier.Contrainte;
import metier.ContraintedeSpecialite;
import metier.Creneau;
import metier.Fonction;
import metier.Personnel;
import metier.Specialite;
import metier.Violation;

/**
 * Cette classe est un DAO (Data Access Object) qui fournit des méthodes pour
 * créer, lire, mettre à jour et supprimer des objets `ContraintedeSpecialite`.
 */
public class ContrainteDeSpecialiteDAO extends DAO<ContraintedeSpecialite> {

	/**
	 * valeur du résultat de la requête SQL.
	 */
	private ResultSet rs;

	/**
	 * Créer un nouvel objet "contrainte de spécialité" dans la base de données.
	 *
	 * @param cds [ContraintedeSpecialite] valeur de la contraintede spécialité à
	 *            créer dans la base de données
	 * @return [ContraintedeSpecialite] valeur de la contrainte de spécialité créée
	 *         dans la base de données
	 * @throws SQLException Si une erreur survient lors de l'exécution de
	 *                      l'instruction SQL.
	 */
	@Override
	public ContraintedeSpecialite create(ContraintedeSpecialite cds) {

		int idCDS = 0;

		try {

			String requete = "INSERT INTO contraintedespe ";
			requete += "(nomContrainteSpe, activation, duree, temporalite, idSpe)";
			requete += "VALUES (?, ?, ?, ?, ?)";

			stmt = connect.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, cds.getNomContrainteSpe());
			stmt.setInt(2, cds.getActivation());
			stmt.setDouble(3, cds.getDuree());
			stmt.setString(4, cds.getTemporalite());
			stmt.setInt(5, cds.getSpecialite().getIdSpecialite());

			stmt.executeUpdate();
			System.out.println("Insertion réussie !\n");

			ResultSet clefs = stmt.getGeneratedKeys();
			if (clefs.next()) {
				// Récupére l'objet dans ResultSet clefs
				Object obj = clefs.getObject(1);

				// Vérifie si l'objet est un BigInteger
				if (obj instanceof java.math.BigInteger) {
					// Cast to Integer if it's BigInteger
					idCDS = ((java.math.BigInteger) obj).intValue();
					// Check if the object is an Integer
				} else if (obj instanceof java.lang.Integer) {
					// Cast directly if it's Integer
					idCDS = (Integer) obj;
				} else {
					throw new ClassCastException(
							// Handle unexpected type
							"The generated key is not an Integer or BigInteger");
				}

				cds.setIdContrainteSpe(idCDS);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cds;
	}

	/**
	 * Mettre à jour une contrainte de spécialité existante dans la base de données.
	 *
	 * @param cds [ContraintedeSpecialite] valeur qui doit être mise à jour
	 * @return [ContraintedeSpecialite] valeur mise à jour de la contrainte de
	 *         spécialité.
	 * @throws SQLException Si une erreur survient lors de l'exécution de
	 *                      l'instruction SQL.
	 */

	@Override
	public ContraintedeSpecialite update(ContraintedeSpecialite cds) {

		try {

			String requete = "UPDATE contraintedespe SET ";
			requete += "idContrainteSpe = ?, ";
			requete += "nomContrainteSpe = ?, ";
			requete += "activation = ?, ";
			requete += "duree = ?, ";
			requete += "temporalite = ?, ";
			requete += "idSpe = ? ";
			requete += "WHERE idContrainteSpe = ?;";

			stmt = connect.prepareStatement(requete);

			stmt.setInt(1, cds.getIdContrainteSpe());
			stmt.setString(2, cds.getNomContrainteSpe());
			stmt.setInt(3, cds.getActivation());
			stmt.setDouble(4, cds.getDuree());
			stmt.setString(5, cds.getTemporalite());
			stmt.setInt(6, cds.getSpecialite().getIdSpecialite());
			stmt.setInt(7, cds.getIdContrainteSpe());

			stmt.executeUpdate();
			System.out.println("Ligne mise à jour ! \n");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cds;
	}

	/**
	 * Supprimer une contrainte de spécialité existant dans la base de données.
	 *
	 * @param cds [ContraintedeSpecialite] valeur de la contrainte de spécialité qui
	 *            doit être supprimé
	 * @throws SQLException Si une erreur survient lors de l'exécution de
	 *                      l'instruction SQL.
	 */

	@Override
	public void delete(ContraintedeSpecialite cds) {

		try {

			String requete = "DELETE FROM contraintedespe ";
			requete += "WHERE idContrainteSpe = ?";

			stmt = connect.prepareStatement(requete);

			stmt.setInt(1, cds.getIdContrainteSpe());

			stmt.executeUpdate();

			System.out.println("Suppression réussie ! \n");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Lecture d'une contrainte de spécialité dans la base de données avec
	 * l'identifiant spécifié. !!Ajout de System.out.println(cds.toString()); car
	 * bien que le retour ne soit pas null, rien n'est renvoyé. Tentative de palier
	 * aux problèmes.
	 *
	 * @param id [int] valeur de l'identifiant spécifié pour être lue
	 * @return [ContraintedeSpecialite] lecture de la contrainte de spécialité
	 */
	public ContraintedeSpecialite read(int id) {

		ContraintedeSpecialite cds = null;
		Specialite spe = null;

		try {

			String requete = "SELECT * ";
			requete += "FROM contraintedespe INNER JOIN specialite ";
			requete += "ON contraintedespe.idSpe = specialite.idSpe ";
			requete += "WHERE idContrainteSpe =" + id;

			stmt = connect.prepareStatement(requete);

			rs = stmt.executeQuery();

			if (rs.next()) {
				spe = new Specialite(rs.getInt("contraintedespe.idSpe"), rs.getString("nomSpe"));

				cds = new ContraintedeSpecialite(rs.getInt("idContrainteSpe"), spe, rs.getInt("activation"),
						rs.getString("nomContrainteSpe"), rs.getDouble("duree"), rs.getString("temporalite"));

				System.out.println(cds.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cds;
	}

	/**
	 * Lecture des contraintes légales présentes dans la base de données.
	 *
	 * @return [ArrayList<\ContraintedeSpecialite\>] tableau de type
	 *         <\ContraintedeSpecialite\>
	 */
	public ArrayList<ContraintedeSpecialite> readAll() {

		ContrainteDeSpecialiteDAO cdsd = new ContrainteDeSpecialiteDAO();
		ContraintedeSpecialite cds = null;
		Specialite spe = null;

		ArrayList<ContraintedeSpecialite> lesContraintes = new ArrayList<>();

		try {

			String requete = "SELECT * ";
			requete += "FROM contraintedespe INNER JOIN specialite ";
			requete += "ON contraintedespe.idSpe = specialite.idSpe ";

			ResultSet result = stmt.executeQuery(requete);

			while (result.next()) {
				cds = this.read(result.getInt(1));
				spe = new Specialite(result.getInt("idSpe"), result.getString("nomSpe"));

				ContraintedeSpecialite sdc = new ContraintedeSpecialite(result.getInt("idContrainteSpe"), spe,
						result.getInt("activation"), result.getString("nomContrainteSpe"), result.getDouble("duree"),
						result.getString("temporalite"));

				lesContraintes.add(sdc);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lesContraintes;
	}

	/**
	 * Cette méthode permet de vérifier le respect des contraintes de spécialité
	 * associé à un personnel. Si elles ne le sont pas, appel de la méthode
	 * Create(Violation violation).
	 *
	 * @param personnel      [Personnel] valeur du personnel à vérifier
	 * @param dateDebSemaine [Date] valeur de date à vérifier
	 * @param dateFinSemaine [Date] valeur de date à vérifier
	 * @param dateDebMois    [Date] valeur de date à vérifier
	 * @param dateFinMois    [Date] valeur de date à vérifier
	 * @return [ArrayList<Violation>] liste de violations
	 */
	public ArrayList<Violation> checker(Personnel personnel, Date dateDebSemaine, Date dateFinSemaine, Date dateDebMois,
			Date dateFinMois, Date dateJour, BesoinEnPersonnel besoinEnPersonnel) {

		ArrayList<Violation> listeViolation = new ArrayList<>();

		try {

			String req = "SELECT * FROM contraintedespe";

			stmt = connect.prepareStatement(req);
			rs = stmt.executeQuery();

			while (rs.next()) {
				Specialite specialite = besoinEnPersonnel.getSpecialite() != null ? besoinEnPersonnel.getSpecialite()
						: null;
				ContraintedeSpecialite contrainte = new ContraintedeSpecialite(rs.getInt("idContrainteSpe"), specialite,
						1, rs.getString("nomContrainteSpe"), rs.getDouble("duree"), rs.getString("temporalite"));
				double duree = contrainte.getDuree();

				int d = (int) duree;

				switch (contrainte.getTemporalite()) {
				case "Quotidienne":
					try {

						String requete = "SELECT *, SUM(horaireFin-horaireDeb) as tempsQuotidien  ";
						requete += "FROM personnel p JOIN estaffecte e ON p.idPersonnel=e.idPersonnel ";
						requete += "JOIN besoinenpersonnel b ON e.idBesoin=b.idBesoin ";
						requete += "JOIN creneau c ON b.idCreneau=c.idCreneau ";
						requete += "JOIN specialite s ON b.idSpe=s.idSpe ";
						requete += "JOIN fonction f ON b.idFonction=f.idFonction ";
						requete += "WHERE p.idPersonnel = ? AND ";
						requete += "c.dateCreneau = ? ";

						stmt = connect.prepareStatement(requete);
						stmt.setInt(1, personnel.getIdPersonnel());
						stmt.setDate(2, dateJour);

						ResultSet result = stmt.executeQuery();

						Time quotidien;
						Time tempsDuree = Time.valueOf("0" + d + ":00:00");

						while (result.next()) {

							double tempsQuotidienDouble = result.getDouble("tempsQuotidien");
							int tqd = (int) tempsQuotidienDouble;
							String tempsQuotidienString = "0" + tqd + ":00:00";
							quotidien = Time.valueOf(tempsQuotidienString);

							if (quotidien.after(tempsDuree)) {

								Violation violation = new Violation(contrainte, personnel, besoinEnPersonnel);

								listeViolation.add(violation);
								// System.out.println("Quotidienne");
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					break;

				case "Hebdomadaire":
					try {

						String requete = "SELECT *, ";
						requete += "SUM(horaireFin-horaireDeb) as tempsHebdomadaire ";
						requete += "FROM personnel p JOIN estaffecte e ON p.idPersonnel=e.idPersonnel ";
						requete += "JOIN besoinenpersonnel b ON e.idBesoin=b.idBesoin ";
						requete += "JOIN creneau c ON b.idCreneau=c.idCreneau ";
						requete += "JOIN sespecialise s ON p.idPersonnel=s.idPersonnel ";
						requete += "JOIN specialite sp ON s.idSpe=sp.idSpe ";
						requete += "JOIN fonction f ON f.idFonction=p.idFonction ";
						requete += "WHERE p.idPersonnel = ? AND ";
						requete += "c.dateCreneau BETWEEN ? AND ? ";

						stmt = connect.prepareStatement(requete);
						stmt.setInt(1, personnel.getIdPersonnel());
						stmt.setDate(2, dateDebSemaine);
						stmt.setDate(3, dateFinSemaine);

						ResultSet result2 = stmt.executeQuery();

						Time hebdomadaire;
						Time tempsDuree = Time.valueOf("0" + d + ":00:00");

						while (result2.next()) {
							double tempsQuotidienDouble = result2.getDouble("tempsHebdomadaire");
							int tqd = (int) tempsQuotidienDouble;
							String tempsQuotidienString = "0" + tqd + ":00:00";
							hebdomadaire = Time.valueOf(tempsQuotidienString);

							if (hebdomadaire.after(tempsDuree)) {

								Violation violation = new Violation(contrainte, personnel, besoinEnPersonnel);

								listeViolation.add(violation);
								// System.out.println("Hebdomadaire");

							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					break;

				case "Mensuelle":
					try {

						String requete = "SELECT *, SUM(horaireFin-horaireDeb) as tempsMensuel ";
						requete += "FROM personnel p JOIN estaffecte e ON p.idPersonnel=e.idPersonnel ";
						requete += "JOIN besoinenpersonnel b ON e.idBesoin=b.idBesoin ";
						requete += "JOIN creneau c ON b.idCreneau=c.idCreneau ";
						requete += "JOIN sespecialise s ON p.idPersonnel=s.idPersonnel ";
						requete += "JOIN specialite sp ON s.idSpe=sp.idSpe ";
						requete += "JOIN fonction f ON f.idFonction=p.idFonction ";
						requete += "WHERE p.idPersonnel = ? AND ";
						requete += "c.dateCreneau BETWEEN ? AND ? ";

						stmt = connect.prepareStatement(requete);
						stmt.setInt(1, personnel.getIdPersonnel());
						stmt.setDate(2, dateDebMois);
						stmt.setDate(3, dateFinMois);

						ResultSet result3 = stmt.executeQuery();

						Time mensuel;
						Time tempsDuree = Time.valueOf("0" + d + ":00:00");

						while (result3.next()) {

							double tempsQuotidienDouble = result3.getDouble("tempsMensuel");
							int tqd = (int) tempsQuotidienDouble;
							String tempsQuotidienString = "0" + tqd + ":00:00";
							mensuel = Time.valueOf(tempsQuotidienString);

							if (mensuel.after(tempsDuree)) {

								Violation violation = new Violation(contrainte, personnel, besoinEnPersonnel);

								listeViolation.add(violation);
								// System.out.println("Mensuelle");

							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					break;

				default:
					System.out.println(
							"La temporalité n'est pas prise en compte. Il y a 'Mensuelle', 'Hebdomadaire' et 'Quotidienne'.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listeViolation;
	}
}
