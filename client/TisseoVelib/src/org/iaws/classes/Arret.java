package org.iaws.classes;

import java.util.HashMap;
import java.util.Map;

public class Arret {
	private String name;
	private Map<String, Destination> destinations;
	private String id;
	
	public Arret(String nom, String id){
		destinations = new HashMap<String, Destination>();
		this.name = nom;
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, Destination> getDestinations() {
		return destinations;
	}
	
	public void ajouter_destination(Destination destination){
		destinations.put(destination.getId(), destination);
	}
	
	
}
