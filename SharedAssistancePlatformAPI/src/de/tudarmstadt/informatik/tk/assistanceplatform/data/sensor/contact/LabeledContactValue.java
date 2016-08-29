package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.contact;

import java.io.Serializable;

import com.datastax.driver.mapping.annotations.UDT;

@UDT(name = "labeledvalue")
public class LabeledContactValue implements Serializable {
  public String label;

  public String value;

  public LabeledContactValue() {}

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
