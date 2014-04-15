package jp.tnu.aptest;

import java.io.*;
import java.util.regex.*;
import java.util.*;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class AnswerCollecter {
	public static final String[] VALID_ANSWERS = "ア イ ウ エ".split(" ");
    public static final Pattern ANSWER_FORMAT = Pattern.compile("^問 (?<qnum>\\d+) (?<answer>(ア|イ|ウ|エ))$"); 

	private String asText;
	private ArrayList<String> asList;
	private Map<String,Integer> answersCountMap;

	public AnswerCollecter(InputStream pdfFileStream) throws IOException {
		PDFParser pdfParser = new PDFParser(pdfFileStream);
		pdfParser.parse();
		PDDocument pdf = pdfParser.getPDDocument();
	    PDFTextStripper stripper = new PDFTextStripper();
	    StringWriter writer = new StringWriter();
	    stripper.writeText(pdf, writer);
	    writer.flush();
	    this.asText = writer.toString();

	    makeAnswersList();
	    makeAnswersCountMap();
	}

	private void makeAnswersList() {
		ArrayList<String> answers = new ArrayList<>();
		answers.add(0, null);

		String[] lines = asText.split("\n");
		for(String line : lines) {
			line = line.trim();
			Matcher m = ANSWER_FORMAT.matcher(line);
			if(m.matches()){
				int qnum = Integer.parseInt(m.group("qnum"));
				String ans = m.group("answer");
				answers.add(qnum, ans);
			}
		}

		this.asList = answers;
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