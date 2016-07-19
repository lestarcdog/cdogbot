package hu.cdogbot.db;

import hu.cdogbot.CdogBot;
import hu.cdogbot.fbparser.db.PooledPostgresDb;
import hu.cdogbot.fbparser.db.RankedResponse;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Stateless
public class ResponseCdogDao {

    @Resource(mappedName = CdogBot.CDOGBOT_DS)
    DataSource cdogbotDs;

    public Optional<List<RankedResponse>> findResponse(String utterance) throws SQLException {
        PooledPostgresDb ds = new PooledPostgresDb(cdogbotDs);
        return ds.findResponse(utterance);
    }
}
