package de.hennroja.immoweb.api;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
/**
 * The Java API offers the ability to send search Query 
 * to immoweb.be.
 * 
 * @author hennroja
 *
 */
public class ImmowebApi {

	static final String DEFAULT_ENCODING = "utf-8";

	public QueryResponse search(SearchQuery config) throws IOException {

		//prepair http request
		URL url = new URL(config.query);
		HttpURLConnection connection;

		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		HttpURLConnection.setFollowRedirects(true);
		connection.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		connection.addRequestProperty("Host", "www.immoweb.be");
		connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:29.0) Gecko/20100101 Firefox/29.0");
		connection.addRequestProperty("Accept-Language", "de,en-US;q=0.7,en;q=0.3");
		connection.addRequestProperty("Accept-Encoding", "deflate");
		//connection.addRequestProperty("Cookie", "CFID=51881495; Domain=.immoweb.be; Expires=Sat, 21-May-2044 15:16:28 GMT; Path=/; HttpOnlyCFTOKEN=e4114546cc42f792-D58907F6-D89D-672C-B120815F2AC10608; Domain=.immoweb.be; Expires=Sat, 21-May-2044 15:16:28 GMT; Path=/; HttpOnlyJSESSIONID=C9AC09CCC68A24C2A2889BAC407C63FA.cfusion; Path=/; HttpOnlyLANGUAGE=en; Expires=Sat, 21-May-2044 15:16:28 GMT; Path=/CFID=\"\"; Domain=.immoweb.be; Expires=Sat, 21-May-2044 15:16:28 GMT; Path=/; HttpOnlyCFTOKEN=\"\"; Domain=.immoweb.be; Expires=Sat, 21-May-2044 15:16:28 GMT; Path=/; HttpOnlyCFID=51881497; Domain=.immoweb.be; Expires=Sat, 21-May-2044 15:16:28 GMT; Path=/; HttpOnlyCFTOKEN=593de5ec28c05e87-D58913E8-D89D-672C-B120957747EC9711; Domain=.immoweb.be; Expires=Sat, 21-May-2044 15:16:28 GMT; Path=/; HttpOnlyLANGUAGE=en; Expires=Sat, 21-May-2044 15:16:28 GMT; Path=/IWEBCHECK=Y; Expires=Sat, 21-May-2044 15:16:28 GMT; Path=/NPI=22%2CParVisit%2CP%2C29%2D05%2D2014%2C1; Expires=Sat, 21-May-2044 15:16:28 GMT; Path=/CKVISITCOUNT=1; Expires=Mon, 28-Jul-2014 15:16:28 GMT; Path=/CKVISITDATELAST=201405291716; Expires=Mon, 28-Jul-2014 15:16:28 GMT; Path=/CKIDBIEN=\"\"; Expires=Thu, 01-Jan-1970 00:00:10 GMT; Path=/CKSECTIONQUERYDATA=rent; Path=/visid_incap_150286=H4Y4Gt65TqCNi9aSiGcd0J1Qh1MAAAAAQUIPAAAAAABnBfFpK1mF7NU9torwFgk+; expires=Sat, 28 May 2016 14:27:02 GMT; path=/; Domain=.immoweb.beincap_ses_108_150286=Yn/GS1cg/lisiQ11XbN/AZ1Qh1MAAAAA9QuX/3+RMT/ImB+l/y+zhg==; path=/; Domain=.immoweb.be___utmvblcuRzmF=a; Max-Age=0; path=/; expires=Tue, 27 May 2014 14:23:45 GMT___utmvalcuRzmF=a; Max-Age=0; path=/; expires=Tue, 27 May 2014 14:23:45 GMT___utmvmlcuRzmF=a; Max-Age=0; path=/; expires=Tue, 27 May 2014 14:23:45 GMTvisid_incap_150286=H4Y4Gt65TqCNi9aSiGcd0J1Qh1MAAAAAQkIPAAAAAACAemNkAVtCpC/qfJ1Rn++ie+ImoSN/Cy4f; expires=Sat, 28 May 2016 14:27:02 GMT; path=/; Domain=.immoweb.be");
		connection.addRequestProperty("Cookie","BIMvisited=yes; CheckCookie=yes; optimizelyEndUserId=oeu1399131516742r0.2689438485272466; __gads=ID=ff53c0490bd5496a:T=1399131518:S=ALNI_MZfNM3uqMnH1UDBP6CyDyIDlP3mfg; __gfp_64b=8Zd6xNqy8uD5mvM50k0ldVhWpEHP0u19p6O2lJtX66f.h7; Immoweb_persist=799294043.16096.1011510248.1137446592; IWEB_OPTIN=Y; nlbi_150286=KvuRRxIzxi+IpNzSld8d9AAAAACaKWIlpVxJcTwR6QxOaRWu; IWEB_ADPROMS=zip%7C25%2C141%2C140%2E%7C%2E; COOKIEVISIBILITY=5%20%2C%20B%20%2C%202%20%2C%201000%20%2CN; CFID=58994724; CFTOKEN=32e35b276bdd7243-CB904DF7-FD85-33AD-1492FA40181FA220; CKSECTIONQUERYDATA=rent; JSESSIONID=9BD9F57153E073451B769301803859FE.cfusion; visid_incap_150286=cxNN6AVfQB++qmHSnylkQOJne1MAAAAAQ0IPAAAAAACASZpkAVtCpC8A4YgCm5Hw9db2tUGPRtkV; incap_ses_108_150286=Ng2cXBm811nuH7HIZrN/AW5AlFMAAAAAbNVnLGxmRBCSEPr8q+Lh7A==; NPI=35%2CParVisit%2CN%2C03%2D05%2D2014%2C70; crtg_hmbe=; CKIDBIEN=5064034; CKTYPEBIENVISITE=0125%3D1050%2F1%3B0524%3D1050%2C1000%2C1040%2C1060%2F50%2C46%2C17%2C9%3B0523%3D1050%2C1000%2C1040%2C1060%2C1210%2F50%2C38%2C9%2C1%2C2%3B0525%3D1050%2C1000%2F1%2C2%3B0522%3D1050%2C1060%2C1000%2C1040%2F8%2C2%2C6%2C1%3B0521%3D1000%2C1050%2F2%2C4; LANGUAGE=en; IWEBCHECK=Y; CKVISITCOUNT=10; CKVISITDATELAST=201406081317; ADVERTISINGTAGSERVEUR=5%2C1%2C11%2C3%2C000000%2C171%2CG%2C1%2C2%2CE%2CAC%2C2%2C000000%2C000000%2CPAR%2C1060%2C1638970%2C000000%2C000000%2Crent%2C5003%2C000000%2C000000; optimizelySegments=%7B%22756215013%22%3A%22false%22%2C%22758704609%22%3A%22search%22%2C%22775630251%22%3A%22gc%22%7D; optimizelyBuckets=%7B%22940032526%22%3A%220%22%2C%22940953089%22%3A%220%22%2C%22949600652%22%3A%220%22%7D; __utma=118884753.168559013.1399131518.1402218745.1402221132.51; __utmb=118884753.120.9.1402226546599; __utmc=118884753; __utmz=118884753.1401278217.32.2.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided)");
		
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
		System.out.println(stripContent);
		QueryResponse response = null;
		try {
			response = objectMapper.readValue(stripContent,
					QueryResponse.class);
		} catch (JsonParseException e ) {
			captureCode(stripContent);
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		}

		return response;
	}
	
	private void captureCode(String stripContent) {
		
		
	}

	private void print(SearchResult s) {
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


}
