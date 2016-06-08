package hu.cdogbot.rest;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * Created by cdog on 2016.06.08..
 */
@Singleton
public class RestResponse {

    private Client client;

    @PostConstruct
    public void setUp() {
        client = ClientBuilder.newClient();
    }


    @PreDestroy
    public void destroy() {
        client.close();
    }
}
