package com.whileloop.smartglucosemanager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.content.Context;

public class Setting implements Serializable{
	private static final String FILENAME = "settings.ser";
	public int unit = 0; //0=metrics, 1=us
	public boolean isSound = true;
	public boolean isVibrate = true;
	public static int[] UNIT_TYPES = {0,1};
	public static String[][] UNIT_TYPE_PARAMS = {{"kg","m"},{"lb","in"}};
	
	public static void saveSetting(Context context,Setting setting){ 
		try{
			FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(setting);
			oos.close();
			fos.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public static Setting getSetting(Context context){
		Setting setting;
		try{
			FileInputStream fis = context.openFileInput(FILENAME);
			ObjectInputStream is = new ObjectInputStream(fis);
			setting = (Setting) is.readObject();
			is.close();
			fis.close();
		}catch(Exception e){
			System.out.println("get setting "+e.getMessage());
			setting = null;
		}
		return setting;
		
	}
	
	
}
