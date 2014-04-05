package org.iaws.classes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
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
		if (this.idLigne.equals("A") || this.idLigne.equals("B")) {
			System.out.println("BABAR");
			return calculerProchainPassageMetro();
		} else {
			return calculerProchainPassageBus();
		}
	}
	
	public int calculerProchainPassageTemps(int tempsAjout) {		
		if (this.idLigne.equals("A") || this.idLigne.equals("B")) {
			return calculerProchainPassageMetroTemps(tempsAjout);
		} else {
			return calculerProchainPassageBusTemps(tempsAjout);
		}
	}
	
	
	private String calculerProchainPassageMetro(){
		
		String resultat = "";
		
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		System.out.println(now.get(Calendar.HOUR_OF_DAY));
		System.out.println(now.get(Calendar.DAY_OF_WEEK));
		/* 1 min 20 en heure de pointe durant la semaine */
		if ((now.get(Calendar.HOUR_OF_DAY) >= 7
				&& now.get(Calendar.HOUR_OF_DAY) < 9 || now
				.get(Calendar.HOUR_OF_DAY) >= 16
				&& now.get(Calendar.HOUR_OF_DAY) < 20)
				&& (now.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && now
						.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)) {
			resultat = "Temps d'attente maximum : 1 minute et 20 secondes";
		}

		/* 4 min en soirée le vendredi et le samedi */
		if ((now.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY && now
				.get(Calendar.HOUR_OF_DAY) >= 20)
				|| (now.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && now
						.get(Calendar.HOUR_OF_DAY) <= 1)
				|| (now.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && now
						.get(Calendar.HOUR_OF_DAY) >= 20)
				|| (now.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY && now
						.get(Calendar.HOUR_OF_DAY) <= 1)) {
			resultat = "Temps d'attente maximum : 4 minutes";
		}

		/* 5 min en heure creuse durant la semaine */
		if (now.get(Calendar.HOUR_OF_DAY) >= 9
				&& now.get(Calendar.HOUR_OF_DAY) < 16
				&& (now.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && now
						.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)) {
			resultat = "Temps d'attente maximum : 5 minutes";
		}

		/*
		 * 7 min en soirée durant la semaine, en heure creuse le dimanche et
		 * les jours fériés
		 */
		if ((now.get(Calendar.HOUR_OF_DAY) >= 20 && (now
				.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && now
				.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY))
				|| (now.get(Calendar.HOUR_OF_DAY) >= 7 && (now
						.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY))
				|| estJourFerie()) {
			resultat = "Temps d'attente maximum : 7 minutes";
		}

		/* 9 min en début de service */
		if (now.get(Calendar.HOUR_OF_DAY) <= 5
				&& (now.get(Calendar.MINUTE) <= 15)) {
			resultat = "Temps d'attente maximum : 9 minutes";
		}
		
		if (resultat.equals("")){
			resultat = "Information indisponible";
		}
		
		return resultat;
	}
	
private int calculerProchainPassageMetroTemps(int tempsAjout){
		
		int resultat = 9 + tempsAjout;
		
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		/* 1 min 20 en heure de pointe durant la semaine */
		if ((now.get(Calendar.HOUR_OF_DAY) >= 7
				&& now.get(Calendar.HOUR_OF_DAY) < 9 || now
				.get(Calendar.HOUR_OF_DAY) >= 16
				&& now.get(Calendar.HOUR_OF_DAY) < 20)
				&& (now.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && now
						.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)) {
			resultat = 1 + tempsAjout;
		}

		/* 4 min en soirée le vendredi et le samedi */
		if ((now.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY && now
				.get(Calendar.HOUR_OF_DAY) >= 20)
				|| (now.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && now
						.get(Calendar.HOUR_OF_DAY) <= 1)
				|| (now.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && now
						.get(Calendar.HOUR_OF_DAY) >= 20)
				|| (now.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY && now
						.get(Calendar.HOUR_OF_DAY) <= 1)) {
			resultat = 4 + tempsAjout;
		}

		/* 5 min en heure creuse durant la semaine */
		if (now.get(Calendar.HOUR_OF_DAY) >= 9
				&& now.get(Calendar.HOUR_OF_DAY) < 16
				&& (now.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && now
						.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)) {
			resultat = 5 + tempsAjout;
		}

		/*
		 * 7 min en soirée durant la semaine, en heure creuse le dimanche et
		 * les jours fériés
		 */
		if ((now.get(Calendar.HOUR_OF_DAY) >= 20 && (now
				.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && now
				.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY))
				|| (now.get(Calendar.HOUR_OF_DAY) >= 7 && (now
						.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY))
				|| estJourFerie()) {
			resultat = 7 + tempsAjout;
		}

		/* 9 min en début de service */
		if (now.get(Calendar.HOUR_OF_DAY) <= 5
				&& (now.get(Calendar.MINUTE) <= 15)) {
			resultat = 9 + tempsAjout;
		}
		
		return resultat;
	}

	private String calculerProchainPassageBus(){
		String resultat = "Dans ";
		long intervalle = this.prochainPassage.getTime()
				- new Date().getTime();
		if (intervalle >= 0) {
			long nbMinutes = TimeUnit.MINUTES.convert(intervalle,
					TimeUnit.MILLISECONDS);
			long nbHeures = nbMinutes / 60;
			if (nbHeures > 1) {
				resultat += nbHeures + " heures ";
				if (nbMinutes % 60 > 1) {
					resultat += "et " + nbMinutes % 60 + " minutes : ";
				} else if (nbMinutes % 60 == 1){
					resultat += "et 1 minute : ";
				}
			} else if (nbHeures == 1) {
				resultat += "1 heure ";
				if (nbMinutes % 60 > 1) {
					resultat += "et " + nbMinutes % 60 + " minutes : ";
				} else if (nbMinutes % 60 == 1){
					resultat += "et 1 minute : ";
				}
			} else if (nbMinutes <= 1) {
				resultat = "Arrivée imminente : ";
			} else {
				resultat += nbMinutes + " minutes : ";
			}

			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(this.prochainPassage);
			String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY),
					calendar.get(Calendar.MINUTE));
			resultat += String.format("%02d:%02d",
					calendar.get(Calendar.HOUR_OF_DAY),
					calendar.get(Calendar.MINUTE));
		} else {
			return null;
		}
		
		return resultat;
	}
	
	private int calculerProchainPassageBusTemps(int tempsAjout){

		int temps = -1;
		
		long intervalle = this.prochainPassage.getTime()
				- new Date().getTime();
		if (intervalle >= 0) {
			long nbMinutes = TimeUnit.MINUTES.convert(intervalle,
					TimeUnit.MILLISECONDS);
			
			
			temps = (int) (nbMinutes + tempsAjout);
		} 
		
		return temps;
	}
	
	public boolean estJourFerie() {
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		// On constitue la liste des jours fériés
		final List<Calendar> joursFeries = new ArrayList<Calendar>();
		// On recherche les jours fériés de l'année de la date en paramètre
		final Calendar jourFerie = (Calendar) now.clone();
		jourFerie.set(jourFerie.get(Calendar.YEAR), Calendar.JANUARY, 1);
		joursFeries.add((Calendar) jourFerie.clone());
		jourFerie.set(jourFerie.get(Calendar.YEAR), Calendar.MAY, 1);
		joursFeries.add((Calendar) jourFerie.clone());
		jourFerie.set(jourFerie.get(Calendar.YEAR), Calendar.MAY, 8);
		joursFeries.add((Calendar) jourFerie.clone());
		jourFerie.set(jourFerie.get(Calendar.YEAR), Calendar.JULY, 14);
		joursFeries.add((Calendar) jourFerie.clone());
		jourFerie.set(jourFerie.get(Calendar.YEAR), Calendar.AUGUST, 15);
		joursFeries.add((Calendar) jourFerie.clone());
		jourFerie.set(jourFerie.get(Calendar.YEAR), Calendar.NOVEMBER, 1);
		joursFeries.add((Calendar) jourFerie.clone());
		jourFerie.set(jourFerie.get(Calendar.YEAR), Calendar.NOVEMBER, 11);
		joursFeries.add((Calendar) jourFerie.clone());
		jourFerie.set(jourFerie.get(Calendar.YEAR), Calendar.DECEMBER, 25);
		joursFeries.add((Calendar) jourFerie.clone());

		// Calcul du jour de pâques (algorithme de Oudin (1940))
		// Calcul du nombre d'or - 1
		final int intGoldNumber = now.get(Calendar.YEAR) % 19;
		// Année divisé par cent
		final int intAnneeDiv100 = (int) (now.get(Calendar.YEAR) / 100);
		// intEpacte est = 23 - Epacte (modulo 30)
		final int intEpacte = (intAnneeDiv100 - intAnneeDiv100 / 4
				- (8 * intAnneeDiv100 + 13) / 25 + (19 * intGoldNumber) + 15) % 30;
		// Le nombre de jours à partir du 21 mars
		// pour atteindre la pleine lune Pascale
		final int intDaysEquinoxeToMoonFull = intEpacte
				- (intEpacte / 28)
				* (1 - (intEpacte / 28) * (29 / (intEpacte + 1))
						* ((21 - intGoldNumber) / 11));
		// Jour de la semaine pour la pleine lune Pascale (0=dimanche)
		final int intWeekDayMoonFull = (now.get(Calendar.YEAR)
				+ now.get(Calendar.YEAR) / 4 + intDaysEquinoxeToMoonFull + 2
				- intAnneeDiv100 + intAnneeDiv100 / 4) % 7;
		// Nombre de jours du 21 mars jusqu'au dimanche de ou
		// avant la pleine lune Pascale (un nombre entre -6 et 28)
		final int intDaysEquinoxeBeforeFullMoon = intDaysEquinoxeToMoonFull
				- intWeekDayMoonFull;
		// mois de pâques
		final int intMonthPaques = 3 + (intDaysEquinoxeBeforeFullMoon + 40) / 44;
		// jour de pâques
		final int intDayPaques = intDaysEquinoxeBeforeFullMoon + 28 - 31
				* (intMonthPaques / 4);
		// lundi de pâques
		jourFerie.set(now.get(Calendar.YEAR), intMonthPaques - 1,
				intDayPaques + 1);
		final Calendar lundiDePaque = (Calendar) jourFerie.clone();
		joursFeries.add(lundiDePaque);
		// Ascension
		final Calendar ascension = (Calendar) lundiDePaque.clone();
		ascension.add(Calendar.DATE, 38);
		joursFeries.add(ascension);
		// Pentecote
		final Calendar lundiPentecote = (Calendar) lundiDePaque.clone();
		lundiPentecote.add(Calendar.DATE, 48);
		joursFeries.add(lundiPentecote);
		return joursFeries.contains(now);
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
