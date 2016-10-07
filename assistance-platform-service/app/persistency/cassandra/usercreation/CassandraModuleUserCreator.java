package persistency.cassandra.usercreation;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import org.apache.commons.codec.digest.DigestUtils;
import persistency.cassandra.CassandraSessionProxyFactory;
import persistency.cassandra.config.CassandraConfig;
import play.Logger;

public class CassandraModuleUserCreator {
    private Session session;

    public Session getSession() {
        if (session == null) {
            this.session = CassandraSessionProxyFactory.getSessionProxy().getSession();
        }
        return session;
    }

    public CassandraModuleUser createUserForModule(String moduleId) {
        CassandraModuleUser user = new CassandraModuleUser();

        user.user = moduleId;
        user.keyspace = DigestUtils.sha1Hex(moduleId);
        user.password = DigestUtils.sha1Hex(DigestUtils.md5(moduleId));

        if (!doesModuleUserAlreadyExists(moduleId)) {
            // Create User & Keyspace
            createUser(user);
            createKeyspace(user);
        }

        // Set Permissions
        setPermissions(user);

        return user;
    }

    private boolean doesModuleUserAlreadyExists(String moduleId) {
        ResultSet users = this.getSession().execute("LIST USERS");

        return users.all().stream().filter((r) -> r.getString(0).equals(moduleId)).count() == 1L;
    }

    private void createUser(CassandraModuleUser user) {
        getSession().execute("CREATE USER '" + user.user + "' WITH PASSWORD '" + user.password + "' NOSUPERUSER");
    }

    private void createKeyspace(CassandraModuleUser user) {
        try {
            getSession().execute("CREATE KEYSPACE \"" + user.keyspace + "\" WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 }");
        } catch (Exception e) {
            Logger.warn("Creating keyspace for module failed.", e);
        }
    }

    private void setPermissions(CassandraModuleUser user) {
        String[] grants = {
                "GRANT SELECT ON KEYSPACE " + CassandraConfig.getKeyspace(),
                "GRANT SELECT ON KEYSPACE \"" + user.keyspace + "\"",
                "GRANT CREATE ON KEYSPACE \"" + user.keyspace + "\"",
                "GRANT DROP ON KEYSPACE \"" + user.keyspace + "\"",
                "GRANT MODIFY ON KEYSPACE \"" + user.keyspace + "\""
        };

        for (String grant : grants) {
            String grantQuery = grant + " TO '" + user.user + "'";

            try {
                getSession().execute(grantQuery);
            } catch (Exception ex) {
                Logger.warn("Granting permission to module user failed. Query: " + grantQuery, ex);
            }
        }

    }
}
