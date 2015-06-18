package models;

public class AssistanceAPIErrors {
	public static APIError invalidOrExpiredToken = new APIError(1, "The used access token is incorrect or has expired.");
	
	public static APIError badAuthenciationData = new APIError(2, "Bad authenciation data.");

	public static APIError userAlreadyExists = new APIError(3, "The user with the provided email already exists.");
	
	public static APIError missingUserParameters = new APIError(4, "Not all parameters (email and password) were provided.");
	
	public static APIError unknownInternalServerError = new APIError(Integer.MAX_VALUE, "Unknown error.");
}