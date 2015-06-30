package models;

public class UserModuleActivation {
	public Long userId;
	
	public String moduleId;
	
	public UserModuleActivation(Long userId, String moduleId) {
		this.userId = userId;
		this.moduleId = moduleId;
	}
}