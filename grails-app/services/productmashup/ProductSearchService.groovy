package productmashup

/**
 * Common wrapper service to issue individual search jobs
 * Consolidates the retrieved response and aggregates as per UI needs.
 *  
 * @author Suman Jakkula
 *
 */
class ProductSearchService {

	BestBuySearchService bestBuySearchService
	
		def searchProducts(ProductSearchCriteriaVO searchCriteriaVO) {
			if ( searchCriteriaVO.bestBuy) {
				bestBuySearchService.searchProducts(searchCriteriaVO)
			}
		}
}
