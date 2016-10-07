package sensorhandling.tucan;

import com.typesafe.config.ConfigFactory;

import java.util.List;

public class TucanSecurityConfig {
    private TucanSecurityConfig() {
    }

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
