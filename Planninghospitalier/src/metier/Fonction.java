/**
 * @author 22106979 - Documentation
 * @author 22101222 - code - documentation - checkstyle
 * @author 22203517t - relecture
 */

package metier;

/**
 * Cette classe contient toute les informations relatives aux fonctions.
 */
public class Fonction {
    /**
     * Chaque fonction poss�de un identifiant unique.
     */
    private int idFonction;
    /**
     * Chaque fonction poss�de un nom.
     */
    private String nomFonction;

    /**
     * Constructeur par défaut qui permet
     * d'implémenter une fonction d'un personnel.
     * @param id L'id d'une fonction
     * @param nom Nom de la fonction
     */
    public Fonction(final int id, final String nom) {
        this.idFonction = id;
        this.nomFonction = nom;
    }
    /**
     * Constructeur par défaut qui permet
     * d'implémenter une fonction d'un personnel.
     */
    public Fonction() {
    	this.idFonction = idFonction;
    	this.nomFonction = nomFonction;
    }

    /**
     * R�cup�re l'identifiant de la fonction.
     * @return L'identifiant de la fonction.
     */
    public int getIdFonction() {
        return idFonction;
    }

    /**
     * D�finit l'identifiant de la fonction.
     * @param fonctionId Le nouvel identifiant de la fonction.
     */
     public void setIdFonction(final int fonctionId) {
        this.idFonction = fonctionId;
     }

    /**
     * R�cup�re le nom de la fonction.
     * @return Le nom de la fonction.
     */
    public String getNomFonction() {
        return nomFonction;
    }

    /**
     * D�finit le nom de la fonction.
     * @param fonctionNom Le nouveau nom de la fonction.
     */
    public void setNomFonction(final String fonctionNom) {
       this.nomFonction = fonctionNom;
    }

    /**
     * Convertit les informations de la fonction
     * en une repr�sentation textuelle.
     * @return Une cha�ne repr�sentant les d�tails de la fonction.
     */
    @Override
    public String toString() {
        return "" + nomFonction;
    }
}
