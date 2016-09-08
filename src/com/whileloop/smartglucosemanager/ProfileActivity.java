package com.whileloop.smartglucosemanager;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ProfileActivity extends Activity {
	
	
	
	
	/*
dob_txt
sexRadioGroup
	maleRadio femalRadio
weightTextField
heightTextField
bmiInput
emailInput
phoneInput
mobileInput
numberInput
mo_do_input
diabEduTxt
emailInputDCP
typeRadioGroup
	dType1
	dType2
	dType3
	dType4
durationTxt

	 */
	EditText dob_txt;
	RadioGroup sexRadioGroup;
	EditText weightTextField;
	EditText heightTextField;
	EditText bmiInput;
	EditText emailInput;
	EditText phoneInput;
	EditText mobileInput;
	EditText diab_care_txt;
	EditText mo_do_input;
	EditText diabEduTxt;
	EditText emailInputDCP;
	RadioGroup typeRadioGroup;
	EditText durationTxt;
	
	RadioButton male;
	RadioButton female;
	
	RadioButton type1;
	RadioButton type2;
	RadioButton type3;
	RadioButton type4;
	
	
	CheckBox[] chks;
	
	private Calendar cal;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		cal = Calendar.getInstance();
		
		dob_txt = (EditText)findViewById(R.id.dob_txt);
		sexRadioGroup = (RadioGroup)findViewById(R.id.sexRadioGroup);
		weightTextField = (EditText)findViewById(R.id.weightTextField);
		heightTextField = (EditText)findViewById(R.id.heightTextField);
		bmiInput = (EditText)findViewById(R.id.bmiInput);
		emailInput = (EditText)findViewById(R.id.emailInput);
		phoneInput = (EditText)findViewById(R.id.phoneInput);
		mobileInput = (EditText)findViewById(R.id.mobileInput);
		diab_care_txt = (EditText)findViewById(R.id.diab_care_txt);
		mo_do_input = (EditText)findViewById(R.id.mo_do_input);
		diabEduTxt = (EditText)findViewById(R.id.diabEduTxt);
		emailInputDCP = (EditText)findViewById(R.id.emailInputDCP);
		typeRadioGroup = (RadioGroup)findViewById(R.id.typeRadioGroup);
		durationTxt = (EditText)findViewById(R.id.durationTxt);
		
		male = (RadioButton)findViewById(R.id.maleRadio);
		female = (RadioButton)findViewById(R.id.femalRadio);
		type1 = (RadioButton)findViewById(R.id.dType1);
		type2 = (RadioButton)findViewById(R.id.dType2);
		type3 = (RadioButton)findViewById(R.id.dType3);
		type4 = (RadioButton)findViewById(R.id.dType4);
		
		chks = new CheckBox[13];
		chks[0] = (CheckBox)findViewById(R.id.checkBox5);
		chks[1] = (CheckBox)findViewById(R.id.checkBox6);
		chks[2] = (CheckBox)findViewById(R.id.checkBox7);
		chks[3] = (CheckBox)findViewById(R.id.checkBox8);
		chks[4] = (CheckBox)findViewById(R.id.checkBox9);
		chks[5] = (CheckBox)findViewById(R.id.checkBox10);
		chks[6] = (CheckBox)findViewById(R.id.checkBox11);
		chks[7] = (CheckBox)findViewById(R.id.checkBox12);
		chks[8] = (CheckBox)findViewById(R.id.checkBox13);
		chks[9] = (CheckBox)findViewById(R.id.checkBox14);
		chks[10] = (CheckBox)findViewById(R.id.checkBox15);
		chks[11] = (CheckBox)findViewById(R.id.checkBox16);
		chks[12] = (CheckBox)findViewById(R.id.checkBox17);
		
		User saveduser = User.getUser(this);
		if(saveduser!=null){
			dob_txt.setText(saveduser.dob);
			if(saveduser.isMale){
				male.setChecked(true);
			}else{
				female.setChecked(true);
			}
			 weightTextField.setText(saveduser.weight+"");
			 heightTextField.setText(saveduser.height+"");
			 bmiInput.setText(saveduser.bmi+"");
			 emailInput.setText(saveduser.email);
			 phoneInput.setText(saveduser.home_phone);
			 mobileInput.setText(saveduser.mobile_phone);
			 diab_care_txt.setText(saveduser.diab_care_provider);
			 mo_do_input.setText(saveduser.mo_do_pa_np);
			 diabEduTxt.setText(saveduser.diab_educator);
			 emailInputDCP.setText(saveduser.dcp_email);
			 
			 if(saveduser.diab_type==1){
				 type1.setChecked(true);
			 }else if(saveduser.diab_type==2){
				 type2.setChecked(true);
			 }else if(saveduser.diab_type==3){
				 type3.setChecked(true);
			 }else{
				 type4.setChecked(true);
			 }
			 
			 for(int i=0;i<13;i++){
				 chks[i].setChecked(saveduser.complications[i]);
			 }
			 
			 durationTxt.setText(saveduser.duration+"");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
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
	public void onItemClick(View view) {
		if(view.getId()==R.id.dob_txt){
			DatePickerDialog dpd;
			OnDateSetListener otsl = new DatePickerDialog.OnDateSetListener() {
				 
				@Override
			    public void onDateSet(DatePicker view, int year, int monthOfYear,
			            int dayOfMonth) {
			        dob_txt.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth); 
			    }
	        };
	        dpd = new DatePickerDialog(this,otsl,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
	        dpd.show();
		}
	}
	
	public void onClickSave(View view) {
		
		User user = new User();
		user.dob = dob_txt.getText().toString();
		user.isMale = (sexRadioGroup.getCheckedRadioButtonId()==male.getId());
		try{
			user.weight = Float.parseFloat(weightTextField.getText().toString());
		}catch(Exception e){
			user.weight = 0;
		}
		try{
			user.height = Float.parseFloat(heightTextField.getText().toString());
		}catch(Exception e){
			user.height = 0;
		}
		try{
			user.bmi = Float.parseFloat(bmiInput.getText().toString());
		}catch(Exception e){
			user.bmi = 0;
		}
		
		
		user.email = emailInput.getText().toString();
		user.home_phone = phoneInput.getText().toString();
		user.mobile_phone = mobileInput.getText().toString();
		user.diab_care_provider = diab_care_txt.getText().toString();
		user.mo_do_pa_np = mo_do_input.getText().toString();
		user.diab_educator = diabEduTxt.getText().toString();
		user.dcp_email = emailInputDCP.getText().toString();
		int typeId = typeRadioGroup.getCheckedRadioButtonId();
		
		if(typeId==type1.getId()){
			user.diab_type = 1;
		}else if(typeId==type2.getId()){
			user.diab_type = 2;
		}else if(typeId==type3.getId()){
			user.diab_type = 3;
		}else{
			user.diab_type = 4;
		}
		try{
			user.duration = Integer.parseInt(durationTxt.getText().toString());
		}catch(Exception e){
			user.duration = 0;
		}
		
		for(int i=0;i<13;i++){
			user.complications[i] = chks[i].isChecked();
		}
		
		User.saveUser(this, user);
		
		Intent i = new Intent(this,MainActivity.class);
		startActivity(i);
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
