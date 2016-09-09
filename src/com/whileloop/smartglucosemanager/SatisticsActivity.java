package com.whileloop.smartglucosemanager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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
		
		float minBgValue = 0;
		float maxBgValue = 0;
		float avgBgValue = 0;
		
		float sumBgValue = 0;
		int totalBgValues = 0;
		
		try{
			ArrayList<String> lineDates = new ArrayList<String>();
			//data set 1
			ArrayList<Entry> lineEntries = new ArrayList<Entry>();
			
			int j = 0;
			for(GlucoseEntry entry:entries){
				lineDates.add(entry.getDate());
				lineEntries.add(new Entry(entry.getBgValue(), j));
				
				//set min/max bg values
				if(entry.getBgValue()>maxBgValue){
					maxBgValue = entry.getBgValue();
				}
				if(entry.getBgValue()<minBgValue){
					minBgValue = entry.getBgValue();
				}
				sumBgValue += entry.getBgValue();
				totalBgValues++;
				//System.out.println("Chart : "+j+" :"+entry.getDate());
				j++;
			}
			if(totalBgValues==0)totalBgValues=1;
			avgBgValue = sumBgValue/totalBgValues;
			
			LineDataSet linedataset = new LineDataSet(lineEntries, "Glucose Level");
			linedataset.setCircleColorHole(Color.BLUE);
			linedataset.setColor(Color.BLUE);
			//dataset 2 MIN LEVEL
			ArrayList<Entry> lineEntries2 = new ArrayList<Entry>();
			lineEntries2.add(new Entry(minBgValue, 0));
			lineEntries2.add(new Entry(minBgValue, j-1));
			
			LineDataSet linedataset2 = new LineDataSet(lineEntries2, "Minimum Level");
			linedataset2.setColor(Color.RED);
			linedataset2.setDrawCircles(false);
			linedataset2.setDrawValues(false);
			
			//dataset 3 MAX LEVEL
			ArrayList<Entry> lineEntries3 = new ArrayList<Entry>();
			lineEntries3.add(new Entry(maxBgValue, 0));
			lineEntries3.add(new Entry(maxBgValue, j-1));
					
			LineDataSet linedataset3 = new LineDataSet(lineEntries3, "Maximum Level");
			linedataset3.setColor(Color.GREEN);
			linedataset3.setDrawCircles(false);
			linedataset3.setDrawValues(false);
			
			//dataset 3 AVG LEVEL
			ArrayList<Entry> lineEntries4 = new ArrayList<Entry>();
			lineEntries4.add(new Entry(avgBgValue, 0));
			lineEntries4.add(new Entry(avgBgValue, j-1));
							
			LineDataSet linedataset4 = new LineDataSet(lineEntries4, "Average Level");
			linedataset4.setColor(Color.YELLOW);
			linedataset4.setDrawCircles(false);
			linedataset4.setDrawValues(false);
					
			//
			List<ILineDataSet> dataset = new ArrayList<ILineDataSet>();
			dataset.add(linedataset);
			dataset.add(linedataset2);
			dataset.add(linedataset3);
			dataset.add(linedataset4);
			
			//LineData lineData = new LineData(lineDates,linedataset);
			LineData lineData = new LineData(lineDates,dataset);
			lineChart.setData(lineData);
			lineChart.setTouchEnabled(true);
			lineChart.setDragEnabled(true);
			lineChart.setScaleEnabled(true);
			
			lineChart.setVisibleXRangeMaximum(10);
			lineChart.setDescription("");
			lineChart.getLegend().setWordWrapEnabled(true);
			if(j>10){
				
				lineChart.moveViewToX(j-10);
			}
			
			XAxis xAxis = lineChart.getXAxis();
			xAxis.setLabelRotationAngle(90.0f);
			xAxis.setPosition(XAxisPosition.BOTTOM);
			xAxis.setLabelsToSkip(0);
		}catch(Exception e){
			Log.e("Char Error", e.getMessage());
		}
		

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
