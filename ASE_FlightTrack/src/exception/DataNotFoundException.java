/*Author: Shashank AV
 * Date: 05/03/2022
 *  The DataNotFoundException is used when a function throws an error
 *  So when ever an function throws an error then a message will be thrown back to the function
 *  so that it will be displayed to the user making them aware an error has occured.
 *  
 */
package exception;
public class DataNotFoundException extends Exception{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataNotFoundException(String message) {
        super(message);
    }
}
