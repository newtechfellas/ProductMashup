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
        //empty as of now. Will need to build the model object required for home page.
    }

    //root search API which delegates to service.
    def search(ProductSearchCriteriaVO productSearchCriteriaVO) {
        def searchResults = productSearchService.searchProducts(productSearchCriteriaVO)
		println "data received in controller=$searchResults"
        render(view:"searchResults" , model: [ "searchResults": searchResults ])
    }
}
