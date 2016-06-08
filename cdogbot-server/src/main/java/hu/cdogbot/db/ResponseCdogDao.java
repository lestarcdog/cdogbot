package hu.cdogbot.db;

import hu.cdogbot.CdogBot;
import hu.cdogbot.fbparser.db.PooledPostgresDb;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Created by cdog on 2016.06.08..
 */
@Stateless
public class ResponseCdogDao {

    @Resource(name = CdogBot.CDOGBOT_DS)
    DataSource cdogbotDs;

    public Optional<List<String>> findResponse(String utterance) throws SQLException {
        PooledPostgresDb ds = new PooledPostgresDb(cdogbotDs);
        return ds.findResponse(utterance);
    }
}
