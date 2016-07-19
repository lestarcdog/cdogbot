package hu.cdogbot.fbparser.db;

import hu.cdogbot.fbparser.linguistic.StopWords;
import hu.cdogbot.fbparser.model.FbMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractPostgresDb implements ICdogbotDb {

    String INSERT_FB_MESSAGE = "INSERT INTO " +
        " chat(id,thread_id, message, keyword, sender, is_sender_me, sent_time, next_message_id) " +
        "VALUES(?,?,?,?::tsvector,?,?,?,?)";

    String FIND_RESPONSE = "SELECT ts_rank(c.keyword,?::tsquery,1) rank,c.id messageId, c.next_message_id responseId, resp.message response, c.message message "
        + " FROM chat c, chat resp " //
        + " WHERE c.is_sender_me = FALSE " // not said by me
        + " AND c.keyword @@ ?::tsquery " //
        + " AND c.next_message_id IS NOT NULL " //we have a response
        + " AND resp.id=c.next_message_id "
        + " ORDER BY rank DESC LIMIT 5";

    private static final Logger log = LoggerFactory.getLogger(AbstractPostgresDb.class);

    @Override
    public Optional<List<RankedResponse>> findResponse(String rawQuestion, Connection connection) throws SQLException {
        log.debug("Find response for '{}'", rawQuestion);
        String question = parseToTsVectorOr(rawQuestion);

        try (PreparedStatement stmt = connection.prepareStatement(FIND_RESPONSE)) {
            stmt.setString(1, question);
            stmt.setString(2, question);


            ResultSet resultSet = stmt.executeQuery();
            List<RankedResponse> answers = new ArrayList<>();
            while (resultSet.next()) {
                answers.add(new RankedResponse(
                    resultSet.getDouble("rank"),
                    resultSet.getLong("messageId"),
                    resultSet.getLong("responseId"),
                    resultSet.getString("message"),
                    resultSet.getString("response"))
                );
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
        stmt.setLong(2, message.getThreadId());
        stmt.setString(3, message.getRawMessage());
        stmt.setString(4, message.getProcessedMessage());
        stmt.setString(5, message.getSender());
        stmt.setBoolean(6, message.isSenderMe());
        stmt.setTimestamp(7, new Timestamp(message.getTimestampAsLong()));
        if (message.getNextMessageId() != null) {
            stmt.setLong(8, message.getNextMessageId());
        } else {
            stmt.setNull(8, java.sql.Types.INTEGER);
        }

        stmt.executeUpdate();
    }

    private String parseToTsVectorOr(String msg) {
        String onlyWords = StopWords.stripNoneLatin(msg);
        return onlyWords.replaceAll("\\s", " | ");
    }
}
