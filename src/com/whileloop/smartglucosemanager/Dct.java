package com.whileloop.smartglucosemanager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.content.Context;

public class Dct implements Serializable{
	private static final String FILENAME = "dct.ser";
	
	String pp_name;
	String pp_phones;
	String pp_fax;
	String pp_email;
	String pp_last_visit;
	String pp_next_visit;
	
	String e_name;
	String e_phones;
	String e_fax;
	String e_email;
	String e_last_visit;
	String e_next_visit;
	
	String de_name;
	String de_phones;
	String de_fax;
	String de_email;
	String de_last_visit;
	String de_next_visit;
	
	
	
	
	public static void saveDct(Context context,Dct dct){ 
		try{
			FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(dct);
			oos.close();
			fos.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public static Dct getDct(Context context){
		Dct dct;
		try{
			FileInputStream fis = context.openFileInput(FILENAME);
			ObjectInputStream is = new ObjectInputStream(fis);
			dct = (Dct) is.readObject();
			is.close();
			fis.close();
		}catch(Exception e){
			System.out.println("get user "+e.getMessage());
			dct = null;
		}
		return dct;
		
	}
}
