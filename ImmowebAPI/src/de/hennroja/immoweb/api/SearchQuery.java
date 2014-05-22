package de.hennroja.immoweb.api;

public class SearchQuery extends Query {

	private int postalCount = 1;

	protected SearchQuery() {
		super(Query.METHOD.GET_RESULTS);
	}

	/**
	 * Type can be Query.TYPE.BUY or Query.TYPE.RENT
	 * 
	 * @param type
	 */
	public SearchQuery type(int type) {
		this.query+="&xVenteLocation=" + type;
		return this;
	}

	/**
	 * Type can be Query.CATEGORY.*wildcard*
	 * 
	 * @param type
	 */
	public SearchQuery category(int category) {
		this.query+="&xIdCategorie=" + category;
		return this;
	}

	/**
	 * Set the minimum price
	 * 
	 * @param price
	 */
	public SearchQuery priceMin(int price) {
		this.query+="&xPrice1=" + price;
		return this;
	}

	/**
	 * Set the maximal price
	 * 
	 * @param price
	 */
	public SearchQuery priceMax(int price) {
		this.query+="&xPrice2=" + price;
		return this;
	}

	/**
	 * Set multiple postal codes.
	 * 
	 * @param postal
	 *            code
	 */
	public SearchQuery postalCode(String plz) {
		this.query+="&xCodeCommune" + postalCount + "=" + plz;
		postalCount++;
		return this;
	}
	
	
	/**
	 * This is an experimental filter. Functionality is not proofed.
	 * this shell filter for furnished objects. 
	 * 
	 * @param furnished
	 *            
	 */
	public SearchQuery furnished(boolean furnished) {
		if(furnished){
			this.query+="&xmeuble=Y";
		}else {
			this.query+="&xmeuble=N";
		}
		return this;
	}
	
	@Override
	public SearchQuery iphone(String iphone) {
		return (SearchQuery) super.iphone(iphone);
	}
	
	@Override
	public SearchQuery clientSource(String clientSource) {
		return (SearchQuery) super.clientSource(clientSource);
	}
	
	@Override
	public SearchQuery codePays(String codepays) {
		return (SearchQuery) super.codePays(codepays);
	}
	
}
