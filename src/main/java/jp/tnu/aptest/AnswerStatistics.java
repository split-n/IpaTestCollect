package jp.tnu.aptest;

import java.util.*;

public class AnswerStatistics {
	private List<AnswerPaper> answers = new ArrayList<>();
	
	public void addAnswer(AnswerPaper paper) {
		answers.add(paper);
	}

	public Map<String, Integer> collectAllAnswersCount(){
		Map<String, Integer> result = new HashMap<>();
		for(String ans : AnswerPaper.VALID_ANSWERS) {
			result.put(ans, 0);
		}

		for(AnswerPaper paper : answers) {
			for(Map.Entry<String,Integer> set : paper.getAnswersCountMap().entrySet() ) {
				String key = set.getKey();
				result.put(key, result.get(key) + set.getValue());
			}
		}

		return result;
	}
	
	public List<AnswerPaper> getAnswers() {
		return answers;
	}
}
