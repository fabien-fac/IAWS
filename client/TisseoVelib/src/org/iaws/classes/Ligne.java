package org.iaws.classes;

import org.iaws.interfaces.Fiabilite;

public class Ligne implements Fiabilite {

	private int nbLike;
	private int nbUnlike;
	private String id;
	private String name;
	private String color;
	private String bgXmlColor;
	private String fgXmlColor;
	private String numLigne;
	private String rev;

	public Ligne(String nom, String id, String ligne) {
		this.name = nom.replaceAll("\"", "");
		this.id = id.replaceAll("\"", "");
		this.numLigne = ligne;
		this.rev = "null";
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

	public void ajout_like(int nb) {
		nbLike += nb;
	}

	public void ajout_unlike(int nb) {
		nbUnlike += nb;
	}

	public void suppr_like(int nb) {
		if ((nbLike - nb) < 0) {
			nbLike = 0;
		} else {
			nbLike -= nb;
		}
	}

	public void suppr_unlike(int nb) {
		if ((nbUnlike - nb) < 0) {
			nbUnlike = 0;
		} else {
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

	public String getNumLigne() {
		return numLigne;
	}

	@Override
	public int get_nb_like() {
		return nbLike;
	}

	@Override
	public int get_nb_unlike() {
		return nbUnlike;
	}
	
	@Override
	public void set_nb_like(int nb) {
		nbLike = nb;
	}

	@Override
	public void set_nb_unlike(int nb) {
		nbUnlike = nb;
	}

	public void set_rev(String rev) {
		this.rev = rev;
	}

	public String get_rev() {
		return rev;
	}

}
