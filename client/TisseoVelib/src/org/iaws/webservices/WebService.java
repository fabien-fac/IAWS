package org.iaws.webservices;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class WebService {

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

		String url_arret = "http://pt.data.tisseo.fr/stopPointsList?bbox=1.4685963%2C43.5734438%2C1.4572666%2C43.5573361&format=json&displayLines=1&key=a03561f2fd10641d96fb8188d209414d8";
		
		try {
			// Envoie de la requête
			InputStream inputStream = sendRequest(new URL(url_arret));

			// Vérification de l'inputStream
			if (inputStream != null) {				
				java.util.Scanner s = new java.util.Scanner(inputStream).useDelimiter("\\A");
			    return s.hasNext() ? s.next() : "";
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("WebService", "Impossible de récupérer les arrets");
		}
		return null;

		
	}

}