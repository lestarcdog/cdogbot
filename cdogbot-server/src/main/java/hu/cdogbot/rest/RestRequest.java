package hu.cdogbot.rest;

import hu.cdogbot.db.ParameterDao;
import hu.cdogbot.db.ParameterType;
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

@Path("/")
public class RestRequest {
    private static final Logger log = LoggerFactory.getLogger(RestRequest.class);


    @Inject
    DialogControl dialog;

    @Inject
    ParameterDao parameterDao;

	@GET
	public String request(@Context HttpServletRequest request) {
        log.info("{}", request.getParameterMap());
        String verifyToken = request.getParameter("hub.verify_token");
        String webhookToken = parameterDao.getParameter(ParameterType.WEBHOOK_TOKEN);
        if (verifyToken != null && verifyToken.equals(webhookToken)) {
            return request.getParameter("hub.challenge");
        } else {
			return "Not this time. Bitch";
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void receiveMessage(FacebookReceive receive) {
        log.debug("{}", receive);
        List<FacebookMessaging> messaging = receive.getEntry().get(0).getMessaging();
        if(messaging != null) {
            dialog.userSaid(messaging);
        } else {
            log.info("Can't process. Only messagings.");
        }
    }
}
