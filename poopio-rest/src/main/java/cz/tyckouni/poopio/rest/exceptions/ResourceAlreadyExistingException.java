package cz.tyckouni.poopio.rest.exceptions;

/**
 * @author Vojtech Sassmann &lt;vojtech.sassmann@gmail.com&gt;
 */
public class ResourceAlreadyExistingException extends RuntimeException {
	public ResourceAlreadyExistingException(String message) {
		super(message);
	}
}
