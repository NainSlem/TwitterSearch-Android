package com.example.twittersearch;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

public class DownloadImageAsyncTask extends AsyncTask<String, Void, Drawable> {

	
	@Override
	protected Drawable doInBackground(String... arg0) {
		final String urlString = arg0[0];
		
		Drawable drawable = null;
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
		    HttpGet request = new HttpGet(urlString);
		    HttpResponse response = httpClient.execute(request);
		    InputStream is = response.getEntity().getContent();
		    drawable = Drawable.createFromStream(is, "src");
		    
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	    return drawable;
	}

}
