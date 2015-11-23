package de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http;

public class PlatformClientFactory {
	public static final String defaultPlatformUrlAndPort = "localhost:9000";
	
	private static PlatformClient instance;

	public static PlatformClient getInstance() {
		return instance;
	}
	
	public static PlatformClient createInstance(String urlAndPort) {		
		if (instance == null) {
			instance = new PlatformClient(urlAndPort);
		}

		return instance;
	}
	
	public static void setInstance(PlatformClient mockInstance) {
		instance = mockInstance; 
	}
}
