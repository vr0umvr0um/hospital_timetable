package tests;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import metier.Fonction;

/**
 * Classe de test pour la classe Fonction.
 * @author 22101222 - code & documentation - checkstyle
 */
public class TestFonction {
/**
 *
 */
    private Fonction fonction;
    /**
     * Pr�pare une instance de Fonction pour chaque test.
     */
    @BeforeEach
    public void setUp() {
        fonction = new Fonction(1, "Exemple");
    }

    /**
     * Teste la m�thode getIdFonction.
     */
    @Test
    public void testGetIdFonction() {
        assertEquals(1, fonction.getIdFonction());
    }

    /**
     * Teste la m�thode setIdFonction.
     */
    @Test
    public void testSetIdFonction() {
        fonction.setIdFonction(2);
        assertEquals(2, fonction.getIdFonction());
    }

    /**
     * Teste la m�thode getNomFonction.
     */
    @Test
    public void testGetNomFonction() {
        assertEquals("Exemple", fonction.getNomFonction());
    }

    /**
     * Teste la m�thode setNomFonction.
     */
    @Test
    public void testSetNomFonction() {
        fonction.setNomFonction("Nouvel Exemple");
        assertEquals("Nouvel Exemple", fonction.getNomFonction());
    }

    /**
     * Teste la m�thode toString.
     */
    @Test
    public void testToString() {
        assertEquals(
                 "Fonction [fonctionId=1, nomFonction=Exemple]",
                 fonction.toString());
    }
}
