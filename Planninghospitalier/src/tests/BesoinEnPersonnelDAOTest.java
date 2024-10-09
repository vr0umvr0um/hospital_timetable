package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dao.BesoinEnPersonnelDAO;
import metier.BesoinEnPersonnel;
import metier.Creneau;
import metier.Fonction;
import metier.Specialite;
import static org.junit.Assert.*;
import org.junit.Before;
import java.util.List;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Before;

public class BesoinEnPersonnelDAOTest {

	private BesoinEnPersonnelDAO dao;

	private Creneau creneau;

	private Specialite specialite;

	@Before
	public void setUp() {
		dao = new BesoinEnPersonnelDAO();

		int idCreneau = 1; // Valeur fictive pour le test
		String dateCreneau = "2023-12-12"; // Valeur fictive pour le test
		String horaireDebut = "08:00:00"; // Valeur fictive pour le test
		String horaireFin = "17:00:00"; // Valeur fictive pour le test
		Creneau creneau = new Creneau(idCreneau, dateCreneau, horaireDebut, horaireFin);

		int idSpecialite = 1; // Valeur fictive pour le test
		String nomSpecialite = "Cardiologie"; // Valeur fictive pour le test
		Specialite specialite = new Specialite(idSpecialite, nomSpecialite);

		int idFonction = 1; // Valeur fictive pour le test
		String nomFonction = "Médecin"; // Valeur fictive pour le test
		Fonction fonction = new Fonction(idFonction, nomFonction);

		// Création de BesoinEnPersonnel avec des instances de Creneau, Specialite, et
		// Fonction
		BesoinEnPersonnel besoin = new BesoinEnPersonnel(0, 5, creneau, specialite, fonction);

	}

	@Test
	public void testCreateBesoinEnPersonnel() {

		int idCreneau = 1; // Valeur fictive pour le test
		String dateCreneau = "2023-12-12"; // Valeur fictive pour le test
		String horaireDebut = "08:00:00"; // Valeur fictive pour le test
		String horaireFin = "17:00:00"; // Valeur fictive pour le test
		Creneau creneau = new Creneau(idCreneau, dateCreneau, horaireDebut, horaireFin);

		int idSpecialite = 1; // Valeur fictive pour le test
		String nomSpecialite = "Cardiologie"; // Valeur fictive pour le test
		Specialite specialite = new Specialite(idSpecialite, nomSpecialite);

		BesoinEnPersonnel besoin = new BesoinEnPersonnel(0, 5, creneau, specialite, new Fonction());
		BesoinEnPersonnel created = dao.create(besoin);

		assertNotNull(created);
		assertTrue(created.getIdBesoin() > 0);
	}

	@Test
	public void testReadBesoinEnPersonnel() {
		int idBesoin = 1;
		BesoinEnPersonnel read = dao.read(idBesoin);

		assertNotNull(read);
		assertEquals(idBesoin, read.getIdBesoin());
	}

	@Test
	public void testReadAllBesoinEnPersonnel() {
		List<BesoinEnPersonnel> besoins = dao.readAll();

		assertNotNull(besoins);
		assertFalse(besoins.isEmpty());
	}

	@Test
	public void testReadParCreneau() {
		int idCreneau = 1;
		List<BesoinEnPersonnel> besoins = dao.readParCreneau(idCreneau);

		assertNotNull(besoins);
		for (BesoinEnPersonnel besoin : besoins) {
			assertEquals(idCreneau, besoin.getCreneau().getIdC());
		}
	}

	@Test
	public void testUpdateBesoinEnPersonnel() {
		BesoinEnPersonnel besoin = dao.read(1);
		assertNotNull(besoin);

		int newNbPersonnes = 10;
		besoin.setNbPersonnes(newNbPersonnes);
		BesoinEnPersonnel updated = dao.update(besoin);

		assertNotNull(updated);
		assertEquals(newNbPersonnes, updated.getNbPersonnes());
	}

	@Test
	public void testDeleteBesoinEnPersonnel() {

		int idCreneau = 1; // Valeur fictive pour le test
		String dateCreneau = "2023-12-12"; // Valeur fictive pour le test
		String horaireDebut = "08:00:00"; // Valeur fictive pour le test
		String horaireFin = "17:00:00"; // Valeur fictive pour le test

		int idSpecialite = 1; // Valeur fictive pour le test
		String nomSpecialite = "Cardiologie"; // Valeur fictive pour le test
		Specialite specialite = new Specialite(idSpecialite, nomSpecialite);

		// Création d'un nouveau besoin pour la suppression
		BesoinEnPersonnel besoin = new BesoinEnPersonnel(0, 5,
				new Creneau(idCreneau, dateCreneau, horaireDebut, horaireFin),
				new Specialite(idSpecialite, nomSpecialite), new Fonction());
		besoin = dao.create(besoin);
		assertNotNull(besoin);

		dao.delete(besoin);
		BesoinEnPersonnel deleted = dao.read(besoin.getIdBesoin());
		assertNull(deleted);
	}
}