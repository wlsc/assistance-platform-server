package de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http;

public class PlatformClientFactory {
	public static final String defaultPlatformUrlAndPort = "localhost:9000";
	
	private static PlatformClient instance;

	public static PlatformClient getInstance() {
		return getInstance(defaultPlatformUrlAndPort);
	}
	
	public static PlatformClient getInstance(String urlAndPort) {
		if (instance == null) {
			return new PlatformClient(urlAndPort);
		}

		return instance;
	}
}
