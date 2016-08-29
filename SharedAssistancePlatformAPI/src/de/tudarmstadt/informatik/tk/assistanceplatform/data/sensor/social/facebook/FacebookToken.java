package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.social.facebook;

import java.util.Set;

import com.datastax.driver.mapping.annotations.Frozen;
import com.datastax.driver.mapping.annotations.Table;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.SensorData;

@Table(name = "sensor_facebooktoken")
public class FacebookToken extends SensorData {
  public String oauthToken;

  @Frozen
  public Set<String> permissions;

  @Frozen
  public Set<String> declinedPermissions;

  public FacebookToken() {}

  public String getOauthToken() {
    return oauthToken;
  }

  public void setOauthToken(String oauthToken) {
    this.oauthToken = oauthToken;
  }

  public Set<String> getPermissions() {
    return permissions;
  }

  public void setPermissions(Set<String> permissions) {
    this.permissions = permissions;
  }

  public Set<String> getDeclinedPermissions() {
    return declinedPermissions;
  }

  public void setDeclinedPermissions(Set<String> declinedPermissions) {
    this.declinedPermissions = declinedPermissions;
  }
}
