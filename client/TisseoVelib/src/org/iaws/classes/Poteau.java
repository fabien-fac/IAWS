package org.iaws.classes;

public class Poteau {
	private String numLigne;
	private Destination destination;
	private GestionLignes gestionLigne;

	public Poteau(String numLigne) {
		this.numLigne = numLigne;
		gestionLigne = GestionLignes.get_instance();
	}

	public String getNumLigne() {
		return numLigne;
	}

	public Ligne getLigne() {
		return GestionLignes.get_instance().get_ligne(numLigne);
	}

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public void like() {
		gestionLigne.ajout_like(numLigne);
	}

	public void unlike() {
		gestionLigne.ajout_unlike(numLigne);
	}

	public int get_nb_like() {
		return gestionLigne.get_nb_like(numLigne);
	}

	public int get_nb_unlike() {
		return gestionLigne.get_nb_unlike(numLigne);
	}

	public void ajout_like_unlike(LikeUnlike likeUnlike) {
		Ligne ligne = getLigne();
		ligne.set_nb_like(likeUnlike.getLike());
		ligne.set_nb_unlike(likeUnlike.getUnlike());
		ligne.set_rev(likeUnlike.getRev());
	}

	public String get_rev() {
		return getLigne().get_rev();
	}
	
	@Override
	public String toString() {
		return "Poteau qui a pour ligne : " + numLigne;
	}
}
