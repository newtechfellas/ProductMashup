package productmashup
import net.sf.ehcache.Ehcache

import org.slf4j.Logger
import org.slf4j.LoggerFactory;

import com.newtechfellas.mashup.service.IProductSearchService

import grails.plugin.cache.Cacheable;
import grails.util.Holders;
import groovy.json.JsonSlurper
import groovyx.net.http.RESTClient
import groovyx.net.http.URIBuilder;
import static Util.*

/**
 * Encapsulates Bestbuy search functionality.
 * @author Suman Jakkula
 *
 */
class BestBuySearchService {
	Logger logger = LoggerFactory.getLogger(BestBuySearchService.class)
//    Ehcache productSearchCache //Cache bean injected by grails
    def interestedDataFieldsInResponse = "name,image,largeImage,mediumImage,productTemplate,modelNumber,shortDescription,url,regularPrice,salePrice,onSale,freeShipping,onlineAvailability,shippingCost,longDescription"
    def bbyOpenQueryPrefix = 'http://api.remix.bestbuy.com/v1/products';

    //hardcoded for now. Load this data using the "reloadable cache" functionality
    def knownManufacturers = ['Dell', 'HP', 'Lenovo', 'Apple', 'HTC','Nokia' ,'LG', 'Ericsson', 'Sony','Acer','Toshiba','Asus','Samsung','Sony'].collect {
        it.toLowerCase()
    }

    String getAPIKey() {

        // at present hardcoded. This needs to be pulled from Database or properties file which should never be
        // exposed to version control (i.e github)
        new File("/Users/yuyutsu/bestbuykey.txt").text.trim()

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
	
	// Refer to https://bbyopen.com/documentation/products-api/get-products-using-search
	def searchAttribute(List searchWordTokens) {
		"search in(${searchWordTokens.join(',')})"
	}
    def queryParams() {
        def queryParamsList = []
        queryParamsList << 'format=json' << "apiKey=${getAPIKey()}" << "show=$interestedDataFieldsInResponse"
        queryParamsList.join('&')
    }

	//returns manufacturer attribute if applicable
	def manufacturerAttribute(ProductSearchCriteriaVO productSearchCriteriaVO, List searchWordTokens) {
		def manufacturerTokens = []
		//If user selected any manufacturers, use them.
		if (productSearchCriteriaVO.manufacturers) {
			productSearchCriteriaVO.manufacturers.each {
				ManufacturerVO manufacturerVO ->
					if (manufacturerVO.isSelected) {
						//BestBuy has weird name for Apple. It uses registered trade mark symbol as well in the name
						manufacturerTokens << (manufacturerVO.name ==~ /apple/ ? 'Apple*' : manufacturerVO.name)
					}
			}
		} else {
			// otherwise compare the input search string tokens against the known list of manufacturers.
			manufacturerTokens = searchWordTokens.findAll {
				it in knownManufacturers
			}
		}
		manufacturerTokens? "manufacturer in(${manufacturerTokens.join(',')})" : null
	}
	//Using grails cache plugin to cache the data againt the Rest URI
	@Cacheable(value="productSearchCache", key="#uri")
	BestBuySearchResultsVO getBbyData(String uri) {
		logger.debug "BbyOpen query=$uri"
		//Call BestBuy API
		RESTClient restClient = new RESTClient(uri)
		def resp = restClient.get([:])

		//Convert the response into Product objects. Response contains "data" field which is a map of fields
		assert resp?.data?.products instanceof List
		def products = resp?.data?.products.collect {
			// Using the easy way to build object by passing a map so that groovy does the magic
			// of populating the appropriate object properties
			new Product(it)
		}


		def bestBuySearchResultsVO = new BestBuySearchResultsVO().with {
			totalPages = resp.data.totalPages
			currentPage = resp.data.currentPage
			totalProducts = resp.data.total
			currentResultStartIndex = resp.data.from
			currentResultEndIndex = resp.data.to
			data = products
			return it
		}
		return bestBuySearchResultsVO
	}
	//Get the proxied instance to invoke to take cache into effect
	BestBuySearchService proxiedService() {
		Holders.grailsApplication.mainContext.bestBuySearchService
	}
	
	Map searchProducts(ProductSearchCriteriaVO productSearchCriteriaVO) {
		ProductSearchMetricsContainer metricsContainer = metricsContainer()
		metricsContainer.addSearchStringMetric(productSearchCriteriaVO.searchQuery)
		
		def bestBuySearchResultsVO = proxiedService().getBbyData(constructRestURI(productSearchCriteriaVO))
		logger.debug "bbyData size="+bestBuySearchResultsVO.data?.size()
		//also update the manufacturers list in search criteria
		//TODO: hardcoded as of now. This list need to be pre-defined and loaded into singleton instance
		if ( ! productSearchCriteriaVO.manufacturers ) {
			productSearchCriteriaVO.manufacturers =   ["acer", "apple", "asus", "chrome", "dell", "hp", "lenovo"].collect{
				new ManufacturerVO(name:it, isSelected: false)
			}
		}
		["bbyData":bestBuySearchResultsVO]
	}
}