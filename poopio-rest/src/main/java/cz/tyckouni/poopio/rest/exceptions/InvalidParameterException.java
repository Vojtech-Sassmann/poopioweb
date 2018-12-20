package cz.tyckouni.poopio.rest.exceptions;

/**
 * @author Vojtech Sassmann &lt;vojtech.sassmann@gmail.com&gt;
 */
public class InvalidParameterException extends RuntimeException {
	public InvalidParameterException(String message) {
		super(message);
	}
}