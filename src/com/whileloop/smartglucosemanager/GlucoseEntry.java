package com.whileloop.smartglucosemanager;


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
		this.date=date;
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
	
	public void setTimeOfEvent(String time_of_event){
		this.time_of_event=time_of_event;
	}
	
	
	public String getTimeOfEvent(){
		return time_of_event;
	}
	
}
