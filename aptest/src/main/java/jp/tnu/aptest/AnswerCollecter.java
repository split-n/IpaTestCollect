package jp.tnu.aptest;

import java.io.*;
import java.util.HashMap;

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

	public HashMap<String, Integer> getAnswersMap() {
		String[] lines = asText.split("\n");
		HashMap<String, Integer> result = new HashMap<>();
		for(String line : lines) {
			
		}

		return result;
	}
	
	public String getAsText() {
		return asText;
	}
}