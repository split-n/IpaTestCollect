package jp.tnu.aptest;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

public class AnswerStatisticsTest {

	@Test
	public void collectAllAnswersCountTest() {
		Map<String, Integer> expected = new HashMap<>();
		expected.put("ア", 18+19);
		expected.put("イ", 18+14);
		expected.put("ウ", 18+21);
		expected.put("エ", 26+26);
		
		AnswerPaper p1 = createInstance("/2013h25a_ap_am_ans.pdf");
		AnswerPaper p2 = createInstance("/2009h21h_fe_am_ans.pdf");
		
		AnswerStatistics stat = new AnswerStatistics();
		stat.addAnswer(p1);
		stat.addAnswer(p2);
		
		Map<String, Integer> actual = stat.collectAllAnswersCount();
		assertArrayEquals(expected.entrySet().toArray(),actual.entrySet().toArray());
	}	

	public AnswerPaper createInstance(String testTargetFileName) {
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
