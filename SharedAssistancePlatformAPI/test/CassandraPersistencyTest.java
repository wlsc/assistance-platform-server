import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Test;
import org.reflections.Reflections;

import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.annotations.Table;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.SensorData;
import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra.CassandraSensorDataPersistency;
import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra.CassandraSessionProxy;


public class CassandraPersistencyTest {
	private static Session getSession() throws UnknownHostException {
		CassandraSessionProxy sessionProxy = new CassandraSessionProxy(new InetAddress[] {
				InetAddress.getByName("127.0.0.1")
		}, "assistancedata", "cassandra", "cassandra");
		
		return sessionProxy.getSession();
	}
	
	@Test
	public void test() throws InstantiationException, IllegalAccessException, UnknownHostException {
		Session session = getSession();
		
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
	public void persistMany() throws InstantiationException, IllegalAccessException, UnknownHostException {
		Session session = getSession();
		
		CassandraSensorDataPersistency persistency = new CassandraSensorDataPersistency(session);
		
		Set<Class<? extends SensorData>> sensorDataClasses = new Reflections("de.tudarmstadt.informatik.tk.assistanceplatform.data").getSubTypesOf(SensorData.class);
		
		List<SensorData> sensorData = new LinkedList<>();
		
		Date timestamp = Calendar.getInstance().getTime();
		for(Class<? extends SensorData> c : sensorDataClasses) {
			for(int i = 0; i < 100; i++) {
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
    public static void oneTimeTearDown() throws UnknownHostException {
    	long start = System.currentTimeMillis();
    	while((System.currentTimeMillis() - start) < 3000);
    	
		Session session = getSession();
		
		Set<Class<? extends SensorData>> sensorDataClasses = new Reflections("de.tudarmstadt.informatik.tk.assistanceplatform.data").getSubTypesOf(SensorData.class);

		for(Class<? extends SensorData> c : sensorDataClasses) {
			for(int i = 0; i < 100; i++) {
				session.execute("DELETE FROM " + c.getAnnotation(Table.class).name() + " WHERE user_id = 0 AND device_id = 0") ;
			}
		}
    }
}
