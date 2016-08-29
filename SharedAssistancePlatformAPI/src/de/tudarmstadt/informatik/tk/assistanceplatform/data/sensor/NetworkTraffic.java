package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import java.io.Serializable;

import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

@Table(name = "sensor_networktraffic")
public class NetworkTraffic extends SensorData implements Serializable {
  public String appName;
  public long rxBytes;
  public long txBytes;
  public boolean background;

  @JsonProperty(value = "longitude")
  public double longitudeOptional;

  @JsonProperty(value = "latitude")
  public double latitudeOptional;

  public NetworkTraffic() {}

  public NetworkTraffic(String appName, long rxBytes, long txBytes, boolean background,
      double longitude, double latitude) {
    super();
    this.appName = appName;
    this.rxBytes = rxBytes;
    this.txBytes = txBytes;
    this.background = background;
    this.longitudeOptional = longitude;
    this.latitudeOptional = latitude;
  }

  public String getAppName() {
    return appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }

  public long getRxBytes() {
    return rxBytes;
  }

  public void setRxBytes(long rxBytes) {
    this.rxBytes = rxBytes;
  }

  public long getTxBytes() {
    return txBytes;
  }

  public void setTxBytes(long txBytes) {
    this.txBytes = txBytes;
  }

  public boolean isBackground() {
    return background;
  }

  public void setBackground(boolean background) {
    this.background = background;
  }

  public double getLongitudeOptional() {
    return longitudeOptional;
  }

  public void setLongitudeOptional(double longitude) {
    this.longitudeOptional = longitude;
  }

  public double getLatitudeOptional() {
    return latitudeOptional;
  }

  public void setLatitudeOptional(double latitude) {
    this.latitudeOptional = latitude;
  }
}
