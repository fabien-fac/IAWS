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
import org.iaws.classes.Ligne;
import org.iaws.classes.LikeUnlike;
import org.iaws.classes.ProchainPassage;
import org.iaws.classes.Station;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ParserJson {

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

		JsonArray lines = objDest.getAsJsonArray("line");
		for (JsonElement elemLine : lines) {
			Ligne ligne = jsonElementToLigne(elemLine);
			ligne.setDestination(destination);
			destination.ajouter_ligne(ligne);
		}

		return destination;
	}

	private Ligne jsonElementToLigne(JsonElement elemLine) {

		JsonObject objLine = elemLine.getAsJsonObject();
		String name = objLine.get("name").toString();
		String id = objLine.get("id").toString();
		String numero = objLine.get("shortName").toString();

		Ligne ligne = new Ligne(name, id, numero);
		ligne.setBgXmlColor(objLine.get("bgXmlColor").toString());
		ligne.setColor(objLine.get("color").toString());
		ligne.setFgXmlColor(objLine.get("fgXmlColor").toString());

		return ligne;
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
			// TODO Auto-generated catch block
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
		int nbVeloDispo = Integer.parseInt(obj.get("available_bikes").toString());
		String ouverte = obj.get("status").toString();
		station = new Station(name, address, totalVelo, nbVeloDispo, ouverte);

		return station;
	}
	
	public Map<String, LikeUnlike> jsonToMapLike(String json){
		Map<String, LikeUnlike> map = new HashMap<String, LikeUnlike>();

		JsonElement jelement = new JsonParser().parse(json);
		JsonObject jobject = jelement.getAsJsonObject();
		
		JsonArray jarray = jobject.getAsJsonArray("rows");

		for (JsonElement jsonElement : jarray) {
			LikeUnlike likeUnlike = jsonElementToLikeUnlike(jsonElement);
			JsonObject obj = jsonElement.getAsJsonObject();
			String id = obj.get("id").toString();
			map.put(id, likeUnlike);
		}
		return map;
	}
	
	private LikeUnlike jsonElementToLikeUnlike(JsonElement elem) {
		JsonObject obj = elem.getAsJsonObject();
		JsonObject doc = obj.getAsJsonObject("doc");
		int nbLike = Integer.parseInt(doc.get("like").toString());
		int nbUnlike = Integer.parseInt(doc.get("unlike").toString());
		LikeUnlike likeUnlike = new LikeUnlike(nbLike, nbUnlike);
		return likeUnlike;
	}
}
