package models;

public class AssistanceAPIErrors {
	// USer REST API
	public static APIError invalidOrExpiredToken = new APIError(1, "The used access token is incorrect or has expired.");
	
	public static APIError badAuthenciationData = new APIError(2, "Bad authenciation data.");

	public static APIError userAlreadyExists = new APIError(3, "The user with the provided email already exists.");
	
	public static APIError missingUserParameters = new APIError(4, "Not all parameters (email and password) were provided.");
	
	public static APIError missingModuleIDParameter = new APIError(5, "The module id was not provided.");
	
	public static APIError moduleActivationAlreadyActive = new APIError(6, "The module is already activated.");
	
	public static APIError moduleActivationNotActive = new APIError(7, "The module is not activated.");
	
	public static APIError moduleDoesNotExist = new APIError(8, "The module does not exist.");
	
	public static APIError alreadyLoggedIn = new APIError(9, "The user is already logged in.");
	
	public static APIError missingParametersGeneral = new APIError(10, "Missing parameters.");
	
	public static APIError invalidParametersGeneral = new APIError(11, "Invalid parameters.");
	
	public static APIError deviceIdNotKnown = new APIError(12, "Device ID not known.");
	
	// Module Rest API
	public static APIError missingModuleParameters = new APIError(995, "Not all required parameters for module registration were provided.");
	
	public static APIError moduleAlreadyExists = new APIError(996, "The module with the provided id already exists.");
	
	public static APIError unknownInternalServerError = new APIError(Integer.MAX_VALUE, "Unknown error.");
}