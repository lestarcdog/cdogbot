package hu.cdogbot.logic;

import hu.cdogbot.db.ResponseCdogDao;
import hu.cdogbot.model.FacebookMessaging;
import hu.cdogbot.rest.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Stateless
public class DialogControl {

    private static final Logger log = LoggerFactory.getLogger(DialogControl.class);
    private static final Random RAND = new Random();

    @Inject
    ResponseCdogDao responseDao;

    @Inject
    RestResponse responseSender;

    public void userSaid(List<FacebookMessaging> messagings) {
        log.debug("#{} messages from {}", messagings.size(), messagings.get(0).getSender().getId());
        String senderId = messagings.get(0).getSender().getId();
        String utterance = messagings.get(0).getMessage().getText();
        String response = searchForResponse(utterance);
        log.debug("For '{}' csaba replied '{}'", utterance, response);
        responseSender.sendResponseToUser(senderId, response);
    }


    private String searchForResponse(String utterance) {
        try {
            Optional<List<String>> responsesList = responseDao.findResponse(utterance);
            return selectResponse(responsesList);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            return DefaultResponses.selectRandom();
        }
    }


    private String selectResponse(Optional<List<String>> responses) {
        if (responses.isPresent()) {
            List<String> list = responses.get();
            return list.get(RAND.nextInt(list.size()));
        } else {
            return DefaultResponses.selectRandom();
        }
    }
}
