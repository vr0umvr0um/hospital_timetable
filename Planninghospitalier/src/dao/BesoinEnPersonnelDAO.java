package dao;
/**
 * @author 22206008 - Meriem bennama Documentation + code
 */
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import metier.BesoinEnPersonnel;
import metier.Creneau;
import metier.Fonction;
import metier.Personnel;
import metier.Specialite;

public class BesoinEnPersonnelDAO extends DAO<BesoinEnPersonnel> {

	// Constructeur par défaut qui établit la connexion à la base de données
	public BesoinEnPersonnelDAO() {
		super();

	}

	/**
	 * Crée un besoin en personnel dans la base de données.
	 *
	 * @param besoin Le besoin en personnel à créer.
	 * @return Le besoin en personnel créé avec l'ID généré, ou null en cas
	 *         d'erreur.
	 */
	@Override
	public BesoinEnPersonnel create(BesoinEnPersonnel besoin) {
		try {
			// Requête SQL pour insérer un besoin en personnel
			String requete = "INSERT INTO besoinenpersonnel (nbPersonnes, idSpe, idFonction, idCreneau) VALUES (?, ?, ?, ?)";

			// Préparation de la requête avec récupération de l'ID généré
			PreparedStatement stmt = connect.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);

			// Paramètres de la requête
			stmt.setInt(1, besoin.getNbPersonnes());
			stmt.setObject(2, besoin.getSpecialite() != null ? besoin.getSpecialite().getIdSpecialite() : null);
			stmt.setInt(3, besoin.getFonction().getIdFonction());
			stmt.setInt(4, besoin.getCreneau().getIdC());

			// Exécution de la requête
			int affectedRows = stmt.executeUpdate();

			// Vérification si des lignes ont été affectées
			if (affectedRows == 0) {
				throw new SQLException("La création du besoin a échoué, aucune ligne affectée.");
			}

			// Récupération de l'ID généré
			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					besoin.setIdBesoin(generatedKeys.getInt(1));
				} else {
					throw new SQLException("La création du besoin a échoué, aucun ID obtenu.");
				}
			}

			// Retourne le besoin en personnel créé avec l'ID généré
			return besoin;
		} catch (SQLException e) {
			// Gestion des exceptions SQL et enregistrement dans le journal
			Logger.getLogger(BesoinEnPersonnelDAO.class.getName()).log(Level.SEVERE,
					"Erreur lors de la création d'un besoin en personnel", e);
		}

		// En cas d'erreur, retourne null
		return null;
	}

	/**
	 * Lit un besoin en personnel à partir de la base de données en utilisant son
	 * ID.
	 *
	 * @param idBesoin L'ID du besoin en personnel à lire.
	 * @return Le besoin en personnel lu depuis la base de données, ou null en cas
	 *         d'erreur.
	 */
	public BesoinEnPersonnel read(int idBesoin) {
		BesoinEnPersonnel besoin = null;
		try {
			// Requête SQL pour sélectionner un besoin en personnel par son ID
			String requete = "SELECT * FROM besoinenpersonnel WHERE idBesoin = ?";
			stmt = connect.prepareStatement(requete);

			// Paramètre de la requête
			stmt.setInt(1, idBesoin);

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				CreneauDAO creneauDAO = new CreneauDAO();
				SpecialiteDAO specialiteDAO = new SpecialiteDAO();
				FonctionDAO fonctionDAO = new FonctionDAO();

				// Utilisation des méthodes read pour récupérer les objets associés
				Creneau creneau = creneauDAO.getCreneau(rs.getInt("idCreneau"));
				Specialite specialite = specialiteDAO.read(rs.getInt("idSpe"));
				Fonction fonction = fonctionDAO.read(rs.getInt("idFonction"));

				// Création d'un objet BesoinEnPersonnel à partir des données de la base de
				// données
				besoin = new BesoinEnPersonnel(rs.getInt("idBesoin"), rs.getInt("nbPersonnes"), creneau, specialite,
						fonction);
			}
		} catch (SQLException e) {
			// Gestion des exceptions SQL et enregistrement dans le journal
			Logger.getLogger(BesoinEnPersonnelDAO.class.getName()).log(Level.SEVERE,
					"Erreur lors de la lecture d'un besoin en personnel", e);
		}

		// Retourne le besoin en personnel lu depuis la base de données, ou null en cas
		// d'erreur
		return besoin;
	}

	/**
	 * Lit tous les besoins en personnel à partir de la base de données.
	 *
	 * @return Une liste contenant tous les besoins en personnel lus depuis la base
	 *         de données.
	 */
	public List<BesoinEnPersonnel> readAll() {
		List<BesoinEnPersonnel> besoins = new ArrayList<>();
		try {
			// Requête SQL pour sélectionner tous les besoins en personnel
			String requete = "SELECT * FROM besoinenpersonnel";
			stmt = connect.prepareStatement(requete);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				// Ajoute le besoin en personnel lu à la liste
				besoins.add(this.read(rs.getInt("idBesoin")));
			}
		} catch (SQLException e) {
			// Gestion des exceptions SQL et enregistrement dans le journal
			Logger.getLogger(BesoinEnPersonnelDAO.class.getName()).log(Level.SEVERE,
					"Erreur lors de la lecture des besoins en personnel", e);
		}

		// Retourne la liste de tous les besoins en personnel lus depuis la base de
		// données
		return besoins;
	}

	/**
	 * Affiche tous les besoins en personnel d'un créneau spécifié.
	 *
	 * @param idCreneau L'identifiant du créneau pour lequel les besoins en
	 *                  personnel doivent être affichés.
	 * @return Une liste contenant tous les besoins en personnel associés au créneau
	 *         spécifié.
	 */
	public List<BesoinEnPersonnel> readParCreneau(int idCreneau) {
		List<BesoinEnPersonnel> besoins = new ArrayList<>();
		try {
			// Requête SQL pour sélectionner tous les besoins en personnel du créneau
			// spécifié
			String requete = "SELECT * FROM besoinenpersonnel WHERE idCreneau = ?";
			stmt = connect.prepareStatement(requete);
			stmt.setInt(1, idCreneau);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				// Ajoute le besoin en personnel lu à la liste
				besoins.add(this.read(rs.getInt("idBesoin")));
			}
		} catch (SQLException e) {
			// Gestion des exceptions SQL et enregistrement dans le journal
			Logger.getLogger(BesoinEnPersonnelDAO.class.getName()).log(Level.SEVERE,
					"Erreur lors de la lecture des besoins en personnel par créneau", e);
			e.printStackTrace();
		}

		// Retourne la liste de tous les besoins en personnel associés au créneau
		// spécifié
		return besoins;
	}

	/**
	 * Met à jour les informations d'un besoin en personnel dans la base de données.
	 *
	 * @param besoin L'objet BesoinEnPersonnel contenant les informations mises à
	 *               jour.
	 * @return L'objet BesoinEnPersonnel mis à jour.
	 */
	@Override
	public BesoinEnPersonnel update(BesoinEnPersonnel besoin) {
	    try {
	        String requete = "UPDATE besoinenpersonnel SET nbPersonnes = ?, idSpe = ?, idFonction = ?, idCreneau = ? WHERE idBesoin = ?";
	        stmt = connect.prepareStatement(requete);

	        stmt.setInt(1, besoin.getNbPersonnes());

	        // Gérer idSpe qui peut être null
	        if (besoin.getSpecialite() != null) {
	            stmt.setInt(2, besoin.getSpecialite().getIdSpecialite());
	        } else {
	            stmt.setNull(2, java.sql.Types.INTEGER); // Ici, nous gérons le cas où la spécialité est null
	        }

	        stmt.setInt(3, besoin.getFonction().getIdFonction());
	        stmt.setInt(4, besoin.getCreneau().getIdC());
	        stmt.setInt(5, besoin.getIdBesoin());

	        stmt.executeUpdate();
	        return besoin;
	    } catch (SQLException e) {
	        Logger.getLogger(BesoinEnPersonnelDAO.class.getName()).log(Level.SEVERE, "Erreur lors de la mise à jour d'un besoin en personnel", e);
	    }
	    return null;
	}


	/**
	 * Supprime un besoin en personnel de la base de données.
	 *
	 * @param besoin L'objet BesoinEnPersonnel à supprimer.
	 */
	@Override
    public void delete(BesoinEnPersonnel besoin) {
        try {
            // suppression des liens dans estAffecte
            String requete = "DELETE FROM estAffecte WHERE idBesoin = ?";
            stmt = connect.prepareStatement(requete);
            stmt.setInt(1, besoin.getIdBesoin());
            stmt.executeUpdate();

            // suppression des liens dans nonRespectLegal
            requete = "DELETE FROM nonRespectLegal WHERE idBesoin = ?";
            stmt = connect.prepareStatement(requete);
            stmt.setInt(1, besoin.getIdBesoin());
            stmt.executeUpdate();

            // suppression des liens dans nonRespectSpe
            requete = "DELETE FROM nonRespectSpe WHERE idBesoin = ?";
            stmt = connect.prepareStatement(requete);
            stmt.setInt(1, besoin.getIdBesoin());
            stmt.executeUpdate();

            // Requête SQL pour supprimer le besoin en personnel en fonction de son ID
            requete = "DELETE FROM besoinenpersonnel WHERE idBesoin = ?";
            stmt = connect.prepareStatement(requete);
            stmt.setInt(1, besoin.getIdBesoin());
            stmt.executeUpdate();

            besoin.getCreneau().suppBesoin(besoin);
        } catch (SQLException e) {
            // Gestion des exceptions SQL et enregistrement dans le journal
            Logger.getLogger(BesoinEnPersonnelDAO.class.getName()).log(Level.SEVERE,
                    "Erreur lors de la suppression d'un besoin en personnel", e);
        }
    }

	// manque de temps -> je n'ai pas pu l'utiliser dans la partie FX
	// passer true pour incrementer et false pour l'inverse depuis les boutons + et
	// - dans javafx

	/**
	 * Modifie le nombre d'effectif pour un besoin en personnel.
	 *
	 * @param idBesoin   L'ID du besoin en personnel à modifier.
	 * @param incremente true pour incrémenter le nombre d'effectif, false pour
	 *                   décrémenter.
	 * @return Le besoin en personnel mis à jour ou null en cas d'erreur.
	 */
	public BesoinEnPersonnel changerNombreEffectif(int idBesoin, boolean incremente) {
		// Récupérer le besoin en personnel à partir de son ID
		BesoinEnPersonnel besoinEnPersonnel = this.read(idBesoin);
		if (besoinEnPersonnel == null) {
			// Gérer l'erreur si le besoin n'existe pas
			return null;
		}

		int nbPersonnesActuelles = besoinEnPersonnel.getNbPersonnes();

		if (incremente) {
			// Incrémente le nombre de personnes
			besoinEnPersonnel.setNbPersonnes(nbPersonnesActuelles + 1);
		} else if (nbPersonnesActuelles > 0) {
			// Décrémente le nombre de personnes, mais assurez-vous qu'il ne devienne pas
			// négatif
			besoinEnPersonnel.setNbPersonnes(nbPersonnesActuelles - 1);
		}

		// Mettez à jour le besoin avec le nouveau nombre de personnes
		return this.update(besoinEnPersonnel);
	}

//--------système d'affectation--------

	/**
	 * Liste le personnel disponible pour un besoin en fonction de l'ID de fonction
	 * et éventuellement de l'ID de spécialité.
	 *
	 * @param idFonction   L'ID de la fonction recherchée.
	 * @param idSpecialite L'ID de la spécialité (peut être null si non spécifié).
	 * @return Une liste de personnel disponible pour le besoin.
	 */
	public List<Personnel> listerPersonnelDisponiblePourBesoin(int idFonction, Integer idSpecialite) {
		List<Personnel> personnels = new ArrayList<>();
		// Requête de base pour récupérer le personnel en fonction de l'ID de fonction
		String requete = "SELECT * FROM personnel WHERE idFonction = ?";
		if (idSpecialite != null) {
			// Si l'ID de spécialité est spécifié, ajouter une clause à la requête pour
			// filtrer par spécialité
			requete += " AND idPersonnel IN (SELECT idPersonnel FROM sespecialise WHERE idSpe = ?)";
		}
		try {
			PersonnelDAO personnelDAO = new PersonnelDAO();
			stmt = connect.prepareStatement(requete);
			stmt.setInt(1, idFonction);
			if (idSpecialite != null) {
				stmt.setInt(2, idSpecialite);
			}
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				personnels.add(personnelDAO.read(rs.getInt("idPersonnel")));
			}
		} catch (SQLException e) {
			Logger.getLogger(BesoinEnPersonnelDAO.class.getName()).log(Level.SEVERE, null, e);
			e.printStackTrace();
		}
		return personnels;
	}


	/**
	 * Affecte un personnel à un besoin en personnel.
	 *
	 * @param idPersonnel L'identifiant du personnel à affecter.
	 * @param idBesoin    L'identifiant du besoin en personnel.
	 * @return true si l'affectation a réussi, false sinon.
	 */
	public boolean affecterPersonnelAuBesoin(int idPersonnel, int idBesoin) {
		try {
			String requete = "INSERT INTO estAffecte (idPersonnel, idBesoin) VALUES (?, ?)";
			stmt = connect.prepareStatement(requete);
			stmt.setInt(1, idPersonnel);
			stmt.setInt(2, idBesoin);
			stmt.executeUpdate();
			return true; // Affectation réussie
		} catch (SQLException e) {
			Logger.getLogger(BesoinEnPersonnelDAO.class.getName()).log(Level.SEVERE, null, e);
			e.printStackTrace();
			return false; // Affectation échouée
		}
	}


	/**
	 * Annule l'affectation d'un personnel à un besoin en personnel.
	 *
	 * @param idPersonnel L'identifiant du personnel dont l'affectation doit être
	 *                    annulée.
	 * @param idBesoin    L'identifiant du besoin en personnel auquel le personnel
	 *                    est actuellement affecté.
	 * @return true si l'affectation est annulée avec succès, false sinon.
	 */
	public boolean annulerAffectationPersonnel(int idPersonnel, int idBesoin) {
		try {
			String requete = "DELETE FROM estAffecte WHERE idPersonnel = ? AND idBesoin = ?";
			stmt = connect.prepareStatement(requete);
			stmt.setInt(1, idPersonnel);
			stmt.setInt(2, idBesoin);
			int affectedRows = stmt.executeUpdate();
			return affectedRows > 0; // Retourne true si l'affectation a été annulée
		} catch (SQLException e) {
			Logger.getLogger(BesoinEnPersonnelDAO.class.getName()).log(Level.SEVERE, null, e);
			e.printStackTrace();
			return false; // En cas d'erreur, retourne false
		}
	}

	/**
	 * Obtient le nombre de personnels affectés à un besoin en personnel spécifique.
	 *
	 * @param idBesoin L'identifiant du besoin en personnel pour lequel on souhaite
	 *                 connaître le nombre de personnels affectés.
	 * @return Le nombre de personnels affectés au besoin en personnel spécifié, ou
	 *         0 en cas d'absence de données ou d'erreur.
	 */
	public int getNombrePersonnelParBesoin(int idBesoin) {
		try {
			String requete = "SELECT COUNT(*) AS count FROM estAffecte WHERE idBesoin = ?";
			stmt = connect.prepareStatement(requete);
			stmt.setInt(1, idBesoin);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("count");
			}
		} catch (SQLException e) {
			Logger.getLogger(BesoinEnPersonnelDAO.class.getName()).log(Level.SEVERE, null, e);
			e.printStackTrace();
		}
		return 0; // Retourne 0 si aucune donnée n'est trouvée ou en cas d'erreur
	}

	/**
	 * Vérifie si un besoin en personnel spécifique est en situation de surcharge.
	 *
	 * @param idBesoin L'identifiant du besoin en personnel à vérifier.
	 * @return true si le besoin en personnel est en surcharge, false sinon.
	 */
	public boolean estSurCharge(int idBesoin) {
		BesoinEnPersonnel besoinEnPersonnel = this.read(idBesoin);
		return this.getNombrePersonnelParBesoin(idBesoin) > besoinEnPersonnel.getNbPersonnes() ? true : false;
	}

	/**
	 * Vérifie s'il y aura une sous-charge après l'annulation de l'affectation de
	 * personnel à un besoin. Si une sous-charge est détectée, une exception est
	 * levée pour indiquer que l'annulation n'est pas recommandée sans confirmation.
	 * Si aucune sous-charge n'est détectée ou si la confirmation est obtenue (gérée
	 * ailleurs dans l'interface utilisateur), l'annulation est effectuée.
	 *
	 * @param idPersonnel L'identifiant du personnel dont l'affectation doit être
	 *                    annulée.
	 * @param idBesoin    L'identifiant du besoin en personnel auquel le personnel
	 *                    est actuellement affecté.
	 * @return true si l'annulation est effectuée avec succès, false sinon.
	 * @throws IllegalArgumentException Si une sous-charge est détectée, une
	 * exception est levée pour indiquer que l'annulation n'est pas recommandée sans confirmation.
	 */
	public boolean verifierSouschargeEtProposerAnnulation(int idPersonnel, int idBesoin)
			throws IllegalArgumentException {
		// Vérifie d'abord si l'annulation entraînera une sous-charge
		if (estSousChargeApresAnnulation(idBesoin)) {
			// Si une sous-charge est détectée, lever une exception pour indiquer que
			// l'annulation n'est pas recommandée
			throw new IllegalArgumentException("Sous-charge détectée. Annulation non recommandée sans confirmation.");
		}
		// Si pas de sous-charge ou si confirmation obtenue (gérée ailleurs dans l'UI),
		// procéder à l'annulation
		return annulerAffectationPersonnel(idPersonnel, idBesoin);
	}

	/**
	 * Vérifie si un besoin en personnel spécifique serait en situation de
	 * sous-charge après l'annulation d'une affectation.
	 *
	 * @param idBesoin L'identifiant du besoin en personnel à vérifier.
	 * @return true si le besoin serait en sous-charge après l'annulation, false
	 *         sinon.
	 */
	public boolean estSousChargeApresAnnulation(int idBesoin) {
		BesoinEnPersonnel besoinEnPersonnel = this.read(idBesoin);
		if (besoinEnPersonnel == null) {
			// Retourner une valeur par défaut ou gérer l'erreur si le besoin n'existe pas
			return false;
		}
		int nombreActuelAffecte = getNombrePersonnelParBesoin(idBesoin);
		int nombreRequis = besoinEnPersonnel.getNbPersonnes();
		// Vérifie si en enlevant une personne, on passe en dessous du nombre requis
		return (nombreActuelAffecte - 1) < nombreRequis;
	}


	/**
	 * Liste les personnes affectées à un besoin en personnel spécifique.
	 *
	 * @param idBesoin L'identifiant du besoin en personnel pour lequel on souhaite
	 *                 lister les personnes affectées.
	 * @return Une liste de personnes affectées au besoin en personnel spécifié.
	 */
	public List<Personnel> listerPersonnelParBesoin(int idBesoin) {
		List<Personnel> personnels = new ArrayList<>();
		try {
			PersonnelDAO personnelDAO = new PersonnelDAO();
			String requete = "SELECT * FROM estAffecte WHERE idBesoin = ?";
			stmt = connect.prepareStatement(requete);
			stmt.setInt(1, idBesoin);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				personnels.add(personnelDAO.read(rs.getInt("idPersonnel")));
			}
		} catch (SQLException e) {
			Logger.getLogger(BesoinEnPersonnelDAO.class.getName()).log(Level.SEVERE, null, e);
			e.printStackTrace();
		}
		return personnels;
	}

	// on a pas utilidé ce code dans le prijet mais il devait fournir les besoins par date et spé
	public List<BesoinEnPersonnel> readParDateEtSpesialite(java.sql.Date date, int idSpecialite) {
		List<BesoinEnPersonnel> besoins = new ArrayList<>();
		try {
			// Requête SQL
			String requete = "SELECT * FROM besoinenpersonnel b JOIN creneau c ON (b.idCreneau = c.idCreneau) " +
					"WHERE c.date = ? AND p.idSpecialite = ?";
			stmt = connect.prepareStatement(requete);
			stmt.setDate(1, date);
			stmt.setInt(2, idSpecialite);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				// Ajoute le besoin en personnel lu à la liste
				besoins.add(this.read(rs.getInt("idBesoin")));
			}
		} catch (SQLException e) {
			// Gestion des exceptions SQL et enregistrement dans le journal
			Logger.getLogger(BesoinEnPersonnelDAO.class.getName()).log(Level.SEVERE,
					"Erreur lors de la lecture des besoins en personnel par date", e);
		}
		return besoins;
	}

		 /**
	     * Détermine les dates de début et de fin du mois en fonction de la date d'un créneau
	     * et les renvoie sous forme d'objets java.sql.Date pour être utilisés en tant que
	     * paramètres d'entrée dans la méthode checker.
	     *
	     * @param creneau Creneau pour lequel trouver le début et la fin du mois.
	     * @return Un tableau contenant la date de début de mois en première position
	     *         et la date de fin de mois en seconde position, tous deux en tant que java.sql.Date.
	     */

	    public Date[] getStartAndEndOfMonthDates(Creneau creneau) {
	        LocalDate creneauDate = creneau.getDate().toLocalDate();
	        LocalDate startOfMonth = creneauDate.with(TemporalAdjusters.firstDayOfMonth());
	        LocalDate endOfMonth = creneauDate.with(TemporalAdjusters.lastDayOfMonth());

	        // Convertir LocalDate en java.sql.Date pour l'utilisation dans les requêtes SQL
	        Date startOfMonthDate = Date.valueOf(startOfMonth);
	        Date endOfMonthDate = Date.valueOf(endOfMonth);

	        return new Date[]{startOfMonthDate, endOfMonthDate};
	    }

	       /**
	       * Cette méthode retourne les dates de début et de fin de semaine
	       * pour une date donnée.
	       *
	       * @param creneau Creneau dont la date est utilisée pour calculer la semaine
	       * @return un tableau de java.sql.Date avec la date de début de semaine
	       * en première position et la date de fin de semaine en seconde position.
	       */
		    public Date[] getStartAndEndOfWeekDates(Creneau creneau) {
		        Calendar cal = Calendar.getInstance();
		        // Convertir java.sql.Date en java.util.Date
		        cal.setTime(new java.util.Date(creneau.getDate().getTime()));

		        // Trouver le lundi de cette semaine
		        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		        Date startOfWeek = new Date(cal.getTimeInMillis());  // Convertir java.util.Date en java.sql.Date

		        // Trouver le dimanche de cette semaine
		        cal.add(Calendar.DAY_OF_WEEK, 6);  // Ajouter 6 jours pour arriver à dimanche
		        Date endOfWeek = new Date(cal.getTimeInMillis());

		        return new Date[] { startOfWeek, endOfWeek };
		    }
}