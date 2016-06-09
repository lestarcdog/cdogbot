package hu.cdogbot.rest;

import hu.cdogbot.CdogBot;
import hu.cdogbot.model.FacebookReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by cdog on 2016.06.08..
 */
@Singleton
public class RestResponse {

    private static final Logger log = LoggerFactory.getLogger(RestResponse.class);
    private Client client;

    @PostConstruct
    public void setUp() {
        client = ClientBuilder.newClient();
    }


    public void sendResponseToUser(String id, String reply) {
        FacebookReply payload = new FacebookReply(id, reply);
        WebTarget target = client.target(CdogBot.FB_REPLY_ADDR);

        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).buildPost(Entity.json(payload)).invoke();
        log.debug("Reply was processed {}", response.getStatus());
        response.close();
    }


    @PreDestroy
    public void destroy() {
        client.close();
    }
}
