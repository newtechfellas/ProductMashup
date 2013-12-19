package productmashup
import groovyx.net.http.RESTClient
import groovyx.net.http.URIBuilder;

class BestBuySearchService {

      def interestedDataFieldsInResponse = "name,image,mediumImage,modelNumber,shortDescription,url,regularPrice,salePrice,onSale,freeShipping,onlineAvailability,shippingCost,longDescription"
    def bbyOpenQueryPrefix = 'http://api.remix.bestbuy.com/v1/products';

    def getAPIKey() {

        // at present hardcoded. This needs to be pulled from Database or properties file which should never be
        // exposed to version control (i.e github)
        "dummy"

        //TODO: implement a pool of API keys. One key might not be sufficient.
        //Use the keys is round-rabit fashion to prevent reaching max limit.
    }

    def constructRestURI(ProductSearchCriteriaVO productSearchCriteriaVO) {
		//There seems to be a bug in Groovy Rest client support. If URI contains space, encoding fails with "illegal" character exception.
		// Issue still persists even after applying the fix mentioned in "http://groovy.329449.n5.nabble.com/RestClient-Escape-URL-encoding-td3448030.html"
		// A work around to replace all white space characters with "%20" is used instead.
        def uri = bbyOpenQueryPrefix+"(search=$productSearchCriteriaVO.searchQuery)?format=json&apiKey="+getAPIKey()+"&show=$interestedDataFieldsInResponse"
		uri.replaceAll(/\s+/, "%20")
    }

    def callBby(def uri) {
        println "BbyOpen query=$uri"
        RESTClient restClient = new RESTClient(uri)
        def resp = restClient.get([:])
        println "rest.data=${resp?.data}"
        resp?.data
    }

    def searchProducts(ProductSearchCriteriaVO productSearchCriteriaVO) {
        callBby(constructRestURI(productSearchCriteriaVO))
    }
}
