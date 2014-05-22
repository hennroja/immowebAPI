package de.hennroja.immoweb.api;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
/**
 * The Java API offers the ability to send search Query 
 * to immoweb.be.
 * 
 * @author hennroja
 *
 */
public class ImmowebApi {

	// Demo Start
	public static void main(String[] args) {
		ImmowebApi api = new ImmowebApi();

		// Build a query
		SearchQuery query = Query.searchQuery()
				.type(Query.TYPE.RENT)
				.iphone("")
				.clientSource("")
				.category(Query.CATEGORY.APARTMENT)
				.postalCode("1000")
				.postalCode("1050")
				.priceMin(400)
				.priceMax(700)
				.furnished(true);

		try {
			
			QueryResponse answer = api.fire(query);
			ArrayList<SearchResult> res = answer.getEstates();

			for (SearchResult s : res) {

				System.out.println("------ ID: " + s.id + " TYPE: " + s.type
						+ " in " + s.postal + " ------");
				System.out.println("Area: " + s.area + "m^2 with "
						+ s.numBedrooms + " bedroom(s) for " + s.price
						+ " Euro");
				System.out
						.println("LAT: " + s.getLat() + " LON:" + s.getLong());
				System.out.println("Image: " + s.image);
				System.out
						.println("-------------------------------------------------------------------");
			}
			System.out.println("TOTAL: " + res.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Demo End
	

	static final String DEFAULT_ENCODING = "utf-8";

	public QueryResponse fire(SearchQuery config) throws IOException {

		//prepair http request
		URL url = new URL(config.query);
		HttpURLConnection connection;

		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		HttpURLConnection.setFollowRedirects(false);

		// fire http request and handle response
		BufferedInputStream bis = new BufferedInputStream(
				connection.getInputStream());
		StringBuffer content = new StringBuffer();

		int read = 0;
		while (true) {
			read = bis.read();
			if (read == -1) {
				break;
			}
			content.append((char) read);
		}

		// strip slashes
		String stripContent = content.toString().replace("\\", "");

		// JSON wrapping
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		QueryResponse response = objectMapper.readValue(stripContent,
				QueryResponse.class);

		return response;
	}

}
