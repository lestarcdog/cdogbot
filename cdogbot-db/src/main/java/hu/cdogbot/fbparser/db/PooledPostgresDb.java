package hu.cdogbot.fbparser.db;

import hu.cdogbot.fbparser.model.FbMessage;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Created by cdog on 2016.06.08..
 */
public class PooledPostgresDb extends AbstractPostgresDb {

    private final DataSource ds;

    public PooledPostgresDb(DataSource dataSource) {
        this.ds = dataSource;
    }

    @Override
    public void save(FbMessage message, Connection connection) throws SQLException {
        throw new UnsupportedOperationException("This is not for you server you only read.");
    }

    public Optional<List<String>> findResponse(String rawQuestion) throws SQLException {
        try (Connection connection = ds.getConnection()) {
            return super.findResponse(rawQuestion, connection);
        }
    }

}
