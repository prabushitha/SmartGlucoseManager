package com.whileloop.smartglucosemanager;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

public class GlucoseEntryActivity extends Activity {
	private RadioGroup radioGroup;
	private RadioButton radioButton;
	private EditText editDateText;
	private EditText editTimeText;
	private Calendar cal;
	DatabaseHelper databaseHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_glucose_entry);
		databaseHelper = new DatabaseHelper(this);
		cal = Calendar.getInstance();
		
		editDateText = (EditText)findViewById(R.id.dateText);
		editDateText.setText(cal.get(Calendar.YEAR)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DAY_OF_MONTH));
		
		editTimeText = (EditText)findViewById(R.id.timeText);
		editTimeText.setText(getNormalTime(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE)));
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
            glucoseEntry.setTime(time);
            glucoseEntry.setBg(bg);
            glucoseEntry.setTimeOfEvent(time_of_event);
            
            databaseHelper.insertGlucoseEntry(glucoseEntry);
            Log.e("Save Button", "Save clicked");
            
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
		}else if(v.getId()==R.id.dateText){
			DatePickerDialog dpd;
			OnDateSetListener otsl = new DatePickerDialog.OnDateSetListener() {
				 
				@Override
			    public void onDateSet(DatePicker view, int year, int monthOfYear,
			            int dayOfMonth) {
			        editDateText.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth); 
			    }
	        };
	        dpd = new DatePickerDialog(this,otsl,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
	        dpd.show();
		}else if(v.getId()==R.id.timeText){
			// TODO Auto-generated method stub
						TimePickerDialog tpd;
						
						OnTimeSetListener otsl = new TimePickerDialog.OnTimeSetListener() {
							 
				            @Override
				            public void onTimeSet(TimePicker view, int hourOfDay,
				                    int minute) {
				            	editTimeText.setText(getNormalTime(hourOfDay,minute));
				            }
				        };
				        tpd = new TimePickerDialog(this,otsl,cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),false);
				        tpd.show();
		}
	
	}
	
	public String getNormalTime( int hours, int minutes){
    	String ampm = "AM";
    	String hourZeros = "";
    	String minuteZero = "";
    	if(hours>=12){
    		ampm = "PM";
    	}
    	if(hours>12){
    		hours = hours%12;
    	}
    	if(hours==0)hours=12;
    	if(hours<10)hourZeros="0";
    	if(minutes<10)minuteZero="0";
    	
    	return hourZeros+hours+":"+minuteZero+minutes+" "+ampm;
    }
	public void onBackPress(View v){
		onBackPressed();
	}
	@Override
     public void onBackPressed(){
		Intent i = new Intent(this,MainActivity.class);
		startActivity(i);
	}
	
}
