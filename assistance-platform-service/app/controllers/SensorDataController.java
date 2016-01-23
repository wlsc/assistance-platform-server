package controllers;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import messaging.JmsMessagingServiceFactory;
import persistency.DevicePersistency;
import persistency.cassandra.ConfiguredSensorPersistencyProxy;
import play.Logger;
import play.mvc.Result;
import play.mvc.Security;
import sensorhandling.JsonToSensorEventConversion;
import sensorhandling.preprocessing.SpecialEventPreprocessor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.SensorData;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;
import errors.APIError;
import errors.APIErrorException;
import errors.AssistanceAPIErrors;

public class SensorDataController extends RestController {
	private final MessagingService ms = JmsMessagingServiceFactory
			.createServiceFromConfig();
	private final ConfiguredSensorPersistencyProxy sensorPersistencyProxy = new ConfiguredSensorPersistencyProxy();
	private final JsonToSensorEventConversion jsonToEvent = new JsonToSensorEventConversion();
	private final SpecialEventPreprocessor eventPreprocessor = new SpecialEventPreprocessor();
	

	@Security.Authenticated(UserAuthenticator.class)
	public Result upload() {
		long start = System.currentTimeMillis();
		JsonNode postData = request().body().asJson();

		APIError result = handleSensorData(postData, getUserIdForRequest());

		if (result != null) {
			return badRequestJson(result);
		}
		
		long processingTime = System.currentTimeMillis() - start;
		
		Map<String, Object> r = new HashMap<>();
		r.put("processingTime", processingTime);

		return ok(r);
	}

	private APIError handleSensorData(JsonNode json, long userID) {
		long deviceID;
		try {
			deviceID = processDeviceID(json, userID);
		} catch (APIErrorException e1) {
			return e1.getError();
		}

		if (deviceID == -1) {
			return AssistanceAPIErrors.deviceIdNotKnown;
		}

		JsonNode sensorreadings = json.get("sensorreadings");

		if (sensorreadings != null && sensorreadings.isArray()) {
			try {
				List<SensorData> sensorData = convertJsonNodeToSensorData(
						userID, deviceID, sensorreadings);

				// In Datenbank abspeichern
				boolean result = sensorPersistencyProxy
						.getSensorDataPersistency().persistMany(
								sensorData.toArray(new SensorData[sensorData
										.size()]));

				if (!result) {
					return AssistanceAPIErrors.unknownInternalServerError;
				}

				// In ActiveMQ distributen
				for (SensorData d : sensorData) {
					result = processSensorReading(d);

					if (!result) {
						return AssistanceAPIErrors.unknownInternalServerError;
					}
				}
			} catch (APIErrorException e1) {
				return e1.getError();
			}

		}

		return null;
	}

	private List<SensorData> convertJsonNodeToSensorData(long userID,
			long deviceID, JsonNode sensorreadings) throws APIErrorException {
		Iterator<JsonNode> elementIterator = sensorreadings.elements();

		List<SensorData> result = new LinkedList<>();

		while (elementIterator.hasNext()) {
			JsonNode sensorReading = elementIterator.next();

			String type = sensorReading.path("type").asText();

			try {
				SensorData data = extractSensorData(type, deviceID, userID,
						sensorReading);
				result.add(data);
			} catch (JsonProcessingException e) {
				Logger.warn("Error processing json", e);
				throw new APIErrorException(
						AssistanceAPIErrors.invalidParametersGeneral);
			} catch (DateTimeParseException e) {
				Logger.warn("Failure on processing created timestamp", e);
				throw new APIErrorException(
						AssistanceAPIErrors.invalidParametersGeneral);
			} catch(Exception e) {
				Logger.warn("Failure on processing json to event conversion", e);
				throw new APIErrorException(
						AssistanceAPIErrors.unknownInternalServerError);
			}
		}

		return result;
	}

	private long processDeviceID(JsonNode json, long userID)
			throws APIErrorException {
		JsonNode deviceIdNode = json.get("device_id");
		long deviceID = -1;

		if (deviceIdNode == null) {
			throw new APIErrorException(
					AssistanceAPIErrors.missingParametersGeneral);
		} else {
			deviceID = deviceIdNode.asLong();

			if (!DevicePersistency.ownedByUser(deviceID, userID)) {
				throw new APIErrorException(
						AssistanceAPIErrors.deviceIdNotKnown);
			}
		}

		return deviceID;
	}

	private <T extends SensorData> boolean processSensorReading(T data) {
		return distributeSensorReading(data, (Class<T>) data.getClass());
	}

	private <T extends SensorData> SensorData extractSensorData(String type,
			long deviceID, long userID, JsonNode reading)
			throws Exception {

		Class<T> classType = jsonToEvent.mapTypeToClass(type);

		try {
			// Map json to class
			T eventObject = jsonToEvent.mapJson(reading, classType);
			setDeviceIdForSensorReading(deviceID, eventObject);
			setUserIdForSensorReading(userID, eventObject);
			
			// Preprocess special events
			eventObject = eventPreprocessor.preprocess(eventObject);
			
			if(eventObject == null) {
				throw(new Exception("Failed preprocessing event"));
			}
			
			return eventObject;
		} catch (DateTimeParseException e) {
			throw (e);
		}
	}

	private <T extends SensorData> void setDeviceIdForSensorReading(
			long deviceID, T data) {
		data.deviceId = deviceID;
	}

	private <T extends SensorData> void setUserIdForSensorReading(long userId,
			T data) {
		data.userId = userId;
	}

	private <T extends SensorData> boolean distributeSensorReading(T reading,
			Class<T> targetClass) {
		return ms.channel(targetClass).publish(reading);
	}
}