package hu.cdogbot.fbparser;

import java.io.IOException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.cdogbot.fbparser.FbMessagesParser.FbThreadIterator;
import hu.cdogbot.fbparser.db.PostgresDb;
import hu.cdogbot.fbparser.model.FbThread;
import hu.cdogbot.fbparser.model.IdSequence;

public class Main {

	private static final Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws IOException, SQLException {
		PostgresDb db = null;
		try {
			db = new PostgresDb();
			db.startUp(args[0], args[1], args[2]);

			FbThreadIterator threads = new FbMessagesParser(null).iterator();

			IdSequence seq = new IdSequence();
			while (threads.hasNext()) {
				FbThread thread = threads.next();
				new DialogChainer(seq, thread, db).persistRequestReply();;
			}
			
		} finally {
			db.commit();
			if (db != null) {
				db.tearDown();
			}
		}
	}

}
