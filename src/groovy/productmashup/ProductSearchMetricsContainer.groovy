package productmashup

import javax.servlet.http.HttpServletRequest

/**
 * Created by IntelliJ IDEA.
 * User: suman
 * Date: 12/26/13.
 * Time: 3:02 PM
 */
class ProductSearchMetricsContainer {

    final ProductSearchMetric metric;

    public ProductSearchMetricsContainer() {
        metric = new ProductSearchMetric()
    }

    void addBrowserMetric(HttpServletRequest httpServletRequest) {
        metric.browser = httpServletRequest.getHeader('user-agent')
    }

    void addSearchStringMetric(String searchString) {
        metric.searchString = searchString
    }

    void addBestBuyRestUriMetric(String bestbuyRestUri) {
        metric.bestbuyRestUri = bestbuyRestUri
    }

    void addWalmartRestUriMetric(String walmartRestUri) {
        metric.walmartRestUri = walmartRestUri
    }

    void addIsCachedMetric(boolean isCached) {
        metric.isCachedData = isCached
    }

    void addAmazonRestUriMetric(String amazonRestUri) {
        metric.amazonRestUri = amazonRestUri
    }

    void addElapsedTimeMetric(long elapsedTime) {
        metric.elapsedTime = elapsedTime
    }
}