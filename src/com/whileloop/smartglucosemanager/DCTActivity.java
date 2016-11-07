package com.whileloop.smartglucosemanager;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

public class DCTActivity extends Activity {
	
	
	
	
	
	
	EditText pp_name;
	EditText pp_phones;
	EditText pp_fax;
	EditText pp_email;
	EditText pp_last_visit;
	EditText pp_next_visit;
	
	
	
	EditText e_name;
	EditText e_phones;
	EditText e_fax;
	EditText e_email;
	EditText e_last_visit;
	EditText e_next_visit;
	
	EditText de_name;
	EditText de_phones;
	EditText de_fax;
	EditText de_email;
	EditText de_last_visit;
	EditText de_next_visit;
	
	private Calendar cal;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dct);
		cal = Calendar.getInstance();
		
		//pp
		pp_name = (EditText)findViewById(R.id.pp_name);
		pp_phones = (EditText)findViewById(R.id.pp_phones);
		pp_fax = (EditText)findViewById(R.id.pp_fax);
		pp_email = (EditText)findViewById(R.id.pp_email);
		pp_last_visit = (EditText)findViewById(R.id.pp_last_visit);
		pp_next_visit = (EditText)findViewById(R.id.pp_next_visit);
		
		//e
		e_name = (EditText)findViewById(R.id.e_name);
		e_phones = (EditText)findViewById(R.id.e_phones);
		e_fax = (EditText)findViewById(R.id.e_fax);
		e_email = (EditText)findViewById(R.id.e_email);
		e_last_visit = (EditText)findViewById(R.id.e_last_visit);
		e_next_visit = (EditText)findViewById(R.id.e_next_visit);

		//de
		de_name = (EditText)findViewById(R.id.de_name);
		de_phones = (EditText)findViewById(R.id.de_phones);
		de_fax = (EditText)findViewById(R.id.de_fax);
		de_email = (EditText)findViewById(R.id.de_email);
		de_last_visit = (EditText)findViewById(R.id.de_last_visit);
		de_next_visit = (EditText)findViewById(R.id.de_next_visit);
		
		
		//saved dct
		Dct saveddct = Dct.getDct(this);
		if(saveddct!=null){
			//pp
			pp_name.setText(saveddct.pp_name);
			pp_phones.setText(saveddct.pp_phones);
			pp_fax.setText(saveddct.pp_fax);
			pp_email.setText(saveddct.pp_email);
			pp_last_visit.setText(saveddct.pp_last_visit);
			pp_next_visit.setText(saveddct.pp_next_visit);
			
			//e
			e_name.setText(saveddct.e_name);
			e_phones.setText(saveddct.e_phones);
			e_fax.setText(saveddct.e_fax);
			e_email.setText(saveddct.e_email);
			e_last_visit.setText(saveddct.e_last_visit);
			e_next_visit.setText(saveddct.e_next_visit);

			//de
			de_name.setText(saveddct.de_name);
			de_phones.setText(saveddct.de_phones);
			de_fax.setText(saveddct.de_fax);
			de_email.setText(saveddct.de_email);
			de_last_visit.setText(saveddct.de_last_visit);
			de_next_visit.setText(saveddct.de_next_visit);
		}else{
			pp_name.setText("Dr. ");
			e_name.setText("Dr. ");
		}
		
		TextWatcher tw1 = new TextWatcher() {

	        @Override
	        public void onTextChanged(CharSequence s, int start, int before, int count) {
	            // TODO Auto-generated method stub

	        }

	        @Override
	        public void beforeTextChanged(CharSequence s, int start, int count,
	                int after) {
	            // TODO Auto-generated method stub

	        }

	        @Override
	        public void afterTextChanged(Editable s) {
	            if(!s.toString().contains("Dr. ")){
	            	pp_name.setText("Dr. ");
	                Selection.setSelection(pp_name.getText(), pp_name.getText().length());
	            }

	        }
	    };
	    
	    TextWatcher tw2 = new TextWatcher() {

	        @Override
	        public void onTextChanged(CharSequence s, int start, int before, int count) {
	            // TODO Auto-generated method stub

	        }

	        @Override
	        public void beforeTextChanged(CharSequence s, int start, int count,
	                int after) {
	            // TODO Auto-generated method stub

	        }

	        @Override
	        public void afterTextChanged(Editable s) {
	            if(!s.toString().contains("Dr. ")){
	            	e_name.setText("Dr. ");
	                Selection.setSelection(e_name.getText(), e_name.getText().length());
	            }

	        }
	    };
	    
	    
	    pp_name.addTextChangedListener(tw1);
	    e_name.addTextChangedListener(tw2);
		
	}
	
	
	public void onItemClick(View view) {
		if(view.getId()==R.id.pp_last_visit){
			showDatePicker(pp_last_visit);
		}else if(view.getId()==R.id.pp_next_visit){
			showDatePicker(pp_next_visit);
		}else if(view.getId()==R.id.e_last_visit){
			showDatePicker(e_last_visit);
		}else if(view.getId()==R.id.e_next_visit){
			showDatePicker(e_next_visit);
		}else if(view.getId()==R.id.de_last_visit){
			showDatePicker(de_last_visit);
		}else if(view.getId()==R.id.de_next_visit){
			showDatePicker(de_next_visit);
		}
		else if(view.getId()==R.id.pp_name){
			Selection.setSelection(pp_name.getText(), pp_name.getText().length());
		}else if(view.getId()==R.id.e_name){
			Selection.setSelection(e_name.getText(), e_name.getText().length());
		}
	}
	private void showDatePicker(EditText visit){
		final EditText current_visit = visit;
		DatePickerDialog dpd1;
		OnDateSetListener otsl = new DatePickerDialog.OnDateSetListener() {
			 
			@Override
		    public void onDateSet(DatePicker view, int year, int monthOfYear,
		            int dayOfMonth) {
				current_visit.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth); 
		    }
        };
        dpd1 = new DatePickerDialog(this,otsl,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
        dpd1.show();
	}
	
	
	//save btn
public void onClickSave(View view) {
		
		Dct dct = new Dct();
		
				//pp
		dct.pp_name = pp_name.getText().toString();
		dct.pp_phones = pp_phones.getText().toString();
		dct.pp_fax = pp_fax.getText().toString();
		dct.pp_email = pp_email.getText().toString();
		dct.pp_last_visit = pp_last_visit.getText().toString();
		dct.pp_next_visit = pp_next_visit.getText().toString();
				
				//e
		dct.e_name = e_name.getText().toString();
		dct.e_phones = e_phones.getText().toString();
		dct.e_fax = e_fax.getText().toString();
		dct.e_email = e_email.getText().toString();
		dct.e_last_visit = e_last_visit.getText().toString();
		dct.e_next_visit = e_next_visit.getText().toString();

				//de
		dct.de_name = de_name.getText().toString();
		dct.de_phones = de_phones.getText().toString();
		dct.de_fax = de_fax.getText().toString();
		dct.de_email = de_email.getText().toString();
		dct.de_last_visit = de_last_visit.getText().toString();
		dct.de_next_visit = de_next_visit.getText().toString();
		
		
		Dct.saveDct(this, dct);
		
		Intent i = new Intent(this,MainActivity.class);
		startActivity(i);
	}
	
	
	//
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
		getMenuInflater().inflate(R.menu.dct, menu);
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
