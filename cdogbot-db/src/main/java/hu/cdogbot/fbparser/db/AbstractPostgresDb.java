package hu.cdogbot.fbparser.db;

import hu.cdogbot.fbparser.linguistic.StopWords;
import hu.cdogbot.fbparser.model.FbMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by cdog on 2016.06.08..
 */
public abstract class AbstractPostgresDb implements ICdogbotDb {

    private static final Logger log = LoggerFactory.getLogger(AbstractPostgresDb.class);

    @Override
    public Optional<List<String>> findResponse(String rawQuestion, Connection connection) throws SQLException {
        log.debug("Find response for '{}'", rawQuestion);
        String question = parseToTsVectorOr(rawQuestion);

        try (PreparedStatement stmt = connection.prepareStatement(FIND_RESPONSE)) {
            stmt.setString(1, question);
            stmt.setString(2, question);

            ResultSet resultSet = stmt.executeQuery();
            List<String> answers = new ArrayList<>();
            while (resultSet.next()) {
                answers.add(resultSet.getString("response"));
            }
            if (answers.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(answers);
            }
        }
    }

    @Override
    public void save(FbMessage message, Connection connection) throws SQLException {
        log.debug("id: {} user: {} message: {}", message.getId(), message.getSender(), message.getProcessedMessage());

        PreparedStatement stmt = connection.prepareStatement(INSERT_FB_MESSAGE);
        stmt.setLong(1, message.getId());
        stmt.setString(2, message.getRawMessage());
        stmt.setString(3, message.getProcessedMessage());
        stmt.setString(4, message.getSender());
        stmt.setBoolean(5, message.isSenderMe());
        stmt.setTimestamp(6, new Timestamp(message.getTimestampAsLong()));
        if (message.getNextMessageId() != null) {
            stmt.setLong(7, message.getNextMessageId());
        } else {
            stmt.setNull(7, java.sql.Types.INTEGER);
        }

        stmt.executeUpdate();
    }

    private String parseToTsVectorOr(String msg) {
        String onlyWords = StopWords.stripNoneLatin(msg);
        return onlyWords.replaceAll("\\s", " | ");
    }
}
