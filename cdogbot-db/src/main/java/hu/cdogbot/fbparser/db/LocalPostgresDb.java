package hu.cdogbot.fbparser.db;

import hu.cdogbot.fbparser.model.FbMessage;
import org.postgresql.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

public class LocalPostgresDb extends AbstractPostgresDb {

    private static final Logger log = LoggerFactory.getLogger(LocalPostgresDb.class);
    private final Driver driver = new Driver();
    private Connection connection = null;


    public void startUpLocal(String url, String username, String password) throws SQLException {
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
        save(message, connection);
    }


    public Optional<List<RankedResponse>> findResponse(String rawQuestion) throws SQLException {
        return super.findResponse(rawQuestion, connection);
    }

    public void commit() throws SQLException {
        connection.commit();
    }

    public void rollback() throws SQLException {
        connection.rollback();
    }

    public void tearDown() throws SQLException {
        log.info("Tear down postgres db");
        if (connection != null && connection.isClosed()) {
            connection.close();
        }
    }
}
