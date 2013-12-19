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
    def interestedDataFieldsInResponse = "name,image,mediumImage,modelNumber,shortDescription,url,regularPrice,salePrice,onSale,freeShipping,onlineAvailability,shippingCost,longDescription"
    def bbyOpenQueryPrefix = 'http://api.remix.bestbuy.com/v1/products';

    def getAPIKey() {

        // at present hardcoded. This needs to be pulled from Database or properties file which should never be
        // exposed to version control (i.e github)
        'dummy'

        //TODO: implement a pool of API keys. One key might not be sufficient.
        //Use the keys in round-rabit fashion to prevent reaching max limit.
		//This should be implemented outside of this service as a generic solution
    }

    def constructRestURI(ProductSearchCriteriaVO productSearchCriteriaVO) {
		// There seems to be a bug in Groovy Rest client support. If URI contains white space characters, encoding fails with "illegal" character exception.
		// Issue still persists even after applying the fix mentioned in "http://groovy.329449.n5.nabble.com/RestClient-Escape-URL-encoding-td3448030.html"
		// A workaround to replace all white space characters with "%20" is used instead.
        def uri = bbyOpenQueryPrefix+"(search=$productSearchCriteriaVO.searchQuery)?format=json&apiKey="+getAPIKey()+"&show=$interestedDataFieldsInResponse&sort=salePrice.desc"
		uri.replaceAll(/\s+/, "%20")
    }

    def getBbyData(def uri) {
        logger.debug "BbyOpen query=$uri"
		//Call BestBuy API
        RESTClient restClient = new RESTClient(uri)
        def resp = restClient.get([:])
		
		//Convert the response into Product objects. Response contains "data" field which is a map of fields
		//All we need is products field from this data 
        logger.debug "rest.data=${resp?.data}"
		assert resp?.data?.products instanceof List
		def data = []
		resp?.data?.products.each {
			data << new Product(it)
		}
		data
    }

    def searchProducts(ProductSearchCriteriaVO productSearchCriteriaVO) {
        def bbyData = getBbyData(constructRestURI(productSearchCriteriaVO))
		logger.debug "bbyData size"+bbyData.size()
		def map =  ["bbyData":bbyData]
		println "data returning fron service $map"
		return map
    }
}
