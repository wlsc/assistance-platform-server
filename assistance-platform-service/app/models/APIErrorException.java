package models;

public class APIErrorException extends Exception {
	private APIError apiError;
	
	public APIErrorException(APIError error) {
		this.apiError = error;
	}
	
	public APIError getError() {
		return this.apiError;
	}
}