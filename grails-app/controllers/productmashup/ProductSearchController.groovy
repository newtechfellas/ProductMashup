package productmashup

class ProductSearchController {

	    ProductSearchService productSearchService
   
    //renders home screen with options for search
    def index() {
        //empty as of now. Will need to build the model object required for home page.
    }

    //root search API which delegates to service.
    def search(ProductSearchCriteriaVO productSearchCriteriaVO) {
        def searchResults = productSearchService.searchProducts(productSearchCriteriaVO)
        println "searchResults=$searchResults"
        render(view:"searchResults" , model: [ "searchResults": searchResults ])
    }
}
