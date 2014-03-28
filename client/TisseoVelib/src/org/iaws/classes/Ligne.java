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
	
	public Ligne(String nom, String id, String ligne){
		this.name = nom;
		this.id = id;
		this.ligne = ligne;
	}

	@Override
	public int get_nb_like() {
		return this.nbLike;
	}

	@Override
	public int get_nb_unlike() {
		return this.nbUnlike;
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
	
	
}
