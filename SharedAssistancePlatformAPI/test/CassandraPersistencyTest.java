import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.AfterClass;
import org.junit.Test;
import org.reflections.Reflections;

import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.annotations.Table;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.Foreground;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.NetworkTraffic;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.RunningProcess;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.RunningService;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.RunningTask;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.SensorData;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.contact.Contact;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.virtualsensor.labels.Label;
import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra.CassandraSensorDataPersistency;
import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra.CassandraSessionProxy;

public class CassandraPersistencyTest {
	private static Session getSession() throws UnknownHostException {
		CassandraSessionProxy sessionProxy = new CassandraSessionProxy(
				new InetAddress[] { InetAddress.getByName("192.168.99.100") },
				"assistancedata", "cassandra", "cassandra");

		return sessionProxy.getSession();
	}

	@Test
	public void test() throws InstantiationException, IllegalAccessException,
			UnknownHostException {
		Session session = getSession();

		CassandraSensorDataPersistency persistency = new CassandraSensorDataPersistency(
				session);

		Set<Class<? extends SensorData>> sensorDataClasses = new Reflections(
				"de.tudarmstadt.informatik.tk.assistanceplatform.data")
				.getSubTypesOf(SensorData.class);

		for (Class<? extends SensorData> c : sensorDataClasses) {
			System.out.println(c.getSimpleName() + " START");
			SensorData data = c.newInstance();
			data.timestamp = Calendar.getInstance().getTime();

			fillNeededFields(data);

			persistency.persist(data);
			System.out.println(c.getSimpleName() + " END");
		}
	}

	private void fillNeededFields(SensorData data) {
		if (data instanceof Label) {
			Label dataAsLabel = (Label) data;
			dataAsLabel.UUID = UUID.randomUUID();
		} else if (data instanceof RunningService) {
			RunningService dataAsRunningService = (RunningService) data;
			dataAsRunningService.packageName = UUID.randomUUID().toString();
			dataAsRunningService.className = UUID.randomUUID().toString();
		} else if (data instanceof RunningTask) {
			RunningTask dataAsRunningTask = (RunningTask) data;
			dataAsRunningTask.name = UUID.randomUUID().toString();
		} else if (data instanceof RunningProcess) {
			RunningProcess dataAsRunningProcess = (RunningProcess) data;
			dataAsRunningProcess.name = UUID.randomUUID().toString();
		} else if (data instanceof Foreground) {
			Foreground dataAsForeground = (Foreground) data;
			dataAsForeground.appName = UUID.randomUUID().toString();
			dataAsForeground.packageName = UUID.randomUUID().toString();
			dataAsForeground.className = UUID.randomUUID().toString();
		} else if (data instanceof NetworkTraffic) {
			NetworkTraffic dataAsNetworkTraffic = (NetworkTraffic) data;
			dataAsNetworkTraffic.appName = UUID.randomUUID().toString();
		} else if (data instanceof de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.calendar.Calendar) {
			de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.calendar.Calendar dataAsCalendar = (de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.calendar.Calendar) data;
			dataAsCalendar.eventId = dataAsCalendar.calendarId = "abc";
		}  else if (data instanceof Contact) {
			Contact dataAsContact = (Contact)data;
			dataAsContact.globalContactId = "123";
		}
	}

	@Test
	public void persistMany() throws InstantiationException,
			IllegalAccessException, UnknownHostException {
		Session session = getSession();

		CassandraSensorDataPersistency persistency = new CassandraSensorDataPersistency(
				session);

		Set<Class<? extends SensorData>> sensorDataClasses = new Reflections(
				"de.tudarmstadt.informatik.tk.assistanceplatform.data")
				.getSubTypesOf(SensorData.class);

		List<SensorData> sensorData = new LinkedList<>();

		Date timestamp = Calendar.getInstance().getTime();
		for (Class<? extends SensorData> c : sensorDataClasses) {
			for (int i = 0; i < 100; i++) {
				Date d = new Date(timestamp.getTime());
				d.setTime(d.getTime() + 1000);
				SensorData data = c.newInstance();
				data.timestamp = d;

				fillNeededFields(data);

				sensorData.add(data);

				timestamp = d;
			}
		}

		System.out.println(sensorData.size());
		persistency.persistMany(sensorData.toArray(new SensorData[sensorData
				.size()]));
	}

	@AfterClass
	public static void oneTimeTearDown() throws UnknownHostException {
		long start = System.currentTimeMillis();
		while ((System.currentTimeMillis() - start) < 3000)
			;

		Session session = getSession();

		Set<Class<? extends SensorData>> sensorDataClasses = new Reflections(
				"de.tudarmstadt.informatik.tk.assistanceplatform.data")
				.getSubTypesOf(SensorData.class);

		for (Class<? extends SensorData> c : sensorDataClasses) {
			for (int i = 0; i < 100; i++) {
				session.execute("DELETE FROM "
						+ c.getAnnotation(Table.class).name()
						+ " WHERE user_id = 0 AND device_id = 0");
			}
		}
	}
}
