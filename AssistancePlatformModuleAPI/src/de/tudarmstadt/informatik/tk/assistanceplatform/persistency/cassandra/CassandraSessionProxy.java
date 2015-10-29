package de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra;

import java.net.InetAddress;
import java.util.function.Consumer;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.Session;

public class CassandraSessionProxy {
	private Cluster cluster;
	private Session session;
	
	public CassandraSessionProxy(InetAddress[] contactPoints, String keystoreName) {
		this((b) -> b.addContactPoints(contactPoints), keystoreName);
	}
	
	public CassandraSessionProxy(String[] contactPoints, String keystoreName) {
		this((b) -> b.addContactPoints(contactPoints), keystoreName); 
	}
	
	private CassandraSessionProxy(Consumer<Builder> clusterBuilderSetter, String keystoreName) {
		setCluster(clusterBuilderSetter);
		
		session = cluster.connect(keystoreName);
	}
	
	private void setCluster(Consumer<Builder> clusterBuilderSetter) {
		Builder b = Cluster.builder();
		
		clusterBuilderSetter.accept(b);
		
		cluster = b.build();
	}
	
	public Session getSession() {
		return session;
	}
}
