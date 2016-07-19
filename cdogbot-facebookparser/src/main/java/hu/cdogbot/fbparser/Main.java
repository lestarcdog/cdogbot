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
import java.util.Objects;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException, SQLException {
        LocalPostgresDb db = null;
        try {
            db = new LocalPostgresDb();
            db.startUpLocal(args[0], args[1], args[2]);

            String htmlPath = args[3];
            Objects.requireNonNull(htmlPath, "4th param as the facebook message html format");

            FbThreadIterator threads = new FbMessagesParser(htmlPath, Arrays.asList("niki nagy")).iterator();

            IdSequence messageSeq = new IdSequence();
            long threadId = 1;
            while (threads.hasNext()) {
                FbThread thread = threads.next();
                new DialogChainer(messageSeq,threadId++, thread, db).persistRequestReply();
            }
        }catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            if (db != null) {
                db.commit();
                db.tearDown();
            }
        }
    }

}
