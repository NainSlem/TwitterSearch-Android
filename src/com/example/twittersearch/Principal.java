package com.example.twittersearch;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;





import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Principal extends Activity implements OnClickListener{
	

	//url="https://api.twitter.com/1/users/show.json?screen_name="
	
	private GetProfile task;
	private DownloadImageAsyncTask dimagen;
	
	public String nameSearch;
	public String jstring;
	private ProgressBar loading;
	private Profile perfil;

	
	private EditText EText;
	private TableLayout tabla;
	private	 TextView user;
	private TextView name;
	private TextView description;
	private TextView tweets;
	private TextView followers;
	private TextView following;
	private TextView LastTweet;
	private TextView datelast;
	private ImageView ImagenPerfil;

      
	private class DownloadAvatarAsyncTask extends DownloadImageAsyncTask {
		@Override
		protected void onPostExecute(Drawable result) {
			ImagenPerfil.setImageDrawable(result);
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.principal);
        //quitar progreso
        loading=(ProgressBar) findViewById(R.id.progressBar);
        loading.setVisibility(View.GONE);
        
        EText=(EditText) findViewById(R.id.edit);
        user=(TextView) findViewById(R.id.user);
        name=(TextView) findViewById(R.id.nombre);
        description=(TextView) findViewById(R.id.descripcion);
        tweets=(TextView) findViewById(R.id.tweets);
        followers=(TextView) findViewById(R.id.followers);
        following=(TextView) findViewById(R.id.following);
       LastTweet=(TextView) findViewById(R.id.last_status);
        datelast=(TextView) findViewById(R.id.last_status_time);
        ImagenPerfil=(ImageView) findViewById(R.id.imagen);
        tabla=(TableLayout) findViewById(R.id.tableLayout1);
        
        Button buscar=(Button) findViewById(R.id.search);
        buscar.setOnClickListener(this);
        clearProfile();
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    	MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.principal, menu);
        return true;
    }
    private void clearProfile() {
		user.setText("");
		name.setText("");
		description.setText("");
		tweets.setText("");
		followers.setText("");
		following.setText("");
		LastTweet.setText("");
		datelast.setText("");
		ImagenPerfil.setImageDrawable(null);
		tabla.setVisibility(View.GONE);
		
	}
    
    public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.clear:
			clearProfile();
			return true;
		case R.id.quit:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		nameSearch=EText.getText().toString();
		task = new GetProfile(this);
		task.execute(nameSearch);

		
		
	}
    
	private class GetProfile extends	AsyncTask<String, Void, Profile>
	{
		private Context mContext;

		public GetProfile(Context context) {
			mContext = context;
		}
		
		protected void onPreExecute(){
			loading.setVisibility(View.VISIBLE);
		}

		@Override
		protected Profile doInBackground(String... params) {
			String name=params[0];
			try {
				try {
					perfil = new Profile(mContext, name);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return perfil;
		}
		
		protected void onPostExecute(Profile perfil){
			loading.setVisibility(View.GONE);
			try{
				
			showProfile(perfil);
			
			
			}
			catch(NullPointerException x){
				//Toast.makeText(mContext, "No se encuentra a adie con ese nombre", Toast.LENGTH_LONG).show();

				
			}
		}

		private void showProfile(Profile perfil) {
			// TODO Auto-generated method stub
			clearProfile();
			tabla.setVisibility(View.VISIBLE);
			Profile profile=perfil;
			new DownloadAvatarAsyncTask().execute(profile.getImagenUrl());
			
			user.setText(profile.getUser());
			name.setText(profile.getName());
			description.setText(profile.getDescription());
			tweets.setText(""+profile.getNumTweets());
			followers.setText(""+profile.getFollowers());
			following.setText(""+profile.getFollowing());
			LastTweet.setText(profile.getLastTwit());
			Typeface bat = Typeface.createFromAsset(getAssets(), "batfo.ttf");
			datelast.setTypeface(bat);
			datelast.setText(profile.getLastDate());
			dimagen.execute(profile.getImagenUrl());
			
			
		}

		
		
		
	}
	
	
	
	
	
}
    

