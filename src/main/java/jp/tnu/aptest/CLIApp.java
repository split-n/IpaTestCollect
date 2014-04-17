package jp.tnu.aptest;

import jp.tnu.aptest.lib.AnswerPaper;
import jp.tnu.aptest.lib.AnswerStatistics;
import jp.tnu.aptest.lib.WebClient;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class CLIApp
{
    public static void main( String[] args ) {
    	List<String> urls= null;
    	if(args.length == 1) { // use pdf url list if arg present
    		try{
    			urls = parseUrlsFromFilePath(args[0]);
    		}catch(IOException e) {
    			e.printStackTrace();
    			System.exit(1);
    		}
    	} else {
			System.err.println("invalid arguament");
			System.exit(1);
		}
		
    	WebClient wc = new WebClient();
    	AnswerStatistics answers = new AnswerStatistics();
	    for (String url : urls) {
	    	try(InputStream file = wc.getStream(url)){
				AnswerPaper paper = new AnswerPaper(file);
				System.out.println("target : " + url.substring(url.lastIndexOf("/")));
				printCollectedResult(paper.getAnswersCountMap());
				System.out.println("----");
				answers.addAnswer(paper);
    		}catch(IOException e) {
				e.printStackTrace();
    		}
    	}

	    TreeMap<String, Integer> collected = new TreeMap<>(answers.collectAllAnswersCount());
		System.out.println("sum : ");
		printCollectedResult(collected);

    }

	private static void printCollectedResult(Map<String,Integer> collected) {
		int sum = 0;
		for(int part : collected.values()){
			sum += part;
		}
		for(Map.Entry<String, Integer> result : collected.entrySet()) {
			System.out.print(result.getKey() + " => " + result.getValue() + "回/");
			System.out.println(String.format("%.1f",(double)result.getValue()/sum*100) + "%");
		}
	}
    
    private static List<String> parseUrlsFromFilePath(String path) throws IOException{
    	Path pdfUrlsListPath = Paths.get(path);
    	List<String> urls = null;
		urls = Files.readAllLines(pdfUrlsListPath, Charset.defaultCharset());

		return urls;
    }
}
