package jp.tnu.aptest;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import org.junit.Test;

public class AnswerCollecterTest {

	@Test
	public void testGetAnswersMap() {
		fail("まだ実装されていません");
	}

	@Test
	public void testGetAsText() {
		// ア、イ、ウ、エを少なくとも含むことを確認
		String pdfPath = getClass().getResource("/2013h25a_ap_am_ans.pdf").getPath();
		try(FileInputStream is = new FileInputStream(pdfPath)) {
			AnswerCollecter coll = new AnswerCollecter(is);
			String text = coll.getAsText();
			assertTrue(text.contains("ア"));
			assertTrue(text.contains("イ"));
			assertTrue(text.contains("ウ"));
			assertTrue(text.contains("エ"));
		}catch(IOException ex) {
			fail(ex.toString());
		}
	}
	
}
