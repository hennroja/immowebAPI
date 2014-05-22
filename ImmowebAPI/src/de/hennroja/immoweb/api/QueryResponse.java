package de.hennroja.immoweb.api;

import java.util.ArrayList;

public class QueryResponse {

	public String NEW;
	public String IMAGEBASEURL;
	public ArrayList<ArrayList<String>> XIPHONEARRAY_RESULTS;
	//public String MSG;

	
	public QueryResponse() {
	}

	public String getIMAGEBASEURL() {
		return IMAGEBASEURL;
	}

	public void setIMAGEBASEURL(String iMAGEBASEURL) {
		IMAGEBASEURL = iMAGEBASEURL;
	}

	public ArrayList<ArrayList<String>> getXIPHONEARRAY_RESULTS() {
		return XIPHONEARRAY_RESULTS;
	}

	public void setXIPHONEARRAY_RESULTS(
			ArrayList<ArrayList<String>> xIPHONEARRAY_RESULTS) {
		XIPHONEARRAY_RESULTS = xIPHONEARRAY_RESULTS;
	}

	public String getNEW() {
		return NEW;
	}

	public void setNEW(String nEW) {
		NEW = nEW;
	}
	
	public ArrayList<SearchResult> getEstates() {
		ArrayList<SearchResult> res = new ArrayList<SearchResult>();
		
		for (ArrayList<String> elem : XIPHONEARRAY_RESULTS) {
			SearchResult e = new SearchResult();
			e.id = Long.parseLong(elem.get(0));
			e.image = elem.get(2);
			e.price = elem.get(3);
			e.type = elem.get(4);
			e.numBedrooms = elem.get(12).equals("") ? -1 : Integer
					.parseInt(elem.get(12));
			e.area = elem.get(13);
			e.postal = elem.get(6);
			e.attributes = elem.get(7);
			e.gps = elem.get(8);
			res.add(e);
		}
		return res;
	}

	
}
