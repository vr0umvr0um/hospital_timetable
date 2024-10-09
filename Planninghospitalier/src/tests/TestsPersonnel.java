package tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import metier.Personnel;
import metier.Specialite;
import metier.Violation;

class TestsPersonnel {

    /*
     * test si la fonction equals renvoie false lors de la comparaison entre 2 Personnel
     * avec des id différents
     *
     * @return boolean
     */
    private boolean testEquals1() {
        Personnel personnel1 = new Personnel(2, "Melina", "Faur", "08-07-2003", 27, null);
        Personnel personnel2 = new Personnel(3, "Pascal", "Lupz", "21-12-2001", 35, null);
        return personnel1.equals(personnel2);
    }

    /*
     * test si la fonction equals renvoie true lors de la comparaison entre 2 Personnel
     * avec des id qui sont le même
     *
     * @return boolean
     */
    private boolean testEquals2() {
        Personnel personnel1 = new Personnel(2, "Melina", "Faur", "08-07-2003", 27, null);
        Personnel personnel2 = new Personnel(2, "Pascal", "Lupz", "21-12-2001", 35, null);
        return personnel1.equals(personnel2);
    }

    /*
     * test si la fonction equals renvoie true lors de la comparaison entre un personnel et lui même
     *
     * @return boolean
     */
    private boolean testEquals3() {
        Personnel personnel1 = new Personnel(2, "Melina", "Faur", "08-07-2003", 27, null);
        return personnel1.equals(personnel1);
    }

    /*
     * test si la fonction equals renvoie true lors de la comparaison d'un Personnel avec null
     *
     * @return boolean
     */
    private boolean testEquals4() {
        Personnel personnel1 = new Personnel(2, "Melina", "Faur", "08-07-2003", 27, null);
        return personnel1.equals(null);
    }


    /**
     * test si la fonction addViolation rajoute bien une violation au tableau des
     * violations du personnel
     * @return boolean
     */
    private boolean testAddViolation1() {
        Violation violation = new Violation(null, null, null);
        Personnel personnel = new Personnel(1, "Jean", "Patry", "24-06-1997", 35, null);

        personnel.addViolation(violation);

        return(personnel.getViolations().contains(violation));
    }

    private boolean testSuppViolation1() {
        Violation violation = new Violation(null, null, null);
        ArrayList<Violation> violations = new ArrayList<>();
        violations.add(violation);
        Personnel personnel = new Personnel(1, "Jean", "Patry", "24-06-1997", 35, null, null, violations);

        personnel.suppViolation(violation);

        return(personnel.getViolations().contains(violation));
    }

    /**
     * test si la fonction addSpecialite rajoute bien une specialite au tableau des
     * specialites du personnel
     * @return boolean
     */
    private boolean testAddSpecialite1() {
        Specialite spe = new Specialite(1, "cardiologie");
        Personnel personnel = new Personnel(1, "Jean", "Patry", "24-06-1997", 35, null);

        personnel.addSpecialite(spe);

        return(personnel.getSpecialites().contains(spe));
    }

    /**
     * test si la fonction suppSpecialite supprime bien une specialite au tableau des
     * specialites du personnel
     * @return boolean
     */
    private boolean testSuppSpecialite1() {
        Specialite spe = new Specialite(1, "cardiologie");
        ArrayList<Specialite> specialites = new ArrayList<>();
        specialites.add(spe);
        Personnel personnel = new Personnel(1, "Jean", "Patry", "24-06-1997", 35, null, specialites, null);

        personnel.suppSpecialite(spe);

        return(personnel.getSpecialites().contains(spe));
    }
    
    /**
     * test si la fonction toString renvoie bien un String composé
     * du nom du personnel et du prénom du personnel
     * @return boolean
     */
    private boolean testToString1() {
        Personnel personnel = new Personnel(2, "Melina", "Faur", "08-07-2003", 27, null);
        return personnel.toString().equals("Melina Faur");
    }
    
    /**
     * test si la fonction toString renvoie bien un String vide
     * si le nom et prenom sont null
     * @return boolean
     */
    private boolean testToString2() {
        Personnel personnel = new Personnel(2, null, null, "08-07-2003", 27, null);
        return personnel.toString().equals("");
    }
    
    /**
     * test si le constructeur instancie bien un personnel
     * sans specialites ni violations
     * @return boolean
     */
    private boolean testConstructeur1() {
        Personnel personnel = new Personnel(2, "Escobar", "Pablo", "08-07-2003", 27, null);
        return personnel.getNomPersonnel().equals("Escobar")
                && personnel.getPrenomPersonnel().equals("Pablo")
                && personnel.getDateNaissance().equals("08-07-2003")
                && personnel.getTempsTravailMensuel() == 27
                && personnel.getFonction() == null
                && personnel.getSpecialites().isEmpty()
                && personnel.getViolations().isEmpty();
    }
    
    /**
     * test si le constructeur instancie bien un personnel
     * avec des specialites et violations
     * @return boolean
     */
    private boolean testConstructeur2() {
        Personnel personnel = new Personnel(2, "Escobar", "Pablo", "08-07-2003", 27,
                null, new ArrayList<>(), new ArrayList<>());
        return personnel.getNomPersonnel().equals("Escobar")
                && personnel.getPrenomPersonnel().equals("Pablo")
                && personnel.getDateNaissance().equals("08-07-2003")
                && personnel.getTempsTravailMensuel() == 27
                && personnel.getFonction() == null
                && personnel.getSpecialites().isEmpty()
                && personnel.getViolations().isEmpty();
    }

    @Test
    void testEqualsAll() {
        assertFalse(testEquals1());
        assertTrue(testEquals2());
        assertTrue(testEquals3());
        assertFalse(testEquals4());
    }

    @Test
    void testAddViolationAll() {
       assertTrue(testAddViolation1());
    }

    @Test
    void testSupViolation() {
        assertFalse(testSuppViolation1());
    }

    @Test
    void testAddSpecialite() {
        assertTrue(testAddSpecialite1());
    }

    @Test
    void testSuppSpecialite() {
        assertFalse(testSuppSpecialite1());
    }
    
    @Test
    void testToString() {
        assertTrue(testToString1());
        assertFalse(testToString2());
    }
    
    @Test
    void testConstructeurs() {
        assertTrue(testConstructeur1());
        assertTrue(testConstructeur2());
    }
}
