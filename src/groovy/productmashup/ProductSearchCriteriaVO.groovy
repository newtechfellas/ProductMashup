package productmashup

import grails.validation.Validateable;

@Validateable
class ProductSearchCriteriaVO {
	String searchQuery
	boolean bestBuy
	boolean walmart
	boolean amazon
	String sortField
	String sortOrder
	List<ManufacturerVO> manufacturers
}
