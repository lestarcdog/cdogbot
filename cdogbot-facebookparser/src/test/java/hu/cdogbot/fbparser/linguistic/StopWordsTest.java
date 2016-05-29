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
	
	@Test
	public void stripStupidChars() {
		String str = "aaűáú őüó ÚŐP ?.-Í[]1 :D 234 965 Haha😀";
		Assert.assertEquals("aaűáú őüó ÚŐP Í1  234 965 Haha",StopWords.stripNoneLatin(str));
	}
	
	@Test
	public void stripSmiley() {
		String str = "Ha a Szerelmunk lapjait emlited rogton beugrott volna :D";
		System.out.println(StopWords.stripSmiley(str));
	}
	
	@Test
	public void remoevsmiley() {
		String str = "Ha a Szerelmunk lapjait emlited rogton beugrott volna :D";
		System.out.println(StopWords.stripNoneLatin(str));
	}


}
