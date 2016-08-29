package de.tudarmstadt.informatik.tk.assistanceplatform.modules;

public class Capability {
  /**
   * The type of this capability (e.g. position)
   */
  public String type;

  /**
   * The maximum interval between event readings of this type (in seconds). Readings can be cached
   * on the client. (in seconds) double; 1.0 = 1 measurement per second, 60.0 = 1 measurement per
   * minute
   */
  public double collection_interval;

  /**
   * The maximum interval in seconds after which the sensor readings have to be sent to the
   * platform. -1.0 means that that the readings are only sent when the device is connected via WiFi
   * or hasn't sent any updates for 24 hours.
   */
  public double update_interval;

  /**
   * The required accuracy (currently only needed for iOS Positions)
   */
  public int accuracy;

  /**
   * The permissions to request from e.g. facebook for this module. e.g. ["email", "public_profile",
   * "user_friends"]
   */
  public String[] permissions;

  public Capability() {}

  public Capability(String type, double collection_interval, double update_interval) {
    this(type, collection_interval, update_interval, -1, null);
  }

  public Capability(String type, double collection_interval, double update_interval, int accuracy,
      String[] permissions) {
    super();
    this.type = type;
    this.collection_interval = collection_interval;
    this.update_interval = update_interval;
    this.accuracy = accuracy;
    this.permissions = permissions;
  }
}
