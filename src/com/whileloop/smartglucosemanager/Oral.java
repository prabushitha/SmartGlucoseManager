package com.whileloop.smartglucosemanager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.content.Context;

public class Oral implements Serializable{
	private static final String FILENAME = "oral.ser";
	int[] cat0 = new int[3];
	int[] cat1 = new int[3];
	int[] cat2 = new int[3];
	int[] cat3 = new int[3];
	int[] cat4 = new int[3];
	String writing_medi = "";
	
	public void resetValues(){
		cat0 = new int[3];
		cat1 = new int[3];
		cat2 = new int[3];
		cat3 = new int[3];
		cat4 = new int[3];
		writing_medi = "";
	}
	public static void saveOral(Context context,Oral oral){ 
		try{
			FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(oral);
			oos.close();
			fos.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public static Oral getOral(Context context){
		Oral oral;
		try{
			FileInputStream fis = context.openFileInput(FILENAME);
			ObjectInputStream is = new ObjectInputStream(fis);
			oral = (Oral) is.readObject();
			is.close();
			fis.close();
		}catch(Exception e){
			System.out.println("get medication "+e.getMessage());
			oral = null;
		}
		return oral;
		
	}

}
