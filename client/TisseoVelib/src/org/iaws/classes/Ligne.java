package org.iaws.classes;

import org.iaws.interfaces.Fiabilite;

public class Ligne implements Fiabilite{
	private String name;
	private String ligne;
	private String color;
	private String bgXmlColor;
	private String fgXmlColor;
	private int nbLike;
	private int nbUnlike;
	private String id;
	private Destination destination;

	public Ligne(String nom, String id, String ligne){
		this.name = nom.replaceAll("\"", "");
		this.id = id.replaceAll("\"", "");
		this.ligne = ligne.replaceAll("\"", "");
		this.nbLike = 0;
		this.nbUnlike = 0;
	}

	@Override
	public int get_nb_like() {
		return this.nbLike;
	}

	@Override
	public int get_nb_unlike() {
		return this.nbUnlike;
	}
	
	@Override
	public void ajout_like(int nb) {
		nbLike += nb;
	}

	@Override
	public void ajout_unlike(int nb) {
		nbUnlike += nb;
	}
	
	@Override
	public void suppr_like(int nb) {
		if((nbLike - nb) < 0){
			nbLike = 0;
		}
		else{
			nbLike -= nb;
		}
	}

	@Override
	public void suppr_unlike(int nb) {
		if((nbUnlike - nb) < 0){
			nbUnlike = 0;
		}
		else{
			nbUnlike -= nb;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLigne() {
		return ligne;
	}

	public void setLigne(String ligne) {
		this.ligne = ligne;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color.replaceAll("\"", "");
	}

	public String getBgXmlColor() {
		return bgXmlColor;
	}

	public void setBgXmlColor(String bgXmlColor) {
		this.bgXmlColor = bgXmlColor.replaceAll("\"", "");
	}

	public String getFgXmlColor() {
		return fgXmlColor;
	}

	public void setFgXmlColor(String fgXmlColor) {
		this.fgXmlColor = fgXmlColor.replaceAll("\"", "");
	}

	public int getNbLike() {
		return nbLike;
	}

	public void setNbLike(int nbLike) {
		this.nbLike = nbLike;
	}

	public int getNbUnlike() {
		return nbUnlike;
	}

	public void setNbUnlike(int nbUnlike) {
		this.nbUnlike = nbUnlike;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}
	
	@Override
	public String toString() {
		String str = this.ligne + " : déstination : " + this.destination.getName();
		return str;
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
		if (obj instanceof Ligne) {

			Ligne other = (Ligne) obj;

			if (other.id.equals(this.id)) {
				return true;
			}
		}

		return false;
	}
	
}
