package hu.cdogbot.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/webhook")
public class RestWebhook {
	private static final Logger log = LoggerFactory.getLogger(CdogBot.class);

	private static final String SECURITY_TOKEN = "this-is-for-the-niki";

	@GET
	public String request(@Context HttpServletRequest request) {
		log.info("{}", request.getParameterMap());
		String verifyToken = request.getParameter("hub.verify_token");
		if (verifyToken.equals(SECURITY_TOKEN)) {
			String challange = request.getParameter("hub.challenge");
			return challange;
		} else {
			return "Not this time. Bitch";
		}

	}
}
