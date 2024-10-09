/**
 * @author 22106979 Antivackis Vanessa - Documentation
 * @author 22203517 Forest Jules - Ecriture de la classe et documentation
 */

package dao;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import metier.Fonction;
import metier.Personnel;
import metier.Specialite;
import metier.Violation;

/**
 * Cette classe DAOPersonnel est enrichie par la classe DAO<T>.
 */
public class PersonnelDAO extends DAO<Personnel> {

    // valeur du résultat des requêtes SQL
    private ResultSet rs;

    public PersonnelDAO() {

    }

    /**
     * Cette méthode permet d'insérer de nouvelles données de type Personnel dans la
     * base de données, par le biais de requêtes SQL.
     * @param personnel [Personnel] valeur d'un personnel à ajouter dans la base de
     *                  données.
     * @param specialites [ArrayList<Specialite>] liste de specialites choisis lors
     *                  de la création du personnel
     * @throws SQLException
     * @return
     */
    @Override
    public Personnel create(Personnel personnel) {
        if(personnel == null) {
            return null;
        }
        try {
              String requete = "INSERT INTO personnel (nomPersonnel, prenomPersonnel, "
                      + "dateNaissance, tempsTravailMensuel, idFonction) VALUES (?,?,?,?,?)";
              stmt = connect.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
              stmt.setString(1, personnel.getNomPersonnel());
              stmt.setString(2, personnel.getPrenomPersonnel());
              stmt.setDate(3, java.sql.Date.valueOf(personnel.getDateNaissance()));
              stmt.setInt(4, personnel.getTempsTravailMensuel());
              // Ajout de fonction : si elle n'a pas de code, c'est qu'elle n'est pas en base
              if (personnel.getFonction().getIdFonction()==0) {
                  Fonction fonctionEnBase= (new FonctionDAO()).create(personnel.getFonction());
                  personnel.getFonction().setIdFonction(fonctionEnBase.getIdFonction());
              }
              stmt.setInt(5, personnel.getFonction().getIdFonction());

              stmt.executeUpdate();

              ResultSet cles = stmt.getGeneratedKeys();
              if(cles.next()){
                  int id = ((BigInteger)cles.getObject(1)).intValue();
                  personnel.setIdPersonnel(id );
              }

              // ajout éventuel des spécialitées et création des liens avec
              if(personnel.getSpecialites()!=null && !personnel.getSpecialites().isEmpty()) {
                  SpecialiteDAO tableSpe = new SpecialiteDAO();
                  requete = "INSERT INTO seSpecialise (idPersonnel, idSpe) VALUES (?, ?)";
                  stmt = connect.prepareStatement(requete);
                  for (Specialite spe:personnel.getSpecialites()) {
                      //   ici nous faisons un check pour éviter des problèmes
                      //   Mais on peut enlever la partie de création des spécialites
                      //   et ne garder que la partie des liaisons

                      // Ajout de spécialité : si elle n'a pas de code, c'est qu'elle n'est pas en base
                      if(spe != null) {
                          if (spe.getIdSpecialite()==0) {
                              Specialite speEnBase = tableSpe.create(spe);
                              spe.setIdSpecialite(speEnBase.getIdSpecialite());
                          }
                          stmt.setInt(1, personnel.getIdPersonnel());
                          stmt.setInt(2, spe.getIdSpecialite());
                          stmt.executeUpdate();
                      }
                  }
              }

              // le choix de ne pas mettre l'ajout des violations du tableau de violations est volontaire
              // lors la création d'un personnel, on ne lui attribue pas directement des violations

          } catch (SQLException e) {
              e.printStackTrace();
          }
          return personnel;
    }

    /**
     * Cette méthode permet de mettre à jour des données de type Personnel dans la
     * base de données, par le biais de requêtes SQL.
     *
     * @param personnel [Personnel] valeur d'un personnel à modifier dans la base de
     *                  données.
     * @throws SQLException
     * @return
     */
    @Override
    public Personnel update(Personnel personnel) {
        if (personnel == null) {
            return null;
        }
        String requete = "UPDATE Personnel SET nomPersonnel = ?, prenomPersonnel = ?"
                + ", dateNaissance = ?, tempsTravailMensuel = ?, idFonction = ? "
                + "WHERE idPersonnel = ?";

        try {
            stmt = connect.prepareStatement(requete);
            stmt.setString(1, personnel.getNomPersonnel());
            stmt.setString(2, personnel.getPrenomPersonnel());
            stmt.setString(3, personnel.getDateNaissance());
            stmt.setInt(4, personnel.getTempsTravailMensuel());
            stmt.setInt(5, personnel.getFonction().getIdFonction());
            stmt.setInt(6, personnel.getIdPersonnel());

            stmt.executeUpdate();

            requete = "DELETE FROM seSpecialise "
                    + "WHERE idPersonnel = ?";
                    stmt = connect.prepareStatement(requete);
            stmt.setInt(1, personnel.getIdPersonnel());
            stmt.executeUpdate();

            for(Specialite spe : personnel.getSpecialites()) {
                requete = "INSERT IGNORE INTO seSpecialise (idPersonnel, idSpe) "
                        + "VALUES (?, ?)";
                stmt = connect.prepareStatement(requete);
                stmt.setInt(1, personnel.getIdPersonnel());
                stmt.setInt(2, spe.getIdSpecialite());
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return personnel;
    }

    /**
     * Cette méthode permet de supprimer des données de type Personnel dans la base
     * de données, par le biais de requêtes SQL.
     * @param personnel[Personnel] valeur d'un personnel à supprimer dans la base de
     *                             données.
     * @throws SQLException
     */
    @Override
    public void delete(Personnel personnel) {
        if (personnel != null) {
            String requete = "DELETE FROM estAffecte WHERE idPersonnel = ?";
            String requete2 = "DELETE FROM seSpecialise WHERE idPersonnel = ?";
            String requete3 = "DELETE FROM nonRespectSpe WHERE idPersonnel = ?";
            String requete4 = "DELETE FROM nonRespectLegal WHERE idPersonnel = ?";
            String requete5 = "DELETE FROM Personnel WHERE idPersonnel = ?";

            try {

                PreparedStatement deleteAffectation = connect.prepareStatement(requete);
                PreparedStatement deleteSpe = connect.prepareStatement(requete2);
                PreparedStatement deleteRespectSpe = connect.prepareStatement(requete3);
                PreparedStatement deleteRespectLegal = connect.prepareStatement(requete4);
                this.stmt = connect.prepareStatement(requete5);

                // création d'une transaction pour éviter les problèmes d'incohérence
                // et préserver l'intégrité des données
                connect.setAutoCommit(false);

                deleteAffectation.setInt(1, personnel.getIdPersonnel());
                deleteAffectation.executeUpdate();

                deleteSpe.setInt(1, personnel.getIdPersonnel());
                deleteSpe.executeUpdate();

                deleteRespectSpe.setInt(1, personnel.getIdPersonnel());
                deleteRespectSpe.executeUpdate();

                deleteRespectLegal.setInt(1, personnel.getIdPersonnel());
                deleteRespectLegal.executeUpdate();

                stmt.setInt(1, personnel.getIdPersonnel());
                stmt.executeUpdate();

                connect.commit();
                connect.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
                if (connect != null) {
                    try {
                        connect.rollback();
                    } catch (SQLException excep) {
                        excep.printStackTrace();
                    }
                 }
            }
        }
    }

    /**
     * Cette méthode permet de lire les données liés à un identifiant de
     * personnel dans la base de données, par le biais de requêtes SQL.
     * @param idPersonnel [int] valeur qui est associée à un personnel dans la base
     *                    de données.
     * @throws SQLException
     * @return Personnel
     */
    public Personnel read(int idPersonnel) {
        Personnel personnel = null;
        try {
            ArrayList<Violation> violations = new ArrayList<>();
            ArrayList<Specialite> specialites = new ArrayList<>();
            FonctionDAO fonctionDAO = new FonctionDAO();

            String requete = "SELECT Personnel.idPersonnel AS id, nomPersonnel, prenomPersonnel, "
                    + "dateNaissance, tempsTravailMensuel, idFonction "
                    + "FROM Personnel "
                    + "WHERE idPersonnel = ?";

            stmt = connect.prepareStatement(requete);
            stmt.setInt(1, idPersonnel);
            rs = stmt.executeQuery();

            if(rs.next()) {
                personnel = new Personnel(rs.getInt("id"), rs.getString("nomPersonnel"),
                        rs.getString("prenomPersonnel"), rs.getDate("dateNaissance").toString(),
                        rs.getInt("TempsTravailMensuel"), fonctionDAO.read(rs.getInt("idFonction")),
                        specialites, violations);

                // readByIdPersonnel va renvoyer une liste de Violations via l'id d'un Personnel
                // ATTENTION, les violations n'ont pas de Personnel, sachant qu'il est en train d'être crée,
                // on va remplir cet attribut par la suite lorsque le Personnel sera complet
                ArrayList<Violation> violationsBDD = this.getViolationsByIdPersonnel(personnel.getIdPersonnel());
                for(Violation violation : violationsBDD) {
                    violation.setPersonnel(personnel);
                }

                personnel.setViolations(violations);

                // readByIdPersonnel va renvoyer une liste de Specialites via l'id d'un Personnel
                personnel.setSpecialites(this.getSpecialitesByIdPersonnel(personnel.getIdPersonnel()));

                rs.close();
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return personnel;
    }

     /**
     * Cette méthode permet de lire tout les personnels dans la base.
     * @throws SQLException
     * @return ArrayList<Personnel>
     */
    public ArrayList<Personnel> readAll() {
        ArrayList<Personnel> listePersonnels = new ArrayList<>();
        try {
            String requete = "SELECT idPersonnel FROM Personnel ORDER BY nomPersonnel ASC";

            stmt = connect.prepareStatement(requete);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                listePersonnels.add(this.read(result.getInt("idPersonnel")));
            }

            return listePersonnels;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return listePersonnels;
    }

    /**
     * Cette méthode permet de lire les données liés à un identifiant de
     * personnel dans la base de données, par le biais de requêtes SQL.
     * Cependant, ici on ne cherchera pas à renvoyer ses violations, pour
     * éviter une boucle infinie lors d'un appel d'un read par ViolationDAO
     * @param idPersonnel [int] valeur qui est associée à un personnel dans la base
     *                    de données.
     * @throws SQLException
     * @return Personnel
     */
    public Personnel readForViolation(int idPersonnel) {
        Personnel personnel = null;
        try {
            ArrayList<Violation> violations = new ArrayList<>();
            ArrayList<Specialite> specialites = new ArrayList<>();
            FonctionDAO fonctionDAO = new FonctionDAO();

            String requete = "SELECT Personnel.idPersonnel AS id, nomPersonnel, prenomPersonnel, "
                    + "dateNaissance, tempsTravailMensuel, idFonction "
                    + "FROM Personnel "
                    + "WHERE idPersonnel = ?";

            stmt = connect.prepareStatement(requete);
            stmt.setInt(1, idPersonnel);
            rs = stmt.executeQuery();

            if(rs.next()) {
                personnel = new Personnel(rs.getInt("id"), rs.getString("nomPersonnel"),
                        rs.getString("prenomPersonnel"), rs.getDate("dateNaissance").toString(),
                        rs.getInt("TempsTravailMensuel"), fonctionDAO.read(rs.getInt("idFonction")),
                        specialites, violations);

                // readByIdPersonnel va renvoyer une liste de Specialites via l'id d'un Personnel
                personnel.setSpecialites(this.getSpecialitesByIdPersonnel(personnel.getIdPersonnel()));

                rs.close();
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return personnel;
    }

    /**
    * Cette méthode permet de renvoyer tout les personnels
    * dans la base qui sont présents dans un créneau.
    * @param int, id du créneau dont on veut les personnels affectés
    * @throws SQLException
    * @return ArrayList<Personnel>
    */
   public ArrayList<Personnel> readAllByIdCreneau(int idCreneau) {
       ArrayList<Personnel> listePersonnels = new ArrayList<>();
       try {
           String requete = "SELECT Personnel.idPersonnel FROM Personnel "
                   + "INNER JOIN estAffecte ON Personnel.idPersonnel = estAffecte.idPersonnel "
                   + "INNER JOIN BesoinEnPersonnel ON besoinEnPersonnel.idBesoin = estAffecte.idBesoin "
                   + "WHERE idCreneau = ?"
                   + "ORDER BY nomPersonnel ASC ";

           stmt = connect.prepareStatement(requete);
           stmt.setInt(1, idCreneau);
           ResultSet result = stmt.executeQuery();

           while (result.next()) {
               listePersonnels.add(this.read(result.getInt("idPersonnel")));
           }

           return listePersonnels;

       } catch (SQLException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
       return listePersonnels;
   }

    /**
    * Cette méthode permet de renvoyer toutes les spécialités
    * dans la base qui sont liées à un personnel.
    * @param int - id du créneau dont on veut les personnels affectés
    * @throws SQLException
    * @return ArrayList<Specialite>
    */
    public ArrayList<Specialite> getSpecialitesByIdPersonnel(int idPersonnel){
        ArrayList<Specialite> specialites = new ArrayList<>();
        try {
            String requete = "SELECT seSpecialise.idSpe FROM seSpecialise "
                    + "INNER JOIN Specialite ON Specialite.idSpe = seSpecialise.idSpe "
                    + "WHERE idPersonnel = ? "
                    + "ORDER BY nomSpe ASC";
            stmt = connect.prepareStatement(requete);
            stmt.setInt(1, idPersonnel);
            ResultSet result = stmt.executeQuery();

            while(result.next()) {
                specialites.add((new SpecialiteDAO()).read(result.getInt("idSpe")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return specialites;
    }

    /**
     * Cette méthode permet de renvoyer toutes les violations
     * dans la base qui sont liées à un personnel.
     * @param idPersonnel
     * @return ArrayList<Violation>
     */
    public ArrayList<Violation> getViolationsByIdPersonnel(int idPersonnel){
        return (new ViolationDAO()).readByIdPersonnel(idPersonnel);
    }

    /**
     * Cette méthode permet d'afficher en sortie si le personnel indiqué est affecté
     * à un créneau et si oui, le créneau auquel il est relié.
     * @param idPersonnel [int] valeur qui est associée à un personnel dans la base
     *                    de données.
     * @throws SQLException
     */
    public boolean isAffected(int idPersonnel) {
          boolean estPresent = false;
          try {
              String affecte = "SELECT idPersonnel FROM estAffecte WHERE idPersonnel=?";
              stmt = connect.prepareStatement(affecte);
              stmt.setInt(1, idPersonnel);
              rs = stmt.executeQuery();

              if (rs.next()) {
                  estPresent = true;
              }
          } catch (SQLException e) {
              e.printStackTrace();
          }
          return estPresent;
    }
}