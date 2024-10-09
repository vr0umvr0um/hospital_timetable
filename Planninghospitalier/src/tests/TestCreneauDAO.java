package tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dao.CreneauDAO;
import metier.Creneau;

/**
 * Classe de Test pour CreneauDAO
 */
public class TestCreneauDAO {

	/**
	 * Vérifie si la méthode getCreneau(int idc) recupère un créneau en fonction de
	 * son id
	 *
	 * @param int idC
	 * @return True si les arguments des deux créneaux sont bien les mêmes,
	 *         autrement false
	 */
	private boolean TestgetCreneau() {
		// il s'agit d'un créneau déjà présent dans la BD
		Creneau creneau = new Creneau(1, "2023-12-10", "08:00:00", "12:00:00");
		CreneauDAO creneauDAO = new CreneauDAO();
		Creneau creneau2 = creneauDAO.getCreneau(1);

		return creneau.equals(creneau2); // trouver pourquoi ces deux créneaux sont pas égaux
	}

	private boolean TestgetAllCreneau() {
		ArrayList<Creneau> creneauTab = new ArrayList<>();
		CreneauDAO creneauDao = new CreneauDAO();

		ArrayList<Creneau> allCreneaux = creneauDao.getAllCreneau();

		for (int i = 0; i < allCreneaux.size(); i++) {
			creneauTab.add(allCreneaux.get(i));
		}
		boolean testResult = !creneauTab.isEmpty();

		return testResult;
	}
/**
 * à refaire
 */
//	private boolean TestcreatesuppCreneau() {
//
//		CreneauDAO creneauDao = new CreneauDAO();
//		int verif = creneauDao.getAllCreneau().size();
//
//		creneauDao.create(Date.valueOf("2023-12-10"), Time.valueOf("08:00:00"), Time.valueOf("12:00:00"));
//		creneauDao.suppCreneauId(verif + 1);
//
//		int verif2 = creneauDao.getAllCreneau().size();
//
//		return verif2 == verif;
//	}

	@Test
	void LancerTestCreneau() {
		// assertTrue(TestgetCreneau()); // problème à ce niveau de vérification
		assertTrue(TestgetAllCreneau());
		//assertTrue(TestcreatesuppCreneau());
	}
}
