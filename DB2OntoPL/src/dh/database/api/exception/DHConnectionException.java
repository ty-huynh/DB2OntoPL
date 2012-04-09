package dh.database.api.exception;

public class DHConnectionException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8812114499004861673L;
	String mistake;
	
	public DHConnectionException() {
		super();
		mistake = "unknown";
	}
	
	public DHConnectionException(String err) {
		super(err);
		mistake = err;
	}
	
	public String getError() {
		return mistake;
	}
}
