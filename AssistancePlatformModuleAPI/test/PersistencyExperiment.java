import java.util.Calendar;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.ConnectionStatus;
import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra.CassandraSensorDataPersistency;


public class PersistencyExperiment {

	public static void main(String[] args) {
		ConnectionStatus data = new ConnectionStatus();
		data.deviceId = 12345;
		data.timestamp = Calendar.getInstance().getTime();
		data.userId = 1212;
		//data.id = UUIDs.random();
		
		Cluster cluster;
		Session session;
		
		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		session = cluster.connect("assistancedata");
		
		Mapper<ConnectionStatus> sensorMapper = new MappingManager(session).mapper(ConnectionStatus.class);

		
		sensorMapper.save(data);
		
		CassandraSensorDataPersistency p = new CassandraSensorDataPersistency(session);
		
		p.pesist(data);
	}
}