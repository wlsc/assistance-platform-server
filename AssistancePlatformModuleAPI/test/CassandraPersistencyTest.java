import java.util.Set;

import org.junit.Test;
import org.reflections.Reflections;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.SensorData;
import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra.CassandraSensorDataPersistency;


public class CassandraPersistencyTest {

	@Test
	public void test() throws InstantiationException, IllegalAccessException {
		Cluster cluster;
		Session session;
		
		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		session = cluster.connect("assistancedata");
		
		CassandraSensorDataPersistency persistency = new CassandraSensorDataPersistency(session);
		
		Set<Class<? extends SensorData>> sensorDataClasses = new Reflections("de.tudarmstadt.informatik.tk.assistanceplatform.data").getSubTypesOf(SensorData.class);
		
		for(Class<? extends SensorData> c : sensorDataClasses) {
			System.out.println(c.getSimpleName() + " START");
			persistency.pesist(c.newInstance());
			System.out.println(c.getSimpleName() + " END");
		}
	}

}
