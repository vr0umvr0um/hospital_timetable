/**
 * @author 22106979 - Documentation
 * @author 22208030 - Implémentation
 */

package metier;

/**
 * Cette classe contient toute les informations relatives aux spécialités.
 */
public class Specialite {

    // valeur de l'idenfiant de la spécialité
    private int idSpecialite;

    // valeur du nom de la spécialité
    private String nomSpecialite;

    /**
     * Constructeur par défaut qui permet l'implémentation d'une spécialité.
     *
     * @param idSpecialite
     * @param nomSpecialite
     */
    public Specialite(int idSpecialite, String nomSpecialite) {
        this.idSpecialite = idSpecialite;
        this.nomSpecialite = nomSpecialite;
    }

    public int getIdSpecialite() {
        return this.idSpecialite;
    }

    public String getNomSpecialite() {
        return this.nomSpecialite;
    }

    public void setIdSpecialite(int idSpecialite) {
        this.idSpecialite = idSpecialite;
    }

    public void setNomSpecialite(String nomSpecialite) {
        this.nomSpecialite = nomSpecialite;
    }

    @Override
    public String toString() {
        return ""+nomSpecialite;
    }


}