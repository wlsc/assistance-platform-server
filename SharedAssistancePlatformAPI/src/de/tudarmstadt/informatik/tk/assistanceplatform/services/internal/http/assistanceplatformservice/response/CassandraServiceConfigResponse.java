package de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.response;

public class CassandraServiceConfigResponse extends ServiceConfigResponse {
	public String keystoreName;
	
	public CassandraServiceConfigResponse(String user, String password,
			String[] address, String keystore) {
		super(user, password, address);
		this.keystoreName = keystore;
	}
}
