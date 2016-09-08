package com.whileloop.smartglucosemanager;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends Activity {
	//EditText docEmail;
	String subject = "Smart Glucose Manager Export Logs";
	String toEmail = "";
	User user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		//docEmail = (EditText)findViewById(R.id.docEmailTxt);
		user = User.getUser(this);
		if(user!=null){
			toEmail = user.dcp_email;
		}
		if(toEmail == null || toEmail.equals("")){
			toEmail = "";
		}
	}
	
	public void onButtonClick(View v){
		Intent intent;
    	switch (v.getId()) {
			case R.id.emaillogbtn:
				intent = new Intent(Intent.ACTION_SEND);
				intent.setData(Uri.parse("mailto:"));
				intent.setType("text/html");//text/plain
				intent.putExtra(Intent.EXTRA_EMAIL, new String[] { toEmail });
				intent.putExtra(Intent.EXTRA_SUBJECT, subject);
				intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(emailBody()));
				try{
					startActivity(Intent.createChooser(intent, "Send Email..."));
					finish();
				}catch(ActivityNotFoundException ex){
					Toast.makeText(this, "No email client found", Toast.LENGTH_SHORT).show();
				}
				
			break;
    	}
	}
	private String emailBody(){
		DatabaseHelper databaseHelper = new DatabaseHelper(this);
		GlucoseEntry[] entries = databaseHelper.getGlucoseEntries();
		String result = "";
		//String result = "<table>";
		//result += "<tr><th>Date</th><th>Time</th><th>Event</th><th>Value</th></tr>";
		for(int i=0;i<entries.length;i++){
			result += "<b>Record "+(i+1)+":</b><br>\n";
			result += "Date:<i>"+entries[i].getDate()+":</i><br>\n";
			result += "Time:<i>"+entries[i].getTime()+":</i><br>\n";
			result += "Event:<i>"+entries[i].getTimeOfEvent()+":</i><br>\n";
			result += "Value:<i>"+entries[i].getBg()+":</i><br><br>\n\n";
			//result += "<tr>";
			//result += "<td>"+entries[i].getDate()+"</td>";
			//result += "<td>"+entries[i].getTime()+"</td>";
			//result += "<td>"+entries[i].getTimeOfEvent()+"</td>";
			//result += "<td>"+entries[i].getBg()+"</td>";
			//result += "</tr>";
		}
		//result += "</table>";
		return result;
	}
	public void onBackPress(View v){
		onBackPressed();
	}
	@Override
     public void onBackPressed(){
		Intent i = new Intent(this,MainActivity.class);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
