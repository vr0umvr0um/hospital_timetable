/**
 * @author 22106979 - Documentation
 * @author 22208030 - Implémentation
 */

package metier;

/**
 * Cette classe contient toutes les informations relatives aux violations, c'est
 * à dire les contraintes non respectées.
 */
public class Violation {

    // valeur de la contrainte associée à la violation
    private Contrainte contrainte;

    // valeur du personnel associé à la violation
    private Personnel personnel;

    // valeur du créneau associé à la violation
    private BesoinEnPersonnel besoin;

    /**
     * Cette méthode permet d'implémenter avec des paramètres des violations.
     *
     * @param contrainte
     * @param personnel
     * @param creneau
     */
    public Violation(Contrainte contrainte, Personnel personnel, BesoinEnPersonnel besoin) {
        this.contrainte = contrainte;
        this.personnel = personnel;
        this.besoin = besoin;
    }

    public Contrainte getContrainte() {
        return this.contrainte;
    }

    public Personnel getPersonnel() {
        return this.personnel;
    }

    public BesoinEnPersonnel getBesoin() {
        return this.besoin;
    }

    public void setBesoin(BesoinEnPersonnel besoin) {
        this.besoin = besoin;
    }

    public void setContrainte(Contrainte contrainte) {
        this.contrainte = contrainte;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

}