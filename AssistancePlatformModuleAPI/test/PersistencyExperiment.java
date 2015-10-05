import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.Accelerometer;
import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra.CassandraSensorDataPersistency;


public class PersistencyExperiment {

	public static void main(String[] args) {
		Accelerometer data = new Accelerometer();
		data.deviceId = 12345;
		data.timestamp = 3222132;
		data.userId = 1212;
		data.x = 0.15;
		data.y = 1.15;
		data.z = 2.15;
		//data.id = UUIDs.random();
		
		Cluster cluster;
		Session session;
		
		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		session = cluster.connect("assistancedata");
		
		Mapper<Accelerometer> sensorMapper = new MappingManager(session).mapper(Accelerometer.class);

		
		sensorMapper.save(data);
		
		CassandraSensorDataPersistency p = new CassandraSensorDataPersistency(session);
		
		p.pesist(data);
	}
}