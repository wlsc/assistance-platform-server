package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.Capability;

/**
 * This POJO represents an active assistance module. It contains information of modules that work in conjunction with the platform.
 */
@JsonIgnoreProperties("administratorEmail")
public class ActiveAssistanceModule {

    public String name;

    public String id;

    public String logoUrl;

    public String descriptionShort;
    public String descriptionLong;

    public Capability[] requiredCapabilities;
    public Capability[] optionalCapabilites;

    public String copyright;

    public String supportEmail;

    public String administratorEmail;

    public String restContactAddress;

    public ActiveAssistanceModule(String name, String id, String logoUrl,
                                  String description_short, String description_long,
                                  Capability[] requiredCapabilites, Capability[] optionalCapabilities,
                                  String copyright, String administratorEmail, String supportEmail, String restContactAddress) {

        this.name = name;

        this.id = id;

        this.logoUrl = logoUrl;

        this.descriptionShort = description_short;
        this.descriptionLong = description_long;

        this.requiredCapabilities = requiredCapabilites;
        this.optionalCapabilites = optionalCapabilities;

        this.copyright = copyright;

        this.administratorEmail = administratorEmail;
        this.supportEmail = supportEmail;

        this.restContactAddress = restContactAddress;
    }

    public String restUrl() {
        return "http://" + restContactAddress + "/rest";
    }

    public String restUrl(String path) {
        return restUrl() + path;
    }
}