package org.iaws.parser;

import java.util.ArrayList;
import java.util.List;

import org.iaws.classes.Arret;
import org.iaws.classes.Destination;
import org.iaws.classes.Ligne;

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
}
