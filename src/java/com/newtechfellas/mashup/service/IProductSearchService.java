package com.newtechfellas.mashup.service;

import java.util.Map;

import productmashup.ProductSearchCriteriaVO;

public interface IProductSearchService {
	/**
	 * Returns the API key to be used. Implementation must address the limitation on the usage of the key and
	 *  return the available key
	 * @return
	 */
	String getAPIKey();
	/**
	 * Create a rest Uri for the specific vendor.
	 * @param productSearchCriteriaVO
	 * @return
	 */
	String constructRestURI(ProductSearchCriteriaVO productSearchCriteriaVO);
	
	/**
	 * Performs the heavy search by calling vendor Rest services
	 * Returned Map contains vendor type and corresponding data.
	 * 
	 * @param productSearchCriteriaVO
	 * @return
	 */
	Map<String,?> searchProducts(ProductSearchCriteriaVO productSearchCriteriaVO);
}
