package org.iaws.classes;

import java.util.Date;

public class ProchainPassage {

	public String idLigne;
	public String idArret;
	public String destination;
	public Date prochainPassage;

	public ProchainPassage() {

	}

	public ProchainPassage(String idLigne, String idArret, String destination,
			Date prochainPassage) {
		this.idLigne = idLigne;
		this.idArret = idArret;
		this.destination = destination;
		this.prochainPassage = prochainPassage;
	}

	public String getIdLigne() {
		return idLigne;
	}

	public void setIdLigne(String idLigne) {
		this.idLigne = idLigne;
	}

	public String getIdArret() {
		return idArret;
	}

	public void setIdArret(String idArret) {
		this.idArret = idArret;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Date getProchainPassage() {
		return prochainPassage;
	}

	public void setProchainPassage(Date prochainPassage) {
		this.prochainPassage = prochainPassage;
	}
}
