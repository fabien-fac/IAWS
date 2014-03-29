package org.iaws.classes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Arret {
	private String name;
	private Set<Destination> destinations;
	private String id;

	public Arret(String nom, String id) {
		destinations = new HashSet<Destination>();
		this.name = nom.replaceAll("\"", "");
		this.id = id.replaceAll("\"", "");;
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

	public Set<Destination> getDestinations() {
		return destinations;
	}

	public void ajouter_destination(Destination destination) {
		destinations.add(destination);
	}

	public List<Ligne> get_lignes() {
		List<Ligne> liste = new ArrayList<Ligne>();

		for (Destination destination : destinations) {
			for (Ligne ligne : destination.getLignes()) {
				liste.add(ligne);
			}
		}

		return liste;
	}
	
	public List<String> get_lignes_string() {
		List<String> liste = new ArrayList<String>();

		for (Destination destination : destinations) {
			for (Ligne ligne : destination.getLignes()) {
				liste.add(ligne.getLigne());
			}
		}

		return liste;
	}
	
	public boolean is_arret_get_ligne(String ligneName){
		for (Destination destination : destinations) {
			for (Ligne ligne : destination.getLignes()) {
				if(ligne.getLigne().equals(ligneName)){
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof Arret) {

			Arret other = (Arret) obj;

			if (other.id.equals(this.id)) {
				return true;
			}
		}

		return false;
	}

}
