package com.example.twittersearch;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;



import android.content.Context;
import android.text.format.DateFormat;
import android.text.format.DateUtils;

public class Profile {
	private Context context;
	private String imagenurl;
	private String user;
	private String nombre;
	private String descripcion;
	private String lastTwit;
	private String datelast;
	private int following;
	private int followers;
	private int twets;
	
	public Profile(Context context, String name) throws JSONException, ClientProtocolException, IOException{
		this.context=context;
		update(name);
	}

	private void update(String name) throws JSONException, ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		String url="https://api.twitter.com/1/users/show.json?screen_name="+name;
		descargaDatos download=new descargaDatos();
		JSONObject profile = new JSONObject(download.downloadData(url));
		imagenurl=profile.getString("profile_image_url");
		user=profile.getString("screen_name");
		nombre=profile.getString("name");
		descripcion=profile.getString("description");
		followers=profile.getInt("followers_count");
		following=profile.getInt("friends_count");
		twets=profile.getInt("statuses_count");
		
		JSONObject status = profile.getJSONObject("status");
		lastTwit=status.getString("text");
		datelast =status.getString("created_at");
	}
	
	private Date parseCreatedAt(String dateStr) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"EEE MMM dd HH:mm:ss ZZZZZ yyyy");
		dateFormat.setLenient(false);
		Date created = null;
		try {
			created = (Date) dateFormat.parse(dateStr);
		} catch (Exception e) {
			return null;
		}
		return created;
	}
	public String getUser() {
		return user.startsWith("@")?user:"@"+user;
	}
	
	public String getName()
	{
		return nombre;
	}
	public String getImagenUrl() {
		return imagenurl;
	}
	
	public Context getContext() {
		return context;
	}

	public String getDescription() {
		return descripcion;
	}

	public int getFollowers() {
		return followers;
	}

	public int getFollowing() {
		return following;
	}
	public int getNumTweets() {
		return twets;
	}

	public String getLastTwit() {
		return lastTwit;
	}
	public  String getLastDate(){
		
			return datelast;
		
	}
	
	
	

	
	
	

}
