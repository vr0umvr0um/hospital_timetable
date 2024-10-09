/**
 * La classe TestCL est un test unitaire pour la classe ContrainteLegale.
 * Elle contient deux tests qui vérifient le bon fonctionnement
 * des méthodes de la classe ContrainteLegale.
 *
 * @author 22106979 - Documentation & Implémentation
 */

package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import metier.ContrainteLegale;

/**
 * La classe TestCL est un test unitaire pour la classe ContrainteLegale.
 */
public class TestContrainteLegale {

  /**
   * Crée un objet `ContrainteLegal` avec l'ID 99, le nom "test", la durée 0, et
   * la temporalité "Quotidienne".
   */
  ContrainteLegale cl = new ContrainteLegale(99, "test", 0, "Quotidienne");

  /**
   * Teste la méthode `getIdContrainteLegale()` pour vérifier qu'elle renvoie
   * l'identifiant correct de la contrainte. Le test fonctionne.
   */
  @Test
  public void TestGetIdContrainteLegale() {
    assertEquals(99, cl.getIdContrainteLegale());
  }

  /**
   * Teste la méthode `setIdContrainteLegale()` pour vérifier qu'elle change
   * l'identifiant de la contrainte. Le test fonctionne.
   */
  @Test
  public void TestSetIdContrainteLegale() {
    cl.setIdContrainteLegale(6);
    assertEquals(6, cl.getIdContrainteLegale());
  }
}
