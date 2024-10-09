package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import metier.Fonction;
// test pour push
/**
 * @author 22101222 - code & documentation & checkstyle
 * @author 22203517 Forest Jules - relecture et debug
 */
public class FonctionDAO extends DAO<Fonction> {
    /**
     * ResultSet utilisé pour stocker les résultats des requétes SQL.
     */
     private ResultSet rs;
    /**
     * Crée une nouvelle fonction dans la base de données.
     *
     * @param fonction La fonction à créer.
     * @return La fonction créée avec son identifiant.
     */
    @Override
    public Fonction create(final Fonction fonction) {
        String query = "INSERT INTO Fonction (nomFonction) VALUES(?)";
        try {
            stmt = connect.prepareStatement(
                    query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, fonction.getNomFonction());
            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                fonction.setIdFonction(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fonction;
     }

    /**
     * Met à jour les données d'une fonction dans la base de données.
     *
     * @param fonction La fonction à mettre à jour avec les nouvelles données.
     * @return La fonction mise à jour.
     */
    @Override
    public Fonction update(final Fonction fonction) {
        String query = "UPDATE Fonction SET nomFonction = ? "
                + "WHERE idFonction = ?";
            try {
                stmt = connect.prepareStatement(query);
                stmt.setString(1, fonction.getNomFonction());
                stmt.setInt(2, fonction.getIdFonction());
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return fonction;
    }

    /**
     * Supprime une fonction de la base de données.
     *
     * @param fonction La fonction à supprimer de la base de données.
     */
    @Override
    public void delete(final Fonction fonction) {
        try {
            // nous n'utilisons pas de read et
            // de delete de PersonnelDAO et BesoinEnPersonnelDAO
            // pour décharger les calculs,
            // donc ici pas de read juste des deletes

            //Suppression des personnels en lien
            String query = "SELECT idPersonnel FROM Personnel "
                    + "WHERE idFonction = ?";
            stmt = connect.prepareStatement(query);
            stmt.setInt(1, fonction.getIdFonction());
            rs = stmt.executeQuery();

            if (rs.next()) {
                // suppression de toutes les dépendances
                String requete = "DELETE FROM estAffecte "
                        + "WHERE idPersonnel = ?";
                String requete2 = "DELETE FROM seSpecialise "
                        + "WHERE idPersonnel = ?";
                String requete3 = "DELETE FROM nonRespectSpe "
                        + "WHERE idPersonnel = ?";
                String requete4 = "DELETE FROM nonRespectLegal "
                        + "WHERE idPersonnel = ?";
                String requete5 = "DELETE FROM Personnel WHERE idPersonnel = ?";

                PreparedStatement deleteAffectation =
                        connect.prepareStatement(requete);
                PreparedStatement deleteSpe =
                        connect.prepareStatement(requete2);
                PreparedStatement deleteRespectSpe =
                        connect.prepareStatement(requete3);
                PreparedStatement deleteRespectLegal =
                        connect.prepareStatement(requete4);
                this.stmt = connect.prepareStatement(requete5);

                // création d'une transaction pour éviter
                // les problèmes d'incohérence
                // et préserver l'intégrité des données
                connect.setAutoCommit(false);

                deleteAffectation.setInt(1, rs.getInt("idPersonnel"));
                deleteAffectation.executeUpdate();

                deleteSpe.setInt(1, rs.getInt("idPersonnel"));
                deleteSpe.executeUpdate();

                deleteRespectSpe.setInt(1, rs.getInt("idPersonnel"));
                deleteRespectSpe.executeUpdate();

                deleteRespectLegal.setInt(1, rs.getInt("idPersonnel"));
                deleteRespectLegal.executeUpdate();

                stmt.setInt(1, rs.getInt("idPersonnel"));
                stmt.executeUpdate();

                connect.commit();
                connect.setAutoCommit(true);
            }

            query = "SELECT idBesoin FROM BesoinEnPersonnel "
                    + "WHERE idFonction = ?";
            stmt = connect.prepareStatement(query);
            stmt.setInt(1, fonction.getIdFonction());
            rs = stmt.executeQuery();

            if (rs.next()) {
                // suppression des liens dans estAffecte
                String requete = "DELETE FROM estAffecte WHERE idBesoin = ?";
                stmt = connect.prepareStatement(requete);
                stmt.setInt(1, rs.getInt("idBesoin"));
                stmt.executeUpdate();

                // suppression des liens dans nonRespectLegal
                requete = "DELETE FROM nonRespectLegal WHERE idBesoin = ?";
                stmt = connect.prepareStatement(requete);
                stmt.setInt(1, rs.getInt("idBesoin"));
                stmt.executeUpdate();

                // suppression des liens dans nonRespectSpe
                requete = "DELETE FROM nonRespectSpe WHERE idBesoin = ?";
                stmt = connect.prepareStatement(requete);
                stmt.setInt(1, rs.getInt("idBesoin"));
                stmt.executeUpdate();

                // suppression du besoin en personnel en fonction de son ID
                requete = "DELETE FROM besoinenpersonnel WHERE idBesoin = ?";
                stmt = connect.prepareStatement(requete);
                stmt.setInt(1, rs.getInt("idBesoin"));
                stmt.executeUpdate();
            }

            query = "DELETE FROM Fonction WHERE idFonction = ?";
            stmt = connect.prepareStatement(query);
            stmt.setInt(1, fonction.getIdFonction());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            if (connect != null) {
                try {
                    System.err.print("Transaction est rejouée");
                    connect.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
             }
         }
     }

    /**
     *Récupère une fonction à partir de son identifiant dans la base de données.
     *
     * @param idFonction L'identifiant de la fonction à récupérer.
     * @return La fonction correspondant à l'identifiant spécifié,
     * ou null si non trouvée.
     */
     public Fonction read(final int idFonction) {
        Fonction fonction = null;
        String query = "SELECT * FROM Fonction WHERE idFonction = ?";
        try {
            stmt = connect.prepareStatement(query);
            stmt.setInt(1, idFonction);
            rs = stmt.executeQuery();

            if (rs.next()) {
                fonction = new Fonction(
                        rs.getInt("idFonction"), rs.getString("nomFonction"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fonction;
    }

    /**
     * Récupère toutes les fonctions présentes dans la base de données.
     *
     * @return Une liste contenant
     * toutes les fonctions présentes dans la base de données.
     */
    public ArrayList<Fonction> readAll() {
        ArrayList<Fonction> fonctions = new ArrayList<>();
        String query = "SELECT * FROM Fonction";
        try {
            stmt = connect.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Fonction fonction = new Fonction(
                        rs.getInt("idFonction"), rs.getString("nomFonction"));
                fonctions.add(fonction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fonctions;
    }
}
