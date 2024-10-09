/**
 * @author 22106979 - Documentation
 * @author 22208030 - Implémentation
 * @author 22203517 Forest Jules - relecture et checkstyle
 */
package dao;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import metier.Specialite;

/**
 * Cette classe SpecialiteDAO est enrichie par la classe DAO<T>.
 */
public class SpecialiteDAO extends DAO<Specialite> {

    /**
     * valeur du résultat des requêtes SQL
     */
    private ResultSet rs;

    /**
     * Cette méthode permet d'insérer de nouvelles données de type Specialite dans
     * la base de données, par le biais de requêtes SQL.
     *
     * @param specialite [Specialite] valeur d'une specialite à ajouter dans la base
     *                   de données.
     * @return [Specialite] Valeur de la spécialité implémentée dans la base de
     *         données
     *
     * @throws SQLException S'il y a une erreur lors de l'execution de la requête
     *                      SQL
     */
    @Override
    public Specialite create(Specialite specialite) {
        try {
            String requete = "INSERT INTO Specialite (nomSpe) VALUES (?)";
            stmt = connect.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, specialite.getNomSpecialite());
            stmt.executeUpdate();

            ResultSet cles = stmt.getGeneratedKeys();
            if (cles.next()) {
                int id = ((BigInteger) cles.getObject(1)).intValue();
                specialite.setIdSpecialite(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return specialite;

    }

    /**
     * Cette méthode permet de mettre à jour des données de type Specialite dans la
     * base de données, par le biais de requêtes SQL.
     *
     * @param specialite [Specialite] valeur d'une specialite à modifier dans la
     *                   base de données.
     * @return [Specialite] Valeur de la spécialité implémentée dans la base de
     *         données
     *
     * @throws SQLException S'il y a une erreur lors de l'execution de la requête
     *                      SQL
     */
    @Override
    public Specialite update(Specialite specialite) {
        try {
            String requete = "UPDATE Specialite SET nomSpe = ? WHERE idSpe = ?";
            stmt = connect.prepareStatement(requete);
            stmt.setString(1, specialite.getNomSpecialite());
            stmt.setInt(2, specialite.getIdSpecialite());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return specialite;
    }

    /**
     * Cette méthode permet de supprimer des données de type Specialite dans la base
     * de données, par le biais de requêtes SQL.
     *
     * @param specialite[Specialite] valeur d'une specialite à supprimer dans la
     *                               base de données.
     *
     * @throws SQLException S'il y a une erreur lors de l'execution de la requête
     *                      SQL
     */
    @Override
    public void delete(Specialite specialite) {
        try {
            String requete = "DELETE FROM seSpecialise WHERE idSpe = ?";
            stmt = connect.prepareStatement(requete);
            stmt.setInt(1, specialite.getIdSpecialite());
            stmt.executeUpdate();

            requete = "UPDATE BesoinEnPersonnel SET idSpe = null WHERE idSpe = ?";
            stmt = connect.prepareStatement(requete);
            stmt.setInt(1, specialite.getIdSpecialite());
            stmt.executeUpdate();

            requete = "DELETE FROM nonRespectSpe "
                    + "WHERE idContrainteSpe IN "
                    + "(SELECT idContrainteSpe FROM ContrainteDeSpe WHERE idSpe = ?); ";
            stmt = connect.prepareStatement(requete);
            stmt.setInt(1, specialite.getIdSpecialite());
            stmt.executeUpdate();

            requete = "DELETE FROM ContrainteDeSpe WHERE idSpe = ?";
            stmt = connect.prepareStatement(requete);
            stmt.setInt(1, specialite.getIdSpecialite());
            stmt.executeUpdate();

            requete = "DELETE FROM Specialite WHERE idSpe = ?";
            stmt = connect.prepareStatement(requete);
            stmt.setInt(1, specialite.getIdSpecialite());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cette méthode permet de retourner les données liés à un identifiant de
     * specialité dans la base de données, par le biais de requêtes SQL.
     *
     * @param idSpecialite [int] valeur qui est associée à une specialite dans la
     *                     base de données.
     * @return Specialite, la specialite en bdd dont l'id est en paramètre
     */
    public Specialite read(int idSpecialite) {
        Specialite specialite = null;
        try {
            String requete = "SELECT * FROM Specialite WHERE idSpe = ?";
            stmt = connect.prepareStatement(requete);
            stmt.setInt(1, idSpecialite);
            rs = stmt.executeQuery();

            if (rs.next()) {
                specialite = new Specialite(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return specialite;
    }

    /**
     * Cette méthode permet de retourner toutes les données de la table specialité
     * en base de données, par le biais de requêtes SQL.
     *
     * @return ArrayList<Specialite>, toutes les specialites en bdd
     */
    public ArrayList<Specialite> readAll() {
        Specialite spe = null;
        String requete = "SELECT * FROM Specialite ORDER BY nomSpe ASC";
        ArrayList<Specialite> lesSpecialites = new ArrayList<>();

        try {
            stmt = connect.prepareStatement(requete);
            rs = stmt.executeQuery();

            while (rs.next()) {
                spe = new Specialite(rs.getInt(1), rs.getString(2));
                lesSpecialites.add(spe);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lesSpecialites;
    }

}
