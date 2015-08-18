package models;

/**
 * A exception that is bound to an API error.
 */
public class APIErrorException extends Exception {
	private APIError apiError;
	
	public APIErrorException(APIError error) {
		this.apiError = error;
	}
	
	public APIError getError() {
		return this.apiError;
	}
}