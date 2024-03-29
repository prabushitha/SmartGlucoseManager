package com.whileloop.smartglucosemanager;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TimePicker;
import android.widget.ToggleButton;

public class ExerciseActivity extends Activity implements OnClickListener{
	AlarmManager alarmManager;
	final int NUMBER_OF_TIMES = 1;
	final int ALARM_TYPE = 3;
	final int STARTING_ALARM_NUMBER = 13;
	public static final String PREFS_NAME = "ALARM_PREFS";
	
	SharedPreferences  settings;
	Editor editor;
	
	private int[] hours = new int[1];
	private int[] minutes = new int[1];
	static final int TIME_DIALOG_ID = 999;
	private Intent[] myIntent = new Intent[1];
	private PendingIntent[] pending_intents = new PendingIntent[1];
	private CheckBox[] days = new CheckBox[7];
	private Button[] changebtns = new Button[1];
	private ToggleButton[] toggles = new ToggleButton[1];
	DatabaseHelper databaseHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise);
		//app logic
				//Alarm manager
				alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
				//sharedpreferences
				settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
				editor = settings.edit();
				//Getting time text views
				//Checkboxes
				days[0] = (CheckBox)findViewById(R.id.checkMon);
				days[1] = (CheckBox)findViewById(R.id.checkTue);
				days[2] = (CheckBox)findViewById(R.id.checkWed);
				days[3] = (CheckBox)findViewById(R.id.checkThu);
				days[4] = (CheckBox)findViewById(R.id.checkFri);
				days[5] = (CheckBox)findViewById(R.id.checkSat);
				days[6] = (CheckBox)findViewById(R.id.checkSun);
				//getting change buttons from the view
				changebtns[0] = (Button)findViewById(R.id.btnChange1);
				
				changebtns[0].setOnClickListener(this);
				
				//Database Helper
				databaseHelper = new DatabaseHelper(this);
				
				//Creating an intents for AlarmReceiver classs
				myIntent[0] = new Intent(this,AlarmReceiver.class);
				
				toggles[0] = (ToggleButton) findViewById(R.id.toggleTime1);
				
				for(int i=0;i<1;i++){
					AlarmEntry alarmEntry = databaseHelper.getAlarmEntry(i+STARTING_ALARM_NUMBER);
					//set toggles checked
					if(alarmEntry.getStatus().equals(AlarmEntry.ALARM_ON)){
						toggles[i].setChecked(true);
					}
					//set days checked
					if(i==0){
						for(int j=0;j<days.length;j++){
							if(alarmEntry.getWeekInfo(j+1).equals(AlarmEntry.ALARM_ON)){
								days[j].setChecked(true);
								days[j].setTextColor(Color.WHITE);
							}
						}
					}
					hours[i] = alarmEntry.getHours();
					minutes[i] = alarmEntry.getMinutes();
					String alamTime = getNormalTime(hours[i],minutes[i]);
					changebtns[i].setText(alamTime);
					
					final int x = i;
					//set toggle change listener
					toggles[x].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {	
					        if (isChecked) {
					        	Log.e("Toggle 1", "ON:"+hours[x]+"-"+minutes[x]);
					        	
					        	setAlarm(x+1);
					        } else {
					        	Log.e("Toggle 1", "OFF");
					        	
					        	cancelAlarm(x+1);
					            // The toggle is disabled
					        }
					        createAlarmEntry(x+1,hours[x],minutes[x],isChecked);
					    }
					});
					//set check box listener
				}
				for(int i=0;i<days.length;i++){
					final int y = i;
					days[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
						public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
							if(isChecked){
								buttonView.setTextColor(Color.WHITE);
							}
							else {
								buttonView.setTextColor(Color.BLACK);
							}
							setAlarmDays();
							//add to sharedprefs
							String alarmday = ALARM_TYPE+"_day_"+(y+1);
				    		editor.putBoolean(alarmday,isChecked);
				    		editor.commit();
				    		System.out.println(alarmday+":"+isChecked);
						}
					});
				} 
	}
	private void createAlarmEntry(int id,int hour,int minute,boolean isSet){
		AlarmEntry alarmEntry = new AlarmEntry();
		alarmEntry.setId(id+STARTING_ALARM_NUMBER-1);
    	alarmEntry.setType(ALARM_TYPE);
    	alarmEntry.setTime(hour, minute);
    	if(isSet){
    		alarmEntry.setStatus("ON");
    	}else{
    		alarmEntry.setStatus("OFF");
    	}
    	for(int i=1;i<=days.length;i++){
    		String status = "OFF";
    		boolean checked = days[i-1].isChecked();
    		if(checked){
    			status = "ON";
    		}
    		alarmEntry.setWeekInfo(i, status);
    	}
    	databaseHelper.updateAlarmEntry(alarmEntry);
	}
	private void setAlarmDays(){
		for(int i=0;i<NUMBER_OF_TIMES;i++){
			createAlarmEntry(i+1,hours[i],minutes[i],toggles[i].isChecked());
		}
	}
	private void setAlarm(int id){
		cancelAlarm(id);
		//Set time to calendar
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hours[id-1]);
		calendar.set(Calendar.MINUTE,minutes[id-1]);
		myIntent[id-1].putExtra("id", id+STARTING_ALARM_NUMBER-1);
		myIntent[id-1].putExtra("type", ALARM_TYPE);
		// The toggle is enabled
		pending_intents[id-1] = PendingIntent.getBroadcast(ExerciseActivity.this, 
		            			id+STARTING_ALARM_NUMBER-1, myIntent[id-1], PendingIntent.FLAG_UPDATE_CURRENT);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pending_intents[id-1]);
	}
	private void cancelAlarm(int id){
		PendingIntent intent;
		if(pending_intents[id-1]!=null){
			intent = pending_intents[id-1];
		}else{
			intent = PendingIntent.getService(ExerciseActivity.this,id+STARTING_ALARM_NUMBER-1,myIntent[id-1],PendingIntent.FLAG_UPDATE_CURRENT);
			/*intent = PendingIntent.getBroadcast(TestingActivity.this, 
        			id, myIntent[id-1], PendingIntent.FLAG_UPDATE_CURRENT);*/
		}
		alarmManager.cancel(intent);
	}
	//OnClickListener interface method overriding
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			TimePickerDialog tpd;
			final int timeid;
			final Button currentButton;
			timeid = 1;
			currentButton = changebtns[0];
			
			OnTimeSetListener otsl = new TimePickerDialog.OnTimeSetListener() {
				 
	            @Override
	            public void onTimeSet(TimePicker view, int hourOfDay,
	                    int minute) {
	            	//set time to array
	            	hours[timeid-1] = hourOfDay;
	            	minutes[timeid-1] = minute;
	            	//off the alarm
	            	toggles[timeid-1].setChecked(false);
	                // Display Selected time in textbox
	            	String normalTime = getNormalTime(hourOfDay,minute);
	            	currentButton.setText(normalTime);
	            	
	            	
	            }
	        };
	        tpd = new TimePickerDialog(this,otsl,hours[timeid-1],minutes[timeid-1],false);
	        tpd.show();
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
			Intent i = new Intent(this,RemindActivity.class);
			startActivity(i);
		}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.exercise, menu);
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
