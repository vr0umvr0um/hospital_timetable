/**
 * La classe `Contrainte`représente une contrainte.
 * Elle contient les informations suivantes :
    Le nom de la contrainte
    La durée de la contrainte
    La temporalité de la contrainte

Attributs:

    nom (String) : le nom de la contrainte
    duree (int) : la durée de la contrainte
    temporalite (String) : la temporalité de la contrainte

Constructeurs:

    Contrainte() : constructeur par défaut

Méthodes:

    getNom() : méthode permettant de récupérer le nom de la contrainte
    setNom(String nom) : méthode permettant de définir le nom de la contrainte
    getDuree() : méthode permettant de récupérer la durée de la contrainte
    setDuree(int duree) : méthode permettant de définir la durée de la contrainte
    getTemporalite() : méthode permettant de récupérer la temporalité de la contrainte
    setTemporalite(String temporalite) : méthode permettant de définir
                                         la temporalité de la contrainte

 *
 * @author 22106979 - Documentation
 * @author 22106979 - Implémentation
 */

package metier;

/**
 * Cette classe contient toutes les informations relatives aux contraintes.
 */
public class Contrainte {

  /**
   *  valeur du nom de la contrainte.
   */
  private String nom;

  /**
   *  valeur de la durée de la contrainte.
   */
  private double duree;

  /**
   *  valeur de la forme du temps (jour/semaine entre deux créneaux).
   *  de la contrainte
   */
  private String temporalite;

  /**
   * Constructeur par défaut qui permet l'implémentation des contraintes.
   *
   * @param nom [String] valeur du nom de la contrainte
   * @param duree [int] valeur de la duree de la contrainte
   * @param temporalite [String] valeur de la temporalite de la contrainte
   */
  public Contrainte(String nom, double duree, String temporalite) {

    this.nom = nom;
    this.duree = duree;
    this.temporalite = temporalite;
  }

  /**
   * Cette méthode permet de retourner le nom de la contrainte.
   *
   * @return [String] valeur du nom de la contrainte
   */
  public String getNom() {
    return this.nom;
  }

  /**
   * Cette méthode permet de remplacer le nom par une valeur définie.
   *
   * @param nom [String] valeur de remplacement
   */
  public void setNom(String nom) {
    this.nom = nom;
  }

  /**
   * Cette méthode permet de retourner le temps de la contrainte.
   *
   * @return [int] valeur du temps de la contrainte
   */
  public double getDuree() {
    return duree;
  }

  /**
   * Cette méthode permet de remplacer la durée par une valeur définie.
   *
   * @param duree [int] valeur de remplacement
   */
  public void setDuree(double duree) {
    this.duree = duree;
  }

  /**
   * Cette méthode permet de retourner la temporalité de la contrainte.
   *
   * @return [String] valeur de temporalité de la contrainte
   */
  public String getTemporalite() {
    return temporalite;
  }

  /**
   * Cette méthode permet de remplacer la temporalité par une valeur définie.
   *
   * @param temporalite [String] valeur de remplacement
   */
  public void setTemporalite(String temporalite) {
    this.temporalite = temporalite;
  }

}


