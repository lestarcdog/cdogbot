package hu.cdogbot.fbparser.db;

import hu.cdogbot.fbparser.model.FbMessage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Created by cdog on 2016.06.08..
 */
public interface ICdogbotDb {

    String INSERT_FB_MESSAGE = "INSERT INTO " +
        " chat(id, message, keyword, sender, is_sender_me, sent_time, next_message_id) " +
        "VALUES(?,?,?::tsvector,?,?,?,?)";

    String FIND_RESPONSE = "SELECT ts_rank(c.keyword,?::tsquery,1) rank, c.next_message_id responseId, resp.message response, c.message message "
        + " FROM chat c, chat resp " //
        + " WHERE c.is_sender_me = FALSE " // not said by me
        + " AND c.keyword @@ ?::tsquery " //
        + " AND c.next_message_id IS NOT NULL " //we have a response
        + " AND resp.id=c.next_message_id "
        + " ORDER BY rank DESC LIMIT 3";

    void save(FbMessage message, Connection connection) throws SQLException;

    Optional<List<String>> findResponse(String rawQuestion, Connection connection) throws SQLException;

}
