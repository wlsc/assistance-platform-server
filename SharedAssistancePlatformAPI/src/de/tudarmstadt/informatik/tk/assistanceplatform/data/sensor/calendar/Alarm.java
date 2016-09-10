package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.calendar;

import java.io.Serializable;

import com.datastax.driver.mapping.annotations.UDT;
import com.fasterxml.jackson.annotation.JsonProperty;

@UDT(name = "alarm")
public class Alarm implements Serializable {

  public Integer type; // <- 0: Default, 1: Alert, 2: Email, 3: SMS, 4: Alarm,
                       // 5: Display 6: Audio, 7: Procedure/Method

  public Boolean defaultOffset;

  // Optional

  @JsonProperty("offset")
  public Integer offsetOptional; // <- Sekunden vom Startzeitpunkt des Events:
                                 // negativ
  // = vorher, musst du also deins Mal -1 nehmen.
  // Falls bei dir -1 gespeichert ist (default)
  // stattdessen defaultOffset auf true setzen und
  // offset weglassen
  @JsonProperty("absoluteDate")
  public String absoluteDateOptional; // <- fester Alarm-Zeitpunkt in ISO 8601
  @JsonProperty("proximity")
  public Integer proximityOptional; // <- 0: None, 1: Enter, 2: Leave (Typen
                                    // für
  // Location-basierten Alarm)
  @JsonProperty("locationTitle")
  public String locationTitleOptional;
  @JsonProperty("locationLatitude")
  public Double locationLatitudeOptional;
  @JsonProperty("locationLongitude")
  public Double locationLongitudeOptional;
  @JsonProperty("locationRadius")
  public Double locationRadiusOptional; // (in Metern, 0 = default)

  public Alarm() {}

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Boolean getDefaultOffset() {
    return defaultOffset;
  }

  public void setDefaultOffset(Boolean defaultOffset) {
    this.defaultOffset = defaultOffset;
  }

  public Integer getOffsetOptional() {
    return offsetOptional;
  }

  public void setOffsetOptional(Integer offsetOptional) {
    this.offsetOptional = offsetOptional;
  }

  public String getAbsoluteDateOptional() {
    return absoluteDateOptional;
  }

  public void setAbsoluteDateOptional(String absoluteDateOptional) {
    this.absoluteDateOptional = absoluteDateOptional;
  }

  public Integer getProximityOptional() {
    return proximityOptional;
  }

  public void setProximityOptional(Integer proximityOptional) {
    this.proximityOptional = proximityOptional;
  }

  public String getLocationTitleOptional() {
    return locationTitleOptional;
  }

  public void setLocationTitleOptional(String locationTitleOptional) {
    this.locationTitleOptional = locationTitleOptional;
  }

  public Double getLocationLatitudeOptional() {
    return locationLatitudeOptional;
  }

  public void setLocationLatitudeOptional(Double locationLatitudeOptional) {
    this.locationLatitudeOptional = locationLatitudeOptional;
  }

  public Double getLocationLongitudeOptional() {
    return locationLongitudeOptional;
  }

  public void setLocationLongitudeOptional(Double locationLongitudeOptional) {
    this.locationLongitudeOptional = locationLongitudeOptional;
  }

  public Double getLocationRadiusOptional() {
    return locationRadiusOptional;
  }

  public void setLocationRadiusOptional(Double locationRadiusOptional) {
    this.locationRadiusOptional = locationRadiusOptional;
  }
}
