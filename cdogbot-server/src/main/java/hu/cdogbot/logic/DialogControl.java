package hu.cdogbot.logic;

import hu.cdogbot.db.ResponseCdogDao;
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

    public String userSaid(String utterance) {
        log.debug("find response to '{}'", utterance);
        try {
            Optional<List<String>> response = responseDao.findResponse(utterance);
            return selectResponse(response);
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
