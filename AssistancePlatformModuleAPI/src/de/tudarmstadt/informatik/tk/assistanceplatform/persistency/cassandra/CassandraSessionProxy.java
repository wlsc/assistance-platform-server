package de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra;

import java.net.InetAddress;
import java.util.function.Consumer;

import org.apache.log4j.Logger;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;

public class CassandraSessionProxy {
	private Cluster cluster;
	private Session session;

	public CassandraSessionProxy(InetAddress[] contactPoints,
			String keystoreName) {
		this((b) -> b.addContactPoints(contactPoints), keystoreName, null);
	}

	public CassandraSessionProxy(InetAddress[] contactPoints,
			String keystoreName, String schemaCQL) {
		this((b) -> b.addContactPoints(contactPoints), keystoreName, schemaCQL);
	}

	private CassandraSessionProxy(Consumer<Builder> clusterBuilderSetter,
			String keystoreName, String schemaCQL) {
		setCluster(clusterBuilderSetter);

		createSchema(schemaCQL, keystoreName);

		session = cluster.connect(keystoreName);
	}

	private void createSchema(String schemaCQL, String keystoreName) {
		if (schemaCQL != null) {
			Logger log = Logger.getLogger(CassandraSessionProxy.class);
			
			log.info("Trying to create Cassandra Schema.");
			
			Session tmpSession = cluster.connect();

			for (String s : schemaCQL.split(";")) {
				s = s.replace("\n", "");

				try {
					tmpSession.execute(s);
				} catch (Exception ex) {
					log.warn("No need to wory if the table / keyspace already exists!", ex);
				}
				
				// If we created a keyspace then connect to it directly afterwards
				// so the next creation queries can be run on this particula keyspace
				if (s.contains("CREATE KEYSPACE")) {
					tmpSession.close();
					tmpSession = cluster.connect(keystoreName);
				}
			}

			tmpSession.close();
			
			log.info("Finished initializing Cassandra schema");
		}
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
