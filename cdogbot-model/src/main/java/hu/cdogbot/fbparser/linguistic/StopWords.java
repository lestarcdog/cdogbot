package hu.cdogbot.fbparser.linguistic;

import java.util.LinkedHashSet;
import java.util.Set;

public class StopWords {

	public static final Set<String> STOP_WORDS;

	static {
		STOP_WORDS = new LinkedHashSet<>();
		STOP_WORDS.add(" az ");
		STOP_WORDS.add(" a ");
		STOP_WORDS.add(" egy ");
		STOP_WORDS.add(" és ");
		STOP_WORDS.add(" vagy ");
		STOP_WORDS.add(" de ");
	}

	public static String removeStopWords(String sentence) {
		String replaced = sentence;
		if(!replaced.isEmpty()) {
			for (String stopWord : STOP_WORDS) {
				//replace with one
				replaced = replaced.replace(stopWord, " ");
			}
			return replaced.replaceAll("\\s+", " ").trim();
		} else {
			return replaced;
		}
	}
	
	public static String stripSmiley(String str) {
		return str.replaceAll("(:D|:d|:p|:P)*", "");
	}
	
	public static String stripNoneLatin(String str) {
		return stripDoubleSpaces(stripSmiley(str.toLowerCase()).replaceAll("[^éáűúőóüöíÉÁŰÚŐÓÜÖÍ\\p{Alnum}\\s]*", "").trim());
	}
	
	public static String stripDoubleSpaces(String str) {
		return str.replaceAll("\\s+", " ");
	}
}
