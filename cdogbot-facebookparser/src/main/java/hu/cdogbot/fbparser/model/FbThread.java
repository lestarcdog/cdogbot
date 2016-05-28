package hu.cdogbot.fbparser.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FbThread implements Iterable<FbMessage> {
	private static final int THREAD_DIV_LENGTH = "<div class=\"thread\">".length();
	private static final String TZ_END = " UTC+02";
	private final List<String> parties;
	private List<FbMessage> fbMessages = new LinkedList<>();

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy 'at' h:mma")
			.withLocale(Locale.ENGLISH);

	public FbThread(Element element) {
		this.parties = extractThreadParties(element.toString());
		Elements messages = element.select(".message");
		// ascending with time
		Collections.reverse(messages);

		for (Element message : messages) {
			String sender = message.select(".user").text();
			LocalDateTime timestamp = parseFacebookStupidTimeFormat(message.select(".meta").text());
			String msg = message.nextElementSibling().text();
			fbMessages.add(new FbMessage(sender, timestamp, msg));
		}

	}

	private LocalDateTime parseFacebookStupidTimeFormat(String time) {
		String transformed = time.replace(TZ_END, "").replace("am", "AM").replace("pm", "PM");
		return formatter.parse(transformed, LocalDateTime::from);
	}

	public List<String> getParties() {
		return parties;
	}

	private List<String> extractThreadParties(String threadHtml) {
		int firstIndex = threadHtml.indexOf("<div class=\"message\">");
		String[] split = threadHtml.substring(THREAD_DIV_LENGTH, firstIndex).toLowerCase().split(",");
		ArrayList<String> list = new ArrayList<>();
		for (String name : split) {
			list.add(name.trim());
		}

		return list;
	}

	@Override
	public Iterator<FbMessage> iterator() {
		return fbMessages.iterator();
	}

}
