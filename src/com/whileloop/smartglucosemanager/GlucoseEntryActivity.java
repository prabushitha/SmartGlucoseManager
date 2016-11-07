package com.whileloop.smartglucosemanager;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

public class GlucoseEntryActivity extends Activity {
	final Context context = this;
	private RadioGroup radioGroup;
	private RadioButton radioButton;
	private EditText editDateText;
	private EditText editTimeText;
	private EditText bgValueText;
	private Calendar cal;
	DatabaseHelper databaseHelper;
	float glucoseAmount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_glucose_entry);
		glucoseAmount = 0;
		databaseHelper = new DatabaseHelper(this);
		cal = Calendar.getInstance();
		
		editDateText = (EditText)findViewById(R.id.dateText);
		editDateText.setText(cal.get(Calendar.YEAR)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DAY_OF_MONTH));
		
		editTimeText = (EditText)findViewById(R.id.timeText);
		editTimeText.setText(getNormalTime(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE)));
		
		bgValueText = (EditText)findViewById(R.id.bgText);
		//bgValueText.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "500")});
		
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
				if(glucoseAmount>=40){
					userInput.setText((int)glucoseAmount+"");
				}else{
					userInput.setHint("40 to 500");
				}
				
				userInput.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "500")});
				final View vi = v;
				// set dialog message
				alertDialogBuilder.setCancelable(false).setPositiveButton("OK",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
						// get user input and set it to result
						// edit text
					    	Editable inp = userInput.getText();
					    	try{
					    		float userInpVal = Float.parseFloat(inp.toString());
					    		if(userInpVal>=40 && userInpVal<=500){
					    			glucoseAmount = userInpVal;
					    			bgValueText.setText(inp.toString()+" mg/dL");
					    		}
					    	}catch(Exception e){
					    		
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
		bgValueText.setOnClickListener(dialogListener);
		
		/*
		bgValueText.addTextChangedListener(new TextWatcher(){
		    public void afterTextChanged(Editable s) {}

		    public void beforeTextChanged(CharSequence s, int start, int count, int after){}

		    public void onTextChanged(CharSequence s, int start, int before, int count){
		        String strEnteredVal = bgValueText.getText().toString();

		        if(!strEnteredVal.equals("") && strEnteredVal.length()>1){
		        	int num=Integer.parseInt(strEnteredVal);
		        	if(num<40){
		        		bgValueText.setText("");
		        	}
		        }
		    }
		}); */
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.glucose_entry, menu);
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
	
	public void onSaveButtonClick(View v){
		if(v.getId()==R.id.savebutton){
			
			radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
			EditText dateText=(EditText)findViewById(R.id.dateText);
			EditText timeText=(EditText)findViewById(R.id.timeText);
			EditText bgText=(EditText)findViewById(R.id.bgText);
			
			int selectedId = radioGroup.getCheckedRadioButtonId();
            radioButton = (RadioButton) findViewById(selectedId);
            
            String date=dateText.getText().toString();
            String time=timeText.getText().toString();
            String bg=Float.toString(glucoseAmount);//bgText.getText().toString();
            String time_of_event=radioButton.getText().toString();
            
            GlucoseEntry glucoseEntry=new GlucoseEntry();
            glucoseEntry.setDate(date);
            glucoseEntry.setTime(time);
            glucoseEntry.setBg(bg);
            glucoseEntry.setTimeOfEvent(time_of_event);
            
            if(glucoseEntry.getBgValue()!=0){
            	databaseHelper.insertGlucoseEntry(glucoseEntry);
                Log.e("Save Button", "Save clicked");
                Intent i = new Intent(this,MainActivity.class);
                startActivity(i);
            }else{
            	Log.e("Save Button", "Not valid bg amount");
            }
            
            
           
		}else if(v.getId()==R.id.dateText){
			DatePickerDialog dpd;
			OnDateSetListener otsl = new DatePickerDialog.OnDateSetListener() {
				 
				@Override
			    public void onDateSet(DatePicker view, int year, int monthOfYear,
			            int dayOfMonth) {
			        editDateText.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth); 
			    }
	        };
	        dpd = new DatePickerDialog(this,otsl,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
	        dpd.show();
		}else if(v.getId()==R.id.timeText){
			// TODO Auto-generated method stub
						TimePickerDialog tpd;
						
						OnTimeSetListener otsl = new TimePickerDialog.OnTimeSetListener() {
							 
				            @Override
				            public void onTimeSet(TimePicker view, int hourOfDay,
				                    int minute) {
				            	editTimeText.setText(getNormalTime(hourOfDay,minute));
				            }
				        };
				        tpd = new TimePickerDialog(this,otsl,cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),false);
				        tpd.show();
		}
	
	}
	
	public String getNormalTime( int hours, int minutes){
    	String ampm = "AM";
    	String hourZeros = "";
    	String minuteZero = "";
    	if(hours>=12){
    		ampm = "PM";
    	}
    	if(hours>12){
    		hours = hours%12;
    	}
    	if(hours==0)hours=12;
    	if(hours<10)hourZeros="0";
    	if(minutes<10)minuteZero="0";
    	
    	return hourZeros+hours+":"+minuteZero+minutes+" "+ampm;
    }
	public void onBackPress(View v){
		onBackPressed();
	}
	@Override
     public void onBackPressed(){
		Intent i = new Intent(this,MainActivity.class);
		startActivity(i);
	}
	
}
