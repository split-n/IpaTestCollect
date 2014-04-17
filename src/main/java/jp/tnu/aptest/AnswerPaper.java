package jp.tnu.aptest;

import java.io.*;
import java.util.*;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class AnswerPaper {
	public static final String[] VALID_ANSWERS = "ア イ ウ エ".split(" ");

	private String asText;
	private ArrayList<String> asList;
	private Map<String,Integer> answersCountMap;

	public AnswerPaper(InputStream pdfFileStream) throws IOException {
		PDFParser pdfParser = new PDFParser(pdfFileStream);
		pdfParser.parse();
		PDDocument pdf = pdfParser.getPDDocument();
	    PDFTextStripper stripper = new PDFTextStripper();
	    StringWriter writer = new StringWriter();
	    stripper.writeText(pdf, writer);
	    writer.flush();
	    this.asText = writer.toString();

        pdf.close();

	    makeAnswersList();
	    makeAnswersCountMap();
	}

	private void makeAnswersList() {
		String[] answers = new String[81];

		String[] lines = asText.split("\n");
		for(String line : lines) {
			// format: "問 1 ウ  問 21 ウ 問 41 イ 問 61 ウ "
			// or: "問 1 ウ  "
			line = line.trim();
			String[] splitted = line.split("\\s+");
			if(!(splitted[0].equals("問"))) continue;

			int qnum = 0;
			String ans = null;
			for(int i=0;i<splitted.length;i++){
				switch(i%3) {
				case 1: // 問番号
					qnum = Integer.valueOf(splitted[i]);
					break;
				case 2: // 正解
					ans = splitted[i];
					break;
				}
				if(qnum != 0 && ans != null) {
					answers[qnum] = ans;
					qnum = 0;
					ans = null;
				}
			}
		}

		this.asList = new ArrayList<>(Arrays.asList(answers));
	}

	private void makeAnswersCountMap() {
		HashMap<String, Integer> result = new HashMap<>();
		for(String key : VALID_ANSWERS) {
			result.put(key,0);
		}

		for(String answer : this.asList) {
			if(answer != null)
				result.put(answer, result.get(answer) + 1);
		}

		this.answersCountMap = result;
	}

	public Map<String, Integer> getAnswersCountMap() {
		return answersCountMap;
	}

	public String getAsText() {
		return asText;
	}
	
	public ArrayList<String> getAsList() {
		return asList;
	}
}