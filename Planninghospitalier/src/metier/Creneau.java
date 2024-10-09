/**
 * @author 22106979 - Documentation
 * @author Dylan Cailleau - Ecriture de la classe et documentation
 */

package metier;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

/**
 * Cette classe contient toutes les informations d'un créneau :
 *
 * @param idC        est un int qui représente l'id du créneau
 * @param dateC      est un Date qui correspond au jour du créneau et est de la
 *                   forme : 2000-01-01
 * @param heureDeb   est un Time qui correspond à l'heure de début du créneau et
 *                   est de la forme : 12:30:00
 * @param heureFin   est un Time qui correspond à l'heure de fin du créneau et
 *                   est de la forme : 12:30:00
 * @param besoins    est une ArrayList de type BesoinEnPersonnel qui renvoit les
 *                   besoins en personnel
 * @param personnels est une ArrayList de type Personnel qui renvoit les
 *                   personnels affectés au créneau
 * @param violation  est une ArrayList de type Violation qui renvoit les
 *                   violations associés au créneau
 */
public class Creneau {

	private int idC;
	private Date dateC;
	private Time heureDeb;
	private Time heureFin;
	private ArrayList<BesoinEnPersonnel> besoins;
	private ArrayList<Personnel> personnels;
	private ArrayList<Violation> violation;

	/**
	 * Prends en entrée :
	 *
	 * @param idC        int
	 * @param dateC      Date (2000-01-01)
	 * @param heureDeb   Time (12:30:00)
	 * @param heureFin   Time (12:30:00)
	 * @param besoins    ArrayList BesoinEnPersonnel
	 * @param personnels ArrayList Personnel
	 * @param violation  ArrayList Violation
	 */
	public Creneau(int idC, String date, String heureDeb, String heureFin) {

		this.idC = idC;
		this.dateC = Date.valueOf(date);
		this.heureDeb = Time.valueOf(heureDeb);
		this.heureFin = Time.valueOf(heureFin);
	}

	public Creneau(String date, String heureDeb, String heureFin) {

		this.dateC = Date.valueOf(date);
		this.heureDeb = Time.valueOf(heureDeb);
		this.heureFin = Time.valueOf(heureFin);
	}

	public int getIdC() {
		return idC;
	}

	public void setIdC(int idC) {
		this.idC = idC;
	}

	public Date getDate() {
		return dateC;
	}

	public void setDate(Date dateC) {
		this.dateC = dateC;
	}

	public Time getHeureDeb() {
		return heureDeb;
	}

	public void setHeureDeb(Time heureDeb) {
		this.heureDeb = heureDeb;
	}

	public Time getHeureFin() {
		return heureFin;
	}

	public void setHeureFin(Time heureFin) {
		this.heureFin = heureFin;
	}

	public ArrayList<BesoinEnPersonnel> getBesoin() {
		return besoins;
	}

	public void setBesoin(ArrayList<BesoinEnPersonnel> besoins) {
		this.besoins = besoins;
	}

	public ArrayList<Personnel> getPersonnels() {
		return personnels;
	}

	public void setPersonnels(ArrayList<Personnel> personnels) {
		this.personnels = personnels;
	}

	public ArrayList<Violation> getViolation() {
		return violation;
	}

	public void setViolation(ArrayList<Violation> violation) {
		this.violation = violation;
	}

	public void addBesoin(BesoinEnPersonnel besoin) {
	    this.besoins.add(besoin);
	}

	public void suppBesoin(BesoinEnPersonnel besoin) {
	    this.besoins.remove(besoin);
	}

	@Override
    public String toString() {
		return ("id :" + idC + ", date :" + dateC + ", heure de début :" + heureDeb + ", heure de fin :" + heureFin);
	}

}