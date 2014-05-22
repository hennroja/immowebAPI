package de.hennroja.immoweb.api;

import java.lang.reflect.Method;

public abstract class Query {

	// http://www.immoweb.be/en/mobile.cfc?method=getResults
	//&iphone=
	//&clientSource=
	//&xVenteLocation=2
	//&xIdCategorie=5
	//&xCodePays=B
	//&xPrice1=300
	//&xPrice2=500
	//&xSurfaceHabitableTotale1=100
	//&xSurfaceHabitableTotale2=400
	//&xCodeCommune1=1000
	//&xCodeCommune2=1050
	//&data0=2
	//&data1=0JWGN2NQHKIY
	//&data2=c0665b2d911b2b2c5d294a84a6f469aae0489d16
	//&data3=AndroidPhone-4.0.2
	//&data4=V4.0

	private static String API_URL = "http://www.immoweb.be/en/mobile.cfc";
	protected String query = "";

	protected final static class METHOD {
		public static final String GET_RESULTS = "getResults";
		public static final String GET_ESTATE = "getEstate";
	}

	public final static class TYPE {
		public static final int BUY = 1;
		public static final int RENT = 2;
	}

	public final static class CATEGORY {
		public static final int HOUSE = 1;
		public static final int OFFICE = 2;
		public static final int APARTMENT = 5;
		public static final int LAND = 6;
		public static final int REAL_ESTATE_APARTMENT = 12;
	}


	protected Query(String method) {
		query+=API_URL;
		query+="?method=" + method;
	}
	


	/**
	 * IPhone field, use is unknown.
	 * 
	 * @param iphone
	 */
	protected Query iphone(String iphone) {
		query+="&iphone=" + iphone;
		return this;
	}

	/**
	 * ClientSource, use is unknown leave blank.
	 * 
	 * @param clientSource
	 */
	protected Query clientSource(String clientSource) {
		query+="&clientSource=" + clientSource;
		return this;
	}

	/**
	 * codepays, use is unknown. Set the default with "B".
	 * 
	 * @param codepays
	 */
	public Query codePays(String codepays) {
		query+="&xCodePays=" + codepays;
		return this;
	}

	/**
	 * data0, use is unknown. e.g. "2" A parameter that was discovered on
	 * reverse engineering, but the meaning is unknown.
	 * 
	 * @param data0
	 */
	public Query data0(String data0) {
		query+="&data0=" + data0;
		return this;
	}

	/**
	 * data1, use is unknown. e.g. "3MJ3P1LQYKV5" A parameter that was
	 * discovered on reverse engineering, but the meaning is unknown. Could be a
	 * token or a unique device id.
	 * 
	 * @param data1
	 */
	public Query data1(String data1) {
		query+="&data1=" + data1;
		return this;
	}

	/**
	 * data2, use is unknown. e.g. "3ccca3d8bb62b27f38a6e2fa1a479d13a1d52d2e" A parameter that was
	 * discovered on reverse engineering, but the meaning is unknown. Could be a
	 * token or a unique device id, like hashed mac adress.
	 * 
	 * @param data2
	 */
	public Query data2(String data2) {
		query+="&data2=" + data2;
		return this;
	}

	/**
	 * set user agent for example AndroidPhone-4.0.1
	 * 
	 * @param user agent
	 */
	public Query userAgent(String agent) {
		query+="&data3=" + agent;
		return this;
	}

	public static SearchQuery searchQuery() {
		return new SearchQuery();
	}
	
	public static EstateQuery estateQuery() {
		return new EstateQuery();
	}
	
	
}
