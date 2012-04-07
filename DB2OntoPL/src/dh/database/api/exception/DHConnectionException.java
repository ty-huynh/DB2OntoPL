package dh.database.api.exception;

public class DHConnectionException extends Exception {
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
