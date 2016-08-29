package de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra;

import java.net.InetAddress;
import java.util.function.Consumer;

import org.apache.log4j.Logger;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.AlreadyExistsException;

public class CassandraSessionProxy {
  private Cluster cluster;
  private Session session;

  private final String keyspaceName;

  public CassandraSessionProxy(InetAddress[] contactPoints, String keyspaceName, String user,
      String password) {
    this((b) -> b.addContactPoints(contactPoints), keyspaceName, user, password, null);
  }

  public CassandraSessionProxy(InetAddress[] contactPoints, String keyspaceName, String user,
      String password, String schemaCQL) {
    this((b) -> b.addContactPoints(contactPoints), keyspaceName, user, password, schemaCQL);
  }

  private CassandraSessionProxy(Consumer<Builder> clusterBuilderSetter, String keyspaceName,
      String user, String password, String schemaCQL) {
    this.keyspaceName = keyspaceName;

    setCluster(clusterBuilderSetter, user, password);

    createSchema(schemaCQL, keyspaceName, true);

    try {
      session = createSessionForKeyspace(keyspaceName);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Tries to create the schema. If it already exists it won't do anything.
   * 
   * @param schemaCQL The schema (can contain multiple create queries)
   */
  public void createSchema(String schemaCQL) {
    this.createSchema(schemaCQL, this.keyspaceName, false);
  }

  /**
   * Tries to create the schema
   * 
   * @param schemaCQL The schema (can contain multiple creation queries)
   * @param keyspaceName If NULL then keyspaceName creation queries will be ignored
   */
  private void createSchema(String schemaCQL, String keyspaceName, boolean allowKeyspaceCreation) {
    if (schemaCQL != null) {
      Logger log = Logger.getLogger(CassandraSessionProxy.class);

      log.info("Trying to create Cassandra Schema.");

      Session tmpSession;

      if (allowKeyspaceCreation) {
        tmpSession = cluster.connect();
      } else {
        tmpSession = createSessionForKeyspace(keyspaceName);
      }

      for (String s : schemaCQL.split(";")) {
        s = s.replace("\n", "");

        try {
          tmpSession.execute(s);
        } catch (AlreadyExistsException ex) {
          // No harm, just already exists
        } catch (Exception ex) {
          log.error(ex);
        }

        // If we created a keyspace then connect to it directly
        // afterwards
        // so the next creation queries can be run on this particula
        // keyspace
        if (allowKeyspaceCreation && s.contains("CREATE KEYSPACE")) {
          tmpSession.close();
          tmpSession = cluster.connect(keyspaceName);
        }
      }

      tmpSession.close();

      log.info("Finished initializing Cassandra schema");
    }
  }

  private Session createSessionForKeyspace(String keyspace) {
    Session result;

    try {
      result = cluster.connect(keyspace);
    } catch (Exception ex) {
      result = cluster.connect("\"" + keyspace + "\"");
    }

    return result;
  }

  private void setCluster(Consumer<Builder> clusterBuilderSetter, String user, String password) {
    Builder b = Cluster.builder().withCredentials(user, password);

    clusterBuilderSetter.accept(b);

    cluster = b.build();
  }

  public Session getSession() {
    return session;
  }
}
