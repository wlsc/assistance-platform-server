package sensorhandling.tucan;

import java.util.List;

import com.typesafe.config.ConfigFactory;

public class TucanSecurityConfig {
	public static String getTucanSecret() {
		return ConfigFactory.defaultApplication().getString("tucan.encryptionSecret");
	}
	
	public static List<String> getAllowedModules() {
		return ConfigFactory.defaultApplication().getStringList("tucan.allowedModules");
	}
	
	public static String getEndpointPassword() {
		return ConfigFactory.defaultApplication().getString("tucan.secretEndpointPassword");
	}
}
