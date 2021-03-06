package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.clientaction.ClientActionSenderDistributor;
import errors.APIErrorException;
import errors.AssistanceAPIErrors;
import persistency.DevicePersistency;
import persistency.UserPersistency;
import play.Logger;
import play.libs.F.Promise;
import play.mvc.Result;
import requests.SendMessageToDeviceRequest;

/**
 * This controller is responsible for handling requests by module to provide the
 * way back to the user / client.
 *
 * @author bjeutter
 */
public class ClientActionController extends RestController {
    public Promise<Result> sendMessageToDevices() {
        JsonNode jsonRequest = request().body().asJson();

        SendMessageToDeviceRequest request = null;

        // Convert the JSON to a Request Object
        try {
            request = (new ObjectMapper()).treeToValue(jsonRequest,
                    SendMessageToDeviceRequest.class);
        } catch (Exception ex) {
            Logger.warn("Parsing send message to device request failed", ex);
            return Promise.pure(badRequestJson(AssistanceAPIErrors.invalidParametersGeneral));
        }

        // Validate the request
        try {
            validateSendMessageRequest(request);
        } catch (APIErrorException e) {
            return Promise.pure(badRequestJson(e.getError()));
        }

        ClientActionSenderDistributor actionDistributor = new ClientActionSenderDistributor();

        Promise<Boolean> sendResultPromise = actionDistributor.sendDataToUserDevices(
                request.userId, request.deviceIds, request.visibleNotification,
                request.data);

        return sendResultPromise.map((sendResult) -> {
            if (sendResult) {
                return ok();
            } else {
                return internalServerErrorJson(AssistanceAPIErrors.unknownInternalServerError);
            }
        });
    }

    private void validateSendMessageRequest(SendMessageToDeviceRequest request)
            throws APIErrorException {
        if (!UserPersistency.doesUserWithIdExist(request.userId)) {
            throw new APIErrorException(AssistanceAPIErrors.userDoesNotExists);
        }

        if (request.deviceIds == null) {
            throw new APIErrorException(
                    AssistanceAPIErrors.missingParametersGeneral);
        } else {
            for (long dId : request.deviceIds) {
                // Check if device is owned by user (implicitly checks if the
                // device exists)
                if (!DevicePersistency.ownedByUser(dId, request.userId)) {
                    throw new APIErrorException(
                            AssistanceAPIErrors.deviceIdNotKnown);
                }
            }
        }
    }
}