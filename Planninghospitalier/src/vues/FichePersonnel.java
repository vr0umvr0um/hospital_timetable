/**
 * @author 22106979 - Documentation
 */
package vues;

import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JFrame;

/**
 * Cette classe contient toutes les informations relatives à la fiche d'un
 * personnel.
 */
public class FichePersonnel extends JFrame {

	/**
	 * Constructeur par défaut qui permet d'implémenter la fiche d'un personnel.
	 */
	private FichePersonnel() {
		// à implémenter
	}

	/**
	 * Cette méthode permet lorsque l'on clique sur un boutton de supprimer une
	 * fiche d'un personnel.
	 *
	 * @param ctrl [ActionListener] valeur de ActionListener qui permet d'effectuer
	 *             la méthode
	 */
	public void addBtnDeleteCtrl(ActionListener ctrl) {
		// à implémenter
	}

	/**
	 * Cette méthode permet de mettre à jour une fiche de personnel.
	 *
	 * @param o   [Observable] valeur d'observation qui permet d'effectuer l'action
	 * @param arg [Object] valeur de remplacement
	 */
	public void update(@SuppressWarnings("deprecation") Observable o, Object arg) {
		// à implémenter
	}

}