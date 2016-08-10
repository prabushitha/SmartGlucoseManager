package com.whileloop.smartglucosemanager;


import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

public class TestingActivity extends Activity implements OnClickListener{
	AlarmManager alarmManager;
	Intent myIntent;
	PendingIntent pending_intent1,pending_intent2,pending_intent3,pending_intent4;
	private int[] hours = new int[4];
	private int[] minutes = new int[4];
	static final int TIME_DIALOG_ID = 999;
	private Button change_btn1,change_btn2,change_btn3,change_btn4;
	private TextView time_txt1,time_txt2,time_txt3,time_txt4;
	private CheckBox[] days = new CheckBox[7];
	DatabaseHelper databaseHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_testing);
		//app logic
		//Alarm manager
		alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
		//Getting time text views
		time_txt1 = (TextView)findViewById(R.id.textViewTime1);
		time_txt2 = (TextView)findViewById(R.id.textViewTime2);
		time_txt3 = (TextView)findViewById(R.id.textViewTime3);
		time_txt4 = (TextView)findViewById(R.id.textViewTime4);
		//Checkboxes
		days[0] = (CheckBox)findViewById(R.id.checkMon);
		days[1] = (CheckBox)findViewById(R.id.checkTue);
		days[2] = (CheckBox)findViewById(R.id.checkWed);
		days[3] = (CheckBox)findViewById(R.id.checkThu);
		days[4] = (CheckBox)findViewById(R.id.checkFri);
		days[5] = (CheckBox)findViewById(R.id.checkSat);
		days[6] = (CheckBox)findViewById(R.id.checkSun);
		//getting change buttons from the view
		change_btn1 = (Button)findViewById(R.id.btnChange1);
		change_btn2 = (Button)findViewById(R.id.btnChange2);
		change_btn3 = (Button)findViewById(R.id.btnChange3);
		change_btn4 = (Button)findViewById(R.id.btnChange4);
		change_btn1.setOnClickListener(this);
		change_btn2.setOnClickListener(this);
		change_btn3.setOnClickListener(this);
		change_btn4.setOnClickListener(this);
		
		//Database Helper
		databaseHelper = new DatabaseHelper(this);
		
		//Creating an intent for AlarmReceiver classs
		myIntent = new Intent(this,AlarmReceiver.class);
		
		
		//onOFF alarm toggle 1
		ToggleButton toggle1 = (ToggleButton) findViewById(R.id.toggleTime1);
		toggle1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		    	
		        if (isChecked) {
		        	Log.e("Toggle 1", "ON:"+hours[0]+"-"+minutes[0]);
		        	//Set time to calendar
		        	Calendar calendar = Calendar.getInstance();
	            	calendar.set(Calendar.HOUR_OF_DAY, hours[0]);
	            	calendar.set(Calendar.MINUTE,minutes[0]);
		            // The toggle is enabled
		        	pending_intent1 = PendingIntent.getBroadcast(TestingActivity.this, 
	            			1, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
	            	alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pending_intent1);
		        } else {
		        	Log.e("Toggle 1", "OFF");
		        	alarmManager.cancel(pending_intent1);
		            // The toggle is disabled
		        }
		        createAlarmEntry(0,0,hours[0],minutes[0],isChecked);
		    }
		});
		//onOFF alarm toggle 2
		ToggleButton toggle2 = (ToggleButton) findViewById(R.id.toggleTime2);
		toggle2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				      if (isChecked) {
				    	Log.e("Toggle 2", "ON :"+hours[1]+"-"+minutes[1]);
				        //Set time to calendar
				        Calendar calendar = Calendar.getInstance();
			            calendar.set(Calendar.HOUR_OF_DAY, hours[1]);
			            calendar.set(Calendar.MINUTE,minutes[1]);
				           // The toggle is enabled
				        pending_intent2 = PendingIntent.getBroadcast(TestingActivity.this, 
			            			2, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			            alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pending_intent2);
				      } else {
				    	Log.e("Toggle 2", "OFF");
				        alarmManager.cancel(pending_intent2);
				            // The toggle is disabled
				       }
				      createAlarmEntry(1,0,hours[1],minutes[1],isChecked);
				   }
			});
		//onOFF alarm toggle 3
		ToggleButton toggle3 = (ToggleButton) findViewById(R.id.toggleTime3);
		toggle3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			        if (isChecked) {
			        	Log.e("Toggle 3", "ON:"+hours[2]+"-"+minutes[2]);
			        	//Set time to calendar
			        	Calendar calendar = Calendar.getInstance();
		            	calendar.set(Calendar.HOUR_OF_DAY, hours[2]);
		            	calendar.set(Calendar.MINUTE,minutes[2]);
			            // The toggle is enabled
			        	pending_intent3 = PendingIntent.getBroadcast(TestingActivity.this, 
		            			3, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		            	alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pending_intent3);
			        } else {
			        	Log.e("Toggle 3", "OFF");
			        	alarmManager.cancel(pending_intent3);
			            // The toggle is disabled
			        }
			        createAlarmEntry(2,0,hours[2],minutes[2],isChecked);
			    }
			});
		//onOFF alarm toggle 4
		ToggleButton toggle4 = (ToggleButton) findViewById(R.id.toggleTime4);
		toggle4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			        if (isChecked) {
			        	Log.e("Toggle 4", "ON:"+hours[3]+"-"+minutes[3]);
			        	//Set time to calendar
			        	Calendar calendar = Calendar.getInstance();
		            	calendar.set(Calendar.HOUR_OF_DAY, hours[3]);
		            	calendar.set(Calendar.MINUTE,minutes[3]);
			            // The toggle is enabled
			        	pending_intent4 = PendingIntent.getBroadcast(TestingActivity.this, 
		            			4, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		            	alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pending_intent4);
			        } else {
			        	Log.e("Toggle 4", "OFF");
			        	alarmManager.cancel(pending_intent4);
			            // The toggle is disabled
			        }
			        createAlarmEntry(3,0,hours[3],minutes[3],isChecked);
			    }
			});	
		
	}
	private void createAlarmEntry(int id,int type,int hour,int minute,boolean isSet){
		AlarmEntry alarmEntry = new AlarmEntry();
		alarmEntry.setId(id);
    	alarmEntry.setType(type);
    	alarmEntry.setTime(hour, minute);
    	if(isSet){
    		alarmEntry.setStatus("ON");
    	}else{
    		alarmEntry.setStatus("OFF");
    	}
    	for(int i=1;i<=days.length;i++){
    		String status = "OFF";
    		if(days[i-1].isChecked()){
    			status = "ON";
    		}
    		alarmEntry.setWeekInfo(i, status);
    	}
    	databaseHelper.updateAlarmEntry(alarmEntry);
    	
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.testing, menu);
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
	//OnClickListener interface method overriding
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		TimePickerDialog tpd;
		final int timeid;
		final TextView currentText;
		
		if(v==change_btn1){
			timeid = 1;
			currentText = time_txt1;
		}else if(v == change_btn2){
			timeid = 2;
			currentText = time_txt2;
		}else if(v==change_btn3){
			timeid = 3;
			currentText = time_txt3;
		}else{
			timeid = 4;
			currentText = time_txt4;
		}
		OnTimeSetListener otsl = new TimePickerDialog.OnTimeSetListener() {
			 
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay,
                    int minute) {
            	//set time to array
            	hours[timeid-1] = hourOfDay;
            	minutes[timeid-1] = minute;
                // Display Selected time in textbox
            	String normalTime = getNormalTime(hourOfDay,minute);
            	currentText.setText(normalTime);
            	
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
        };
        tpd = new TimePickerDialog(this,otsl,hours[timeid-1],minutes[timeid-1],false);
        tpd.show();
	}
	
}
