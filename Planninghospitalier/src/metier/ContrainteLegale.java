/**
 * La classe ContrainteLegale est une classe dérivée de la classe Contrainte.
 * Elle contient des informations supplémentaires relatives aux contraintes légales.
 *
 * @author 22106979 - Documentation & Implémentation
 */

package metier;

/**
 * Cette classe contient toutes les informations relations aux contraintes
 * légales.
 */
public class ContrainteLegale extends Contrainte {

  /**
   * valeur de l'identifiant de la contrainte légale.
   */
  private int idContrainteLegale;

  /**
  * Constructeur par défaut qui permet d'implémenter les contraintes légales.
  *
  * @param idContrainteLegale [int] valeur de l'identifiant
  *                                 de la contrainte légale
  * @param nom [String] valeur du nom de la contrainte légale
  * @param duree [double] valeur de la durée de la contrainte légale
  * @param temporalite [String] valeur de la temporalité de la contrainte légale
  */
  public ContrainteLegale(
      int idContrainteLegale,
      String nom,
      double duree,
      String temporalite
  ) {

    super(nom, duree, temporalite); // Appel explicite du super constructeur

    this.idContrainteLegale = idContrainteLegale;

  }

  /**
  * Constructeur par défaut qui permet d'implémenter les contraintes légales.
  *
  * @param nom [String] valeur du nom de la contrainte légale
  * @param duree [double] valeur de la durée de la contrainte légale
  * @param temporalite [String] valeur de la temporalité de la contrainte légale
  */
  public ContrainteLegale(
      String nom,
      double duree,
      String temporalite
  ) {

    super(nom, duree, temporalite); // Appel explicite du super constructeur

    this.idContrainteLegale = 0;

  }

  /**
   * Cette méthode renvoie les informations relatives à la contrainte légale.
   *
   * @return [int] valeur de l'identifiant de la contrainte légale
   */
  public int getIdContrainteLegale() {
    return idContrainteLegale;
  }

  /**
   * Cette méthode renvoie le nom de la contrainte légale.
   *
   * @return [String] valeur du nom de la contrainte légale.
   */
  public String getNomContrainteLegale() {
    return super.getNom();
  }

  /**
   * Cette méthode permet de remplacer la valeur de l'identifiante de la valeur
   * contrainte légale par un identifiant donné.
   *
   * @param idContrainteLegale [int]valeur de la contrainte légale de
   *                           remplamcement
   */
  public void setIdContrainteLegale(int idContrainteLegale) {
    this.idContrainteLegale = idContrainteLegale;
  }

  /**
   * Cette méthode permet d'afficher les informations relatives à une contrainte
   * de spécialité selon une forme prédéfinie.
   */
  @Override
  public String toString() {
    String s;
    s = "Contrainte légale : ";
    s += "Identifiant : " + this.idContrainteLegale;
    s += ", Nom : " + this.getNom();
    s +=  ", Durée : " + this.getDuree();
    s += ", Temporalité : " + this.getTemporalite() + "\n";
    return s;
  }
}