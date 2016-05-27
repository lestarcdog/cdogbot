package hu.cdogbot.rest;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.cdogbot.model.FacebookMessaging;
import hu.cdogbot.model.FacebookReceive;

@Path("/webhook")
public class RestWebhook {
	private static final Logger log = LoggerFactory.getLogger(CdogBot.class);

	private static final String SECURITY_TOKEN = "this-is-for-the-niki";

	@GET
	public String request(@Context HttpServletRequest request) {
		log.info("{}", request.getParameterMap());
		String verifyToken = request.getParameter("hub.verify_token");
		if (verifyToken != null && verifyToken.equals(SECURITY_TOKEN)) {
			String challange = request.getParameter("hub.challenge");
			return challange;
		} else {
			return "Not this time. Bitch";
		}
	}

	@POST
	public void receiveMessage(FacebookReceive receive) {
		log.info("{}", receive);
		List<FacebookMessaging> messaging = receive.getEntry().get(0).getMessaging();
		messaging.stream().forEach(m -> {
			String sender = m.getSender().getId();
			String text = m.getMessage().getText();
			LocalDateTime timestamp = m.timestamp();
			log.info("user {} said {} at {}", sender, text, timestamp);
		});
	}
}
