package com.whileloop.smartglucosemanager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.e("We are in the receiver","Yey!!:"+intent.toString());
		notification(context,"Smart Glucose Manager","You should do a test now!");
		
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
