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
		
		def manufacturersMap = [ 'Laptop': ['Dell', 'HP', 'Lenovo', 'Apple', 'Sony','Acer','Toshiba','Asus','Samsung','Sony'],
								 'Mobile': [ 'Apple','Samsung','HTC','Nokia', 'Ericsson','LG'],
								 'Camera': ['Sony','Canon','Casio','Kodak','Nikon','Panasonic'],
								 'Desktop' : ['Dell', 'HP', 'Lenovo', 'Apple', 'Sony','Acer','Toshiba','Asus','Samsung','Sony']
								]
		def modelMap = ['productTypes': ['Laptop','Camera','Mobile','Desktop'],
							'manufacturers' : manufacturersMap
						]			
		render(view:"index", model : modelMap)
    }

    //root search API which delegates to service.
    def search(ProductSearchCriteriaVO productSearchCriteriaVO) {
        def searchResults = productSearchService.searchProducts(productSearchCriteriaVO)
        render(view:"searchResults" , model: [ "searchResults": searchResults ])
    }
}
