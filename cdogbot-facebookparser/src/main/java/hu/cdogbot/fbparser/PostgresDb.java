package hu.cdogbot.fbparser;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.postgresql.Driver;

public class PostgresDb {

	private static final Driver driver = new Driver();
	private Connection connection = null;

	private PostgresDb() {
	}

	public void startUp(String url, String username, String password) throws SQLException {
		Properties props = new Properties();
		props.setProperty("user", username);
		props.setProperty("password", password);

		connection = driver.connect(url, props);
	}

	public void tearDown() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}
}
