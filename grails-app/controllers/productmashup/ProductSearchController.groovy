package productmashup

/**
 * Root controller for product search.
 * @author Suman Jakkula
 *
 */
class ProductSearchController {

	ProductSearchService productSearchService
   
    //renders home screen with options for search
    def index() {
		//hardcoded for now. Load this data using the "reloadable cache" functionality 
		render(view:"index")
    }

    //root search API which delegates to service.
    def search(ProductSearchCriteriaVO productSearchCriteriaVO) {
		println "search called"
        def searchResults = productSearchService.searchProducts(productSearchCriteriaVO)?:[:]
        render(view:"searchResults" , model: [ "searchResults": searchResults, "productSearchCriteriaVO":productSearchCriteriaVO ])
    }
}
