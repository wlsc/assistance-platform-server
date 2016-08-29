package de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider;

import java.util.Date;

import com.google.gson.annotations.Expose;

import de.tudarmstadt.informatik.tk.assistance.model.client.feedback.content.ContentDto;

/**
 * POJO for providing the current (worthy) information for a user provided by a module.
 * 
 * @author bjeutter
 *
 */
public class ModuleInformationCard {
  @Expose
  private String moduleId;

  private Date timestamp;

  private ContentDto content;

  /**
   * Just for serialization purpose!
   */
  public ModuleInformationCard() {
    timestamp = new Date(0);
  }

  public ModuleInformationCard(String moduleId, Date timestamp) {
    this.moduleId = moduleId;
    this.timestamp = timestamp;
  }

  public ModuleInformationCard(String moduleId) {
    this(moduleId, new Date());
  }

  public String getModuleId() {
    return moduleId;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public ContentDto getContent() {
    return this.content;
  }

  public void setContent(ContentDto content) {
    this.content = content;
  }
}
