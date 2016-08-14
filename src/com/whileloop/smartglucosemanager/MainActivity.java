package com.whileloop.smartglucosemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    //Main Menu button click
    public void onButtonPress(View v){
    	Intent i;
    	switch (v.getId()) {
			case R.id.button1:
				i = new Intent(this,RemindActivity.class);
				startActivity(i);
			break;
			case R.id.entrybtn:
				i = new Intent(this,GlucoseEntryActivity.class);
				startActivity(i);
			break;
			case R.id.profilebtn:
				i = new Intent(this,ProfileActivity.class);
				startActivity(i);
			break;
			case R.id.medsinsulinbtn:
				i = new Intent(this,MedsAndInsulineActivity.class);
				startActivity(i);
			break;
			case R.id.calculatorbtn:
				i = new Intent(this,CalculatorActivity.class);
				startActivity(i);
			break;
			case R.id.logstatisticsbtn:
				i = new Intent(this,LogbookSatisticsActivity.class);
				startActivity(i);
			break;
			case R.id.dctbtn:
				i = new Intent(this,DCTActivity.class);
				startActivity(i);
			break;

		default:
			break;
		}
    }
}
