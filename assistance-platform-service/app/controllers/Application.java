package controllers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra.CassandraSessionProxy;
import dto.ResponsetimeLog;
import dto.ResponsetimeLog.ResponseTimeEntry;
import persistency.cassandra.CassandraSessionProxyFactory;
import persistency.cassandra.ConfiguredSensorPersistencyProxy;
import play.api.libs.json.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends RestController {

	public Result index() {
		// return ok(index.render("Your new application is ready."));
		return ok("Your new application is ready.");
	}

	public Result ip() {
		InetAddress thisIp = null;
		try {
			thisIp = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ok(thisIp.getHostAddress());
	}

	// / NUR FÜR LANGZEIT PERFORMANCE TEST RELEVANT!!!
	private final CassandraSessionProxy cassandraProxy = CassandraSessionProxyFactory
			.getSessionProxy();
	
	private ObjectMapper objMapper = new ObjectMapper();

	public Result log_responsetimes() throws JsonProcessingException {		
		JsonNode postData = request().body().asJson();
		
		ResponsetimeLog log = objMapper.treeToValue(postData, ResponsetimeLog.class);

		for(ResponseTimeEntry entry : log.data) {
			//ResponseTimeEntry entry = log.data[i];
			
			long networkTime = entry.responseTime - entry.processingTime;
			
			String query = "INSERT INTO responsetimes "
					+ "(user_id, device_id, serverTimestamp, startTime, responseTime, processingTime, networkTime, bodySize, eventsNumber, networkType) VALUES "
					+ "(" + log.userId + "," + log.deviceId + ", dateof(now())," + entry.startTime + "," + entry.responseTime + "," + entry.processingTime + "," + networkTime + "," + entry.bodySize + "," + entry.eventsNumber + ",'" + entry.networkType + "')";
			
			cassandraProxy
			.getSession()
			.execute(
					query);
		}

		return ok();
	}
}