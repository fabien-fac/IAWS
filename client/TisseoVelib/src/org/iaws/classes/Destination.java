package org.iaws.classes;

import java.util.HashSet;
import java.util.Set;

public class Destination {

	private Set<Ligne> lignes;
	private String cityName;
	private String id;
	private String name;
	private Arret arret;

	public Destination(String nom, String id) {
		lignes = new HashSet<Ligne>();
		this.name = nom.replaceAll("\"", "");
		this.id = id.replaceAll("\"", "");
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName.replaceAll("\"", "");
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

	public Set<Ligne> getLignes() {
		return lignes;
	}

	public void ajouter_ligne(Ligne ligne) {
		lignes.add(ligne);
	}
	
	public Arret getArret() {
		return arret;
	}

	public void setArret(Arret arret) {
		this.arret = arret;
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
		if (obj instanceof Destination) {

			Destination other = (Destination) obj;

			if (other.id.equals(this.id)) {
				return true;
			}
		}

		return false;
	}

}
