package org.iaws.webservices;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.GsonBuilder;

import android.util.Log;

public class WebService {

	private final String KEY_TISSEO = "a03561f2fd10641d96fb8188d209414d8";
	private final String URL_TISSEO = "http://pt.data.tisseo.fr/";

	private final String KEY_JCDECAUX = "9e6c731e4916e512a85e4de995de0d90462d5cf5";
	private final String URL_JCDECAUX = "https://api.jcdecaux.com/vls/v1/";

	private final String URL_LIKE = "http://fabienserver.dyndns.org:5984/";

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
		String url_like = URL_LIKE + "like_unlike/_all_docs?include_docs=true";
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

	public String send_like_unlike(String id, String nbLike, String nbUnlike,
			String rev) {
		String url_like = URL_LIKE + "like_unlike/" + id;
		String json = paramLikeToStringJson(id, nbLike, nbUnlike, rev);
		HttpClient httpCli = new DefaultHttpClient();

		try {
			HttpPut httpPut = new HttpPut(url_like);
			httpPut.setEntity(new StringEntity(json));
			httpPut.setHeader("Accept", "application/json");
			httpPut.setHeader("Content-type", "application/json");
			HttpResponse response = httpCli.execute(httpPut);
			if (response.getStatusLine().getStatusCode() == 201) {
				InputStream inputStream = response.getEntity().getContent();
				// Vérification de l'inputStream
				if (inputStream != null) {
					java.util.Scanner s = new java.util.Scanner(inputStream)
							.useDelimiter("\\A");
					return (s.hasNext() ? s.next() : "");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("WebService", "Impossible d'envoyer les like et unlike");
		}

		return null;
	}

	private String paramLikeToStringJson(String id, String nbLike,
			String nbUnlike, String rev) {
		Map<String, String> comment = new HashMap<String, String>();
		comment.put("_id", id);
		comment.put("like", nbLike);
		comment.put("unlike", nbUnlike);
		if (!rev.equals("null")) {
			comment.put("_rev", rev);
		}
		String json = new GsonBuilder().create().toJson(comment, Map.class);

		return json;
	}

}