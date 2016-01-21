package controllers;

import java.util.HashMap;
import java.util.Map;

import messaging.JmsMessagingServiceFactory;
import models.ActiveAssistanceModule;
import models.Device;
import models.Token;
import models.User;
import models.UserModuleActivation;
import persistency.ActiveAssistanceModulePersistency;
import persistency.DevicePersistency;
import persistency.UserModuleActivationPersistency;
import persistency.UserPersistency;
import play.Logger;
import play.cache.Cache;
import play.libs.F.Promise;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;
import utility.DateTimeHelper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.typesafe.config.ConfigFactory;

import de.tudarmstadt.informatik.tk.assistanceplatform.platform.data.UserRegistrationInformationEvent;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;
import errors.AssistanceAPIErrors;

//@Api(value = "/users", description = "User authentication / reigstration operations")
public class UsersController extends RestController {
	public Result login() {
		return performActionIfEmailAndPasswordAvailable(this::tryToAuthUser);
	}

	@Security.Authenticated(UserAuthenticator.class)
	public Result refreshToken() {
		String newToken = generateToken(getUserIdForRequest());

		Map<String, Object> result = new HashMap<>();
		result.put("token", newToken);

		return ok(result);
	}

	private Result tryToAuthUser(String mail, String password) {
		if (!User.authenticate(mail, password)) {
			return badRequestJson(AssistanceAPIErrors.badAuthenciationData);
		}

		JsonNode postData = request().body().asJson();

		// Find User and update last login
		User u = UserPersistency.findUserByEmail(mail, false);

		// Check and process device information
		Device d = readDeviceInfos(postData);

		if (d == null) {
			return badRequestJson(AssistanceAPIErrors.missingParametersGeneral);
		}

		d.userId = u.id; // Set user id for device

		Result updateOrCreateResult = updateOrCreateDeviceInfos(postData, d);

		if (updateOrCreateResult != null) {
			return updateOrCreateResult;
		}

		// Update last activity of user and device
		Promise.promise(() -> {
			UserPersistency.updateLastLogin(u.id);
			DevicePersistency.updateLastActivityOfDevice(d.id);

			return null;
		});

		// Create login token
		String token = generateToken(u.id);

		Map<String, Object> result = new HashMap<>();
		result.put("token", token);
		result.put("device_id", d.id);

		return ok(result);
	}

	private String generateToken(long userId) {
		int validityInHours = ConfigFactory.defaultApplication().getInt(
				"token.validityInHours");

		return Token.buildToken(userId, validityInHours).token;
	}

	private Device readDeviceInfos(JsonNode postData) {
		if (postData.has("device")) {
			JsonNode deviceNode = postData.findPath("device");

			if (deviceNode.has("id") || hasDeviceCreationParameters(postData)) { // Mindestens
																					// muss
																					// ID
																					// oder
																					// die
																					// Device
																					// Specs
																					// vorhanden
																					// sein
																					// (mindestens
																					// eins
																					// von
																					// beiden)
				ObjectMapper mapper = new ObjectMapper();

				try {
					return (Device) mapper
							.treeToValue(deviceNode, Device.class);
				} catch (JsonProcessingException e) {
					Logger.error(
							"Something went wrong with the parsing of device infos on login",
							e);
				}
			}
		}

		return null;
	}

	private Result updateOrCreateDeviceInfos(JsonNode postData, Device d) {
		boolean hasDeviceCreationParameters = hasDeviceCreationParameters(postData);

		// If no existing ID was passed, than create one
		if (d.id == 0) {
			if (!hasDeviceCreationParameters) {
				return badRequestJson(AssistanceAPIErrors.missingParametersGeneral);
			}

			DevicePersistency.createIfNotExists(d);
		} else { // If an existing ID was passed
			if (!DevicePersistency.ownedByUser(d.id, d.userId)) {
				return badRequestJson(AssistanceAPIErrors.deviceIdNotKnown);
			}

			if (hasDeviceCreationParameters) { // And a spec was delivered
				DevicePersistency.update(d);
			} else if (!DevicePersistency.doesExist(d)) { // If no spec was
															// delivered
				return badRequestJson(AssistanceAPIErrors.deviceIdNotKnown);
			}
		}

		return null;
	}

	private boolean hasDeviceCreationParameters(JsonNode postData) {
		JsonNode deviceNode = postData.findPath("device");
		return (deviceNode.has("os") && deviceNode.has("os_version")
				&& deviceNode.has("brand") && deviceNode.has("model") && deviceNode
					.has("device_identifier"));
	}

	public Result register() {
		UserAuthenticator authenticator = new UserAuthenticator();

		if (authenticator.getUserId(ctx()) != -1) {
			return badRequestJson(AssistanceAPIErrors.alreadyLoggedIn);
		}

		return performActionIfEmailAndPasswordAvailable(this::tryToRegisterUser);
	}

	private Result tryToRegisterUser(String mail, String password) {
		// TODO: Check for length of password
		// TODO: Check for correctnes of email

		if (UserPersistency.doesUserWithEmailExist(mail)) {
			return badRequestJson(AssistanceAPIErrors.userAlreadyExists);
		}

		User newUser = new User(mail);
		UserPersistency.createAndUpdateIdOnSuccess(newUser, password);

		if (newUser.id != 0) {
			Map<String, Object> result = new HashMap<>();
			result.put("user_id", newUser.id);

			return ok(result);
		}

		return internalServerErrorJson(AssistanceAPIErrors.unknownInternalServerError);
	}

	private Result performActionIfEmailAndPasswordAvailable(
			TwoArgFunction<String, String, Result> action) {
		JsonNode postData = request().body().asJson();

		if (areAllRequiredParametersPosted(postData)) {
			String mail = getEmailNode(postData).textValue();
			String password = getPasswordNode(postData).textValue();

			return action.apply(mail, password);
		} else {
			return badRequestJson(AssistanceAPIErrors.missingUserParameters);
		}
	}

	public interface TwoArgFunction<A, B, C> {
		C apply(A a, B b);
	}

	private boolean areAllRequiredParametersPosted(JsonNode postData) {
		return postData != null && postData.has("email")
				&& postData.has("password");
	}

	private JsonNode getEmailNode(JsonNode postData) {
		return postData.findPath("email");
	}

	private JsonNode getPasswordNode(JsonNode postData) {
		return postData.findPath("password");
	}

	@Security.Authenticated(UserAuthenticator.class)
	public Result myProfile(String type) {
		Long id = getUserIdForRequest();

		String requestedType = type;

		boolean putServices = false;
		switch (requestedType) {
		case "short":
			putServices = false;
			break;
		case "long":
			putServices = true;
			break;
		default:
			return badRequestJson(AssistanceAPIErrors.invalidParametersGeneral);
		}

		User profile = UserPersistency.findUserById(id, true);

		if (profile == null) {
			return internalServerErrorJson(AssistanceAPIErrors.unknownInternalServerError);
		}

		JsonNode result = Json.toJson(profile);
		ObjectNode modifiableResult = (ObjectNode) result;
		modifiableResult.put("joinedSince",
				DateTimeHelper.localDateTimeToTimestamp(profile.joinedSince));
		modifiableResult.put("lastLogin",
				DateTimeHelper.localDateTimeToTimestamp(profile.lastLogin));

		if (putServices) {
			modifiableResult.putArray("services");
		}

		return ok(result);
	}

	@Security.Authenticated(UserAuthenticator.class)
	public Result updateProfile() {
		Long id = getUserIdForRequest();

		JsonNode postData = request().body().asJson();

		if (!postData.has("firstname") && !postData.has("lastname")) {
			return badRequestJson(AssistanceAPIErrors.missingParametersGeneral);
		}

		JsonNode firstnameNode = postData.findPath("firstname");
		JsonNode lastnameNode = postData.findPath("lastname");

		if (!firstnameNode.isTextual() && !lastnameNode.isTextual()) {
			return badRequestJson(AssistanceAPIErrors.invalidParametersGeneral);
		}

		User u = UserPersistency.findUserById(id, false);

		if (u == null) {
			return internalServerErrorJson(AssistanceAPIErrors.unknownInternalServerError);
		}

		u.firstName = firstnameNode.asText();
		u.lastName = lastnameNode.asText();

		UserPersistency.updateProfile(u);

		return ok();
	}

	// /// KILL CODE AFTER EVALUATION
	/**
	 * JUST FOR JMETER!!! KILL FOR PRODUCTION
	 * 
	 * @return
	 */
	public Result creatJmeterTestUsers(String proof) {
		if (!proof.equals("terra")) {
			return badRequest();
		}

		/*
		 * Result result = Cache.getOrElse( "jmeterusers", () -> {
		 */
		String csvResult = "token,device_id";

		ActiveAssistanceModule[] modules = ActiveAssistanceModulePersistency
				.list();

		for (int i = 0; i < 2000; i++) {
			String email = "jmeter_" + i + "@test.de";
			User newUser;

			if ((newUser = UserPersistency.findUserByEmail(email, false)) != null) {
			} else {
				newUser = new User(email);
				UserPersistency.createAndUpdateIdOnSuccess(newUser, "test123");
			}

			long id = newUser.id;

			// Token für alle angelegten Benutzer anlegen
			String token = generateToken(id);

			// Alle nodule aktivieren
			for (ActiveAssistanceModule m : modules) {
				long userId = getUserIdForRequest();
				UserModuleActivationPersistency
						.create(new UserModuleActivation(userId, m.id));
				publishUserRegistrationInformationEvent(id, m.id, true);
			}

			long deviceId = 0;
			Device[] devicesOfUser = DevicePersistency.findDevicesOfUser(id);

			if (devicesOfUser != null && devicesOfUser.length != 0) {
				deviceId = devicesOfUser[0].id;
			} else {
				Device d = new Device(id, "os", "osvers", "dev", "jmeter",
						"jmeter");
				DevicePersistency.createIfNotExists(d);
				deviceId = d.id;
			}

			csvResult += "\n" + email + "," + token + "," + deviceId;
		}

		return ok(csvResult);
		// }, 36000);

		// return result;
	}

	MessagingService ms = JmsMessagingServiceFactory.createServiceFromConfig();

	private void publishUserRegistrationInformationEvent(long userId,
			String moduleId, boolean wantsToBeRegistered) {
		ms.channel(UserRegistrationInformationEvent.class).publish(
				new UserRegistrationInformationEvent(userId, moduleId,
						wantsToBeRegistered));
	}
	// / END KILL CODE
}