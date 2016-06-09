package hu.cdogbot.fbparser.model;

import hu.cdogbot.fbparser.linguistic.StopWords;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FbThread implements Iterable<FbMessage> {
	private static final int THREAD_DIV_LENGTH = "<div class=\"thread\">".length();
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy 'at' h:mma")
			.withLocale(Locale.ENGLISH);

	private static final String TZ_END1 = " UTC+02";
	private static final String TZ_END2 = " UTC+01";
    private final List<FbMessage> fbMessages = new LinkedList<>();
    private final List<String> parties;
	private final Element thread;

	public FbThread(Element element) {
		this.parties = extractThreadParties(element.toString());
		this.thread = element;
	}

	public void loadUpData() {
		Elements messages = thread.select(".message");
		// ascending with time
		Collections.reverse(messages);

		for (Element message : messages) {
			String sender = message.select(".user").text().toLowerCase();
			LocalDateTime timestamp = parseFacebookStupidTimeFormat(message.select(".meta").text());
			
			String rawMessage = message.nextElementSibling().text().trim();
			String processedMessage = StopWords.stripNoneLatin(rawMessage.toLowerCase());
			if (!processedMessage.isEmpty()) {
				fbMessages.add(new FbMessage(sender, timestamp, rawMessage,processedMessage));
			}
		}
	}

	private LocalDateTime parseFacebookStupidTimeFormat(String time) {
		String transformed = time.replace(TZ_END1, "").replace(TZ_END2, "").replace("am", "AM").replace("pm", "PM");
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
