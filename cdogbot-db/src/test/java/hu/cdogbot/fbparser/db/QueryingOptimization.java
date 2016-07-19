package hu.cdogbot.fbparser.db;

import org.junit.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;


public class QueryingOptimization {


    @Test
    public void queryDb() throws SQLException {
        LocalPostgresDb db = new LocalPostgresDb();
        db.startUpLocal("jdbc:postgresql://localhost:5432/cdogbot","postgres","postgres");
        Optional<List<RankedResponse>> resp = db.findResponse("szia");

        System.out.println(resp.get());

    }

}
