package hu.cdogbot.fbparser;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.cdogbot.fbparser.model.FbThread;

public class FbMessagesParser {
	private static final Logger log = LoggerFactory.getLogger(FbMessagesParser.class);

	private final Document soup;
	private final FbThreadIterator iterator;
	private final List<String> senders;

	public FbMessagesParser(List<String> senders) throws IOException {
		this.senders = senders;
		log.info("Start parsing {}", Config.MESSAGES_HTML_PATH);
		soup = Jsoup.parse(new File(Config.MESSAGES_HTML_PATH), "UTF-8");
		log.info("Parsed the html");
		Elements threads = soup.select(".thread");
		iterator = new FbThreadIterator(threads);
	}

	public FbThreadIterator iterator() {
		return iterator;
	}

	public class FbThreadIterator implements Iterator<FbThread> {

		private final Elements threads;
		private int idx = 0;
		private FbThread next = null;

		public FbThreadIterator(Elements threads) {
			this.threads = threads;
		}

		@Override
		public boolean hasNext() {
			next = null;
			while (next == null && idx < threads.size()) {
				next = new FbThread(threads.get(idx++));
				//select all except @facebook.com users
				if(senders == null) {
					if (next.getParties().stream().anyMatch(s -> s.contains("@facebook.com"))) {
						next = null;
					}
					//sender filter is active
				} else if(next.getParties().stream().noneMatch(x -> senders.contains(x))) {
					next = null;
				}
			}
			return next != null;
		}

		@Override
		public FbThread next() {
			next.loadUpData();
			return next;
			
		}

	}
}
