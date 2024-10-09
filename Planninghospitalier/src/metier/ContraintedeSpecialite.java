/**
    La classe ContraintedeSpecialite représente une contrainte de spécialité.
    Elle étend la classe Contrainte.
    Elle dispose des propriétés suivantes :
        idContrainteSpe : l'identifiant de la contrainte de spécialité
        specialite : la spécialité associée à la contrainte
        activated : l'activation de la contrainte
    Elle dispose des méthodes suivantes :
        isActivated() : renvoie vrai ou faux si la contrainte est activée
        switchActivation() : active ou désactive la contrainte
        getIdContrainteSpe() : renvoie l'identifiant de la contrainte
        setIdContrainteSpe() : définit l'identifiant de la contrainte
        getSpecialite() : renvoie la spécialité associée à la contrainte
        setSpecialite() : définit la spécialité associée à la contrainte
        toString() : retourne une représentation textuelle de la contrainte

 *
 * @author 22106979 - Documentation & Implémentation
 */

package metier;

/**
 * Cette classe contient toute les informations relatives aux contraintes de
 * spécialités. Elle étend la classe Contrainte ce qui permet de partager des
 * propriétés et des méthodes communes.
 */
public class ContraintedeSpecialite extends Contrainte {

  /**
   * valeur de l'identifiant de la contrainte de spécialité.
   */
  private int idContrainteSpe;

  /**
   * valeur de la spécialité.
   */
  private Specialite specialite;

  /**
   * valeur de l'activation de la spécialité.
   */
  private int activated;

  /**
   * Méthode qui permet d'implémenter les contraintes de
   * spécialité.
   *
   * @param idContrainteSpe [int] valeur de l'identifiant de la contrainte de
   *                        spécialité
   * @param specialite      Specialite] ensemble de valeurs (identifiant et nom)
   *                        de la spécialité
   * @param i       [true/false] si la valeur est activée
   */
  private void initContraintedeSpecialite(
      int idContrainteSpe,
      Specialite specialite,
      int i
  ) {

    this.idContrainteSpe = idContrainteSpe;
    this.specialite = specialite;
    this.activated = i;

  }

  /**
   * Constructeur qui permet d'implémenter les contraintes de
   * spécialité.
   *
   * @param specialite      Specialite] ensemble de valeurs (identifiant et nom)
   *                        de la spécialité
   * @param i       [true/false] si la valeur est activée
   * @param nom             [String] valeur du nom
   *  de la contrainte de spécialité
   * @param duree           [int] valeur de la durée selon la temporalité à
   *                        respecter pour ne pas enfreindre la contrainte de
   *                        spécialité
   * @param temporalite     [String] valeur de temporalité
   *  associée à une durée à
   *                        respecter pour ne pas enfreindre la contrainte de
   *                        spécialité
   */
  public ContraintedeSpecialite(
      Specialite specialite,
      int i,
      String nom,
      double duree,
      String temporalite
  ) {

    super(nom, duree, temporalite); // Appel explicite du super constructeur

    initContraintedeSpecialite(0, specialite, i);

  }

  /**
   * Constructeur qui permet d'implémenter les contraintes de
   * spécialité.
   *
   * @param idContraintedeSpecialite [int] valeur de l'identifiant
   * de la contrainte de specialite
   * @param specialite      [Specialite] ensemble de valeurs
   * (identifiant et nom)
   *                        de la spécialité
   * @param i       [true/false] si la valeur est activée
   * @param nom             [String] valeur du nom
   *  de la contrainte de spécialité
   * @param duree           [int] valeur de la durée selon la temporalité à
   *                        respecter pour ne pas enfreindre la contrainte de
   *                        spécialité
   * @param temporalite     [String] valeur de temporalité
   *  associée à une durée à
   *                        respecter pour ne pas enfreindre la contrainte de
   *                        spécialité
   */
  public ContraintedeSpecialite(
      int idContraintedeSpecialite,
      Specialite specialite,
      int i,
      String nom,
      double duree,
      String temporalite
  ) {

    super(nom, duree, temporalite); // Appel explicite du super constructeur

    initContraintedeSpecialite(idContraintedeSpecialite, specialite, i);

  }

  /**
   * Cette méthode renvoie le nom de la contrainte de spécialité.
   *
   * @return [String] valeur du nom de la contrainte de spécialité
   */
  public String getNomContrainteSpe() {
    return super.getNom();
  }

  /**
   * Cette méthode renvoie vrai(1) ou faux(0) si la contrainte de spécialité
   * est prise en compte dans
   * la liste des violations créneau.
   *
   * @return [1/0] si la contrainte est nécessaire
   */
  public int getActivation() {
    return this.activated;
  }

  /**
   * Cette méthode permet d'activer, c'est-à-dire de prendre en compte la
   * contrainte de spécialité dans la liste des violations, ou non la contrainte
   * de spécialité.
   */
  public void switchActivation() {

    // remplace true par false
    if (this.activated == 1) {
      activated = 0;
    } else { //remplace false par true
      activated = 1;
    }
  }

  /**
   * Cette méthode renvoie l'identifiant de la contrainte de specialité.
   *
   * @return idContrainte [int] valeur de l'identifiant de la contrainte de
   *         specialite
   */
  public int getIdContrainteSpe() {
    return this.idContrainteSpe;
  }

  /**
   * Cette méthode permet de remplacer
   * la valeur de l'identifiante de la valeur de
   * spécialité par un identifiant donné.
   *
   * @param idContrainteSpe [int] valeur de l'identifiant pour remplacer
   */
  public void setIdContrainteSpe(int idContrainteSpe) {
    this.idContrainteSpe = idContrainteSpe;
  }

  /**
   * Cette méthode renvoie les informations relatives à la contrainte de
   * spécialité.
   *
   * @return specialite [Specialite] ensemble
   * de valeurs (identifiant, nom) de la
   *         contrainte de spécialité
   */
  public Specialite getSpecialite() {
    return this.specialite;
  }

  /**
   * Cette méthode permet de remplacer l'ensemble
   * des valeurs de la contrainte de
   * spécialité par des valeurs données.
   *
   * @param specialite [Specialite] ensemble de valeurs (identifiant, nom) de la
   *                   contrainte de spécialité pour remplacer
   */
  public void setSpecialite(Specialite specialite) {
    this.specialite = specialite;
  }

  /**
   * Cette méthode renvoie l'identifiant de la spécialité.
   *
   * @return [int] valeur de l'identifiant de la spécialité
   */
  public int getIdSpe() {
    return this.specialite.getIdSpecialite();
  }

  /**
   * Cette méthode renvoie le nom de la spécialité.
   *
   * @return [String] valeur du nom de la spécialité
   */
  public String getNomSpe() {
    return this.specialite.getNomSpecialite();
  }

  /**
   * Cette méthode permet d'afficher les informations relatives à une contrainte
   * de spécialité selon une forme prédéfinie.
   */
  @Override
  public String toString() {
    String s;
    s = "Contrainte de Spécialité : ";
    s += "Identifiant : " + this.idContrainteSpe;
    s += ", Nom : " + this.getNom();
    s += ", Durée : " + this.getDuree();
    s += ", Temporalité : " + this.getTemporalite();
    s += ", Identifiant de la spécialité associée : ";
    s += this.specialite.getIdSpecialite() + "\n";
    return s;
  }
}