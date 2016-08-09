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
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

public class TestingActivity extends Activity implements OnClickListener{
	AlarmManager alarmManager;
	Intent myIntent1,myIntent2,myIntent3,myIntent4;
	PendingIntent pending_intent1,pending_intent2,pending_intent3,pending_intent4;
	private int[] hours = new int[4];
	private int[] minutes = new int[4];
	static final int TIME_DIALOG_ID = 999;
	private Button change_btn1,change_btn2,change_btn3,change_btn4;
	private TextView time_txt1,time_txt2,time_txt3,time_txt4;

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
		//getting change buttons from the view
		change_btn1 = (Button)findViewById(R.id.btnChange1);
		change_btn2 = (Button)findViewById(R.id.btnChange2);
		change_btn3 = (Button)findViewById(R.id.btnChange3);
		change_btn4 = (Button)findViewById(R.id.btnChange4);
		change_btn1.setOnClickListener(this);
		change_btn2.setOnClickListener(this);
		change_btn3.setOnClickListener(this);
		change_btn4.setOnClickListener(this);
		
		//Creating an intent for AlarmReceiver classs
		myIntent1 = new Intent(this,AlarmReceiver.class);
		myIntent2 = new Intent(this,AlarmReceiver.class);
		myIntent3 = new Intent(this,AlarmReceiver.class);
		myIntent4 = new Intent(this,AlarmReceiver.class);
		
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
	            			0, myIntent1, PendingIntent.FLAG_UPDATE_CURRENT);
	            	alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pending_intent1);
		        } else {
		        	Log.e("Toggle 1", "OFF");
		        	alarmManager.cancel(pending_intent1);
		            // The toggle is disabled
		        }
		    }
		});
		//onOFF alarm toggle 1
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
			            			0, myIntent2, PendingIntent.FLAG_UPDATE_CURRENT);
			            alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pending_intent2);
				      } else {
				    	Log.e("Toggle 2", "OFF");
				        alarmManager.cancel(pending_intent2);
				            // The toggle is disabled
				       }
				   }
			});
		
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
