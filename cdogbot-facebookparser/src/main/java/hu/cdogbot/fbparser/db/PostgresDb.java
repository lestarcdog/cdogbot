package hu.cdogbot.fbparser.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

import org.postgresql.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.cdogbot.fbparser.linguistic.StopWords;
import hu.cdogbot.fbparser.model.FbMessage;

public class PostgresDb {

	private static final Logger log = LoggerFactory.getLogger(PostgresDb.class);
	private static final Driver driver = new Driver();
	private Connection connection = null;

	private static final String INSERT_FB_MESSAGE = "INSERT INTO chat(id, message, keyword, sender, is_sender_me, sent_time, next_message_id) "
			+ "VALUES(?,?,?::tsvector,?,?,?,?)";

	private static final String FIND_RESPONSE = "SELECT ts_rank(c.keyword,?::tsquery,1) rank, c.next_message_id responseId, resp.message response, c.message message "
			+ " FROM chat c, chat resp " // 
			+ " WHERE c.is_sender_me = FALSE " // not said by me 
			+ " AND c.keyword @@ ?::tsquery " //
			+ " AND c.next_message_id IS NOT NULL " //we have a response
			+ " AND resp.id=c.next_message_id " 
			+ " ORDER BY rank DESC LIMIT 1";

	public PostgresDb() {
	}

	public void startUp(String url, String username, String password) throws SQLException {
		Objects.requireNonNull(url, "missing url parameter");
		Objects.requireNonNull(username, "missing username parameter");
		Objects.requireNonNull(password, "missing password parameter");

		Properties props = new Properties();
		props.setProperty("user", username);
		props.setProperty("password", password);

		connection = driver.connect(url, props);

		log.info("Starting db up on {}", url);
		connection.setAutoCommit(false);
	}

	public void save(FbMessage message) throws SQLException {
		log.info("id: {} user: {} message: {}", message.getId(), message.getSender(), message.getProcessedMessage());

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
	
	public Optional<String> findResponse(String rawQuestion) throws SQLException {
		log.info("Find response for {}",rawQuestion);
		String question = parseToTsVectorOr(rawQuestion);
		
		PreparedStatement stmt = connection.prepareStatement(FIND_RESPONSE);
		stmt.setString(1, question);
		stmt.setString(2, question);
		
		ResultSet resultSet = stmt.executeQuery();
		if(resultSet.next()) {
			return Optional.of(resultSet.getString("response"));
		} else {
			return Optional.empty();
		}
	}
	
	private String parseToTsVectorOr(String msg) {
		String onlyWords = StopWords.stripNoneLatin(msg);
		return onlyWords.replaceAll("\\s", " | ");
	}

	public void commit() throws SQLException {
		connection.commit();
	}

	public void rollback() throws SQLException {
		connection.rollback();
	}

	public void tearDown() throws SQLException {
		log.info("Tear down postgrs db");
		if (connection != null) {
			connection.close();
		}
	}
}
