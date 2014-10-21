import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.management.ImmutableDescriptor;

import de.hennroja.immoweb.api.ImmowebApi;
import de.hennroja.immoweb.api.Query;
import de.hennroja.immoweb.api.QueryResponse;
import de.hennroja.immoweb.api.SearchResult;

public class Checker {

	public static void main(String[] args) {
		ImmowebApi api = new ImmowebApi();

		try {

			QueryResponse r = api.search(Query.searchQuery()
					.type(Query.TYPE.RENT).category(Query.CATEGORY.APARTMENT)
					.clientSource("").iphone("").priceMin(399).priceMax(700)
					.postalCode("1000").postalCode("1040").postalCode("1050")
					.furnished(true));

			List<String> lines = Files.readAllLines(
					Paths.get("properties.obj"), Charset.forName("UTF-8"));

			//mads works
			float l1 = 50.8408597f;
			float l2 = 4.3709325f;

			for (SearchResult e : r.getEstates()) {
				boolean existing = false;
				for (String string : lines) {
					if (e.id == Integer.parseInt(string)) {
						existing = true;
						break;
					}
				}
				if (!existing) {
					System.out.print(e.id);
					if (e.getLat() != 0 && e.getLong() != 0) {
						System.out.print(" -> meter: "+gps2m(l1, l2, e.getLat(), e.getLong()));
					}
					System.out.println();
				}
			}

			System.out.println("done.");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static double gps2m(double lat1, double lon1, double lat2, double lon2) 
	{
		double a = 6378137, b = 6356752.314245, f = 1 / 298.257223563;
		double L = Math.toRadians(lon2 - lon1);
		double U1 = Math.atan((1 - f) * Math.tan(Math.toRadians(lat1)));
		double U2 = Math.atan((1 - f) * Math.tan(Math.toRadians(lat2)));
		double sinU1 = Math.sin(U1), cosU1 = Math.cos(U1);
		double sinU2 = Math.sin(U2), cosU2 = Math.cos(U2);
		double cosSqAlpha;
		double sinSigma;
		double cos2SigmaM;
		double cosSigma;
		double sigma;

		double lambda = L, lambdaP, iterLimit = 100;
		do 
		{
			double sinLambda = Math.sin(lambda), cosLambda = Math.cos(lambda);
			sinSigma = Math.sqrt(	(cosU2 * sinLambda)
									* (cosU2 * sinLambda)
									+ (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda)
									* (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda)
								);
			if (sinSigma == 0) 
			{
				return 0;
			}

			cosSigma = sinU1 * sinU2 + cosU1 * cosU2 * cosLambda;
			sigma = Math.atan2(sinSigma, cosSigma);
			double sinAlpha = cosU1 * cosU2 * sinLambda / sinSigma;
			cosSqAlpha = 1 - sinAlpha * sinAlpha;
			cos2SigmaM = cosSigma - 2 * sinU1 * sinU2 / cosSqAlpha;

			double C = f / 16 * cosSqAlpha * (4 + f * (4 - 3 * cosSqAlpha));
			lambdaP = lambda;
			lambda = 	L + (1 - C) * f * sinAlpha	
						* 	(sigma + C * sinSigma	
								* 	(cos2SigmaM + C * cosSigma
										* 	(-1 + 2 * cos2SigmaM * cos2SigmaM)
									)
							);
		
		} while (Math.abs(lambda - lambdaP) > 1e-12 && --iterLimit > 0);

		if (iterLimit == 0) 
		{
			return 0;
		}

		double uSq = cosSqAlpha * (a * a - b * b) / (b * b);
		double A = 1 + uSq / 16384
				* (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
		double B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));
		double deltaSigma = 
					B * sinSigma
						* (cos2SigmaM + B / 4
							* (cosSigma 
								* (-1 + 2 * cos2SigmaM * cos2SigmaM) - B / 6 * cos2SigmaM
									* (-3 + 4 * sinSigma * sinSigma)
										* (-3 + 4 * cos2SigmaM * cos2SigmaM)));
		
		double s = b * A * (sigma - deltaSigma);
		
		return s;
	}
}
