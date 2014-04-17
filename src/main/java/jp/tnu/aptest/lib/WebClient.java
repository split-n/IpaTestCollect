package jp.tnu.aptest.lib;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;


public class WebClient {
	private HttpClient client;
	public WebClient() {
		this.client = HttpClientBuilder.create().build();
	}
	
	public InputStream getStream(String uri) throws IOException {
		HttpGet request = new HttpGet(uri);
		HttpResponse response = client.execute(request);
		HttpEntity entity = response.getEntity();
		return new BufferedInputStream(entity.getContent());
	}
}
