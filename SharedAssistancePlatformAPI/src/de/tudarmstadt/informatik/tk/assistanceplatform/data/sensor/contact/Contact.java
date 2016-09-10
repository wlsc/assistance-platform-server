package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.contact;

import java.util.Set;

import com.datastax.driver.mapping.annotations.Frozen;
import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.SensorData;

@Table(name = "sensor_contact")
public class Contact extends SensorData {
  // Required
  public String globalContactId;
  public String givenName;
  public String familyName;
  public String note;
  public Boolean isDeleted;

  // Optional
  @JsonProperty("displayName")
  public String displayNameOptional;

  @JsonProperty("starred")
  public Integer starredOptional;

  @JsonProperty("lastTimeContacted")
  public Integer lastTimeContactedOptional;

  @JsonProperty("timesContacted")
  public Integer timesContactedOptional;

  @JsonProperty("contactType")
  public Integer contactTypeOptional; // <- 0: Person, 1: Organisation

  @JsonProperty("namePrefix")
  public String namePrefixOptional;

  @JsonProperty("middleName")
  public String middleNameOptional;

  @JsonProperty("previousFamilyName")
  public String previousFamilyNameOptional;

  @JsonProperty("nameSuffix")
  public String nameSuffixOptional;

  @JsonProperty("nickname")
  public String nicknameOptional;

  @JsonProperty("phoneticGivenName")
  public String phoneticGivenNameOptional;

  @JsonProperty("phoneticMiddleName")
  public String phoneticMiddleNameOptional;

  @JsonProperty("phoneticFamilyName")
  public String phoneticFamilyNameOptional;

  @JsonProperty("organizationName")
  public String organizationNameOptional;

  @JsonProperty("departmentName")
  public String departmentNameOptional;

  @JsonProperty("jobTitle")
  public String jobTitleOptional;

  @JsonProperty("phoneNumbers")
  @Frozen
  public Set<LabeledContactValue> phoneNumbersOptional;

  @JsonProperty("emailAddresses")
  @Frozen
  public Set<LabeledContactValue> emailAddressesOptional;

  public Contact() {}

  public String getGlobalContactId() {
    return globalContactId;
  }

  public void setGlobalContactId(String globalContactId) {
    this.globalContactId = globalContactId;
  }

  public String getGivenName() {
    return givenName;
  }

  public void setGivenName(String givenName) {
    this.givenName = givenName;
  }

  public String getFamilyName() {
    return familyName;
  }

  public void setFamilyName(String familyName) {
    this.familyName = familyName;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public Boolean getIsDeleted() {
    return isDeleted;
  }

  public void setIsDeleted(Boolean isDeleted) {
    this.isDeleted = isDeleted;
  }

  public String getDisplayNameOptional() {
    return displayNameOptional;
  }

  public void setDisplayNameOptional(String displayNameOptional) {
    this.displayNameOptional = displayNameOptional;
  }

  public Integer getStarredOptional() {
    return starredOptional;
  }

  public void setStarredOptional(Integer starredOptional) {
    this.starredOptional = starredOptional;
  }

  public Integer getLastTimeContactedOptional() {
    return lastTimeContactedOptional;
  }

  public void setLastTimeContactedOptional(Integer lastTimeContactedOptional) {
    this.lastTimeContactedOptional = lastTimeContactedOptional;
  }

  public Integer getTimesContactedOptional() {
    return timesContactedOptional;
  }

  public void setTimesContactedOptional(Integer timesContactedOptional) {
    this.timesContactedOptional = timesContactedOptional;
  }

  public Integer getContactTypeOptional() {
    return contactTypeOptional;
  }

  public void setContactTypeOptional(Integer contactTypeOptional) {
    this.contactTypeOptional = contactTypeOptional;
  }

  public String getNamePrefixOptional() {
    return namePrefixOptional;
  }

  public void setNamePrefixOptional(String namePrefixOptional) {
    this.namePrefixOptional = namePrefixOptional;
  }

  public String getMiddleNameOptional() {
    return middleNameOptional;
  }

  public void setMiddleNameOptional(String middleNameOptional) {
    this.middleNameOptional = middleNameOptional;
  }

  public String getPreviousFamilyNameOptional() {
    return previousFamilyNameOptional;
  }

  public void setPreviousFamilyNameOptional(String previousFamilyNameOptional) {
    this.previousFamilyNameOptional = previousFamilyNameOptional;
  }

  public String getNameSuffixOptional() {
    return nameSuffixOptional;
  }

  public void setNameSuffixOptional(String nameSuffixOptional) {
    this.nameSuffixOptional = nameSuffixOptional;
  }

  public String getNicknameOptional() {
    return nicknameOptional;
  }

  public void setNicknameOptional(String nicknameOptional) {
    this.nicknameOptional = nicknameOptional;
  }

  public String getPhoneticGivenNameOptional() {
    return phoneticGivenNameOptional;
  }

  public void setPhoneticGivenNameOptional(String phoneticGivenNameOptional) {
    this.phoneticGivenNameOptional = phoneticGivenNameOptional;
  }

  public String getPhoneticMiddleNameOptional() {
    return phoneticMiddleNameOptional;
  }

  public void setPhoneticMiddleNameOptional(String phoneticMiddleNameOptional) {
    this.phoneticMiddleNameOptional = phoneticMiddleNameOptional;
  }

  public String getPhoneticFamilyNameOptional() {
    return phoneticFamilyNameOptional;
  }

  public void setPhoneticFamilyNameOptional(String phoneticFamilyNameOptional) {
    this.phoneticFamilyNameOptional = phoneticFamilyNameOptional;
  }

  public String getOrganizationNameOptional() {
    return organizationNameOptional;
  }

  public void setOrganizationNameOptional(String organizationNameOptional) {
    this.organizationNameOptional = organizationNameOptional;
  }

  public String getDepartmentNameOptional() {
    return departmentNameOptional;
  }

  public void setDepartmentNameOptional(String departmentNameOptional) {
    this.departmentNameOptional = departmentNameOptional;
  }

  public String getJobTitleOptional() {
    return jobTitleOptional;
  }

  public void setJobTitleOptional(String jobTitleOptional) {
    this.jobTitleOptional = jobTitleOptional;
  }

  public Set<LabeledContactValue> getPhoneNumbersOptional() {
    return phoneNumbersOptional;
  }

  public void setPhoneNumbersOptional(Set<LabeledContactValue> phoneNumbersOptional) {
    this.phoneNumbersOptional = phoneNumbersOptional;
  }

  public Set<LabeledContactValue> getEmailAddressesOptional() {
    return emailAddressesOptional;
  }

  public void setEmailAddressesOptional(Set<LabeledContactValue> emailAddressesOptional) {
    this.emailAddressesOptional = emailAddressesOptional;
  }


}
