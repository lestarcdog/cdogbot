package hu.cdogbot.logic;

import hu.cdogbot.db.LocalResponseCdogDaoDummy;
import hu.cdogbot.db.ResponseCdogDao;
import hu.cdogbot.model.FacebookMessage;
import hu.cdogbot.model.FacebookMessaging;
import hu.cdogbot.model.FacebookUser;
import hu.cdogbot.rest.RestResponse;
import org.apache.http.protocol.ResponseServer;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Created by cdog on 2016.07.19..
 */
public class DialogControlManual {

    private class LoggerResponseSender extends RestResponse {
        @Override
        public void sendResponseToUser(String id, String reply) {
            System.out.println(id + " " + reply);
        }
    }

    @Test
    public void manualTest() {
        DialogControl dc = new DialogControl();
        dc.responseDao = new LocalResponseCdogDaoDummy();
        dc.responseSender = new LoggerResponseSender();


        String text = "szia";

        FacebookMessaging messaging = new FacebookMessaging();
        messaging.setSender(new FacebookUser("cdog"));
        messaging.setRecipient(new FacebookUser("asdf"));
        messaging.setMessage(new FacebookMessage("abcd",123L,text));

        dc.userSaid(Arrays.asList(messaging));

    }

}
