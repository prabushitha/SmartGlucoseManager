package com.whileloop.smartglucosemanager;

public class AlarmEntry {
	private int id;
	private String type;
	private String status;
	private String time;
	
	
	public static final String ALARM_ON = "ON";
	public static final String ALARM_OFF = "OFF";
	public static final String[] TYPES = {"TEST","INSULIN","MEAL","EXERCISE"};
	
	private String[] week_info;
	public AlarmEntry(){
		this.status = ALARM_OFF;
		this.setTime(0, 0);
		week_info = new String[7];
		for(int i=0;i<7;i++){
			week_info[i] = ALARM_OFF;
		}
	}
	public void setWeekInfo(int day,String status){
		if(status.equals(ALARM_ON) || status.equals(ALARM_OFF)){
			week_info[day-1] = status;
		}
	}
	public String getWeekInfo(int day){
		return week_info[day-1];
	}
	public int[] getAllWeekInfoStatus(){
		//0th index to monday etc
		int[] days = new int[week_info.length];
		for(int i = 0;i<week_info.length;i++){
			days[i] = 0;
			if(week_info[i].equals(ALARM_ON)){
				days[i] = 1;
			}
		}
		return days;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(int type) {
		if(type<TYPES.length){
			this.type = TYPES[type];
		}
	}
	public void setType(String type){
		boolean isType = false;
		for(int i=0;i<TYPES.length;i++){
			if(type.equals(TYPES[i])){
				isType = true;
				break;
			}
		}
		if(isType)this.type=type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTime() {
		return time;
	}
	public void setTime(int hours,int minutes) {
		this.time = hours+":"+minutes;
	}
	public void setTime(String time){
		this.time = time;
	}
	public int getHours(){
		String[] hoursMinutes = this.time.split(":");
		return Integer.parseInt(hoursMinutes[0]);
	}
	public int getMinutes(){
		String[] hoursMinutes = this.time.split(":");
		return Integer.parseInt(hoursMinutes[1]);
	}
}
