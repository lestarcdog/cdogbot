package hu.cdogbot.fbparser.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Properties;

import org.postgresql.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.cdogbot.fbparser.model.FbMessage;

public class PostgresDb {

	private static final Logger log = LoggerFactory.getLogger(PostgresDb.class);
	private static final Driver driver = new Driver();
	private Connection connection = null;

	private static final String INSERT_FB_MESSAGE = "INSERT INTO chat(id,message,sender,is_sender_me,sent_time) " +
													"VALUES(?,?::tsvector,?,?,?)";

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
		log.info("id: {} user: {} message: {}", message.getId(), message.getSender(),message.getMessage());
		
		PreparedStatement stmt = connection.prepareStatement(INSERT_FB_MESSAGE);
		stmt.setLong(1, message.getId());
		stmt.setString(2, message.getMessage());
		stmt.setString(3, message.getSender());
		stmt.setBoolean(4, message.isSenderMe());
		stmt.setTimestamp(5, new Timestamp(message.getTimestampAsLong()));
		
		stmt.executeUpdate();
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
