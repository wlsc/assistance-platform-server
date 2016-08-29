package de.tudarmstadt.informatik.tk.assistanceplatform.data;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.datastax.driver.core.utils.UUIDs;
import com.datastax.driver.mapping.annotations.ClusteringColumn;

/**
 * Describes an event by timestamp an an UUID
 * 
 * @author bjeutter
 * 
 */
public abstract class Event implements Serializable {
  public UUID id;

  @ClusteringColumn
  public Date timestamp;

  public Date serverTimestamp;

  public Event() {
    if (id == null) {
      id = UUIDs.random();
    }
  }

  public Event(Date timestamp) {
    this.id = UUIDs.random();
    this.timestamp = timestamp;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public Date getServerTimestamp() {
    return serverTimestamp;
  }

  public void setServerTimestamp(Date serverTimestamp) {
    this.serverTimestamp = serverTimestamp;
  }
}
