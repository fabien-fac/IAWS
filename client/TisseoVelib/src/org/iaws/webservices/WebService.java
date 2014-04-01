package org.iaws.webservices;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class WebService {

	private final String KEY_TISSEO = "a03561f2fd10641d96fb8188d209414d8";
	private final String URL_TISSEO = "http://pt.data.tisseo.fr/";

	private final String KEY_JCDECAUX = "9e6c731e4916e512a85e4de995de0d90462d5cf5";
	private final String URL_JCDECAUX = "https://api.jcdecaux.com/vls/v1/";

	private final String URL_LIKE = "http://localhost:5984/";

	private InputStream sendRequest(URL url) throws IOException {

		// Ouverture de la connexion
		HttpURLConnection urlConnection = (HttpURLConnection) url
				.openConnection();

		// Connexion à l'url
		urlConnection.connect();

		// Si le serveur nous répond avec un code OK
		if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			return urlConnection.getInputStream();
		}

		return null;

	}

	public String get_arrets() {

		String url_arret = URL_TISSEO
				+ "stopPointsList?bbox=1.4685963%2C43.5734438%2C1.4572666%2C43.5573361&format=json&displayLines=1&key="
				+ KEY_TISSEO;

		try {
			// Envoie de la requête
			InputStream inputStream = sendRequest(new URL(url_arret));

			// Vérification de l'inputStream
			if (inputStream != null) {
				java.util.Scanner s = new java.util.Scanner(inputStream)
						.useDelimiter("\\A");
				return s.hasNext() ? s.next() : "";
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("WebService", "Impossible de récupérer les arrets");
		}
		return null;

	}

	public String get_horaires(String numLigne, String numArret) {

		String url_arret = URL_TISSEO + "departureBoard?stopPointId="
				+ numArret + "&format=json&displayRealTime=1&lineId="
				+ numLigne + "&key=" + KEY_TISSEO;

		try {
			// Envoie de la requête
			InputStream inputStream = sendRequest(new URL(url_arret));

			// Vérification de l'inputStream
			if (inputStream != null) {
				java.util.Scanner s = new java.util.Scanner(inputStream)
						.useDelimiter("\\A");
				return s.hasNext() ? s.next() : "";
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("WebService",
					"Impossible de récupérer les horaires de passage");
		}
		return null;

	}

	public String get_stations() {
		String url_station = URL_JCDECAUX + "stations?apiKey=" + KEY_JCDECAUX
				+ "&contract=TOulouse";

		try {
			// Envoie de la requête
			InputStream inputStream = sendRequest(new URL(url_station));

			// Vérification de l'inputStream
			if (inputStream != null) {
				java.util.Scanner s = new java.util.Scanner(inputStream)
						.useDelimiter("\\A");
				return s.hasNext() ? s.next() : "";
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("WebService", "Impossible de récupérer les stations");
		}
		return null;
	}

	public String get_like_unlike() {
		String url_like = URL_LIKE + "";

		try {
			// Envoie de la requête
			InputStream inputStream = sendRequest(new URL(url_like));

			// Vérification de l'inputStream
			if (inputStream != null) {
				java.util.Scanner s = new java.util.Scanner(inputStream)
						.useDelimiter("\\A");
				return s.hasNext() ? s.next() : "";
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("WebService", "Impossible de récupérer les like et unlike");
		}
		return null;
	}

	public String send_like_unlike(String id, String type) {
		String url_like = URL_LIKE + "";

		try {
			// Envoie de la requête
			InputStream inputStream = sendRequest(new URL(url_like));

			// Vérification de l'inputStream
			if (inputStream != null) {
				java.util.Scanner s = new java.util.Scanner(inputStream)
						.useDelimiter("\\A");
				return s.hasNext() ? s.next() : "";
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("WebService", "Impossible d'envoyer les like et unlike");
		}
		return null;
	}

}