package hu.cdogbot.fbparser.db;

import java.sql.Connection;
import java.sql.SQLException;
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

	public PostgresDb() {
	}

	public void startUp(String url, String username, String password) throws SQLException {
		Objects.requireNonNull(url,"missing url parameter");
		Objects.requireNonNull(username,"missing username parameter");
		Objects.requireNonNull(password,"missing password parameter");
		
		Properties props = new Properties();
		props.setProperty("user", username);
		props.setProperty("password", password);

		connection = driver.connect(url, props);
		
		log.info("Starting db up on {}",url);
	}
	
	public void save(FbMessage message) {
		log.info("Saving {}'s message {}",message.getSender(),message.getMessage());
	}

	public void tearDown() throws SQLException {
		log.info("Tear down postgrs db");
		if (connection != null) {
			connection.close();
		}
	}
}
