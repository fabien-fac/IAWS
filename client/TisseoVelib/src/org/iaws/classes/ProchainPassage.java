package org.iaws.classes;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import android.view.View;
import android.view.View.OnClickListener;

public class ProchainPassage implements OnClickListener {

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
		System.out.println("BABAAAAAR : " + TimeUnit.HOURS.convert(intervalle, TimeUnit.MILLISECONDS) + " heures");
		long nbMinutes = TimeUnit.MINUTES.convert(intervalle, TimeUnit.MILLISECONDS);
		long nbHeures = nbMinutes/60;
		if (nbHeures >= 1){
			resultat += nbHeures + " heures et " + nbMinutes%60 + " minutes : ";
		} else if (nbHeures == 1){
			resultat += "1 heure et " + nbMinutes%60 + " minutes : ";
		}else{
			resultat += nbMinutes + " minutes : ";
		}
		
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(this.prochainPassage);
		String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
		resultat += String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
