package hu.cdogbot.fbparser;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.cdogbot.fbparser.FbMessagesParser.FbThreadIterator;
import hu.cdogbot.fbparser.model.FbMessage;
import hu.cdogbot.fbparser.model.FbThread;

public class Main {

	private static final Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws IOException {
		System.out.println(args[0]);
		System.out.println(args[1]);
		System.out.println(args[2]);

		FbThreadIterator threads = new FbMessagesParser().iterator();
		while (threads.hasNext()) {
			FbThread thread = threads.next();
			for (FbMessage msg : thread) {
				log.info("sender: {} msg: {}", msg.getSender(), msg.getMessage());
			}
		}
	}

}
