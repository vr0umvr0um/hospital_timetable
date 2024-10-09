/**
 * La classe TestCdsDAO est un test unitaire pour la classe ContraintedeSpecialiteDAO.
 * Elle contient quatre tests qui vérifient le bon fonctionnement
 * des méthodes de la classe ContraintedeSpecialiteDAO.
 *
 * @author 22106979 - Documentation & Implémentation
 */

package tests;

import java.sql.Date;

import dao.ContrainteDeSpecialiteDAO;
import metier.BesoinEnPersonnel;
import metier.ContraintedeSpecialite;
import metier.Fonction;
import metier.Personnel;
import metier.Specialite;

/**
 * La classe TestCdsDao est un test unitaire pour la classe
 * ContraintedeSpecialiteDAO.
 */
public class TestContrainteDeSpecialiteDAO {

  /**
   * La méthode main permet d'effectuer les appels de méthode qui servent de tests.
   *
   * @param args [String[]]
   */
  public static void main(String[] args) {

    //Crée un objet `Specialite` avec l'ID 99 et le nom "test".
    Specialite spe = new Specialite(1, "Obstétrique");

    //Crée un objet `ContraintedeSpecialite` avec la spécialité `spe`, le
    //statut d'activation vrai, le nom "test", la durée 2, et la temporalité
    //"Quotidienne".
    ContraintedeSpecialite cds = new ContraintedeSpecialite(spe, 1, "test", 2, "Quotidienne");

    ContrainteDeSpecialiteDAO c = new ContrainteDeSpecialiteDAO();

    //Teste de la méthode create() de la classe `ContrainteDeSpecialiteDAO`.
    //Le test fonctionne.
    c.create(cds);
    c.readAll();

    //Modifie la durée qui passe de 2 à 8.
    cds.setDuree(9);
    //Teste de la méthode update() de la classe `ContrainteDeSpecialiteDAO`.
    //Le test fonctionne.
    c.update(cds);
    //Teste de la méthode read() de la classe `ContrainteDeSpecialiteDAO`.
    //Le test fonctionne.
    c.read(cds.getIdContrainteSpe());

    //Modifie la temporalité "Quotidionne" à "Hebdomadaire"
    cds.setTemporalite("Hebdomadaire");
    //Teste de la méthode update() de la classe `ContrainteDeSpecialiteDAO`.
    //Le test fonctionne.
    c.update(cds);
    //Teste de la méthode read() de la classe `ContrainteDeSpecialiteDAO`.
    //Le test fonctionne.
    c.read(cds.getIdContrainteSpe());

    //Modifie le nom "test" en "updateTest"
    cds.setNom("updateTest");
    //Teste de la méthode update() de la classe `ContrainteDeSpecialiteDAO`.
    //Le test fonctionne.
    c.update(cds);
    //Teste de la méthode read() de la classe `ContrainteDeSpecialiteDAO`.
    //Le test fonctionne.
    c.read(cds.getIdContrainteSpe());

    //Teste de la méthode delete() de la classe `ContrainteDeSpecialiteDAO`.
    //Le test fonctionne.
    c.delete(cds);

    //Teste de la méthode checker() de la clase `ContrainteDeSpecialiteDAO`.
    //Le test fonctionne.
    Fonction fonction = new Fonction(1, "Pharmacologie");
    Personnel personnel = new Personnel (1, "Smith", "John", "2024-11-11", 150, fonction);
    String dj = "2023-12-10";
    Date dateJour = Date.valueOf(dj);
    String dbs = "2023-12-04";
    Date dateDebSemaine=Date.valueOf(dbs);
    String dfs = "2023-12-10";
    Date dateFinSemaine = Date.valueOf(dfs);
    String dbm = "2023-12-01";
    Date dateDebMois = Date.valueOf(dbm);
    String dfm = "2023-12-31";
    Date dateFinMois = Date.valueOf(dfm);
    BesoinEnPersonnel besoin = new BesoinEnPersonnel(0, 0, null, null, null);
    c.checker(personnel,dateDebSemaine,dateFinSemaine,dateDebMois,dateFinMois,dateJour, besoin );
  }

}
