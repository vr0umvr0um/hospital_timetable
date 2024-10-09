/**
 * @author 22106979 - Documentation
 */

package vues;

import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JFrame;

/**
 * Cette classe contient toutes les informations relations au popUp de
 * validation de suppression d'un créneau/personnel/contrainte.
 */
public class PopUpValidationSuppression extends JFrame {

	// valeur représentant le message à afficher
	public String message;

	/**
	 * Constructeur par défaut qui permet d'implémenter le popUp de validation de
	 * suppression d'un créneau/personnel/contrainte.
	 */
	private PopUpValidationSuppression() {
		// à implémenter
	}

	/**
	 * Cette méthode permet lorsque l'on clique sur le button de valider une
	 * opération.
	 *
	 * @param ctrl [ActionListener] valeur de l'action de cliquer sur un button de
	 *             validation
	 */
	public void addBtnValidationCtrl(ActionListener ctrl) {
		// à implémenter
	}

	/**
	 * Cette méthode permet lorsque l'on clique sur le button d'annuler une
	 * opération.
	 *
	 * @param ctrl ActionListener
	 */
	public void addBtnAnnulationCtrl(ActionListener ctrl) {
		// à implémenter
	}

	/**
	 * Cette méthode permet de mettre à jour le popUp de validation ou de
	 * suppression par le biais d'une action effectuée.
	 *
	 * @param o   [Observable] valeur de l'observateur de l'action à effectué pour
	 *            faire une action
	 * @param arg [Object] valeur de remplacement pour la mise à joru
	 */
	public void update(
			@SuppressWarnings("deprecation") Observable o,
			Object arg
			) {
		// à implémenter
	}

	/**
	 * Cette méthode permet de mettre à jour le message à afficher dans le popUp.
	 *
	 * @param message [String] valeur de remplacement
	 */
	public void setMessage(String message) {
		// à implémenter
	}

}