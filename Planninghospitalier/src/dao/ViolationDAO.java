/**
 * @author 22106979 - Documentation
 * @author 22208030 - Implémentation
 * @author 22203517 Forest Jules - Ajout méthode readByIdPersonnel et relecture
 */
package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import metier.ContrainteLegale;
import metier.ContraintedeSpecialite;
import metier.Violation;

/**
 * Cette classe ViolationDAO est enrichie par la classe DAO<T>.
 */
public class ViolationDAO extends DAO<Violation> {

    // valeur du résultat des requêtes SQL
    private ResultSet rs;

    /**
     * Cette méthode permet d'insérer de nouvelles données de type Violation dans la
     * base de données, par le biais de requêtes SQL.
     * ATTENTION : la classe Violation représente les non respects de contraintes légales
     * mais aussi ceux de contraintes de spécialités
     *
     * @param violation [Violation] valeur d'une violation à ajouter dans la base de
     *                  données.
     * @return [Violation] Valeur de la violation implémentée dans la base de
     *         données
     *
     * @throws SQLException S'il y a une erreur lors de l'execution de la requête
     *                      SQL
     */
    @Override
    public Violation create(Violation violation) {
        try {
            //vérification du type de contrainte lié à la violation
            if (violation.getContrainte() instanceof ContrainteLegale) {
                String requete = "INSERT INTO nonRespectLegal (idPersonnel, idContrainteLegale, idBesoin) "
                        + "VALUES (?,?,?)";
                stmt = connect.prepareStatement(requete);
                stmt.setInt(1, violation.getPersonnel().getIdPersonnel());
                stmt.setInt(2, ((ContrainteLegale) violation.getContrainte()).getIdContrainteLegale());
                stmt.setInt(3, violation.getBesoin().getIdBesoin());
            } else if (violation.getContrainte() instanceof ContraintedeSpecialite) {
                String requete = "INSERT INTO nonRespectSpe (idPersonnel, idContrainteSpe, idBesoin) "
                        + "VALUES (?,?,?)";
                stmt = connect.prepareStatement(requete);
                stmt.setInt(1, violation.getPersonnel().getIdPersonnel());
                stmt.setInt(2, ((ContraintedeSpecialite) violation.getContrainte()).getIdContrainteSpe());
                stmt.setInt(3, violation.getBesoin().getIdBesoin());
            }
            stmt.executeUpdate();
            System.out.println("Insertion réussie");

        } catch (SQLException e) {
            System.err.println("Erreur requête SQL");
            e.printStackTrace();
        }
        return violation;
    }
    /**
     * Cette méthode permet de mettre à jour des données de type Violation dans la
     * base de données, par le biais de requêtes SQL.
     * ATTENTION : la classe Violation représente les non respects de contraintes légales
     * mais aussi ceux de contraintes de spécialités
     *
     * @param violation [Violation] valeur d'une violation à modifier dans la base
     *                  de données.
     * @return [Violation] Valeur de la violation implémentée dans la base de
     *         données
     *
     * @throws SQLException S'il y a une erreur lors de l'execution de la requête
     *                      SQL
     */
    @Override
    public Violation update(Violation violation) {
        try {
            if (violation.getContrainte() instanceof ContrainteLegale) {
                String requete = "UPDATE nonRespectLegal SET idContrainteLegale = ?, idPersonnel = ?, idBesoin = ?";
                stmt = connect.prepareStatement(requete);
                stmt.setInt(1, ((ContrainteLegale) violation.getContrainte()).getIdContrainteLegale());
                stmt.setInt(2, violation.getPersonnel().getIdPersonnel());
                stmt.setInt(3, violation.getBesoin().getIdBesoin());
            } else if (violation.getContrainte() instanceof ContraintedeSpecialite) {
                String requete = "UPDATE nonRespectSpe SET idContrainteSpe = ?, idPersonnel = ?, idBesoin = ?";
                stmt = connect.prepareStatement(requete);
                stmt.setInt(1, ((ContraintedeSpecialite) violation.getContrainte()).getIdContrainteSpe());
                stmt.setInt(2, violation.getPersonnel().getIdPersonnel());
                stmt.setInt(3, violation.getBesoin().getIdBesoin());
            }
            stmt.executeUpdate();
            System.out.println("Modification réussie");
        } catch (SQLException e) {
            System.err.println("Erreur requête SQL");
            e.printStackTrace();
        }

        return violation;
    }

    /**
     * Cette méthode permet de supprimer des données de type Violation dans la base
     * de données, par le biais de requêtes SQL.
     * ATTENTION : la classe Violation représente les non respects de contraintes légales
     * mais aussi ceux de contraintes de spécialités
     *
     * @param violation[Violation] valeur d'une violation à supprimer dans la base
     *                             de données.
     *
     * @throws SQLException S'il y a une erreur lors de l'execution de la requête
     *                      SQL
     */
    @Override
    public void delete(Violation violation) {
        try {
            // Definir si la contrainte est une ContrainteLegale ou une
            // ContraintedeSpecialite et mettre l'id de la contrainte associée dans la
            // variable 'i'
            if (violation.getContrainte() instanceof ContrainteLegale) {
                String requete = "DELETE FROM nonRespectLegal "
                        + "WHERE idContrainteLegale = ? "
                        + "AND idPersonnel = ? AND idBesoin = ?";
                stmt = connect.prepareStatement(requete);
                stmt.setInt(1, ((ContrainteLegale) violation.getContrainte()).getIdContrainteLegale());
                stmt.setInt(2, violation.getPersonnel().getIdPersonnel());
                stmt.setInt(3, violation.getBesoin().getIdBesoin());

                stmt.executeUpdate();
            } else if (violation.getContrainte() instanceof ContraintedeSpecialite) {
                String requete = "DELETE FROM nonRespectSpe "
                        + "WHERE idContrainteSpe = ? "
                        + "AND idPersonnel = ? AND idBesoin = ?";
                stmt = connect.prepareStatement(requete);
                stmt.setInt(1, ((ContraintedeSpecialite) violation.getContrainte()).getIdContrainteSpe());
                stmt.setInt(2, violation.getPersonnel().getIdPersonnel());
                stmt.setInt(3, violation.getBesoin().getIdBesoin());

                stmt.executeUpdate();
            }
            violation.getPersonnel().suppViolation(violation);

            System.out.println("Suppression réussie");
        } catch (SQLException e) {
            System.err.println("Erreur requête SQL");
            e.printStackTrace();
        }
    }

    /**
     * Cette méthode permet de retourner toutes les violations en
     * base de données, par le biais de requêtes SQL.
     * ATTENTION : la classe Violation représente les non respects de contraintes légales
     * mais aussi ceux de contraintes de spécialités, donc ici nous renvoyons
     * les lignes de tables NonRespectLegal et NonRespectSpe
     *
     * @return ArrayList<Violations> toutes les violations
     */
    public ArrayList<Violation> readAll() {
        ArrayList<Violation> violations = new ArrayList<>();

        try {
            String requete = "SELECT * FROM nonRespectLegal";
            stmt = connect.prepareStatement(requete);
            rs = stmt.executeQuery();

            while (rs.next()) {
                violations.add(
                        new Violation(
                                (new ContrainteLegaleDAO()).read(rs.getInt("idContrainteLegale")),
                                (new PersonnelDAO()).read(rs.getInt("idPersonnel")),
                                (new BesoinEnPersonnelDAO()).read(rs.getInt("idBesoin"))
                        )
                );
            }

            requete = "SELECT * FROM nonRespectSpe";
            stmt = connect.prepareStatement(requete);
            rs = stmt.executeQuery();

            while (rs.next()) {
                violations.add(
                        new Violation(
                                (new ContrainteDeSpecialiteDAO()).read(rs.getInt("idContrainteSpe")),
                                (new PersonnelDAO()).read(rs.getInt("idPersonnel")),
                                (new BesoinEnPersonnelDAO()).read(rs.getInt("idBesoin"))
                        )
                );
            }
            rs.close();
            System.out.println("Lecture réussie");

        } catch (SQLException e) {
            System.err.println("Erreur requête SQL");
            e.printStackTrace();
        }

        return violations;
    }

    /**
     * Cette méthode permet de retourner toutes les violations en lien avec
     * un Personnel, par le biais de requêtes SQL.
     * ATTENTION : la classe Violation représente les non respects de contraintes légales
     * mais aussi ceux de contraintes de spécialités, donc ici nous renvoyons
     * les lignes de tables NonRespectLegal et NonRespectSpe
     *
     * @param int idPersonnel, l'id du personnel dont on veut les violations
     *
     * @return ArrayList<Violations> toutes les violations
     */
    public ArrayList<Violation> readByIdPersonnel(int idPersonnel){
        ArrayList<Violation> violations = new ArrayList<>();

        try {
            String requete = "SELECT * FROM nonRespectLegal WHERE idPersonnel = ?";
            stmt = connect.prepareStatement(requete);
            stmt.setInt(1, idPersonnel);
            rs = stmt.executeQuery();

            while (rs.next()) {
                violations.add(
                        new Violation(
                                (new ContrainteLegaleDAO()).read(rs.getInt("idContrainteLegale")),
                                (null),
                                (new BesoinEnPersonnelDAO()).read(rs.getInt("idBesoin"))
                        )
                );
            }

            requete = "SELECT * FROM nonRespectSpe WHERE idPersonnel = ?";
            stmt = connect.prepareStatement(requete);
            stmt.setInt(1, idPersonnel);
            rs = stmt.executeQuery();

            while (rs.next()) {
                violations.add(
                        new Violation(
                                (new ContrainteDeSpecialiteDAO()).read(rs.getInt("idContrainteSpe")),
                                (null),
                                (new BesoinEnPersonnelDAO()).read(rs.getInt("idBesoin"))
                        )
                );
            }
            rs.close();
            System.out.println("Lecture réussie");

        } catch (SQLException e) {
            System.err.println("Erreur requête SQL");
            e.printStackTrace();
        }

        return violations;
    }

    /**
     * Cette méthode permet de retourner toutes les violations en lien avec
     * un Personnel, par le biais de requêtes SQL.
     * ATTENTION : la classe Violation représente les non respects de contraintes légales
     * mais aussi ceux de contraintes de spécialités, donc ici nous renvoyons
     * les lignes de tables NonRespectLegal et NonRespectSpe
     *
     * @param int idPersonnel, l'id du personnel dont on veut les violations
     *
     * @return ArrayList<Violations> toutes les violations
     */
    public ArrayList<Violation> readByIdCreneau(int idCreneau){
        ArrayList<Violation> violations = new ArrayList<>();

        try {
            String requete = "SELECT * FROM nonRespectLegal "
                    + "INNER JOIN BesoinEnPersonnel "
                    + "ON BesoinEnPersonnel.idBesoin = nonRespectLegal.idBesoin "
                    + "WHERE idCreneau = ?";
            stmt = connect.prepareStatement(requete);
            stmt.setInt(1, idCreneau);
            rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("lol");
                violations.add(
                        new Violation(
                                (new ContrainteLegaleDAO()).read(rs.getInt("idContrainteLegale")),
                                (new PersonnelDAO()).readForViolation(rs.getInt("idPersonnel")),
                                (new BesoinEnPersonnelDAO()).read(rs.getInt("idBesoin"))
                        )
                );
            }

            requete = "SELECT * FROM nonRespectSpe "
                    + "INNER JOIN BesoinEnPersonnel "
                    + "ON BesoinEnPersonnel.idBesoin = nonRespectSpe.idBesoin "
                    + "WHERE idCreneau = ?";
            stmt = connect.prepareStatement(requete);
            stmt.setInt(1, idCreneau);
            rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("lol");
                violations.add(
                        new Violation(
                                (new ContrainteDeSpecialiteDAO()).read(rs.getInt("idContrainteSpe")),
                                (new PersonnelDAO()).readForViolation(rs.getInt("idPersonnel")),
                                (new BesoinEnPersonnelDAO()).read(rs.getInt("idBesoin"))
                        )
                );
            }
            rs.close();
            System.out.println("Lecture réussie");

        } catch (SQLException e) {
            System.err.println("Erreur requête SQL");
            e.printStackTrace();
        }
        System.out.println("liste violations dao "+violations.toString());
        return violations;
    }

    public String getPersonnelProblematique(Violation violation) {
        String nomPrenom = "";
        try {
            String requete = "SELECT nomPersonnel, prenomPersonnel FROM Personnel "
                    + "WHERE idPersonnel = ?";
            stmt = connect.prepareStatement(requete);
            stmt.setInt(1, violation.getPersonnel().getIdPersonnel());
            rs = stmt.executeQuery();

            if(rs.next()) {
                nomPrenom = rs.getString("nomPersonnel")
                        + " " + rs.getString("prenomPersonnel");
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return nomPrenom;
    }
}
