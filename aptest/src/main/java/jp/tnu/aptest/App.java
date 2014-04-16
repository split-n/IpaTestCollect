package jp.tnu.aptest;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class App 
{
    public static void main( String[] args ) {
    	List<String> urls= new ArrayList<>();; 
    	if(args.length != 0) { // use pdf url list if arg present
    		try{
    			urls = parseUrlsFromFilePath(args[0]);
    		}catch(IllegalArgumentException e) {
    			e.printStackTrace();
    			System.exit(1);
    		}
    	} else { // use preset if no arg present
    		urls.add("http://www.jitec.ipa.go.jp/1_04hanni_sukiru/mondai_kaitou_2013h25_2/2013h25a_ap_am_ans.pdf");
    		urls.add("http://www.jitec.ipa.go.jp/1_04hanni_sukiru/mondai_kaitou_2013h25_1/2013h25h_ap_am_ans.pdf");
    		urls.add("http://www.jitec.ipa.go.jp/1_04hanni_sukiru/mondai_kaitou_2012h24_2/2012h24a_ap_am_ans.pdf");
    		urls.add("http://www.jitec.ipa.go.jp/1_04hanni_sukiru/mondai_kaitou_2012h24_1/2012h24h_ap_am_ans.pdf");
    		urls.add("http://www.jitec.ipa.go.jp/1_04hanni_sukiru/mondai_kaitou_2011h23_2/2011h23a_ap_am_ans.pdf");
    		urls.add("http://www.jitec.ipa.go.jp/1_04hanni_sukiru/mondai_kaitou_2011h23_1/2011h23tokubetsu_ap_am_ans.pdf");
    	}
    	
    	WebClient wc = new WebClient();
    	AnswerStatistics answers = new AnswerStatistics();
	    for (String url : urls) {
	    	try(InputStream file = wc.getStream(url);){
			answers.addAnswer(new AnswerPaper(file));
    		}catch(IOException e) {
    			System.out.println("failed to analyze : " + url);
    		}
	    	System.out.println("use : " + url);
    	}
	    
	    for(Map.Entry<String, Integer> result : answers.collectAllAnswersCount().entrySet()) {
	    	System.out.println(result.getKey() + " => " + result.getValue() + "回");
	    }
    }
    
    private static List<String> parseUrlsFromFilePath(String path) {
    	Path pdfUrlsListPath = Paths.get(path);
    	List<String> urls = null;
    	try {
			urls = Files.readAllLines(pdfUrlsListPath, Charset.defaultCharset());
		} catch (IOException e) {
			throw new IllegalArgumentException();
		}
    	
    	return urls;
    }
}
