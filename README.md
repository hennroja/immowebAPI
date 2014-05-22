immowebAPI
==========

Java API for immoweb.be

This Java API helps you to easily access estates on immoweb.be.
At the moment it only supports searchquerys but not detailed view.::

   Example:
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

