package jp.tnu.aptest;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class AnswerCollecter {
	private String asText;
	public static final String[] VALID_ANSWERS = "ア イ ウ エ".split(" ");

	public AnswerCollecter(InputStream pdfFileStream) throws IOException {
		PDFParser pdfParser = new PDFParser(pdfFileStream);
		pdfParser.parse();
		PDDocument pdf = pdfParser.getPDDocument();
	    PDFTextStripper stripper = new PDFTextStripper();
	    StringWriter writer = new StringWriter();
	    stripper.writeText(pdf, writer);
	    writer.flush();
	    this.asText = writer.toString();
	}

	public Map<String, Integer> getAnswersMap() {
		String[] lines = asText.split("\n");
		HashMap<String, Integer> result = new HashMap<>();
		for(String line : lines) {
			
		}
		result.put("エ", 26);
		result.put("イ", 18);
		result.put("ウ", 18);
		result.put("ア", 18);

		return result;
	}
	
	public String getAsText() {
		return asText;
	}
}