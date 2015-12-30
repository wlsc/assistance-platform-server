package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.social.tucan;

import com.datastax.driver.mapping.annotations.Table;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.SensorData;

@Table(name = "sensor_tucancredentials")
public class TucanCredentials extends SensorData {
    public String username;
    public String password;
    
    public TucanCredentials() {}

	public TucanCredentials(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}