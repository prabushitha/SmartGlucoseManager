package com.whileloop.smartglucosemanager;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver{
	DatabaseHelper databaseHelper;
	AlarmManager alarmManager;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		System.out.println(intent.getAction());
		databaseHelper = new DatabaseHelper(context);
		
		if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
			//if phone reboots set all alarms
			alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
			AlarmEntry[] entries = databaseHelper.getActiveAlarms();
			for(AlarmEntry entry:entries){
				setAlarm(context,entry);
			}  
	    } else {
			int alarmId = intent.getExtras().getInt("id");
			AlarmEntry alarm = databaseHelper.getAlarmEntry(alarmId);
			//week alarm status, active days are == 1
			//Monday = dayStatus[0] ... Sunday = dayStatus[6]
			int[] dayStatus = alarm.getAllWeekInfoStatus();
			
			//today
			Calendar calendar = Calendar.getInstance();
			int day = calendar.get(Calendar.DAY_OF_WEEK); //Sunday = 1, Monday = 2 etc
			day = day-2; 
			if(day==-1){
				day = Calendar.SUNDAY;
			}
			//check if the day is selected by the user
			if(dayStatus[day]==1){
				Log.e("Alarm Alert","ALARM id:"+alarmId+" :--> Alarm time:"+alarm.getTime());
				notification(context,"Smart Glucose Manager","You should do a test at !"+alarm.getTime());
			}else{
				Log.e("Alarm Alert","Alarm is not set for today");
			}
	    }
		
		
		
		
		
	}
	public void notification(Context context,String title,String message){
		Intent intent = new Intent(context, GlucoseEntryActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);
		
     // Build notification
        // Actions are just fake
        Notification noti = new Notification.Builder(context)
                        .setContentTitle(title)
                        .setContentText(message).setSmallIcon(R.drawable.ic_launcher)
                        .setContentIntent(pIntent).build();
        
		NotificationManager ntm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		// hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        ntm.notify(0, noti);
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
	}

}
