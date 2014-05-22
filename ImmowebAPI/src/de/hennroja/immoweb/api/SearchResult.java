package de.hennroja.immoweb.api;

public class SearchResult {

	public long id;
	public String image;
	public String price;
	public String type;
	public String area;
	public String postal;
	public String attributes; // "y,n,n,n,n,n,n,n,0",
	public String date1;
	public String date2;
	public String gps;
	public int numBedrooms; // 12

	public float getLong() {
		return gps.equals("") ? 0 : Float.parseFloat(gps.split("-")[1]);
	}

	public float getLat() {
		return gps.equals("") ? 0 : Float.parseFloat(gps.split("-")[0]);
	}

}
