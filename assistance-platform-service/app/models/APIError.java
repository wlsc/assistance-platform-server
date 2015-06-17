package models;

public class APIError {
	public int code;
	public String message;
	
	public APIError(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
