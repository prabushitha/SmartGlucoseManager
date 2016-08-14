package com.whileloop.smartglucosemanager;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompleteReceiver extends BroadcastReceiver {
	DatabaseHelper databaseHelper;
	AlarmManager alarmManager;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("Phone Successfully Booted!");
		databaseHelper = new DatabaseHelper(context);
		if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
			//if phone reboots set all alarms
			alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
			AlarmEntry[] entries = databaseHelper.getActiveAlarms();
			for(AlarmEntry entry:entries){
				setAlarm(context,entry);
			}  
			System.out.println("COMPLETED SETTING ALARMS");
	    } 

	}
	private void setAlarm(Context context,AlarmEntry alarm){
		//Set time to calendar
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, alarm.getHours());
		calendar.set(Calendar.MINUTE,alarm.getMinutes());
		Intent myIntent = new Intent(context,AlarmReceiver.class);
		myIntent.putExtra("id", alarm.getId());
		// The toggle is enabled
		PendingIntent pending_intent = PendingIntent.getBroadcast(context, 
				alarm.getId(), myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pending_intent);
		System.out.println("BOOT ALARM SET AT :"+alarm.getTime());
		
	}

}
