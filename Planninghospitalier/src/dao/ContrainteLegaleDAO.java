/**
 * La classe 'ContrainteLegaleDAO' fournit des méthodes pour gérer
 * les objets 'ContrainteLegale' dans une base de données.
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
import metier.ContrainteLegale;
import metier.Creneau;
import metier.Fonction;
import metier.Personnel;
import metier.Specialite;
import metier.Violation;

/**
 * Cette classe est un DAO (Data Access Object) qui fournit des méthodes pour
 * créer, lire, mettre à jour et supprimer des objets `ContrainteLegale`.
 */
public class ContrainteLegaleDAO
      extends DAO<ContrainteLegale> {

  /**
   * valeur du résultat de la requête SQL.
   */
  private ResultSet rs;

  /**
   * Créer un nouvel objet "contrainte légale" dans la base de données.
   *
   * @param cl [ContrainteLegale] valeur de la contrainte légale à créer dans la
   *           base de données
   * @return [ContrainteLegale] valeur de la contrainte légale créée
   * dans la base
   *         de données
   * @throws SQLException Si une erreur survient lors de l'exécution de
   *              l'instruction SQL.
   */
  @Override
  public ContrainteLegale create(ContrainteLegale cl) {

    int idCL = 0;

    try {

      String requete = "INSERT INTO contraintelegale ";
      requete += "(nomContrainteLegale, duree, temporalite)";
      requete += "VALUES (?, ?, ?)";

      stmt = connect.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
      stmt.setString(1, cl.getNom());
      stmt.setDouble(2, cl.getDuree());
      stmt.setString(3, cl.getTemporalite());

      stmt.executeUpdate();
      System.out.println("Insertion réussie !\n");

      ResultSet clefs = stmt.getGeneratedKeys();
      if (clefs.next()) {
        // Récupére l'objet dans ResultSet clefs
        Object obj = clefs.getObject(1);

        // Vérifie si l'objet est un BigInteger
        if (obj instanceof java.math.BigInteger) {
          // Cast to Integer if it's BigInteger
          idCL = ((java.math.BigInteger) obj).intValue();
       // Check if the object is an Integer
        } else if (obj instanceof java.lang.Integer) {
          // Cast directly if it's Integer
          idCL = (Integer) obj;
        } else {
          throw new ClassCastException(
            // Handle unexpected types
            "The generated key is not an Integer or BigInteger");
        }

        cl.setIdContrainteLegale(idCL);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return cl;
  }

  /**
   * Mettre à jour une contrainte légale existant dans la base de données.
   *
   * @param cl [ContrainteLegale] valeur qui doit être mise à jour
   * @return [ContrainteLegale] valeur mise à jour de la contrainte légale.
   * @throws SQLException Si une erreur survient lors de l'exécution de
   *              l'instruction SQL.
   */

  @Override
  public ContrainteLegale update(ContrainteLegale cl) {

    try {

      String requete = "UPDATE contraintelegale SET ";
      requete += "idContrainteLegale = ?, ";
      requete += "nomContrainteLegale = ?, ";
      requete += "duree = ?, ";
      requete += "temporalite = ? ";
      requete += "WHERE idContrainteLegale = ?;";

      stmt = connect.prepareStatement(requete);

      stmt.setInt(1, cl.getIdContrainteLegale());
      stmt.setString(2, cl.getNom());
      stmt.setDouble(3, cl.getDuree());
      stmt.setString(4, cl.getTemporalite());
      stmt.setInt(5, cl.getIdContrainteLegale());

      stmt.executeUpdate();
      System.out.println("Ligne mise à jour !\n");
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return cl;
  }

  /**
   * Supprimer une contrainte légale existant dans la base de données.
   *
   * @param cl [ContrainteLegale] valeur de la contrainte légale qui doit être
   *           supprimé
   * @throws SQLException Si une erreur survient lors de l'exécution de
   *              l'instruction SQL.
   */

  @Override
  public void delete(ContrainteLegale cl) {

    try {

      String requete = "DELETE FROM contraintelegale ";
      requete += "WHERE idContrainteLegale = ";
      requete += cl.getIdContrainteLegale();

      stmt = connect.prepareStatement(requete);
      stmt.executeUpdate();

      System.out.println("Suppression réussie !\n");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Lecture d'une contrainte légale dans la base de données avec l'identifiant
   * spécifié.
   *
   * @param id [int] valeur de l'identifiant spécifié pour être lue
   * @return [ContrainteLegale] lecture de la contrainte de spécialité
   */
  public ContrainteLegale read(int id) {

    ContrainteLegale cl = null;


    try {

      String requete = "SELECT * FROM contraintelegale ";
      requete += "WHERE idContrainteLegale =" + id;

      stmt = connect.prepareStatement(requete);

      rs = stmt.executeQuery();

      if (rs.next()) {
        cl = new ContrainteLegale(
          rs.getInt(1),
          rs.getString(2),
          rs.getDouble(3),
          rs.getString(4));

        System.out.print(cl.toString() + "\n");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return cl;
  }

  /**
   * Lecture des contraintes légales présentes dans la base de données.
   *
   * @return [ArrayList<\ContrainteLegale\>] tableau de 
   * type <\ContrainteLegale\>
   */
  public ArrayList<ContrainteLegale> readAll() {
    ContrainteLegaleDAO cld = new ContrainteLegaleDAO();
    ContrainteLegale cl = null;
    ArrayList<ContrainteLegale> lesContraintes = new ArrayList<>();

    try {

      String requete = "SELECT * FROM contraintelegale";
      rs = stmt.executeQuery(requete);

      while (rs.next()) {
        cl = cld.read(rs.getInt(1));
        ContrainteLegale lc = new ContrainteLegale(
            rs.getInt(1),
            rs.getString(2),
            rs.getDouble(3),
            rs.getString(4));
        lesContraintes.add(lc);
      }
      rs.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return lesContraintes;
  }

  /**
   * Cette méthode permet de vérifier le respect des contraintes de spécialité
   * associé à un personnel. Si elles ne le sont pas,
   * appel de la méthode Create(Violation violation).
   *
   * @param personnel  [Personnel] valeur du personnel à vérifier
   * @param dateDebSemaine  [Date] valeur de date à vérifier
   * @param dateFinSemaine  [Date] valeur de date à vérifier
   * @param dateDebMois  [Date] valeur de date à vérifier
   * @param dateFinMois  [Date] valeur de date à vérifier
 * @param besoinEnPersonnel 
   * @return [ArrayList<Violation>] liste de violations
   */
  public ArrayList<Violation> checker(
         Personnel personnel,
         Date dateDebSemaine,
         Date dateFinSemaine,
         Date dateDebMois,
         Date dateFinMois,
         Date dateJour, BesoinEnPersonnel besoinEnPersonnel) {

	  ArrayList<Violation> listeViolation = new ArrayList<Violation>();

	  try {

		  String req = "SELECT * FROM contraintelegale";

		  stmt = connect.prepareStatement(req);
		  rs = stmt.executeQuery();

		  while(rs.next()) {

			  ContrainteLegale contrainte = new ContrainteLegale(rs.getInt("idContrainteLegale"), rs.getString("nomContrainteLegale"),rs.getDouble("duree"), rs.getString("temporalite"));

			    double duree = contrainte.getDuree();

			    int d = (int)duree;
			    		
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
			        	  String tempsQuotidienString = "0" + tqd+":00:00";
			        	  quotidien = Time.valueOf(tempsQuotidienString);
				          

			            if (quotidien.after(tempsDuree)) {


			              Violation violation = new Violation(contrainte, personnel, besoinEnPersonnel);

			              listeViolation.add(violation);
			              //System.out.println("Quotidienne");
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
			        	  String tempsQuotidienString = "0" + tqd+":00:00";
			        	  hebdomadaire = Time.valueOf(tempsQuotidienString);

			            if (hebdomadaire.after(tempsDuree)) {

				              Violation violation = new Violation(contrainte, personnel, besoinEnPersonnel);

			              listeViolation.add(violation);
			              //System.out.println("Hebdomadaire");

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
			        	  String tempsQuotidienString = "0" + tqd+":00:00";
			        	  mensuel = Time.valueOf(tempsQuotidienString);

			            if (mensuel.after(tempsDuree)) {

				              Violation violation = new Violation(contrainte, personnel, besoinEnPersonnel);


			              listeViolation.add(violation);
			              //System.out.println("Mensuelle");

			            }
			          }
			        } catch (SQLException e) {
			          e.printStackTrace();
			        }
			        break;

			      default:
			        System.out.println("La temporalité n'est pas prise en compte. Il y a 'Mensuelle', 'Hebdomadaire' et 'Quotidienne'.");
			    }
		  }
	  } catch (SQLException e) {
          e.printStackTrace();
        }

	return listeViolation;
  }
}
