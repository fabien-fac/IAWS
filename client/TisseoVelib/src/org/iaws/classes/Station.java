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
	private String idStation;
	private String rev;

	public Station(String nom, String adresse, int totalVelo, int nbVeloDispo,
			String ouverte, String idStation) {
		this.nom = nom.replace("\"", "");
		this.adresse = adresse.replace("\"", "");
		this.totalVelo = totalVelo;
		this.nbVeloDispo = nbVeloDispo;
		String open = ouverte.replace("\"", "");
		this.ouverte = (open.equalsIgnoreCase("OPEN"));
		this.nbLike = 0;
		this.nbUnlike = 0;
		this.idStation = idStation;
		this.rev = "null";
	}

	public String getIdStation() {
		return idStation;
	}

	public void setIdStation(String idStation) {
		this.idStation = idStation;
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
	
	@Override
	public void set_nb_like(int nb) {
		nbLike = nb;
	}

	@Override
	public void set_nb_unlike(int nb) {
		nbUnlike = nb;
	}
	
	public void setRev(String rev) {
		this.rev = rev;
	}
	
	public String getRev() {
		return rev;
	}

	public String getColorVeloDispo(){
		if(nbVeloDispo == 0){
			return "#fa3e3e";
		}
		else if(nbVeloDispo < 6){
			return "#f38234";
		}
		
		return "#000000";
	}
	
	public String getColorPlaceDispo(){
		int nbplace = calculerNbStandDIsponible();
		if(nbplace == 0){
			return "#fa3e3e";
		}
		else if(nbplace < 6){
			return "#f38234";
		}
		
		return "#000000";
	}
}
