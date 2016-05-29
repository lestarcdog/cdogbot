package hu.cdogbot.fbparser.db;

import java.sql.SQLException;
import java.time.LocalDateTime;

import org.junit.Test;

import hu.cdogbot.fbparser.model.FbMessage;

public class PostgresDbTest {

	@Test
	public void test() throws SQLException {
		PostgresDb db = new PostgresDb();
		try {
			db.startUp("jdbc:postgresql://localhost:5432/cdogbot", "postgres", "postgres");
			FbMessage msg = new FbMessage("csaba", LocalDateTime.now(), "ez egy jó buli lesz");
			msg.setId(1L);

			db.save(msg);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//db.rollback();
			db.commit();
			db.tearDown();
		}

	}

}
