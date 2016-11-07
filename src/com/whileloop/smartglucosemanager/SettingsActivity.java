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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class SettingsActivity extends Activity {
	Setting setting;
	
	
	
	public static final String CACHE_FILE_NAME = "data.csv";
	Context context;
	//EditText docEmail;
	String subject = "Smart Glucose Manager Export Logs";
	String toEmail = "";
	String fromEmail = "";
	User user;
	Button email_btn;
	
	RadioGroup unitRadioGroup;
	CheckBox chkSound;
	CheckBox chkVibrate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		setting = Setting.getSetting(this);
		if(setting==null){
			setting = new Setting();
		}
		
		user = User.getUser(this);
		
		if(user!=null){
			toEmail = user.dcp_email;
			fromEmail = user.email;
		}
		if(toEmail == null){
			toEmail = "";
		}
		
		if(fromEmail == null){
			fromEmail = "";
		}
		
		
		context = this;
		email_btn = (Button)findViewById(R.id.emaillogbtn);
		chkSound = (CheckBox)findViewById(R.id.chksound);
		chkVibrate = (CheckBox)findViewById(R.id.chkvibrate);
		
		chkSound.setChecked(setting.isSound);
		chkVibrate.setChecked(setting.isVibrate);
		
		if(setting.unit==Setting.UNIT_TYPES[0]){
			RadioButton rb = (RadioButton)findViewById(R.id.metricRadio);
			rb.setChecked(true);
		}else if(setting.unit==Setting.UNIT_TYPES[1]){
			RadioButton rb = (RadioButton)findViewById(R.id.usRadio);
			rb.setChecked(true);
		}
		
		unitRadioGroup = (RadioGroup)findViewById(R.id.unitRadioGroup);
		unitRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
            	Log.d("chk", "id" + checkedId);

                if (checkedId == R.id.metricRadio) {
                	setting.unit = Setting.UNIT_TYPES[0];
                    //some code
                } else if (checkedId == R.id.usRadio) {
                	setting.unit = Setting.UNIT_TYPES[1];
                    //some code
                }
                Setting.saveSetting(context, setting);

            }

        });
		
		
		
		
		OnClickListener dialogListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(v.getId()==email_btn.getId()){
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
						    	}else if(selectedId==R.id.day07_logs){
						    		type = "7days";
						    	}else if(selectedId==R.id.day14_logs){
						    		type = "14days";
						    	}else if(selectedId==R.id.day30_logs){
						    		type = "30days";
						    	}else if(selectedId==R.id.day90_logs){
						    		type = "90days";
						    	}
						    	StringBuilder csvContent = createCSVString(type);
						    	promptEmailSender(csvContent);
						    	/*
						    	String attachmentPath = "";
						    	try{
						    		attachmentPath = createCachedFile(context, csvContent);
						    	}catch(IOException e){
						    		
						    	}
						    	Log.v("CSV File Path",attachmentPath);
						    	//String attachmentPath = saveCSV(type);
						    	setEmail(type,attachmentPath);
						    	*/
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
				else if(v.getId()==chkSound.getId()){
					Log.v("CHK SOUND CHANGE", chkSound.isChecked()+"");
					setting.isSound = chkSound.isChecked();
					Setting.saveSetting(context, setting);
				}else if(v.getId()==chkVibrate.getId()){
					Log.v("CHK VIBRATE CHANGE", chkVibrate.isChecked()+"");
					setting.isVibrate = chkVibrate.isChecked();
					Setting.saveSetting(context, setting);
				}
				
			}
		};
		email_btn.setOnClickListener(dialogListener);
		chkSound.setOnClickListener(dialogListener);
		chkVibrate.setOnClickListener(dialogListener);
	}
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
		return "See the attachment for "+type+" Logs";
	}

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
	//SEND VIA PHP SERVER SCRIPT
	private void promptEmailSender(StringBuilder data){
		LayoutInflater li = LayoutInflater.from(context);
		View promptsView = li.inflate(R.layout.prompt_email, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setView(promptsView);
		
		final String logdata = data.toString();
		//final RadioGroup userInput = (RadioGroup) promptsView.findViewById(R.id.duration_select);
		final EditText userMail = (EditText)promptsView.findViewById(R.id.editUserMail);
		final EditText receiverMail = (EditText)promptsView.findViewById(R.id.editReceiverMail);
		final EditText noteText = (EditText)promptsView.findViewById(R.id.editNotes);
		
		receiverMail.setText(toEmail);
		userMail.setText(fromEmail);
		// set dialog message
		alertDialogBuilder.setCancelable(false).setPositiveButton("OK",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
			    	// get user input and set it to result
			    	
			    	
			    	/*
			    	 * 
			    	---------SEND EMAIL SCRIPT HERE------------
			    	*
			    	*
			    	*/
			    	String additional = noteText.getText().toString();
			    	if(additional==null){
			    		additional = "";
			    	}
			    	new ServerEmail(context).execute(
			    			userMail.getText().toString(),
			    			receiverMail.getText().toString(),
			    			additional,
			    			logdata
			    			);
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
