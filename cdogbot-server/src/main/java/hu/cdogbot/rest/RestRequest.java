package hu.cdogbot.rest;

import hu.cdogbot.logic.DialogControl;
import hu.cdogbot.model.FacebookMessaging;
import hu.cdogbot.model.FacebookReceive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/webhook")
public class RestRequest {
    private static final Logger log = LoggerFactory.getLogger(RestRequest.class);

	private static final String SECURITY_TOKEN = "this-is-for-the-niki";

    @Inject
    DialogControl dialog;

	@GET
	public String request(@Context HttpServletRequest request) {
        log.debug("{}", request.getParameterMap());
        String verifyToken = request.getParameter("hub.verify_token");
		if (verifyToken != null && verifyToken.equals(SECURITY_TOKEN)) {
			String challange = request.getParameter("hub.challenge");
			return challange;
		} else {
			return "Not this time. Bitch";
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void receiveMessage(FacebookReceive receive) {
        log.debug("{}", receive);
        List<FacebookMessaging> messaging = receive.getEntry().get(0).getMessaging();
        dialog.userSaid(messaging);
    }
}
