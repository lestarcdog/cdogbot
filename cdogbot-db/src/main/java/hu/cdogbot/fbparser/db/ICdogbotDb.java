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

    void save(FbMessage message, Connection connection) throws SQLException;

    Optional<List<RankedResponse>> findResponse(String rawQuestion, Connection connection) throws SQLException;

}
