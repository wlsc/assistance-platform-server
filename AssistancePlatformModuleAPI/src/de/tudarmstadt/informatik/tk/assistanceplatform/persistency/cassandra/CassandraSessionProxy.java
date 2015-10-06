package de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class CassandraSessionProxy {
	private Cluster cluster;
	private Session session;
	
	public CassandraSessionProxy(String[] contactPoints, String keystoreName) {
		cluster = Cluster.builder()
				.addContactPoints(contactPoints)
				.build();
		
		session = cluster.connect(keystoreName);
	}
	
	public Session getSession() {
		return session;
	}
}
