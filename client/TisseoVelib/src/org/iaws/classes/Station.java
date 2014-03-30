package org.iaws.classes;

public class Station {
	
	private String nom;
	private String adresse;
	private int totalVelo;
	private int nbVeloDispo;
	private int nbLike;
	private int nbUnlike;
	
	public Station(String nom, String adresse, int totalVelo, int nbVeloDispo) {
		super();
		this.nom = nom;
		this.adresse = adresse;
		this.totalVelo = totalVelo;
		this.nbVeloDispo = nbVeloDispo;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getAdresse() {
		return adresse;
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
	
}
