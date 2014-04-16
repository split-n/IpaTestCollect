package jp.tnu.aptest;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.junit.Test;

public class WebClientTest {

	@Test
	public void download() throws IOException {
		String expectedFilePath = getClass().getResource("/examplecom.html").getPath();
		char[] expected = null;
		try(FileInputStream is = new FileInputStream(expectedFilePath)) {
			expected = readStreamAll(is);
		}
		
		WebClient wc = new WebClient();
		try(InputStream stream = wc.getStream("http://example.com/")) {
			char[] data = readStreamAll(stream);
			assertArrayEquals(expected, data);
		}
	}
	
	private char[] readStreamAll(InputStream stream) throws IOException {
		ArrayList<Character> data = new ArrayList<>();
		
		int buf;
		while((buf = stream.read()) != -1) {
			data.add((char)buf);
		}

		// meaning: return data.toArray(new Character[0]);
		
		int len = data.size();
		char[] result = new char[len];
		for(int i=0;i<len;i++) {
			result[i] = data.get(i);			
		}
		
		return result;
		
	}

}
