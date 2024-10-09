/**
 * La classe TestCDS est un test unitaire pour la classe ContraintedeSpecialite.
 * Elle contient six tests qui vérifient le bon fonctionnement
 * des méthodes de la classe ContraintedeSpecialite.
 *
 * @author 22106979 - Documentation & Implémentation
 */

package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import metier.ContraintedeSpecialite;
import metier.Specialite;

/**
 * La classe TestCDS est un test unitaire pour la classe ContraintedeSpecialite.
 */
public class TestContrainteDeSpecialite {

  /**
   * Crée un objet `Specialite` avec l'ID 99 et le nom "test".
   */
  Specialite spe = new Specialite(99, "test");

  /**
   * Crée un objet `ContraintedeSpecialite` avec l'ID 11, la spécialité `spe`, le
   * statut d'activation vrai, le nom "test", la durée 2, et la temporalité
   * "Quotidienne".
   */
  ContraintedeSpecialite cds = new ContraintedeSpecialite(11, spe, 1, "test", 2, "Quotidienne");

  /**
   * Teste la méthode `getActivation()` pour vérifier qu'elle renvoie l'état
   * d'activation correct de la contrainte. Le test fonctionne.
   */
  @Test
  public void testIsActivated() {
    assertEquals(1, cds.getActivation());
  }

  /**
   * Teste la méthode `switchActivation()` pour vérifier qu'elle bascule l'état
   * d'activation de la contrainte. Le test fonctionne.
   */
  @Test
  public void testSwitchActivation() {
    cds.switchActivation();
    assertEquals(0, cds.getActivation());
  }

  /**
   * Teste la méthode `getIdContrainteSpe()` pour vérifier qu'elle renvoie
   * l'identifiant correct de la contrainte. Le test fonctionne.
   */
  @Test
  public void testGetIdContrainteSpe() {
    assertEquals(11, cds.getIdContrainteSpe());
  }

  /**
   * Teste la méthode `setIdContrainteSpe()` pour vérifier qu'elle change
   * l'identifiant de la contrainte. Le test fonctionne.
   */
  @Test
  public void testSetIdContrainteSpe() {
    cds.setIdContrainteSpe(99);
    assertEquals(99, cds.getIdContrainteSpe());
  }

  /**
   * Teste la méthode `getSpecialite()` pour vérifier qu'elle renvoie la bonne
   * spécialité associée à la contrainte. Le test fonctionne.
   */
  @Test
public void testGetSpecialite() {
    assertEquals(this.spe, cds.getSpecialite());
  }

  /**
   * Teste la méthode `setSpecialite()` pour vérifier qu'elle modifie la
   * spécialité associée à la contrainte. Le test fonctionne.
   */
  @Test
  public void testSetSpecialite() {
    Specialite spe = new Specialite(100, "test2");
    cds.setSpecialite(spe);

    assertEquals(spe, cds.getSpecialite());
  }
}
