package com.whileloop.smartglucosemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;

public class LogbookActivity extends Activity {

	
	
	
	DatabaseHelper databaseHelper;
	Context ccontext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logbook);
		
		ccontext = this;
		//logic
		databaseHelper = new DatabaseHelper(this);
		GlucoseEntry[] entries = databaseHelper.getGlucoseEntries();
		
		TableLayout tl = (TableLayout)findViewById(R.id.tableLog);
		
		for(int i=0;i<entries.length;i++){
			int j = entries.length-1-i;
			TableRow row = new TableRow(this);
			TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
			row.setLayoutParams(lp);
			row.setGravity(Gravity.CENTER_VERTICAL);
			
			//Text View dateTxt
			/*
			 * <TextView
			    android:id="@+id/dateTxt"
			    android:layout_width="0dp"
			    android:layout_height="wrap_content"
			    android:layout_weight="3"
			    android:gravity="center"
			    android:text="2016/7/8 11:59 PM"
			    android:textAppearance="?android:attr/textAppearanceLarge" 
			    android:textSize="15dp"
			    />
			 */
			TextView tv1 = new TextView(this);
			tv1.setId(i);
			tv1.setGravity(Gravity.CENTER);
			tv1.setText(entries[j].getDate()+" "+entries[j].getTime());
			tv1.setTextSize(10);
			TableRow.LayoutParams lpp1 = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT,2.0f);
			row.addView(tv1,lpp1);
			
			//
			/*
			 * <TextView
			    android:id="@+id/dataTxt"
			    android:layout_width="0dp"
			    android:layout_height="wrap_content"
			    android:layout_weight="4"
			    android:gravity="center"
			    android:text="127 MG dl"
			    android:textAppearance="?android:attr/textAppearanceLarge"
			    android:textSize="20dp"
			     />

			 */
			TextView tv2 = new TextView(this);
			tv2.setId(i+entries.length);
			tv2.setGravity(Gravity.CENTER);
			tv2.setText(Math.round(entries[j].getBgValue())+" mg dL");
			tv2.setTextSize(20);
			TableRow.LayoutParams lpp2 = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT,4.0f);
			row.addView(tv2,lpp2);
			//
			/*
			 * <TextView
		        android:id="@+id/typeTxt"
		        android:layout_width="0dp"
		        android:layout_height="wrap_content"
		        android:layout_weight="3"
		        android:gravity="center"
		        android:text="After Bed"
		        android:textAppearance="?android:attr/textAppearanceLarge"
		        android:textSize="15dp" />

			 */
			TextView tv3 = new TextView(this);
			tv3.setId(i+2*entries.length);
			tv3.setGravity(Gravity.CENTER);
			tv3.setText(entries[j].getTimeOfEvent());
			tv3.setTextSize(10);
			TableRow.LayoutParams lpp3 = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT,3.0f);
			row.addView(tv3,lpp3);
			//
			/*
			 * <TextView
			    android:id="@+id/timeTxt"
			    android:layout_width="0dp"
			    android:layout_height="wrap_content"
			    android:layout_weight="2"
			    android:gravity="center"
			    android:text="EDIT"
			    android:textAppearance="?android:attr/textAppearanceLarge"
			    android:textSize="10dp"
			     />

			 */
			Button tv4 = new Button(this);
			tv4.setId(i+3*entries.length);
			tv4.setGravity(Gravity.CENTER);
			tv4.setText("Delete");
			tv4.setTextSize(7);
			final int entryID = entries[j].getId();
			//Log.v("GlucoseEntry","for:"+j+" : id  = "+entryID+" :date = "+entries[j].getDate());
			tv4.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					createAndShowAlertDialog(entryID);
					
				}
			});
			TableRow.LayoutParams lpp4 = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT,2.0f);
			row.addView(tv4,lpp4);
			
			tl.addView(row,i);
		}
		
		
	}
	
	private void createAndShowAlertDialog(int id) {
		final int gid = id;
	     AlertDialog.Builder builder = new AlertDialog.Builder(this);
	     builder.setTitle("Delete this record?");
	     builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	         public void onClick(DialogInterface dialog, int id) {
	              //TODO
	        	  boolean isRemoved = databaseHelper.removeGlucoseEntry(gid);
	        	  if(isRemoved){
	        		  Log.v("Database","Removed Successfully");
	        	  }else{
	        		  Log.v("Database","Remove Fail");
	        	  }
	        	  Intent i = new Intent(ccontext,LogbookActivity.class);
	      		  startActivity(i);
	              //dialog.dismiss();
	         }
	     });
	     builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
	         public void onClick(DialogInterface dialog, int id) {
	              //TODO
	              dialog.dismiss();
	         }
	     });
	     AlertDialog dialog = builder.create();
	     dialog.show();
	   }
	public void onBackPress(View v){
		onBackPressed();
	}
	@Override
     public void onBackPressed(){
		Intent i = new Intent(this,LogbookSatisticsActivity.class);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.logbook, menu);
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
