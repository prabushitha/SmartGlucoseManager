package com.whileloop.smartglucosemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class LogbookSatisticsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logbook_satistics);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.logbook_satistics, menu);
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
	
	//program logic
    //button click
    public void onButtonPress(View v){
    	Intent i;
    	switch (v.getId()) {
			case R.id.btnLogbook:
				i = new Intent(this,LogbookActivity.class);
				startActivity(i);
				break;
			case R.id.btnSatistics:
				i = new Intent(this,SatisticsActivity.class);
				startActivity(i);
				break;

			default:
				break;
		}
    }
}
