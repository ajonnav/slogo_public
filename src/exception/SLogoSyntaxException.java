package exception;

public class SLogoSyntaxException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public SLogoSyntaxException (String message, Object ... values) {
        super(String.format(message, values));
    }
    
    /**
     * Create an exception based on a caught exception with a different message.
     */
    public SLogoSyntaxException (Throwable cause, String message, Object ... values) {
        super(String.format(message, values), cause);
    }
}