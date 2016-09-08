package com.whileloop.smartglucosemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CalculatorActivity extends Activity {
	
	
	
	
	
	final Context context = this;
	private TextView answerTxt;
	private EditText targetInp;
	private EditText icrationInp;
	private EditText cfactorInp;
	
	private Button calculateBtn;
	private Button carbsBtn;
	private Button clevelBtn;
	
	private float carbs=0;//
	private float carbLevel=0;//
	private int target=0;
	private int carbRatio=1;
	private int cfactor=1;
	public float calculation(){
		try{
			target = Integer.parseInt(targetInp.getText().toString());
		}catch(Exception e){
			target = 0;
		}
		
		try{
			carbRatio = Integer.parseInt(icrationInp.getText().toString());
		}catch(Exception e){
			carbRatio = 0;
		}
		
		try{
			cfactor = Integer.parseInt(cfactorInp.getText().toString());
		}catch(Exception e){
			cfactor = 0;
		}
		// ((cl - target) / correction_factor) + (grams / carb_ratio)
		return ((carbLevel-target)/cfactor) + (carbs/carbRatio);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator);
		
		answerTxt = (TextView)findViewById(R.id.answerTxt);
		targetInp = (EditText)findViewById(R.id.targetInp);
		icrationInp = (EditText)findViewById(R.id.icratioInp);
		cfactorInp  = (EditText)findViewById(R.id.cfactorInp);
		
		
		carbsBtn = (Button)findViewById(R.id.carbsBtn);
		clevelBtn = (Button)findViewById(R.id.clevelBtn);
		calculateBtn = (Button)findViewById(R.id.calculateBtn);
		
		OnClickListener dialogListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LayoutInflater li = LayoutInflater.from(context);
				View promptsView = li.inflate(R.layout.prompt, null);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);
				alertDialogBuilder.setView(promptsView);

				final EditText userInput = (EditText) promptsView
						.findViewById(R.id.editTextDialogUserInput);
				final View vi = v;
				// set dialog message
				alertDialogBuilder.setCancelable(false).setPositiveButton("OK",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
						// get user input and set it to result
						// edit text
					    	Editable inp = userInput.getText();
					    	if(vi.getId()==carbsBtn.getId()){
					    		try{
					    			carbs = Float.parseFloat(inp.toString());
					    		}catch(Exception e){
					    			carbs = 0;
					    		}
					    		carbsBtn.setText(carbs+" grams");
					    	}else if(vi.getId()==clevelBtn.getId()){
					    		try{
					    			carbLevel = Float.parseFloat(inp.toString());
					    		}catch(Exception e){
					    			carbLevel = 0;
					    		}
					    		clevelBtn.setText(carbLevel+ " mg/DL");
					    	}
					    	
					    }
					  })
					.setNegativeButton("Cancel",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					    }
					  });

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
			}
		};
		OnClickListener calListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(v.getId()==calculateBtn.getId()){
					String ans = String.format("%.2f", calculation());
					answerTxt.setText(ans);
				}
			}
		};
		
		carbsBtn.setOnClickListener(dialogListener);
		clevelBtn.setOnClickListener(dialogListener);
		calculateBtn.setOnClickListener(calListener);
	}
	

	public void onBackPress(View v){
		onBackPressed();
	}
	@Override
     public void onBackPressed(){
		Intent i = new Intent(this,MainActivity.class);
		startActivity(i);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calculator, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
