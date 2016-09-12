package com.whileloop.smartglucosemanager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;

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
		
		
		
		try{
			//xAxis values
			ArrayList<String> lineDates = new ArrayList<String>();
			//entries for data sets		
			ArrayList<Entry> maxEntries = new ArrayList<Entry>();
			ArrayList<Entry> minEntries = new ArrayList<Entry>();
			ArrayList<Entry> avgEntries = new ArrayList<Entry>();
			
			Map<Integer,ArrayList<Entry>> allDayEntries = new TreeMap<Integer, ArrayList<Entry>> ();
			int j = 0;
			
			//adding xAxis values (dates) and classinfying entries for each day
			for(int i=0;i<entries.length;i++){
				GlucoseEntry entry = entries[i];
				if(i>1 && entry.getDate().equals(entries[i-1].getDate())){
					int xIndex = j-1;
					allDayEntries.get(xIndex).add(new Entry(entry.getBgValue(), xIndex));
				}else{
					lineDates.add(entry.getDate());
					//adding to all day entries
					ArrayList<Entry> currentEntries = new ArrayList<Entry>();
					currentEntries.add(new Entry(entry.getBgValue(), j));
					allDayEntries.put(j, currentEntries);
					j++;
				}
			}
			
			int numXValues = lineDates.size();
			//setting max,min and avg entries
			Iterator it = allDayEntries.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry<Integer, ArrayList<Entry>> pair = (Map.Entry<Integer, ArrayList<Entry>>)it.next();
				int numDayEntries = pair.getValue().size();
				Entry maxEntry = pair.getValue().get(0);
				Entry minEntry = pair.getValue().get(0);
				float sumValue = pair.getValue().get(0).getVal();
				for(int i=1;i<pair.getValue().size();i++){
					Entry currentEntry = pair.getValue().get(i);
					if(maxEntry.getVal()<currentEntry.getVal()){
						maxEntry = currentEntry;
					}
					if(minEntry.getVal()>currentEntry.getVal()){
						minEntry = currentEntry;
					}
					sumValue += currentEntry.getVal();
				}
				Entry avgEntry = new Entry(sumValue/numDayEntries, pair.getKey());
				maxEntries.add(maxEntry);
				minEntries.add(minEntry);
				avgEntries.add(avgEntry);
				Log.v("All Entries", "KEY:"+pair.getKey()+" NUM ITEMS:"+numDayEntries);
				it.remove();
			}
			//log
			int k=0;
				//max
			for(k=0;k<maxEntries.size();k++){
				Log.v("MAX Entries", "Day:"+maxEntries.get(k).getXIndex()+" Value:"+maxEntries.get(k).getVal());
			}
				//min
			for(k=0;k<minEntries.size();k++){
				Log.v("MIN Entries", "Day:"+minEntries.get(k).getXIndex()+" Value:"+minEntries.get(k).getVal());
			}
				//avg
			for(k=0;k<avgEntries.size();k++){
				Log.v("AVG Entries", "Day:"+avgEntries.get(k).getXIndex()+" Value:"+avgEntries.get(k).getVal());
			}
			//CREATING MAX,MIN,AVG GRAPH
			//MAX
			LineDataSet linedataset_max = new LineDataSet(maxEntries, "Maximum Level");
			linedataset_max.setColor(Color.RED);
			linedataset_max.setDrawCircles(true);
			linedataset_max.setDrawValues(true);
			
			//MIN
			LineDataSet linedataset_min = new LineDataSet(minEntries, "Minimum Level");
			linedataset_min.setColor(Color.GREEN);
			linedataset_min.setDrawCircles(true);
			linedataset_min.setDrawValues(true);
			
			//AVG
			LineDataSet linedataset_avg = new LineDataSet(avgEntries, "Average Level");
			linedataset_avg.setColor(Color.BLUE);
			linedataset_avg.setDrawCircles(true);
			linedataset_avg.setDrawValues(true);
			
			//all the graphs are added to this
			List<ILineDataSet> dataset = new ArrayList<ILineDataSet>();
			dataset.add(linedataset_max);
			dataset.add(linedataset_min);
			dataset.add(linedataset_avg);
			
			//LineData lineData = new LineData(lineDates,linedataset);
			LineData lineData = new LineData(lineDates,dataset);
			lineChart.setData(lineData);
			lineChart.setTouchEnabled(true);
			lineChart.setDragEnabled(true);
			lineChart.setScaleEnabled(true);
			
			lineChart.setVisibleXRangeMaximum(10);
			lineChart.setDescription("");
			lineChart.getLegend().setWordWrapEnabled(true);
			lineChart.getLegend().setEnabled(true);
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
