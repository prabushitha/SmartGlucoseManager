package com.whileloop.smartglucosemanager;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver{
	AlarmManager alarmManager;
	public static final String PREFS_NAME = "ALARM_PREFS";
	SharedPreferences settings;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		//System.out.println(intent.getAction());
		//week alarm status, active days are == 1
		//Monday = dayStatus[0] ... Sunday = dayStatus[6]
		
		int alarmType = intent.getExtras().getInt("type");
		int alarmId = intent.getExtras().getInt("id");
		//today
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK); //Sunday = 1, Monday = 2 etc
		day = day-1; //Sunday = 0, Monday = 1,...Saturaday = 6,
		if(day==0){
			day = 7; //Sunday = 7
		}
		//get alarm status of each alarm category
		boolean todayStatusAlarm1 = settings.getBoolean("0_day_"+day, false);
		boolean todayStatusAlarm2 = settings.getBoolean("1_day_"+day, false);
		boolean todayStatusAlarm3 = settings.getBoolean("2_day_"+day, false);
		boolean todayStatusAlarm4 = settings.getBoolean("3_day_"+day, false);
		//check whether the alarm is set for today
		if(alarmType==0 && todayStatusAlarm1){
			Log.e("Alarm Alert","ALARM type:"+alarmType+"   id:"+alarmId);
			notification(context,"Smart Glucose Manager","You should do a test");
		}else if(alarmType==1 && todayStatusAlarm2){
			Log.e("Alarm Alert","ALARM type:"+alarmType+"   id:"+alarmId);
			notification(context,"Smart Glucose Manager","You should get insuling & medication");
			
		}else if(alarmType==2 && todayStatusAlarm3){
			Log.e("Alarm Alert","ALARM type:"+alarmType+"   id:"+alarmId);
			notification(context,"Smart Glucose Manager","You should get meals");
			
		}else if(alarmType==3 && todayStatusAlarm4){
			Log.e("Alarm Alert","ALARM type:"+alarmType+"   id:"+alarmId);
			notification(context,"Smart Glucose Manager","You should do excerise");
			
		}else{
			Log.e("Alarm Alert","Alarm is not set for today");
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
}
