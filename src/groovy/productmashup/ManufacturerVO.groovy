package productmashup
/**
 * Created by IntelliJ IDEA.
 * User: suman
 * Date: 12/26/13.
 * Time: 9:36 AM
 */
class ManufacturerVO {

	String name
	boolean isSelected

	@Override
	public String toString() {
		return "ManufacturerVO [name=" + name + ", isSelected=" + isSelected
				+ "]";
	}	
}
