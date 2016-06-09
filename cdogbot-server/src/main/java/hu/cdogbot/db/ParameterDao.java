package hu.cdogbot.db;

import hu.cdogbot.CdogBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Created by cdog on 2016.06.09..
 */
@Stateless
public class ParameterDao {

    private static final Logger log = LoggerFactory.getLogger(ParameterDao.class);

    @Resource(mappedName = CdogBot.CDOGBOT_DS)
    DataSource cdogbotDs;

    private static final String GET_P = "SELECT p.value FROM PARAMETERS WHER p.name = ?";

    public String getParameter(ParameterType param) {
        Objects.requireNonNull(param);
        try (Connection c = cdogbotDs.getConnection(); PreparedStatement stmt = c.prepareStatement(GET_P)) {
            stmt.setString(1, param.name());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return rs.getString(1);
            else throw new RuntimeException("Parameter name not found: " + param.name());

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        throw new RuntimeException("Something is messed up. See previous log.");
    }


}
