package hu.cdogbot.fbparser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.cdogbot.fbparser.db.PostgresDb;
import hu.cdogbot.fbparser.model.FbThread;

public class DialogChainer {
	
	private static final Logger log = LoggerFactory.getLogger(DialogChainer.class);
	
	private final FbThread thread;
	private final PostgresDb db;
	
	public DialogChainer(FbThread thread,PostgresDb db) {
		this.thread = thread;
		this.db = db;
		log.info("Parsing thread {}", thread.getParties());
	}

}
