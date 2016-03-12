package exception;

public class SLogoException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public SLogoException (String message, Object ... values) {
        super(String.format(message, values));
    }
    
    /**
     * Create an exception based on a caught exception with a different message.
     */
    public SLogoException (Throwable cause, String message, Object ... values) {
        super(String.format(message, values), cause);
    }
}