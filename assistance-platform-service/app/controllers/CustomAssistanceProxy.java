package controllers;

import errors.AssistanceAPIErrors;
import models.ActiveAssistanceModule;
import persistency.UserModuleActivationPersistency;
import play.libs.F.Promise;
import play.libs.ws.WS;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import play.mvc.Http.RequestBody;
import play.mvc.Result;
import play.mvc.Results;
import play.mvc.Security;

import java.util.function.Function;

/**
 * This controller acts as a proxy for module rest services
 *
 * @author bjeutter
 */
public class CustomAssistanceProxy extends RestController {
    @Security.Authenticated(UserAuthenticator.class)
    public Promise<Result> customGet(String moduleId, String path) {
        return custom(moduleId, path, WSRequest::get);
    }

    @Security.Authenticated(UserAuthenticator.class)
    public Promise<Result> customPost(String moduleId, String path) {
        return custom(moduleId, path, (r) -> {
            RequestBody body = request().body();

            String postBody = null;

            if (body.asText() != null) {
                postBody = body.asText();
            } else if (body.asJson() != null) {
                r.setContentType("application/json");
                postBody = body.asJson().toString();
            } else if (body.asXml() != null) {
                r.setContentType("application/xml");
                postBody = body.asXml().toString();
            }

            return r.post(postBody);
        });
    }


    private Promise<Result> custom(
            String moduleId,
            String path,
            Function<WSRequest, Promise<WSResponse>> prepareAndFireRequest) {
        long userId = getUserIdForRequest();

        if (!UserModuleActivationPersistency.doesActivationExist(userId,
                moduleId)) {
            return Promise
                    .pure(badRequestJson(AssistanceAPIErrors.moduleActivationNotActive));
        }

        ActiveAssistanceModule module = UserModuleActivationPersistency.activatedModuleEndpointsForUser(new String[]{moduleId})[0];

        WSRequest request = WS.url(module.restUrl("/custom/" + path));
        request.setHeader("ASSISTANCE-USER-ID", Long.toString(userId));
        Promise<WSResponse> responsePromise = prepareAndFireRequest.apply(request);

        return responsePromise.map((response) -> (Result) Results.status(response.getStatus(),
                response.getBody()));
    }
}
