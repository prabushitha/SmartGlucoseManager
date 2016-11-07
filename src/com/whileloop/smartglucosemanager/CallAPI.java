package com.whileloop.smartglucosemanager;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import android.os.AsyncTask;
//import org.apache.commons.io.IOUtils;
public class CallAPI extends AsyncTask<String, String, String>{
	 public CallAPI(){
         //set context variables if required
     }

     @Override
     protected void onPreExecute() {
         super.onPreExecute();
     }


      @Override
      protected String doInBackground(String... params) {

         String urlString = params[0]; // URL to call

         String resultToDisplay = "";

         InputStream in = null;
         try {

             URL url = new URL(urlString);

             HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
             urlConnection.setRequestMethod("GET");
             urlConnection.connect();
             in = new BufferedInputStream(urlConnection.getInputStream());


         } catch (Exception e) {

             System.out.println(e.getMessage());

             return e.getMessage();

         }

         try {
             resultToDisplay = IOUtils.toString(in, "UTF-8");
             //to [convert][1] byte stream to a string 
         } 
         catch (IOException e) {
             e.printStackTrace();
         }
         return resultToDisplay;
     }


      @Override
     protected void onPostExecute(String result) {
         //Update the UI
     }
      

}
