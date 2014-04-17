package jp.tnu.aptest;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;


public class WebClient {
	private HttpClient client;
	public WebClient() {
		this.client = HttpClientBuilder.create().build();
	}
	
	public InputStream getStream(String uri) throws IOException {
		HttpGet request= new HttpGet(uri);
		HttpResponse response = client.execute(request);
		HttpEntity entity = response.getEntity();
		return entity.getContent();
	}
}
