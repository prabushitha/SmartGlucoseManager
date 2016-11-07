package com.whileloop.smartglucosemanager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class ServerEmail extends AsyncTask<String,Void,String>{
	
	
	
	
	
	
	private Context context;
	private String HASH_PARAM = "hSHJksdkagHlJhshadtgnasdG";
	public ServerEmail(Context context){
		this.context = context;
	}
	@Override
	protected String doInBackground(String... params) {
		try{
			//send using post method
			String hashparam = HASH_PARAM;
            String usermail = (String)params[0];
            String doctormail = (String)params[1];
            String additionalNote = (String)params[2];
            String tableData = (String)params[3];
            
            String link="http://www.kithealthcare.com/smartglucosemanager/emsender.php";//"http://www.teamwhileloop.com/smartglucosemanager/email_sender.php";
            String data  = URLEncoder.encode("hashparam", "UTF-8") + "=" + URLEncoder.encode(hashparam, "UTF-8");
            data += "&" + URLEncoder.encode("usermail", "UTF-8") + "=" + URLEncoder.encode(usermail, "UTF-8");
            data += "&" + URLEncoder.encode("doctormail", "UTF-8") + "=" + URLEncoder.encode(doctormail, "UTF-8");
            data += "&" + URLEncoder.encode("additionalNote", "UTF-8") + "=" + URLEncoder.encode(additionalNote, "UTF-8");
            data += "&" + URLEncoder.encode("tableData", "UTF-8") + "=" + URLEncoder.encode(tableData, "UTF-8");
            
            URL url = new URL(link);
            URLConnection conn = url.openConnection(); 
            
            conn.setDoOutput(true); 
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
            
            wr.write( data ); 
            wr.flush(); 
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            StringBuilder sb = new StringBuilder();
            String line = null;
            
            // Read Server Response
            while((line = reader.readLine()) != null)
            {
               sb.append(line);
               break;
            }
            return sb.toString();
         }
         catch(Exception e){
            return new String("Exception: " + e.getMessage());
         }
	}
	
	@Override
	 protected void onPostExecute(String result){
	      Log.i("Email Data", result);
	      if(!result.equals("error")){
	    	  try{
	    		  //creating user
	    		  User user = new User();
		    	  JSONObject json = new JSONObject(result); 
		    	  boolean status = json.getBoolean("success");
		    	  String error = json.getString("error");
		    	  if(status){
		    		  Toast.makeText(context.getApplicationContext(), "Email Sent", Toast.LENGTH_SHORT).show();
		    	  }else{
		    		  throw new Exception("Server Script Error :"+error);
		    	  }
		    	  
		      }catch(Exception e){
		    	  Toast.makeText(context.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
		      }
	      }else{
	    	  Toast.makeText(context.getApplicationContext(), "Error Occured", Toast.LENGTH_SHORT).show();
	      }
	      //direct user to the settings page
    	  Intent in = new Intent(context,SettingsActivity.class);
          context.startActivity(in);
	 }

}
