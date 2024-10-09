/**
 * La classe TestC est un test unitaire pour la classe `Contrainte`.
 * Elle contient six tests qui vérifient le bon fonctionnement
 * des méthodes de la classe `Contrainte`.
 *
 * @author 22106979 - Documentation & Implémentation
 */

package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import metier.Contrainte;



/**
 * La classe TestC est un test unitaire pour la classe Contrainte.
 */
public class TestContrainte {

  /**
   * Crée un objet `Contrainte` avec le nom "test", la durée 4,
   * et la temporalité "Quotidienne".
   */
  Contrainte contrainte = new Contrainte("test", 4.0, "Quotidienne");

  /**
   * Teste la méthode `getNom()` pour vérifier qu'elle renvoie le nom correct de
   * la contrainte. Le test fonctionne.
   */
  @Test
  public void TestGetNom() {
    assertEquals("test", contrainte.getNom());
  }

  /**
   * Teste la méthode `setNom()` pour vérifier qu'elle change le nom de la
   * contrainte. Le test fonctionne.
   */
  @Test
  public void TestSetNom() {
    contrainte.setNom("tst");
    assertEquals("tst", contrainte.getNom());
  }

  /**
   * Teste la méthode `getDuree()` pour vérifier qu'elle renvoie la durée correcte
   * de la contrainte. Le test fonctionne.
   */
  @Test
  public void TestGetDuree() {
    assertEquals(4.0, contrainte.getDuree(),0);
  }

  /**
   * Teste la méthode `setDuree()` pour vérifier qu'elle change la durée de la
   * contrainte. Le test fonctionne.
   * assertEquals(double expected,double actual) est
   * maintenant une méthode dépréciée.
   * J'utlise donc le delta avec une précision de perte à 0.
   */
  @Test
  public void TestSetDuree() {
    contrainte.setDuree(9.0);

    assertEquals(9.0, contrainte.getDuree(),0);
  }

  /**
   * Teste la méthode `getTemporalite()` pour vérifier qu'elle renvoie la
   * temporalité correcte de la contrainte. Le test fonctionne.
   */
  @Test
  public void TestGetTemporalite() {
    assertEquals("Quotidienne", contrainte.getTemporalite());
  }

  /**
   * Teste la méthode `setTemporalite()` pour vérifier qu'elle change la
   * temporalité de la contrainte. Le test fonctionne.
   */
  @Test
  public void TestSetTemporalite() {
    contrainte.setTemporalite("Hebdomadaire");
    assertEquals("Hebdomadaire", contrainte.getTemporalite());
  }
}
