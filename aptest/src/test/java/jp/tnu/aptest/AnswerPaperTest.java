package jp.tnu.aptest;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import org.junit.Test;

public class AnswerPaperTest {

	@Test
	public void testGetAnswersMap() {
		AnswerPaper coll = createInstance("/2013h25a_ap_am_ans.pdf");
		Map<String, Integer> actual = coll.getAnswersCountMap();
		Map<String, Integer> expected = new HashMap<>();
		expected.put("ア", 18);
		expected.put("イ", 18);
		expected.put("ウ", 18);
		expected.put("エ", 26);
		assertArrayEquals(
				expected.entrySet().toArray(),actual.entrySet().toArray() );
	}

	@Test
	public void testGetAsText() {
		// ア、イ、ウ、エを少なくとも含むことを確認
		AnswerPaper coll = createInstance("/2013h25a_ap_am_ans.pdf");
		String text = coll.getAsText();
		assertTrue(text.contains("ア"));
		assertTrue(text.contains("イ"));
		assertTrue(text.contains("ウ"));
		assertTrue(text.contains("エ"));
	}
	
	@Test
	public void testGetAsList() {
		String[] expected = "アアエエイエイイウエイウアアエアイアエウアウエイエウウアイエイエイアエウアエエウエアウイアアイエエエエイエウアイウアウウエウイイイイエウウエエエウウアエアイエア".split("");
		expected[0] = null;
		// first is null

		AnswerPaper coll = createInstance("/2013h25a_ap_am_ans.pdf");
		List<String> actual = coll.getAsList();
		assertArrayEquals(expected, actual.toArray());
	}

	private AnswerPaper createInstance(String testTargetFileName) {
		AnswerPaper coll = null;
		String pdfPath = getClass().getResource(testTargetFileName).getPath();
		try(FileInputStream is = new FileInputStream(pdfPath)) {
			coll = new AnswerPaper(is);
		}catch(IOException ex) {
			fail(ex.toString());
		}
		
		return coll;
	}
	
}
