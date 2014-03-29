package org.iaws.model;

public class LigneItem {

	private String nomArret;
	private String nomLigne;
	private String color;
	private String bgXmlColor;
	private String fgXmlColor;
	private String destination;
	
	public LigneItem(String arret, String ligne, String color, String bgcolor, String fgColor, String destination){
		this.nomArret = arret;
		this.nomLigne = ligne;
		this.color = color;
		this.bgXmlColor = bgcolor;
		this.fgXmlColor = fgColor;
		this.destination = destination;
	}

	public String getNomArret() {
		return nomArret;
	}

	public String getNomLigne() {
		return nomLigne;
	}

	public String getColor() {
		return color;
	}

	public String getBgXmlColor() {
		return bgXmlColor;
	}

	public String getFgXmlColor() {
		return fgXmlColor;
	}
	
	public String getDestination() {
		return destination;
	}
}
