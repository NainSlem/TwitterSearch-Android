package com.example.twittersearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;



public class descargaDatos {
		
	
	public String downloadData(String url) throws ClientProtocolException,
	IOException {
	final HttpClient client = new DefaultHttpClient();
	final HttpGet request = new HttpGet(url);
	final HttpResponse response = client.execute(request);
	final InputStream contentStream = response.getEntity().getContent();
	final BufferedReader reader = new BufferedReader(
	new InputStreamReader(contentStream));
	final StringBuilder result = new StringBuilder();
	String line;
	while ((line = reader.readLine()) != null) {
	result.append(line);
	}
	
	return result.toString();
	}
	
	

}
