/**
 * La classe TestContrainteLegaleDAO est un test unitaire pour la classe ContrainteLegaleDAO.
 * Elle contient quatre tests qui vérifient le bon fonctionnement
 * des méthodes de la classe ContrainteLegaleDAO.
 *
 * @author 22106979 - Documentation & Implémentation
 */

package tests;

import dao.ContrainteLegaleDAO;
import metier.ContrainteLegale;

/**
 * La classe TestContrainteLegale est un test unitaire pour la classe
 * ContrainteLegaleDAO.
 */
public class TestContrainteLegaleDAO {

  /**
   * La méthode main permet d'effectuer les appels de méthode qui servent de tests.
   *
   * @param args [String[]]
   */
  public static void main(String[] args) {

    //Crée un objet `ContrainteLegale` avec le nom "test", la durée 2,
    //et la temporalité "Quotidienne".
    ContrainteLegale cl = new ContrainteLegale("test", 2, "Quotidienne");

    ContrainteLegaleDAO c = new ContrainteLegaleDAO();

    //Teste de la méthode create() de la classe `ContrainteLegaleDAO`.
    //Le test fonctionne.
    c.create(cl);
    //Teste de la méthode readAll() de la classe `ContrainteLegaleDAO`.
    //Le test fonctionne.
    c.readAll();

    //Modifie la durée qui passe de 2 à 8.
    cl.setDuree(9);
    //Teste de la méthode update() de la classe `ContrainteLegaleDAO`.
    //Le test fonctionne.
    c.update(cl);
    //Teste de la méthode read() de la classe `ContrainteLegaleDAO`.
    //Le test fonctionne.
    c.read(cl.getIdContrainteLegale());

    //Modifie la temporalité "Quotidionne" à "Hebdomadaire"
    cl.setTemporalite("Hebdomadaire");
    //Teste de la méthode update() de la classe `ContrainteLegaleDAO`.
    //Le test fonctionne.
    c.update(cl);
    //Teste de la méthode read() de la classe `ContrainteLegaleDAO`.
    //Le test fonctionne.
    c.read(cl.getIdContrainteLegale());

    //Modifie le nom "test" en "updateTest"
    cl.setNom("updateTest");
    //Teste de la méthode update() de la classe `ContrainteLegaleDAO`.
    //Le test fonctionne.
    c.update(cl);
    //Teste de la méthode read() de la classe `ContrainteLegaleDAO`.
    //Le test fonctionne.
    c.read(cl.getIdContrainteLegale());

    //Teste de la méthode delete() de la classe `ContrainteLegaleDAO`.
    //Le test fonctionne.
    c.delete(cl);
  }

}
