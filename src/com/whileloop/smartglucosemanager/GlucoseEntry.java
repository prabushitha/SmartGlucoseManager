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
	public float getBgValue(){
		float f;
		try{
			System.out.println("Glucose : "+bg+" Split : "+bg.split("BG (mg dl)")[0].trim());
			
			f = Float.parseFloat(bg.split("BG (mg dl)")[0].trim());
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
	
}
