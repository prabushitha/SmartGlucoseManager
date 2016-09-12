package com.whileloop.smartglucosemanager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SettingsActivity extends Activity {
	
	
	
	
	public static final String CACHE_FILE_NAME = "data.csv";
	Context context;
	//EditText docEmail;
	String subject = "Smart Glucose Manager Export Logs";
	String toEmail = "";
	User user;
	Button email_btn;
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
		context = this;
		email_btn = (Button)findViewById(R.id.emaillogbtn);
		OnClickListener dialogListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LayoutInflater li = LayoutInflater.from(context);
				View promptsView = li.inflate(R.layout.prompt_duration, null);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);
				alertDialogBuilder.setView(promptsView);

				final RadioGroup userInput = (RadioGroup) promptsView.findViewById(R.id.duration_select);
				// set dialog message
				alertDialogBuilder.setCancelable(false).setPositiveButton("OK",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
						// get user input and set it to result
						// edit text
					    	int selectedId = userInput.getCheckedRadioButtonId();
					    	String type = "All";
					    	if(selectedId==R.id.all_logs){
					    		type = "All";
					    	}else if(selectedId==R.id.week_logs){
					    		type = "Week";
					    	}else if(selectedId==R.id.month_logs){
					    		type = "Month";
					    	}else if(selectedId==R.id.year_logs){
					    		type = "Year";
					    	}
					    	StringBuilder csvContent = createCSVString(type);
					    	String attachmentPath = "";
					    	try{
					    		attachmentPath = createCachedFile(context, csvContent);
					    	}catch(IOException e){
					    		
					    	}
					    	Log.v("CSV File Path",attachmentPath);
					    	//String attachmentPath = saveCSV(type);
					    	setEmail(type,attachmentPath);
					    }
					  })
					.setNegativeButton("Cancel",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					    }
					  });

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
			}
		};
		email_btn.setOnClickListener(dialogListener);
		
	}
	/*
	public void onButtonClick(View v){
    	switch (v.getId()) {
			case R.id.emaillogbtn:
				//setEmail();
			break;
    	}
	}*/
	private void setEmail(String type,String attachmentPath){
		Intent intent;
		intent = new Intent(Intent.ACTION_SEND);
		intent.setData(Uri.parse("mailto:"));
		intent.setType("text/html");//text/plain
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] { toEmail });
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(emailBody(type)));
		intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(attachmentPath));
		try{
			startActivity(Intent.createChooser(intent, "Send Email..."));
			finish();
		}catch(ActivityNotFoundException ex){
			Toast.makeText(this, "No email client found", Toast.LENGTH_SHORT).show();
		}
	}
	private String emailBody(String type){//"All","Week","Month","Year"
		/*
		DatabaseHelper databaseHelper = new DatabaseHelper(this);
		GlucoseEntry[] entries = databaseHelper.getGlucoseEntries();
		entries = GlucoseEntry.LastEntries(entries, type);
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
		Log.v("Email Entries", result);
		//result += "</table>";
		return result;*/
		return "See the attachment for "+type+" Logs";
	}
	/*
	public String saveCSV(String type){
		DatabaseHelper databaseHelper = new DatabaseHelper(this);
		GlucoseEntry[] entries = databaseHelper.getGlucoseEntries();
		entries = GlucoseEntry.LastEntries(entries, type);
		
		String path = context.getFilesDir().getPath().toString() + "myfile.csv";
		CSVWriter csvWriter = null;
		try 
		{
			csvWriter = new CSVWriter(new FileWriter(path), ',');
			for(int i=0;i<entries.length;i++){
				String result = "";
				result += (i+1)+"#";
				result += entries[i].getDate()+"#";
				result += entries[i].getTime()+"#";
				result += entries[i].getTimeOfEvent()+"#";
				result += entries[i].getBg();
				String[] res = result.split("#");
				csvWriter.writeNext(res); 
			}
		    csvWriter.close();
		    Log.v("CSV", "success");
		    return path;
		} 
		catch (IOException e)
		{
			Log.e("CSV", e.getMessage());
		    //error
		}
		return "";
	}
	public void readCSV(){
		String path = context.getFilesDir().getPath().toString() + "myfile.csv";
		try{
			//Build reader instance
		      CSVReader reader = new CSVReader(new FileReader(path), ',', '"', 1);
		       
		      //Read all rows at once
		      List<String[]> allRows = reader.readAll();
		       
		      //Read CSV line by line and use the string array as you want
		     for(String[] row : allRows){
		        Log.v("Read CSV",Arrays.toString(row));
		     }
		     reader.close();
		}catch(Exception e){
			Log.e("CSV", e.getMessage());
		}
	}
	*/
	//caching
	//content = id,name,time,..\n 1,umesh,12,..\n
	public static String createCachedFile(Context context, StringBuilder content) throws IOException {
		  String filePath = context.getCacheDir() + File.separator + CACHE_FILE_NAME;
		  File cacheFile = new File(filePath);
		  cacheFile.createNewFile();

		  FileOutputStream fos = new FileOutputStream(cacheFile);
		  OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF8");
		  PrintWriter pw = new PrintWriter(osw);
		  
		  pw.write(content.toString());
		  
		  pw.flush();
		  pw.close();
		  
		  return "content://" + CachedFileProvider.AUTHORITY + "/" + CACHE_FILE_NAME;
	}
	
	public StringBuilder createCSVString(String type){
		DatabaseHelper databaseHelper = new DatabaseHelper(this);
		GlucoseEntry[] entries = databaseHelper.getGlucoseEntries();
		entries = GlucoseEntry.LastEntries(entries, type);
		StringBuilder sb = new StringBuilder();
        sb.append("id");
        sb.append(',');
        sb.append("date");
        sb.append(',');
        sb.append("time");
        sb.append(',');
        sb.append("event");
        sb.append(',');
        sb.append("value");
        sb.append('\n');
        
			for(int i=0;i<entries.length;i++){
				String result = "";
				result += (i+1)+",";
				result += entries[i].getDate()+",";
				result += entries[i].getTime()+",";
				result += entries[i].getTimeOfEvent()+",";
				result += entries[i].getBg()+"\n";
				sb.append(result);
			}
		   
		
		return sb;
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
