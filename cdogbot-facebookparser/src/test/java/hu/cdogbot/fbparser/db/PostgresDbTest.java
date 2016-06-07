package hu.cdogbot.fbparser.db;

import hu.cdogbot.fbparser.model.FbMessage;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class PostgresDbTest {

	@Test
	public void test() throws SQLException {
		PostgresDb db = new PostgresDb();
		try {
			db.startUp("jdbc:postgresql://localhost:5432/cdogbot", "postgres", "postgres");
			FbMessage msg = new FbMessage("csaba", LocalDateTime.now(),"ez is fuck yeah", "ez egy jó buli lesz? szerintem is.");
			msg.setId(1L);

			db.save(msg);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.rollback();
			//db.commit();
			db.tearDown();
		}
	}
	
	@Test
	public void findResponse() throws SQLException {
		PostgresDb db = new PostgresDb();
		try {
			db.startUp("jdbc:postgresql://localhost:5432/cdogbot", "postgres", "postgres");
			System.out.println(db.findResponse("szeretlek drágám"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//db.rollback();
            //db.commit();
            db.tearDown();
        }
	}

}
