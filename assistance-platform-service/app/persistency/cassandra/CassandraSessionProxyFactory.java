package persistency.cassandra;

import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra.CassandraSessionProxy;
import persistency.cassandra.config.CassandraConfig;
import play.Play;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CassandraSessionProxyFactory {
    private static CassandraSessionProxy sessionProxy;

    private CassandraSessionProxyFactory() {
    }

    public static CassandraSessionProxy getSessionProxy() {
        if (sessionProxy == null) {
            sessionProxy = new CassandraSessionProxy(
                    CassandraConfig.getContactPointsAsAddr(), CassandraConfig.getKeyspace(), CassandraConfig.getUser(),
                    CassandraConfig.getPassword(), getSchemaCQL());
        }

        return sessionProxy;
    }

    private static String getSchemaCQL() {
        Path evolutionPath = Play.application()
                .getFile("conf/CassandraEvolutions/1.cql").toPath();
        String schemaCQL = "";
        try {
            schemaCQL = new String(Files.readAllBytes(evolutionPath)).replace(
                    "\n", "");
        } catch (IOException e) {
        }
        return schemaCQL;
    }
}
