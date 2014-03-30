package org.iaws.classes;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class ProchainPassage {

	private String idLigne;
	private String idArret;
	private String destination;
	private Date prochainPassage;

	public ProchainPassage() {
	}

	public ProchainPassage(String idLigne, String idArret, String destination,
			Date prochainPassage) {
		this.idLigne = idLigne;
		this.idArret = idArret;
		this.destination = destination;
		this.prochainPassage = prochainPassage;
	}

	public String toString() {
		return "ligne : " + this.idLigne + " / arret : " + this.idArret
				+ " / destination : " + this.destination + " / horaire : "
				+ this.prochainPassage;

	}

	public String calculerProchainPassage() {
		String resultat = "Dans ";
		long intervalle = this.prochainPassage.getTime() - new Date().getTime();
		resultat += TimeUnit.MINUTES.convert(intervalle, TimeUnit.MILLISECONDS)
				+ "minutes : ";
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(this.prochainPassage);
		resultat += String.format("%02dH%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
		return resultat;
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
