package hu.cdogbot.fbparser.linguistic;

import org.junit.Assert;
import org.junit.Test;

public class StopWordsTest {

	@Test
	public void removeStopWords() {
		String sentence = "Ezt az egy mondatod a szedd ki.".toLowerCase();
		Assert.assertEquals("ezt mondatod szedd ki." ,StopWords.removeStopWords(sentence));
	}
	
	@Test
	public void emptyOnlyStopWords() {
		String sentence = "az de egy".toLowerCase();
		String removed = StopWords.removeStopWords(sentence);
		Assert.assertEquals("az egy", removed);
	}


}
