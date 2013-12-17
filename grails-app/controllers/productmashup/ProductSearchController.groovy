package productmashup

class ProductSearchController {

	ProductSearchService searchService
	
	//renders home screen with options for search 
    def index() {
		def productSearchResultsJson = "test";
		["productSearchResultsJson":productSearchResultsJson]
	}
	def search() {
		println "Searched called"
		render(view:"searchResults")
	}
}
