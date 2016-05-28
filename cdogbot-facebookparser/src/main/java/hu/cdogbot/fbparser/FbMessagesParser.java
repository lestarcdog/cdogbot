package hu.cdogbot.fbparser;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

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

	public FbMessagesParser() throws IOException {
		log.info("Start parsing {}", Config.MESSAGES_HTML_PATH);
		soup = Jsoup.parse(new File(Config.MESSAGES_HTML_PATH), "UTF-8");
		Elements threads = soup.select(".thread");
		iterator = new FbThreadIterator(threads);
	}

	public FbThreadIterator iterator() {
		return iterator;
	}

	public class FbThreadIterator implements Iterator<FbThread> {

		private final Elements threads;
		private int idx = 0;

		public FbThreadIterator(Elements threads) {
			this.threads = threads;
		}

		@Override
		public boolean hasNext() {
			return (idx < threads.size());
		}

		@Override
		public FbThread next() {
			return new FbThread(threads.get(idx++));
		}

	}
}
