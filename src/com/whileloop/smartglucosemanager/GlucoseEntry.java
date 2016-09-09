package com.whileloop.smartglucosemanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GlucoseEntry {
	private int id;
	private String date;
	private String time;
	private String bg;
	private String time_of_event;
	
	public void setId(int id){
		this.id=id;
	}
	
	public int getId(){
		return id;
	}
	
	public void setDate(String date){
		this.date=date; //eg.2015/8/13
	}
	
	public String getDate(){
		return date; 
	}
	
	public void setTime(String time){
		this.time=time;
	}
	
	
	public String getTime(){
		return time;
	}
	
	public void setBg(String bg){
		this.bg=bg;
	}
	
	
	public String getBg(){
		return bg;
	}
	public float getBgValue(){
		float f;
		try{
			String s = new String(bg);
			Matcher matcher = Pattern.compile("\\d+").matcher(s);
			matcher.find();
			f = Float.valueOf(matcher.group());
			
			//System.out.println("Glucose : "+bg+" Split : "+bg.split("BG")[0].trim());
			
			//f = Float.parseFloat(bg.split("BG")[0].trim());
		}catch(Exception e){
			f = 0;
		}
		return f;
	}
	
	public void setTimeOfEvent(String time_of_event){
		this.time_of_event=time_of_event;
	}
	
	
	public String getTimeOfEvent(){
		return time_of_event;
	}
	
	public static GlucoseEntry[] LastEntries(GlucoseEntry[] entries,String type){
		GlucoseEntry[] results = new GlucoseEntry[entries.length];
		entries = SortByDate(entries);
		for(int i=0;i<entries.length;i++){
			results[i] = entries[entries.length-i-1];
		}
		
		Date d;
		Date current = new Date();
		long currentMilies = current.getTime();
		long timeless;
		
		long milisForADay = 24*60*60*1000;
		if(type.equals("Week")){
			timeless = 7*milisForADay;
		}else if(type.equals("Month")){
			timeless = 31*milisForADay;
		}else if(type.equals("Month")){
			timeless = 365*milisForADay;
		}else{
			return results;
		}
		
		ArrayList<GlucoseEntry> resultEntries = new ArrayList<GlucoseEntry>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm a");
		
		for(int i=0;i<results.length;i++){
			GlucoseEntry ge = results[i];
			String dformated = ge.getDate().replaceAll("-","/")+" "+ge.getTime();
			try{
				d = sdf.parse(dformated);
				long timeMilies = d.getTime();
				if(timeMilies>currentMilies-timeless){
					resultEntries.add(ge);
				}
			}catch(ParseException e){
				
			}
		}
		return resultEntries.toArray(new GlucoseEntry[resultEntries.size()]);
	}
	
	//sorting
	public static GlucoseEntry[] SortByDate(GlucoseEntry[] entries){
		long[] days = new long[entries.length];
		SimpleDateFormat sdf;
		Date d;
		for(int i=0;i<entries.length;i++){
			GlucoseEntry ge = entries[i];
			long dayCount = 0;
			sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm a");
			String dformated = ge.getDate().replaceAll("-","/")+" "+ge.getTime();
			try{
				d = sdf.parse(dformated);
				dayCount = d.getTime()/1000;
				days[i] = dayCount;
			}catch(ParseException e){
				
			}
		}
		quickSort(days,entries,0,entries.length-1);
		return entries;
	}
	
	public static void quickSort(long[] arr, GlucoseEntry[] entries, int low, int high) {
		if (arr == null || arr.length == 0)
			return;
 
		if (low >= high)
			return;
 
		// pick the pivot
		int middle = low + (high - low) / 2;
		long pivot = arr[middle];
 
		// make left < pivot and right > pivot
		int i = low, j = high;
		while (i <= j) {
			while (arr[i] < pivot) {
				i++;
			}
 
			while (arr[j] > pivot) {
				j--;
			}
 
			if (i <= j) {
				long temp = arr[i];
				GlucoseEntry ge = entries[i];
				
				arr[i] = arr[j];
				entries[i] = entries[j];
				
				arr[j] = temp;
				entries[j] = ge;
				
				i++;
				j--;
			}
		}
 
		// recursively sort two sub parts
		if (low < j)
			quickSort(arr, entries, low, j);
 
		if (high > i)
			quickSort(arr, entries, i, high);
	}
	
	
}
