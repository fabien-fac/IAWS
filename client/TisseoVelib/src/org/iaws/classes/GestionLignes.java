package org.iaws.classes;

import java.util.HashMap;
import java.util.Map;

public class GestionLignes {

	private static GestionLignes instance;
	private static Map<String, Ligne> lignes;

	private GestionLignes() {
		lignes = new HashMap<String, Ligne>();
	}

	public static GestionLignes get_instance() {
		if (instance == null) {
			instance = new GestionLignes();
		}

		return instance;
	}

	public void ajouter_ligne(Ligne ligne) {
		if (!lignes.containsKey(ligne.getNumLigne())) {
			lignes.put(ligne.getNumLigne(), ligne);
		}
	}

	public boolean is_ligne_exists(String idLigne) {
		return lignes.containsKey(idLigne);
	}

	public Ligne get_ligne(String idligne) {
		if (lignes.containsKey(idligne)) {
			return lignes.get(idligne);
		}

		return null;
	}

	public void ajout_like(String id) {
		if (lignes.containsKey(id)) {
			lignes.get(id).ajout_like(1);
		}
	}

	public void ajout_unlike(String id) {
		if (lignes.containsKey(id)) {
			lignes.get(id).ajout_unlike(1);
		}
	}

	public int get_nb_like(String id) {
		if (lignes.containsKey(id)) {
			return lignes.get(id).get_nb_like();
		}

		return 0;
	}

	public int get_nb_unlike(String id) {
		if (lignes.containsKey(id)) {
			return lignes.get(id).get_nb_unlike();
		}

		return 0;
	}

}
