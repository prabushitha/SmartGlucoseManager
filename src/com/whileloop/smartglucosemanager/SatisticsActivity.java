package com.whileloop.smartglucosemanager;

import java.util.ArrayList;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SatisticsActivity extends Activity {
	
	
	
	
	LineChart lineChart;
	DatabaseHelper databaseHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_satistics);
		
		//Database Helper
		databaseHelper = new DatabaseHelper(this);
		GlucoseEntry[] entries = databaseHelper.getGlucoseEntries();
		//Line chart
		lineChart = (LineChart)findViewById(R.id.linegraph);
		ArrayList<Entry> lineEntries = new ArrayList<Entry>();
		ArrayList<String> lineDates = new ArrayList<String>();
		
		int j = 0;
		for(GlucoseEntry entry:entries){
			lineDates.add(entry.getDate());
			lineEntries.add(new Entry(entry.getBgValue(), j));
			System.out.println("Chart : "+j+" :"+entry.getDate());
			j++;
		}
		LineDataSet linedataset = new LineDataSet(lineEntries, "BG (mg dl)");
		LineData lineData = new LineData(lineDates,linedataset);
		
		lineChart.setData(lineData);
		lineChart.setTouchEnabled(true);
		lineChart.setDragEnabled(true);
		lineChart.setScaleEnabled(true);

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.satistics, menu);
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
