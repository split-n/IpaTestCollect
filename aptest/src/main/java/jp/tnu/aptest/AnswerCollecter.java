package jp.tnu.aptest;

import java.io.*;
import java.util.regex.*;
import java.util.*;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class AnswerCollecter {
	private String asText;
	public static final String[] VALID_ANSWERS = "ア イ ウ エ".split(" ");
    public static final Pattern ANSWER_FORMAT = Pattern.compile("^問 (?<qnum>\\d+) (?<answer>(ア|イ|ウ|エ))$"); 

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

	public Map<String, Integer> getAnswersCountMap() {
		HashMap<String, Integer> result = new HashMap<>();
		for(String key : VALID_ANSWERS) {
			result.put(key,0);
		}
		String[] lines = asText.split("\n");

		for(String line : lines) {
			line = line.trim();
			Matcher m = ANSWER_FORMAT.matcher(line);
			if(m.matches()){
				String key = m.group("answer");
				result.put(key, result.get(key) + 1);
			}
		}
		return result;
	}
	
	public String getAsText() {
		return asText;
	}
}