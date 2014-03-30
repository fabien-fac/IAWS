package org.iaws.model;

public class LigneItem {

	private String nomArret;
	private String nomLigne;
	private String bgXmlColor;
	private String fgXmlColor;
	private String destination;
	private String idArret;
	private String idLigne;
	
	public LigneItem(String arret, String ligne, String bgcolor, String fgColor, String destination, String idArret, String idLigne){
		this.nomArret = arret;
		this.nomLigne = ligne;
		this.bgXmlColor = bgcolor;
		this.fgXmlColor = fgColor;
		this.destination = destination;
		this.idArret = idArret;
		this.idLigne = idLigne;
	}

	public String getNomArret() {
		return nomArret;
	}

	public String getNomLigne() {
		return nomLigne;
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

	public String getIdArret() {
		return idArret;
	}

	public String getIdLigne() {
		return idLigne;
	}

}
