package de.tudarmstadt.informatik.tk.assistanceplatform.services.clientaction.gcm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.action.rest.VisibleNotification;

public class GCMRequest {
    public List<String> registration_ids;
    @SuppressWarnings("unused")
    public long time_to_live;
    public Map<String,String> data;
    @SuppressWarnings("unused")
    public VisibleNotification notification;
    
    public GCMRequest(long ttl) {
    	this.time_to_live = ttl;
    }
    
    public GCMRequest() {
    	this(10 * 60); // Default 10 Minutes
    }

    public void addRegId(String regId){
        if(registration_ids == null)
            registration_ids = new LinkedList<String>();
        registration_ids.add(regId);
    }

    public void setData(String dat){
        if(data == null)
            data = new HashMap<String,String>();

        data.put("payload", dat);
    }
    
    public void setVisibleNotification(VisibleNotification notification) {
    	this.notification = notification;
    }
}