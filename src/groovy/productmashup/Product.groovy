package productmashup

import groovy.transform.Canonical;
import groovy.transform.TupleConstructor;

/**
 * 
 * @author Suman Jakkula
 *
 */
class Product {
	String name
	String image
	String mediumImage
	String modelNumber
	String shortDescription
	String url
	double regularPrice
	double salePrice
	boolean onSale
	boolean freeShipping
	boolean onlineAvailability
	double shippingCost
	String longDescription;
	
	//Creating this constructor
	Product(Map map) {
		// Simulate Groovy's 'map constructor'
		map.each { k,v -> if (this.hasProperty(k)) { this."$k" = v } }
		// Now we can do our initialization.
	  }
	@Override
	public String toString() {
		return """\
				Product name=$name image=$image modelNumber=$modelNumber
				shortDescription=$shortDescription url=$url regularPrice=$regularPrice
				salePrice=$salePrice onSale=$onSale freeShipping=$freeShipping
				onlineAvailability=$onlineAvailability shippingCost=$shippingCost
				longDescription=$longDescription""".stripIndent()
	}
	
}
