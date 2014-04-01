package org.iaws.classes;

import org.iaws.interfaces.Fiabilite;

public class Station implements Fiabilite {

	private String nom;
	private String adresse;
	private Boolean ouverte;
	private int totalVelo;
	private int nbVeloDispo;
	private int nbLike;
	private int nbUnlike;

	public Station(String nom, String adresse, int totalVelo, int nbVeloDispo,
			String ouverte) {
		this.nom = nom.replace("\"", "");
		this.adresse = adresse.replace("\"", "");
		this.totalVelo = totalVelo;
		this.nbVeloDispo = nbVeloDispo;
		String open = ouverte.replace("\"", "");
		this.ouverte = (open.equalsIgnoreCase("OPEN"));
		this.nbLike = 0;
		this.nbUnlike = 0;
	}

	public String toString() {
		return "Nom : " + this.nom + " / adresse : " + this.adresse
				+ " / Sation ouverte : " + this.ouverte + " / totalVelo : "
				+ this.totalVelo + " / nbVeloDispo : " + this.nbVeloDispo;

	}

	public int calculerNbStandDIsponible() {
		return this.totalVelo - nbVeloDispo;
	}

	public Boolean getOuverte() {
		return ouverte;
	}

	public void setOuverte(Boolean ouverte) {
		this.ouverte = ouverte;
	}

	public String getNom() {
		return nom.toLowerCase();
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getAdresse() {
		return adresse.toLowerCase();
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public int getTotalVelo() {
		return totalVelo;
	}

	public void setTotalVelo(int totalVelo) {
		this.totalVelo = totalVelo;
	}

	public int getNbVeloDispo() {
		return nbVeloDispo;
	}

	public void setNbVeloDispo(int nbVeloDispo) {
		this.nbVeloDispo = nbVeloDispo;
	}

	public int getNbLike() {
		return nbLike;
	}

	public void setNbLike(int nbLike) {
		this.nbLike = nbLike;
	}

	public int getNbUnlike() {
		return nbUnlike;
	}

	public void setNbUnlike(int nbUnlike) {
		this.nbUnlike = nbUnlike;
	}

	@Override
	public int get_nb_like() {
		return nbLike;
	}

	@Override
	public int get_nb_unlike() {
		return nbUnlike;
	}

	@Override
	public void ajout_like(int nb) {
		nbLike += nb;
	}

	@Override
	public void ajout_unlike(int nb) {
		nbUnlike += nb;
	}

	@Override
	public void suppr_like(int nb) {
		if ((nbLike - nb) < 0) {
			nbLike = 0;
		} else {
			nbLike -= nb;
		}
	}

	@Override
	public void suppr_unlike(int nb) {
		if ((nbUnlike - nb) < 0) {
			nbUnlike = 0;
		} else {
			nbUnlike -= nb;
		}
	}

}
