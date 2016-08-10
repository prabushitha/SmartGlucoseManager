package com.whileloop.smartglucosemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION=1;
	private static final String DATABASE_NAME="GlucoseEntry.db";
	private static final String TABLE_NAME="glucoseEntry";
	private static final String COLUMN_ID="id";
	private static final String COLUMN_DATE="date";
	private static final String COLUMN_TIME="time";
	private static final String COLUMN_BG="bg";
	private static final String COLUMN_TIME_OF_EVENT="time_of_event";
	SQLiteDatabase db;
	
	private static final String TABLE_CREATE="create table glucoseEntry (id integer primary key not null auto_increment,date text not null,time text not null,bg text not null,time_of_event text not null );";
			
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
		this.db=db;
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String query="DROP TABLE IF EXISTS"+TABLE_NAME;
		db.execSQL(query);
		this.onCreate(db);
		
	}

	public void insertGlucoseEntry(GlucoseEntry e) {
		db=this.getWritableDatabase();
		ContentValues contentValues=new ContentValues();
		contentValues.put(COLUMN_ID,e.getId());
		contentValues.put(COLUMN_DATE, e.getDate());
		contentValues.put(COLUMN_TIME, e.getTime());
		contentValues.put(COLUMN_BG, e.getBg());
		contentValues.put(COLUMN_TIME_OF_EVENT, e.getTimeOfEvent());
		db.insert(TABLE_NAME, null, contentValues);
		
		
	}

}