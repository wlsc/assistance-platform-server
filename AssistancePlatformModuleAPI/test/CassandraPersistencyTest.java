import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Test;
import org.reflections.Reflections;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.annotations.Table;

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
			SensorData data = c.newInstance();
			data.timestamp = Calendar.getInstance().getTime();
			persistency.persist(data);
			System.out.println(c.getSimpleName() + " END");
		}
	}
	
	@Test
	public void persistMany() throws InstantiationException, IllegalAccessException {
		Cluster cluster;
		Session session;
		
		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		session = cluster.connect("assistancedata");
		
		CassandraSensorDataPersistency persistency = new CassandraSensorDataPersistency(session);
		
		Set<Class<? extends SensorData>> sensorDataClasses = new Reflections("de.tudarmstadt.informatik.tk.assistanceplatform.data").getSubTypesOf(SensorData.class);
		
		List<SensorData> sensorData = new LinkedList<>();
		
		Date timestamp = Calendar.getInstance().getTime();
		for(Class<? extends SensorData> c : sensorDataClasses) {
			for(int i = 0; i < 5000; i++) {
				Date d = new Date(timestamp.getTime());
				d.setTime(d.getTime() + 1000);
				SensorData data = c.newInstance();
				data.timestamp = d;
				sensorData.add(data);
				
				timestamp = d;
			}
		}
		
		System.out.println(sensorData.size());
		persistency.persistMany(sensorData.toArray(new SensorData[sensorData.size()]));
	}

    @AfterClass
    public static void oneTimeTearDown() {
    	long start = System.currentTimeMillis();
    	while((System.currentTimeMillis() - start) < 3000);
    	
		Cluster cluster;
		Session session;
    	
		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		session = cluster.connect("assistancedata");
		
		Set<Class<? extends SensorData>> sensorDataClasses = new Reflections("de.tudarmstadt.informatik.tk.assistanceplatform.data").getSubTypesOf(SensorData.class);

		for(Class<? extends SensorData> c : sensorDataClasses) {
			for(int i = 0; i < 100; i++) {
				session.execute("DELETE FROM " + c.getAnnotation(Table.class).name() + " WHERE user_id = 0 AND device_id = 0") ;
			}
		}
    }
}
