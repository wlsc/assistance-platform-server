package models;

/**
 * This POJO represents the link betwen user and module.
 */
public class UserModuleActivation {
	public long userId;
	
	public String moduleId;
	
	public UserModuleActivation(long userId, String moduleId) {
		this.userId = userId;
		this.moduleId = moduleId;
	}
}