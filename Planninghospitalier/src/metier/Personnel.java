/**
 * @author 22106979 (ANTIVACKIS VANESSA) - Documentation
 * @author 2203517 (FOREST Jules) - écriture de la classe et documentation
 *
 */

package metier;

import java.util.ArrayList;

import dao.PersonnelDAO;

/**
 * Cette classe permet de représenter un personnel de l'hopital.
 * @see PersonnelDAO
 * @see dao.ControleurListePersonnel
 */
public class Personnel {

    /**
     *  valeur de l'identifiant du personnel
     *  N'est jamais rentré par le cadre manuellement car c'est une
     *  valeur Auto-incrémental dans la BDD
     */
	private int idPersonnel;

	/**
	 *  valeur du nom du personnel
	 */
	private String nomPersonnel;

	/**
	 *  valeur du prénom du personnel
	 */
	private String prenomPersonnel;

	/**
	 *  valeur de la date de naissance du personnel
	 *  De la forme "dd-mm-yyyy"
	 */
	private String dateNaissance;

	/**
	 *  valeur du temps de travail mensuel effectué en heures
	 */
	private int tempsTravailMensuel;

	/**
	 *  la fonction du personnel
	 *  @see Fonction
	 */
	private Fonction fonction;

	/**
	 *  la liste des specialités du personnel
	 *  @see Specialite
	 */
	private ArrayList<Specialite> specialites;

	/**
	 *  la liste des créneaux liées au personnel
	 *  @see Creneau
	 */
	//private ArrayList<Creneau> creneaux;


	/**
	 *  la liste des violations liées au personnel
	 *  @see Violation
	 */
	private ArrayList<Violation> violations;


	// ------------ Constructeurs ------------------

	/**
	 * Constructeur par défaut qui permet l'implémentation d'un personnel
	 * sans qu'il ai de spécialités associées, ni  de créneaux
	 * et de violations
	 *
	 * @param idPersonnel
     * @param nomPersonnel
     * @param prenomPersonnel
     * @param dateNaissance
     * @param tempsTravailMensuel
     * @param fonction
	 */
	public Personnel(int idPersonnel, String nomPersonnel, String prenomPersonnel,
            String dateNaissance, int tempsTravailMensuel, Fonction fonction) {
        this.idPersonnel = idPersonnel;
        this.nomPersonnel = nomPersonnel;
        this.prenomPersonnel = prenomPersonnel;
        this.dateNaissance = dateNaissance;
        this.tempsTravailMensuel = tempsTravailMensuel;
        this.fonction = fonction;
        this.specialites = new ArrayList<Specialite>();
        this.violations = new ArrayList<Violation>();
	}

	/**
     * Constructeur permettant l'instanciation d'un personnel à partir de ces
     * informations
     * Cette méthode sera appelé notamment lors de la création d'un personnel
     * via PersonnelDAO
     * @param idPersonnel
     * @param nomPersonnel
     * @param prenomPersonnel
     * @param dateNaissance
     * @param tempsTravailMensuel
     * @param fonction
     * @param specialites
     * @param violations
     */
    public Personnel(int idPersonnel, String nomPersonnel, String prenomPersonnel,
            String dateNaissance, int tempsTravailMensuel, Fonction fonction,
            ArrayList<Specialite> specialites, ArrayList<Violation> violations) {
        super();
        this.idPersonnel = idPersonnel;
        this.nomPersonnel = nomPersonnel;
        this.prenomPersonnel = prenomPersonnel;
        this.dateNaissance = dateNaissance;
        this.tempsTravailMensuel = tempsTravailMensuel;
        this.fonction = fonction;
        this.specialites = specialites;
        this.violations = violations;
    }

	// ----------- Getters et Setters -----------------

    public int getIdPersonnel() {
        return idPersonnel;
    }


    public void setIdPersonnel(int idPersonnel) {
        this.idPersonnel = idPersonnel;
    }


    public String getNomPersonnel() {
        return nomPersonnel;
    }


    public void setNomPersonnel(String nomPersonnel) {
        this.nomPersonnel = nomPersonnel;
    }


    public String getPrenomPersonnel() {
        return prenomPersonnel;
    }


    public void setPrenomPersonnel(String prenomPersonnel) {
        this.prenomPersonnel = prenomPersonnel;
    }


    public String getDateNaissance() {
        return dateNaissance;
    }


    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }


    public int getTempsTravailMensuel() {
        return tempsTravailMensuel;
    }


    public void setTempsTravailMensuel(int tempsTravailMensuel) {
        this.tempsTravailMensuel = tempsTravailMensuel;
    }


    public Fonction getFonction() {
        return fonction;
    }


    public void setFonction(Fonction fonction) {
        this.fonction = fonction;
    }

    public ArrayList<Specialite> getSpecialites() {
        return specialites;
    }


    public void setSpecialites(ArrayList<Specialite> specialites) {
        this.specialites = specialites;
    }

    public ArrayList<Violation> getViolations() {
        return violations;
    }

    public void setViolations(ArrayList<Violation> violations) {
        this.violations = violations;
    }


    // -------------- Méthodes de classe --------------

    /**
	 * Cette méthode permet d'ajouter une spécialité à la liste des specialités
	 * du personnel.
	 *
	 * @param spe [Specialite] la spécialité à affecter au personnel
	 */
	public void addSpecialite(Specialite spe) {
		this.getSpecialites().add(spe);
	}

	/**
	 * Cette méthode permet d'ajouter une violation à la liste des violations
	 * du personnel, c'est à dire toutes les violations auxquels il est lié
	 *
	 * @param violation [Violation] violation lié au Personnel
	 */
	public void addViolation(Violation violation) {
		this.getViolations().add(violation);
	}

	/**
	 * Cette méthode permet de supprimer une spécialité affectée au personnel.
	 *
	 * @param spe [Specialite] la spécialité à supprimer
	 */
	public void suppSpecialite(Specialite spe) {
		this.getSpecialites().remove(spe);
	}

	/**
	 * Cette méthode permet de supprimer une violation de la liste des
	 * violations du personnel
	 *
	 * @param violation [Violation] violation à supprimer
	 */
	public void suppViolation(Violation violation) {
		this.getViolations().remove(violation);
	}

	/**
     * Cette méthode permet d'afficher textuellement les informations demandées.
     *
     * @return [String]
    */
	@Override
	public String toString() {
	    /*
	    // premier jet de toString avec toutes les infos
		String chaine = "";
		chaine += this.nomPersonnel+" " + this.prenomPersonnel + "\n";
		chaine += "Date de naissance : " + this.dateNaissance + "\n";
		chaine += "Temps de travail mensuel : " + this.tempsTravailMensuel
		        + "\n";
		chaine += "Fonction : " + this.fonction.toString() + "\n";
		return chaine;
		*/
	    return this.nomPersonnel+" "+this.prenomPersonnel;
	}

	/**
	 * Cette méthode permet de savoir si 2 instances de Personnels sont égales
	 * La comparaison se base seulement sur l'id du Personnel
	 * En effet un Personnel est censé avoir un id unique, si les autres
	 * attributs sont différents mais l'id est le même, ils seront quand même
	 * comptés comme égaux
	 *
	 * @return boolean - true si les personnels sont égaux, false sinon
	 */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }
        Personnel other = (Personnel) obj;
        return this.idPersonnel == other.idPersonnel;
    }
}