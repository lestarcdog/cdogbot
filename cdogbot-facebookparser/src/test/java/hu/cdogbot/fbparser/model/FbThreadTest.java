package hu.cdogbot.fbparser.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.junit.Test;

public class FbThreadTest {

	@Test
	public void parseDateTime() {
		String time = "Sunday, April 12, 2015 at 6:57PM";
		DateTimeFormatter format = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy 'at' h:mma")
				.withLocale(Locale.ENGLISH);

		System.out.println(LocalDateTime.now().format(format));
		System.out.println(format.parse(time));

		// format.parse(time);
	}

}
