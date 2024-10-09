/**
 * @author 22106979 - Documentation
 */

package tests;

import metier.Personnel;

/**
 * <p>
 * Cette classe permet l'affichage de l'emploi du temps global et personnel d'un
 * hopital.
 * </p>
 * <p>
 * A partir de ce dernier, nous pouvons supprimer, ajouter ou modifier des
 * créneaux, ses contraintes mais aussi les informations d'un personnel.
 * </p>
 */
public class Main {

	/**
	 * Constructeur par défaut qui d'afficher l'emploi du temps.
	 */
	public Main() {
	}


	/**
	 * Cette méthode permet d'afficher une fenêtre précisant que le personnel est
	 * affecté à un créneau.
	 */
	public void afficherDemandeValidationAffecte() {
		// à implémenter
	}

	/**
	 * Cette méthode permet d'afficher une fenêtre précisant que le personnel n'est
	 * pas affecté à un créneau.
	 */
	public void afficherDemandeValidationNonAffecte() {
		// à implémenter
	}

	/**
	 * Cette méthode affichage toutes les informations relatives d'un personnel.
	 *
	 * @param personnel [Personnel] valeur d'un personnel de la base de données
	 */
	public void affichageFichePersonnel(Personnel personnel) {
		// à implémenter
	}

	/**
	 * Cette méthode permet d'afficher un pop-up qui confirme la suppression
	 * demandée.
	 */
	public void notifeSuppressionReussie() {
		// à implémenter
	}

}