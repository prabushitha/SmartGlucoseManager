package com.whileloop.smartglucosemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION=5;//3;
	private static final String DATABASE_NAME="Glucoseappz.db";//"GlucoseEntry.db";
	//glucose entry (GE)
	private static final String TABLE_GE="glucoseEntry";
	private static final int GE_NUM_COLUMNS = 5;
	private static final String GE_ID="id";
	private static final String GE_DATE="add_date";
	private static final String GE_TIME="add_time";
	private static final String GE_BG="bg";
	private static final String GE_TIME_OF_EVENT="time_of_event";
	
	//alarm test (AT)
	private static final String TABLE_AT="alarmTime";
	private static final int AT_NUM_COLUMNS = 11;
	private static final String AT_ID = "id";
	private static final String AT_TYPE = "type"; //TEST,INSULIN,MEAL,EXERCISE
	private static final String AT_STATUS = "status";//ON,OFF
	private static final String AT_TIME = "a_time";
	private static final String AT_MON = "d_mon";//ON,OFF
	private static final String AT_TUE = "d_tue";
	private static final String AT_WED = "d_wed";
	private static final String AT_THU = "d_thu";
	private static final String AT_FRI = "d_fri";
	private static final String AT_SAT = "d_sat";
	private static final String AT_SUN = "d_sun";
	
	
	SQLiteDatabase db;
	
	private static final String TABLE_GE_CREATE = "create table glucoseEntry (id integer primary key not null, add_date text not null,add_time text not null,bg text not null,time_of_event text not null );";
	private static final String TABLE_AT_CREATE = "create table "+TABLE_AT+
			" ("+
			AT_ID+" integer primary key not null, "+
			AT_TYPE+" text not null,"+
			AT_STATUS+" text not null,"+
			AT_TIME+" text not null,"+
			AT_MON+" text not null,"+
			AT_TUE+" text not null,"+
			AT_WED+" text not null,"+
			AT_THU+" text not null,"+
			AT_FRI+" text not null,"+
			AT_SAT+" text not null,"+
			AT_SUN+" text not null"+
			");";
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_GE_CREATE);
		db.execSQL(TABLE_AT_CREATE);
		this.db=db;
		initAlarmTimeTable(db); //add records to the alarm timetable
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String query="DROP TABLE IF EXISTS "+TABLE_GE+";"+
					 "DROP TABLE IF EXISTS "+TABLE_AT+";";
		db.execSQL(query);
		this.onCreate(db);
		
	}
	//private operations
	private void initAlarmTimeTable(SQLiteDatabase db){
		if(db == null || !db.isOpen()) {
			db=this.getWritableDatabase();
	    }
		
		final int NUMBER_OF_ALARMS = 13;
		final int[] TYPE_AMOUNTS = {4,4,4,1};
		for(int i=1;i<=NUMBER_OF_ALARMS;i++){
			AlarmEntry e = new AlarmEntry();
			e.setId(i);
			if(i<=TYPE_AMOUNTS[0]){
				e.setType(0);
			}else if(i<=TYPE_AMOUNTS[0]+TYPE_AMOUNTS[1]){
				e.setType(1);
			}else if(i<=TYPE_AMOUNTS[0]+TYPE_AMOUNTS[1]+TYPE_AMOUNTS[2]){
				e.setType(2);
			}else if(i<=TYPE_AMOUNTS[0]+TYPE_AMOUNTS[1]+TYPE_AMOUNTS[2]+TYPE_AMOUNTS[3]){
				e.setType(3);
			}
			ContentValues contentValues=new ContentValues();
			contentValues.put(AT_ID, e.getId());
			contentValues.put(AT_TYPE, e.getType());
			contentValues.put(AT_STATUS, e.getStatus());
			contentValues.put(AT_TIME, e.getTime());
			contentValues.put(AT_MON, e.getWeekInfo(1));
			contentValues.put(AT_TUE, e.getWeekInfo(2));
			contentValues.put(AT_WED, e.getWeekInfo(3));
			contentValues.put(AT_THU, e.getWeekInfo(4));
			contentValues.put(AT_FRI, e.getWeekInfo(5));
			contentValues.put(AT_SAT, e.getWeekInfo(6));
			contentValues.put(AT_SUN, e.getWeekInfo(7));
			db.insert(TABLE_AT, null, contentValues);
		}
		
		printTable(TABLE_AT,5);
	}
	private void printTable(String table,int columns){
		if(db == null || !db.isOpen()) {
			db = this.getReadableDatabase();
	    }
		
		String query="select * from "+table;
		Cursor cursor=db.rawQuery(query, null);
		System.out.println("---TABLE "+table+"---");
		if (cursor.moveToFirst()) {
            do {
            	String ss = "";
            	for(int i=0;i<columns;i++){
            		ss += "  "+cursor.getString(i);
            	}
                System.out.println(ss);
            } while (cursor.moveToNext());
        }
		System.out.println("--------------------");
	}
	//public operations
	public void insertGlucoseEntry(GlucoseEntry e) {
		db=this.getWritableDatabase();
		
		ContentValues contentValues=new ContentValues();
		String query="select * from "+TABLE_GE;
		Cursor cursor=db.rawQuery(query, null);
		int count=cursor.getCount();
		contentValues.put(GE_ID,count+1);
		//contentValues.put(COLUMN_ID,e.getId());
		contentValues.put(GE_DATE, e.getDate());
		contentValues.put(GE_TIME, e.getTime());
		contentValues.put(GE_BG, e.getBg());
		contentValues.put(GE_TIME_OF_EVENT, e.getTimeOfEvent());
		db.insert(TABLE_GE, null, contentValues);
		printTable(TABLE_GE,GE_NUM_COLUMNS);
		
	}
	public void updateAlarmEntry(AlarmEntry e){
		db=this.getWritableDatabase();
		ContentValues contentValues=new ContentValues();
		
		contentValues.put(AT_TYPE, e.getType());
		contentValues.put(AT_STATUS, e.getStatus());
		contentValues.put(AT_TIME, e.getTime());
		contentValues.put(AT_MON, e.getWeekInfo(1));
		contentValues.put(AT_TUE, e.getWeekInfo(2));
		contentValues.put(AT_WED, e.getWeekInfo(3));
		contentValues.put(AT_THU, e.getWeekInfo(4));
		contentValues.put(AT_FRI, e.getWeekInfo(5));
		contentValues.put(AT_SAT, e.getWeekInfo(6));
		contentValues.put(AT_SUN, e.getWeekInfo(7));
		db.update(TABLE_AT, contentValues, "id="+e.getId(), null);
		printTable(TABLE_AT,AT_NUM_COLUMNS);
	}
	public AlarmEntry getAlarmEntry(int id){
		db = this.getReadableDatabase();
		
		String query="select * from "+TABLE_AT+" where "+AT_ID+"="+id;
		Cursor cursor=db.rawQuery(query, null);
		AlarmEntry alarmEntry = new AlarmEntry();
		String[] column_data = new String[AT_NUM_COLUMNS];
		if (cursor.moveToFirst()) {
			for(int i=0;i<AT_NUM_COLUMNS;i++){
				column_data[i] = cursor.getString(i);
        	}
        }
		alarmEntry.setId(id);
		alarmEntry.setType(column_data[1]);
		alarmEntry.setStatus(column_data[2]);
		alarmEntry.setTime(column_data[3]);
		for(int i=0;i<7;i++){
			alarmEntry.setWeekInfo(i+1, column_data[4+i]);
		}
		return alarmEntry;
	}
	public AlarmEntry[] getActiveAlarms(){
		db = this.getReadableDatabase();
		String query="select * from "+TABLE_AT+" where "+AT_STATUS+" = 'ON'";
		Cursor cursor=db.rawQuery(query, null);
		int count=cursor.getCount();
		AlarmEntry[] alarmEntries = new AlarmEntry[count];
		
		if (cursor.moveToFirst()) {
			int j = 0;
            do {
            	AlarmEntry alarmEntry = new AlarmEntry();
        		String[] column_data = new String[AT_NUM_COLUMNS];
        		for(int i=0;i<AT_NUM_COLUMNS;i++){
        			column_data[i] = cursor.getString(i);
                }
        		alarmEntry.setId(Integer.parseInt(column_data[0]));
        		alarmEntry.setType(column_data[1]);
        		alarmEntry.setStatus(column_data[2]);
        		alarmEntry.setTime(column_data[3]);
        		for(int i=0;i<7;i++){
        			alarmEntry.setWeekInfo(i+1, column_data[4+i]);
        		}
        		alarmEntries[j] = alarmEntry;
        		j++;
            } while (cursor.moveToNext());
        }
		
		return alarmEntries;
	}
	

}
