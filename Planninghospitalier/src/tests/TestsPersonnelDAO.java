package tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dao.PersonnelDAO;
import metier.Fonction;
import metier.Personnel;

/**
 * @author 22203517 - Forest Jules
 *
 * Classe de test de PersonnelDAO.
 * Toutes les méthodes ne sont pas testées, certaines sont trop
 * compliqués à tester, notamment celle renvoyant un ArrayList
 *
 * Nous nous pencherons alors sur les méthodes de base d'un DAO
 * et quelques méthodes simples à tester
 */
class TestsPersonnelDAO {

    PersonnelDAO dao = new PersonnelDAO();

    public boolean testCreate1() {
        Fonction fonction = new Fonction(0, "fonctionTest");
        Personnel personnel1 = new Personnel(0, "Pascal", "Lupz", "2001-12-21", 35, fonction);
        Personnel personnel_copy = dao.create(personnel1);
        return dao.read(personnel_copy.getIdPersonnel()).equals(personnel_copy);
    }

    public boolean testCreate2() {
        Personnel personnel = dao.create(null);
        return (personnel == null);
    }

    public boolean testUpdate1() {
        Fonction fonction = new Fonction(0, "fonctionTest");
        Personnel personnel = new Personnel(0, "Pascal", "Lupz", "2001-12-21", 35, fonction);
        dao.create(personnel);

        Personnel personnel1 = new Personnel(personnel.getIdPersonnel(),
                "Jean", "Lafite", "2001-12-21", 25, fonction);
        Personnel personnel2 = dao.update(personnel1);
        Personnel personnel3 = dao.read(personnel2.getIdPersonnel());

        return (personnel3.getNomPersonnel().equals("Jean")
                && personnel3.getPrenomPersonnel().equals("Lafite")
                && personnel3.getDateNaissance().equals("2001-12-21")
                && personnel3.getTempsTravailMensuel()==25);
    }

    public boolean testUpdate2() {
        Personnel personnel2 = dao.update(null);
        return (personnel2 == null);
    }

    public boolean testDelete1() {
        Fonction fonction = new Fonction(0, "fonctionTest");
        Personnel personnel1 = new Personnel(0, "Pascal", "Lupz", "2001-12-21", 35, fonction);
        Personnel personnelCopy = dao.create(personnel1);

        dao.delete(personnelCopy);
        Personnel personnelEnBase = dao.read(personnelCopy.getIdPersonnel());

        return (personnelEnBase == null);
    }

    public boolean testRead1() {
        Fonction fonction = new Fonction(0, "fonctionTest");
        Personnel personnel1 = new Personnel(0, "Dujardin", "Jean", "2001-12-21", 35, fonction);
        Personnel personnelCopy = dao.create(personnel1);
        Personnel personnel = dao.read(personnelCopy.getIdPersonnel());

        return personnel.getIdPersonnel()==personnelCopy.getIdPersonnel();
    }

    public boolean testReadForViolation1() {
        Fonction fonction = new Fonction(0, "fonctionTest");
        Personnel personnel1 = new Personnel(0, "Dujardin", "Jean", "2001-12-21", 35, fonction);
        Personnel personnelCopy = dao.create(personnel1);
        Personnel personnel = dao.read(personnelCopy.getIdPersonnel());

        return personnel.getIdPersonnel()==personnelCopy.getIdPersonnel()
                && personnel.getViolations().isEmpty();
    }

    @Test
    void testCreate() {
        assertTrue(testCreate1());
        assertTrue(testCreate2());
    }

    @Test
    void testUpdate() {
        assertTrue(testUpdate1());
        assertTrue(testUpdate2());
    }

    @Test
    void testDelete() {
        assertTrue(testDelete1());
    }

    @Test
    void testRead() {
        assertTrue(testRead1());
    }

    @Test
    void testReadForViolation() {
        assertTrue(testReadForViolation1());
    }
}
