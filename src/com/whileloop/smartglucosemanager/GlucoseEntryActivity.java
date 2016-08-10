package com.whileloop.smartglucosemanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class GlucoseEntryActivity extends Activity {
	private RadioGroup radioGroup;
	private RadioButton radioButton;
	DatabaseHelper databaseHelper=new DatabaseHelper(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_glucose_entry);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.glucose_entry, menu);
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
	
	public void onSaveButtonClick(View v){
		if(v.getId()==R.id.savebutton){
			radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
			EditText dateText=(EditText)findViewById(R.id.dateText);
			EditText timeText=(EditText)findViewById(R.id.timeText);
			EditText bgText=(EditText)findViewById(R.id.bgText);
			
			int selectedId = radioGroup.getCheckedRadioButtonId();
            radioButton = (RadioButton) findViewById(selectedId);
            
            String date=dateText.getText().toString();
            String time=timeText.getText().toString();
            String bg=bgText.getText().toString();
            String time_of_event=radioButton.getText().toString();
            
            GlucoseEntry glucoseEntry=new GlucoseEntry();
            glucoseEntry.setDate(date);
            glucoseEntry.setBg(time);
            glucoseEntry.setBg(bg);
            glucoseEntry.setTimeOfEvent(time_of_event);
            
            databaseHelper.insertGlucoseEntry(glucoseEntry);

		}
	
	}
	
}
