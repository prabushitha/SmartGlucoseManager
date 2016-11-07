package com.whileloop.smartglucosemanager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.content.Context;

public class User implements Serializable{
	
	private static final String FILENAME = "user.ser";
	
	int unit = 0;
	
	String dob;
	boolean isMale;
	float height; //cm
	float weight; //kg
	
	float bmi;
	String email;
	String home_phone;
	String mobile_phone;
	String diab_care_provider;
	//DCP
	String mo_do_pa_np; 
	String diab_educator;
	String dcp_email;
	int diab_type;//1=type1,2=type2,3=unknown,4=none
	int duration;
	boolean[] complications = new boolean[13];
	
	public static void saveUser(Context context,User user){ 
		try{
			FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(user);
			oos.close();
			fos.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public static User getUser(Context context){
		User user;
		try{
			FileInputStream fis = context.openFileInput(FILENAME);
			ObjectInputStream is = new ObjectInputStream(fis);
			user = (User) is.readObject();
			is.close();
			fis.close();
		}catch(Exception e){
			System.out.println("get user "+e.getMessage());
			user = null;
		}
		return user;
		
	}
}
