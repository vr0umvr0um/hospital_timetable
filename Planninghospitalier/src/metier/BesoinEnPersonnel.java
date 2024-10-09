/**
 * @author 22106979 - Documentation
 * @author 22206008 - Meriem bennama - code
 */

package metier;

import java.io.Serializable;


/**
 * Cette classe met en avant tous les besoins en personnel principalement
 * nécessaires au créneau
 */
public class BesoinEnPersonnel implements Serializable{

    /**
     * serialVersionUID du Personnel
     */
    private static final long serialVersionUID = 1L;

    // valeur de l'identifiant du besoin en personnel
    private int idBesoin;

    // valeur du nombre de personnes nécessaires pour le créneau
    private int nbPersonnes;

    // valeur des informations du créneau
    private Creneau creneau;

	// valeur des spécialités nécessaires
	private Specialite specialite;

	// valeur des fonctions nécessaires
	private Fonction fonction;

	/**
	 * Constructeur
	 */

	public BesoinEnPersonnel(int idBesoin, int nbPersonnes, Creneau creneau, Specialite specialite, Fonction fonction) {
		super();
		this.idBesoin = idBesoin;
		this.nbPersonnes = nbPersonnes;
		this.creneau = creneau;
		this.specialite = specialite;
		this.fonction = fonction;
	}


//Getters et Setters
	public int getIdBesoin() {
		return idBesoin;
	}

	public void setIdBesoin(int idBesoin) {
		this.idBesoin = idBesoin;
	}

	public int getNbPersonnes() {
		return nbPersonnes;
	}

	public void setNbPersonnes(int nbPersonnes) {
		this.nbPersonnes = nbPersonnes;
	}

	public Creneau getCreneau() {
		return creneau;
	}

	public void setCreneau(Creneau creneau) {
		this.creneau = creneau;
	}

	public Specialite getSpecialite() {
		return specialite;
	}

	public void setSpecialite(Specialite specialite) {
		this.specialite = specialite;
	}

	public Fonction getFonction() {
		return fonction;
	}

	public void setFonction(Fonction fonction) {
		this.fonction = fonction;
	}

	public void setIdCreneau(int idCreneau) {
	    if (this.creneau == null) {
	        throw new IllegalStateException("Creneau is not set. Please set Creneau before trying to set its ID.");
	    } else {
	        this.creneau.setIdC(idCreneau);
	    }
	}



	@Override
	public String toString() {
		 return "Fonction : "+this.fonction.getNomFonction()+", Specialite : "+(this.specialite != null ? this.specialite.getNomSpecialite() : "Aucune") + " ,Nombre requis : "+ this.nbPersonnes;
	}



}