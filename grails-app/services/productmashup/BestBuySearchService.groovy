package productmashup
import org.slf4j.Logger
import org.slf4j.LoggerFactory;

import groovy.json.JsonSlurper
import groovyx.net.http.RESTClient
import groovyx.net.http.URIBuilder;

/**
 * Encapsulates Bestbuy search functionality.
 * @author Suman Jakkula
 *
 */
class BestBuySearchService {
	Logger logger = LoggerFactory.getLogger(BestBuySearchService.class)
    //only for test phase to avoid hits to vendor site, Not for PROD.
    Map cache = [:]
    def interestedDataFieldsInResponse = "name,image,largeImage,mediumImage,productTemplate,modelNumber,shortDescription,url,regularPrice,salePrice,onSale,freeShipping,onlineAvailability,shippingCost,longDescription"
    def bbyOpenQueryPrefix = 'http://api.remix.bestbuy.com/v1/products';

    //hardcoded for now. Load this data using the "reloadable cache" functionality
    def knownManufacturers = ['Dell', 'HP', 'Lenovo', 'Apple', 'HTC','Nokia' ,'LG', 'Ericsson', 'Sony','Acer','Toshiba','Asus','Samsung','Sony'].collect {
        it.toLowerCase()
    }

    String getAPIKey() {

        // at present hardcoded. This needs to be pulled from Database or properties file which should never be
        // exposed to version control (i.e github)
        new File("c:\\temp\\bestbuykey.txt").text.trim()

        //TODO: implement a pool of API keys. One key might not be sufficient.
        //Use the keys in round-robin fashion to prevent reaching max limit.
		//This should be implemented outside of this service as a generic solution
    }

    // Rest URI construction will need to consider many attributes to get meaningful data.
    // parse the user entered input and scan for the presence of the attributes required.
    // Ex: if user enters "dell laptop" in the search field, we can use manufacturer=dell in the API URI,
    // "manufacturer" is predefined BbyOpen API attribute. Refer to BbyOpen attributes documentation
    String constructRestURI(ProductSearchCriteriaVO productSearchCriteriaVO) {

        def searchWordTokens = productSearchCriteriaVO.searchQuery.toLowerCase().split(/\s/) as List
        //Uri will be in the form of
        //http://api.remix.bestbuy.com/v1/products(attribute=value)?apiKey=APIKeyHere

        def uri = "${bbyOpenQueryPrefix}(${attributes(productSearchCriteriaVO, searchWordTokens)})?${queryParams()}"

        // There seems to be a bug in Groovy Rest client support. If URI contains white space characters, encoding fails with "illegal" character exception.
        // Issue still persists even after applying the fix mentioned in "http://groovy.329449.n5.nabble.com/RestClient-Escape-URL-encoding-td3448030.html"
        // A workaround to replace all white space characters with "%20" is used instead.
        uri.replaceAll(/\s+/, "%20")
    }

    def attributes(ProductSearchCriteriaVO productSearchCriteriaVO, List searchWordTokens) {
        def attributesList = []
        attributesList << manufacturerAttribute(productSearchCriteriaVO, searchWordTokens)
        attributesList << searchAttribute(searchWordTokens)
        attributesList.removeAll([null])
        attributesList.join('&')
    }

    def queryParams() {
        def queryParamsList = []
        queryParamsList << 'format=json' << "apiKey=${getAPIKey()}" << "show=$interestedDataFieldsInResponse"
        queryParamsList.join('&')
    }

    //returns manufacturer attribute if applicable
    def manufacturerAttribute(ProductSearchCriteriaVO productSearchCriteriaVO, List searchWordTokens) {
        def manufacturerTokens = []

        if (productSearchCriteriaVO.manufacturers) {
            productSearchCriteriaVO.manufacturers.each {
                Map map ->
                    map.each { key, val ->
                        if (val == 'on')
                            manufacturerTokens << key
                    }
            }
        }
        else {
                // compare the input tokens against the known list of manufacturers.
               manufacturerTokens = searchWordTokens.findAll {
                it in knownManufacturers
            }
        }
        if (manufacturerTokens) {
            "manufacturer in(${manufacturerTokens.join(',')})"
        }
    }

    //returns category if applicable
    def categoryAttribute(List searchWordTokens) {

    }

    //returns procuctTemplate (ex: Computer_Notebooks, Monitors) if applicable
    def procuctTemplateAttribute(List searchWordTokens) {

    }

    // Refer to https://bbyopen.com/documentation/products-api/get-products-using-search
    def searchAttribute(List searchWordTokens) {
        "search in(${searchWordTokens.join(',')})"
    }

    def getBbyData(def uri) {
        if ( cache[uri] ) {
            logger.debug("returning cached data for $uri")
            return cache[uri]
        }
        logger.debug "BbyOpen query=$uri"
		//Call BestBuy API
        RESTClient restClient = new RESTClient(uri)
        def resp = restClient.get([:])
		
		//Convert the response into Product objects. Response contains "data" field which is a map of fields
		//All we need is products field from this data 
		assert resp?.data?.products instanceof List
		def data = resp?.data?.products.collect {
            // Using the easy way to build object by passing a map so that groovy does the magic
            // of populating the appropriate object properties
			new Product(it)
		}
        cache[uri] = data
        data
    }

    Map searchProducts(ProductSearchCriteriaVO productSearchCriteriaVO) {
        def bbyData = getBbyData(constructRestURI(productSearchCriteriaVO))
		logger.debug "bbyData size="+bbyData.size()
        //also update the manufacturers list in search criteria
        //TODO: hardcoded as of now. This list need to be pre-defined and loaded into singleton instance
        productSearchCriteriaVO.manufacturers = []
        productSearchCriteriaVO.manufacturers.addAll( ["acer", "apple", "asus", "chrome", "dell", "hp", "lenovo"] )
		["bbyData":bbyData]
    }
}