package org.iaws.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iaws.classes.Arret;
import org.iaws.classes.Destination;
import org.iaws.classes.GestionLignes;
import org.iaws.classes.Ligne;
import org.iaws.classes.Poteau;
import org.iaws.classes.LikeUnlike;
import org.iaws.classes.ProchainPassage;
import org.iaws.classes.Station;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ParserJson {

	private GestionLignes gestionLignes;

	public ParserJson() {
		gestionLignes = GestionLignes.get_instance();
	}

	public List<Arret> jsonToListArretBus(String json) {

		List<Arret> liste = new ArrayList<Arret>();

		JsonElement jelement = new JsonParser().parse(json);
		JsonObject jobject = jelement.getAsJsonObject();
		jobject = jobject.getAsJsonObject("physicalStops");
		JsonArray jarray = jobject.getAsJsonArray("physicalStop");

		for (JsonElement jsonElement : jarray) {
			Arret arret = jsonElementToArret(jsonElement);
			liste.add(arret);
		}

		return liste;
	}

	private Arret jsonElementToArret(JsonElement elem) {
		Arret arret;

		JsonObject obj = elem.getAsJsonObject();
		String name = obj.get("name").toString();
		String id = obj.get("id").toString();

		arret = new Arret(name, id);

		JsonArray destinations = obj.getAsJsonArray("destinations");
		for (JsonElement elemDest : destinations) {
			Destination destination = jsonElementToDestination(elemDest);
			destination.setArret(arret);
			arret.ajouter_destination(destination);
		}

		return arret;
	}

	private Destination jsonElementToDestination(JsonElement elemDest) {
		JsonObject objDest = elemDest.getAsJsonObject();

		String name = objDest.get("name").toString();
		String id = objDest.get("id").toString();

		Destination destination = new Destination(name, id);
		destination.setCityName(objDest.get("cityName").toString());

		JsonArray poteaux = objDest.getAsJsonArray("line");
		for (JsonElement elemPoteau : poteaux) {
			Poteau poteau = jsonElementToPoteau(elemPoteau);
			poteau.setDestination(destination);
			destination.ajouter_poteau(poteau);
		}

		return destination;
	}

	private Poteau jsonElementToPoteau(JsonElement elemLine) {

		JsonObject objLine = elemLine.getAsJsonObject();
		String name = objLine.get("name").toString();
		String id = objLine.get("id").toString();
		String numero = objLine.get("shortName").toString()
				.replaceAll("\"", "");

		Ligne ligne;

		if (!gestionLignes.is_ligne_exists(numero)) {
			ligne = new Ligne(name, id, numero);
			ligne.setBgXmlColor(objLine.get("bgXmlColor").toString());
			ligne.setColor(objLine.get("color").toString());
			ligne.setFgXmlColor(objLine.get("fgXmlColor").toString());
			gestionLignes.ajouter_ligne(ligne);
		} else {
			ligne = gestionLignes.get_ligne(id);
		}

		Poteau poteau = new Poteau(numero);

		return poteau;
	}

	public List<ProchainPassage> jsonToListProchainPassage(String json) {
		List<ProchainPassage> liste = new ArrayList<ProchainPassage>();

		JsonElement jelement = new JsonParser().parse(json);
		JsonObject jobject = jelement.getAsJsonObject();
		jobject = jobject.getAsJsonObject("departures");
		String idArret = jobject.getAsJsonObject().get("stop")
				.getAsJsonObject().get("id").toString();

		JsonArray jarray = jobject.getAsJsonArray("departure");
		for (JsonElement jsonElement : jarray) {
			try {
				ProchainPassage prochainPassage = new ProchainPassage();
				prochainPassage.setIdArret(idArret);
				prochainPassage = jsonElementToProchainPassage(jsonElement,
						prochainPassage);
				liste.add(prochainPassage);
			} catch (java.lang.NullPointerException e) {

			}
		}
		return liste;
	}

	private ProchainPassage jsonElementToProchainPassage(JsonElement elem,
			ProchainPassage prochainPassage) {
		JsonObject obj = elem.getAsJsonObject();
		JsonObject ligne = obj.getAsJsonObject("line");
		prochainPassage.setIdLigne(ligne.get("shortName").toString());
		prochainPassage.setDestination(obj.get("destination").getAsJsonArray()
				.get(0).getAsJsonObject().get("name").toString());
		String prochainPassageString = obj.get("dateTime").toString()
				.replaceAll("\"", "");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date prochainHoraire = new Date();
		try {
			prochainHoraire = sdf.parse(prochainPassageString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		prochainPassage.setProchainPassage(prochainHoraire);

		return prochainPassage;
	}

	public List<Station> jsonToListStation(String json) {

		List<Station> liste = new ArrayList<Station>();

		JsonElement jelement = new JsonParser().parse(json);
		JsonArray jArray = jelement.getAsJsonArray();

		for (JsonElement jsonElement : jArray) {
			Station station = jsonElementToStation(jsonElement);
			liste.add(station);
		}
		return liste;
	}

	private Station jsonElementToStation(JsonElement elem) {
		Station station;

		JsonObject obj = elem.getAsJsonObject();
		String name = obj.get("name").toString();
		String address = obj.get("address").toString();
		int totalVelo = Integer.parseInt(obj.get("bike_stands").toString());
		int nbVeloDispo = Integer.parseInt(obj.get("available_bikes")
				.toString());
		String ouverte = obj.get("status").toString();
		String idStation = obj.get("number").toString();
		station = new Station(name, address, totalVelo, nbVeloDispo, ouverte,
				idStation);

		return station;
	}

	public Map<String, LikeUnlike> jsonToMapLike(String json) {
		Map<String, LikeUnlike> map = new HashMap<String, LikeUnlike>();

		JsonElement jelement = new JsonParser().parse(json);
		JsonObject jobject = jelement.getAsJsonObject();

		JsonArray jarray = jobject.getAsJsonArray("rows");

		for (JsonElement jsonElement : jarray) {
			LikeUnlike likeUnlike = jsonElementToLikeUnlike(jsonElement);
			JsonObject obj = jsonElement.getAsJsonObject();
			String id = obj.get("id").toString().replaceAll("\"", "");
			map.put(id, likeUnlike);
		}
		return map;
	}

	private LikeUnlike jsonElementToLikeUnlike(JsonElement elem) {
		JsonObject obj = elem.getAsJsonObject();
		JsonObject doc = obj.getAsJsonObject("doc");
		int nbLike = Integer.parseInt(doc.get("like").toString()
				.replaceAll("\"", ""));
		int nbUnlike = Integer.parseInt(doc.get("unlike").toString()
				.replaceAll("\"", ""));
		String rev = doc.get("_rev").toString().replaceAll("\"", "");
		String id = doc.get("_id").toString().replaceAll("\"", "");
		LikeUnlike likeUnlike = new LikeUnlike(id, nbLike, nbUnlike, rev);
		return likeUnlike;
	}

	public LikeUnlike jsonToLikeUnlike(String json) {

		JsonElement jelement = new JsonParser().parse(json);
		JsonObject obj = jelement.getAsJsonObject();

		String rev = obj.get("rev").toString().replaceAll("\"", "");
		String id = obj.get("id").toString().replaceAll("\"", "");
		LikeUnlike likeUnlike = new LikeUnlike(id, 0, 0, rev);
		return likeUnlike;
	}

	public String jsonToTempsTrajet(String json) {
		String tempsTrajet = "";
		JsonElement jelement = new JsonParser().parse(json);
		JsonObject jobject = jelement.getAsJsonObject();
		JsonArray routes = jobject.getAsJsonArray("routes");
		JsonObject legs = routes.get(0).getAsJsonObject();
		JsonArray tabLegs = legs.get("legs").getAsJsonArray();
		tempsTrajet = tabLegs.get(0).getAsJsonObject().get("duration")
				.getAsJsonObject().get("text").toString();
		System.out.println("temps trajet : " + tempsTrajet);
		return tempsTrajet;
	}

	public String jsonElementToIdStop(String json) {
		String idStop = "";
		JsonElement jelement = new JsonParser().parse(json);
		JsonObject jobject = jelement.getAsJsonObject();
		JsonObject places = jobject.get("placesList").getAsJsonObject();
		JsonArray place = places.get("place").getAsJsonArray();
		if(place.size()>0){
			JsonObject bb = place.get(0).getAsJsonObject();
			idStop = bb.get("id").toString();
			// String tempsTrajet = legs.get(0).getAsJsonObject().get(
			System.out.println("idStop " + idStop);
		}
		return idStop;
	}
	
	public List<String> jsonToListLigne(String json) {

		List<String> liste = new ArrayList<String>();

		JsonElement jelement = new JsonParser().parse(json);
		JsonObject jobject = jelement.getAsJsonObject();
		jobject = jobject.getAsJsonObject("physicalStops");
		JsonArray jarray = jobject.getAsJsonArray("physicalStop");

		for (JsonElement jsonElement : jarray) {
			JsonArray destinations = jsonElement.getAsJsonObject().getAsJsonArray("destinations");
			for (JsonElement jsonDest : destinations) {
				JsonArray lignes = jsonDest.getAsJsonObject().getAsJsonArray("line");
				for (JsonElement jsonligne : lignes) {
					String ligne = jsonligne.getAsJsonObject().get("shortName").toString();
					if (!liste.contains(ligne)){
						liste.add(ligne.replace("\"", ""));
					}
				}
			}
		}
		return liste;
	}
	
}
