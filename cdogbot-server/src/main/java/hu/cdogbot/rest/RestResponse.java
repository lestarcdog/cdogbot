package hu.cdogbot.rest;

import hu.cdogbot.CdogBot;
import hu.cdogbot.db.ParameterDao;
import hu.cdogbot.db.ParameterType;
import hu.cdogbot.model.FacebookReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
public class RestResponse {

    private static final Logger log = LoggerFactory.getLogger(RestResponse.class);
    private Client client;

    @Inject
    ParameterDao parameterDao;

    private String replyAddress;

    @PostConstruct
    public void setUp() {
        client = ClientBuilder.newClient();
        replyAddress = CdogBot.FB_REPLY_ADDR + parameterDao.getParameter(ParameterType.ACCESS_TOKEN);
    }


    public void sendResponseToUser(String id, String reply) {
        FacebookReply payload = new FacebookReply(id, reply);
        WebTarget target = client.target(replyAddress);

        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).buildPost(Entity.json(payload)).invoke();
        log.debug("Reply was processed {}", response.getStatus());
        response.close();
    }


    @PreDestroy
    public void destroy() {
        client.close();
    }
}
