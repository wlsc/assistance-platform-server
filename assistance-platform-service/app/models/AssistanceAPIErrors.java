package models;

public class AssistanceAPIErrors {
	public static APIError invalidOrExpiredToken = new APIError(1, "The used access token is incorrect or has expired.");
	
	public static APIError badAuthenciationData = new APIError(2, "Bad authenciation data.");

	public static APIError userAlreadyExists = new APIError(3, "The user with the provided email already exists.");
	
	public static APIError missingUserParameters = new APIError(4, "Not all parameters (email and password) were provided.");
	
	public static APIError missingModuleParameters = new APIError(5, "Not all required parameters were provided.");
	
	public static APIError moduleAlreadyExists = new APIError(6, "The module with the provided id already exists.");
	
	public static APIError unknownInternalServerError = new APIError(Integer.MAX_VALUE, "Unknown error.");
}