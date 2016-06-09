package hu.cdogbot.fbparser;

import hu.cdogbot.fbparser.FbMessagesParser.FbThreadIterator;
import hu.cdogbot.fbparser.db.LocalPostgresDb;
import hu.cdogbot.fbparser.model.FbThread;
import hu.cdogbot.fbparser.model.IdSequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

public class Main {

	private static final Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws IOException, SQLException {
        LocalPostgresDb db = null;
        try {
            db = new LocalPostgresDb();
            db.startUpLocal(args[0], args[1], args[2]);

			FbThreadIterator threads = new FbMessagesParser(Arrays.asList("niki nagy")).iterator();

			IdSequence seq = new IdSequence();
			while (threads.hasNext()) {
				FbThread thread = threads.next();
                new DialogChainer(seq, thread, db).persistRequestReply();
            }

        } finally {
			db.commit();
			if (db != null) {
				db.tearDown();
			}
		}
	}

}
