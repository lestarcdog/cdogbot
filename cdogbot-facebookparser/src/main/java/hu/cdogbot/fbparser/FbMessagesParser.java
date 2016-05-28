package hu.cdogbot.fbparser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.cdogbot.fbparser.model.Pair;

public class FbMessagesParser implements Iterable<Pair> {
	private static final Logger log = LoggerFactory.getLogger(FbMessagesParser.class);
	private static final int THREAD_DIV_LENGTH = "<div class=\"thread\">".length();

	private final Document soup;
	private final FbThreadIterator iterator;

	public FbMessagesParser() throws IOException {
		log.info("Start parsing {}", Config.MESSAGES_HTML_PATH);
		soup = Jsoup.parse(new File(Config.MESSAGES_HTML_PATH), "UTF-8");
		Elements threads = soup.select(".thread");

		log.info(extractThreadParties(threads.outerHtml()).toString());

		iterator = new FbThreadIterator();
	}

	private List<String> extractThreadParties(String html) {
		int firstIndex = html.indexOf("<div class=\"message\">");
		String[] split = html.substring(THREAD_DIV_LENGTH, firstIndex).toLowerCase().split(",");
		ArrayList<String> list = new ArrayList<>();
		for (String name : split) {
			list.add(name.trim());
		}

		return list;
	}

	@Override
	public Iterator<Pair> iterator() {
		return iterator;
	}

	public class FbThreadIterator implements Iterator<Pair> {

		public FbThreadIterator() {

		}

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Pair next() {
			// TODO Auto-generated method stub
			return null;
		}

	}
}
