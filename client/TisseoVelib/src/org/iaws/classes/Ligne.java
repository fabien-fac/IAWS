package org.iaws.classes;

import org.iaws.interfaces.Fiabilite;

public class Ligne implements Fiabilite{

	private int nbLike;
	private int nbUnlike;
	private String id;
	private String name;
	private String color;
	private String bgXmlColor;
	private String fgXmlColor;
	private Destination destination;

	public Ligne(String nom, String id, String ligne){
		this.name = nom.replaceAll("\"", "");
		this.id = id.replaceAll("\"", "");
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getBgXmlColor() {
		return bgXmlColor;
	}

	public void setBgXmlColor(String bgXmlColor) {
		this.bgXmlColor = bgXmlColor;
	}

	public String getFgXmlColor() {
		return fgXmlColor;
	}

	public void setFgXmlColor(String fgXmlColor) {
		this.fgXmlColor = fgXmlColor;
	}

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public int getNbLike() {
		return nbLike;
	}

	public int getNbUnlike() {
		return nbUnlike;
	}

	public void ajout_like(int nb) {
		nbLike += nb;
	}

	public void ajout_unlike(int nb) {
		nbUnlike += nb;
	}
	
	public void suppr_like(int nb) {
		if((nbLike - nb) < 0){
			nbLike = 0;
		}
		else{
			nbLike -= nb;
		}
	}

	public void suppr_unlike(int nb) {
		if((nbUnlike - nb) < 0){
			nbUnlike = 0;
		}
		else{
			nbUnlike -= nb;
		}
	}

	public void setNbLike(int nbLike) {
		this.nbLike = nbLike;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int get_nb_like() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int get_nb_unlike() {
		// TODO Auto-generated method stub
		return 0;
	}
}
