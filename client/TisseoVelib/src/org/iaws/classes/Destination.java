package org.iaws.classes;

import java.util.HashMap;
import java.util.Map;

public class Destination {
	
	private Map<String, Ligne> lignes;
	private String cityName;
	private String id;
	private String name;
	
	public Destination(String nom, String id){
		lignes = new HashMap<String, Ligne>();
		this.name = nom;
		this.id = id;
	}
	
	
	
	public String getCityName() {
		return cityName;
	}



	public void setCityName(String cityName) {
		this.cityName = cityName;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public Map<String, Ligne> getLignes() {
		return lignes;
	}



	public void ajouter_ligne(Ligne ligne){
		lignes.put(ligne.getId(), ligne);
	}
	
	
}
