package com.whileloop.smartglucosemanager;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableLayout.LayoutParams;

public class ProfileActivity extends Activity {
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
	int UNIT_TYPE = 0;
	int[] UNIT_TYPES;// = {0,1};//metrics, us
	String[][] UNIT_TYPE_PARAMS;// = {{"kg","m"}};
	private Calendar cal;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		UNIT_TYPES = Setting.UNIT_TYPES;
		UNIT_TYPE_PARAMS = Setting.UNIT_TYPE_PARAMS;
		
		
		Setting mysetting = Setting.getSetting(this);
		if(mysetting==null){
			mysetting = new Setting();
		}
		UNIT_TYPE = mysetting.unit;
		
		
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
			 float weightNowUnits = roundOFF(translateWeight(saveduser.weight,saveduser.unit,UNIT_TYPE),3);
			 float heightNowUnits = roundOFF(translateHeight(saveduser.height,saveduser.unit,UNIT_TYPE),4);
			 float bmiNowUnits = roundOFF(translateBMI(weightNowUnits,heightNowUnits,UNIT_TYPE),4);
			 weightTextField.setText(weightNowUnits+"");
			 heightTextField.setText(heightNowUnits+"");
			 bmiInput.setText(bmiNowUnits+"");
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
		
		TextWatcher tw = new TextWatcher() {

	          public void afterTextChanged(Editable s) {
	          }

	          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

	          public void onTextChanged(CharSequence s, int start, int before, int count) {
	        	  try{
	        		  bmiInput.setText(calculateBMI(
	        				  Float.parseFloat(weightTextField.getText().toString()), 
	        				  Float.parseFloat(heightTextField.getText().toString()), 
	        				  UNIT_TYPE)+"");
	        	  }catch(Exception e){
	        		  Log.e("Error BMI", e.getMessage());
	        	  }
	        	  
	          }
	       };
		 
		 weightTextField.addTextChangedListener(tw);
		 heightTextField.addTextChangedListener(tw);
		 
		 weightTextField.setHint(UNIT_TYPE_PARAMS[UNIT_TYPE][0]);
		 heightTextField.setHint(UNIT_TYPE_PARAMS[UNIT_TYPE][1]);
		 
		 phoneInput.addTextChangedListener(new PhoneNumberWatcher());//(new PhoneNumberFormattingTextWatcher());
		 mobileInput.addTextChangedListener(new PhoneNumberWatcher());//(new PhoneNumberFormattingTextWatcher());
	
	
		 //Medications
		 
		 //oral
		 List<String> om_sp1_cat = new ArrayList<String>();
			
			om_sp1_cat.add("None");
			om_sp1_cat.add("Acarbose (Precose )");
			om_sp1_cat.add("Chlorpropamide (Diabinese)");
			om_sp1_cat.add("Eucreas");
			om_sp1_cat.add("Farxiga");
			om_sp1_cat.add("Gliclazide");
			om_sp1_cat.add("Glibenclamide");
			om_sp1_cat.add("Glimeperide (Amaryl)");
			om_sp1_cat.add("Glyburide(DiaBeta)");
			om_sp1_cat.add("Glipizide(Glucotrol)");
			om_sp1_cat.add("Glipizide ER");
			om_sp1_cat.add("Invokana");
			om_sp1_cat.add("Invokamet");
			om_sp1_cat.add("Jardiance");
			om_sp1_cat.add("Janumet");
			om_sp1_cat.add("Janumet XR");
			om_sp1_cat.add("Jentadueto");
			om_sp1_cat.add("Komboglyze");
			om_sp1_cat.add("Linagliptin(Tradjenta)");
			om_sp1_cat.add("Metformin (Glucophage)");
			om_sp1_cat.add("Metformin XR");
			om_sp1_cat.add("Piaglitazone(Actos)");
			om_sp1_cat.add("Rosiglitazone(Avandia)");
			om_sp1_cat.add("Repaglinide (Prandin)");
			om_sp1_cat.add("Sexaglipitine (Onglyza)");
			om_sp1_cat.add("Sitagliptin (Januvia)");
			om_sp1_cat.add("Tolbutamide");
			om_sp1_cat.add("Vildagliptin (Galvus)");
			om_sp1_cat.add("Xigduo XR");
		 
		 
		 LinearLayout oralLayout = (LinearLayout) findViewById(R.id.oralLayout);
		 Oral oral = Oral.getOral(this);
		 String[] oralNames = {"None","None","None","None","None","None"};
		 if(oral==null){
			 oral = new Oral();
			 oral.resetValues();
		 }
		 oralNames[0] = om_sp1_cat.get(oral.cat0[0]);
		 oralNames[1] = om_sp1_cat.get(oral.cat1[0]);
		 oralNames[2] = om_sp1_cat.get(oral.cat2[0]);
		 oralNames[3] = om_sp1_cat.get(oral.cat3[0]);
		 oralNames[4] = om_sp1_cat.get(oral.cat4[0]);
		 oralNames[5] = oral.writing_medi;
				 
		 int oralCount = 0;
		 for(int i=0;i<6;i++){
			 if(oralNames[i]!="None" && oralNames[i]!=""){
				 oralCount++;
				 TextView tv1 = new TextView(this);
				 tv1.setId(i+2500);
				 tv1.setText(oralNames[i]);
				 //tv1.setTextSize(10);
				 oralLayout.addView(tv1);
			 }
		 }
		 if(oralCount==0){
			 TextView tv1 = new TextView(this);
			 tv1.setId(2500);
			 tv1.setText("None");
			 //tv1.setTextSize(10);
			 oralLayout.addView(tv1);
		 }
		 
		 
		 
		 //Insuline and medication
		 List<String> ni_sp1_cat = new ArrayList<String>();
			
			ni_sp1_cat.add("None");
			ni_sp1_cat.add("Albiglutide (Tanzeum)");
			ni_sp1_cat.add("Dulaglutide (Trulicity)");
			ni_sp1_cat.add("Exenatide (Byetta)");
			ni_sp1_cat.add("Exenatide long acting (Bydureon)");
			ni_sp1_cat.add("Liraglutide (Victoza)");
		
		 List<String> li_sp1_cat = new ArrayList<String>();
			li_sp1_cat.add("None");
			li_sp1_cat.add("Degludec (Tresiba)");
			li_sp1_cat.add("Detemir (Levemir)");
			li_sp1_cat.add("Glargine (Lantus)");
			li_sp1_cat.add("Toujeo");
			li_sp1_cat.add("NPH");
			
		 List<String> ri_sp1_cat = new ArrayList<String>();
			ri_sp1_cat.add("None");
			ri_sp1_cat.add("Aspart (Novolog)");
			ri_sp1_cat.add("Humulin R u 500");
			ri_sp1_cat.add("Glulisine (Apidra)");
			ri_sp1_cat.add("Lispro (Humalog) u 100");
			ri_sp1_cat.add("Lispro (Humalog) u 200");
			ri_sp1_cat.add("Regular insulin");
			
		  List<String> pi_sp1_cat = new ArrayList<String>();
			pi_sp1_cat.add("None");
			pi_sp1_cat.add("Humalog 75/25");
			pi_sp1_cat.add("Humalog 50/50");
			pi_sp1_cat.add("Humulin 70/30");
			pi_sp1_cat.add("Novolog 70/30");
			pi_sp1_cat.add("Novolin 70/30");
			
		  List<String> ii_sp1_cat = new ArrayList<String>();
			ii_sp1_cat.add("None");
			ii_sp1_cat.add("Afrezza");
			
		 LinearLayout insulineLayout = (LinearLayout) findViewById(R.id.insulineLayout);
		 Medication medication = Medication.getMedication(this);
		 String[] insulineNames = {"None","None","None","None","None","None"};
		 if(medication==null){
			 medication = new Medication();
			 medication.resetValues();
		 }
		 insulineNames[0] = ni_sp1_cat.get(medication.cat1[0]);
		 insulineNames[1] = li_sp1_cat.get(medication.cat2[0]);
		 insulineNames[2] = ri_sp1_cat.get(medication.cat3[0]);
		 insulineNames[3] = pi_sp1_cat.get(medication.cat4[0]);
		 insulineNames[4] = ii_sp1_cat.get(medication.cat5[0]);
		 insulineNames[5] = medication.writing_medi;
		 int insulineCount = 0;
		 for(int i=0;i<6;i++){
			 if(insulineNames[i]!="None" && insulineNames[i]!=""){
				 insulineCount++;
				 TextView tv2 = new TextView(this);
				 tv2.setId(i+2600);
				 tv2.setText(insulineNames[i]);
				 //tv2.setTextSize(10);
				 insulineLayout.addView(tv2);
			 }
		 }
		 if(insulineCount==0){
			 TextView tv2 = new TextView(this);
			 tv2.setId(2600);
			 tv2.setText("None");
			 //tv2.setTextSize(10);
			 insulineLayout.addView(tv2);
		 }
	
	}
	private float calculateBMI(float weight,float height,int unit){
		if(unit == UNIT_TYPES[0]){
			return weight/(height*height);
		}else if (unit == UNIT_TYPES[1]) {
			return (weight*703)/(height*height);
		}
		return weight/(height*height);
	}
	
	private float translateHeight(float value,int savedUnit,int nowUnit){
		if(savedUnit!=nowUnit){
			if(savedUnit==UNIT_TYPES[0] && nowUnit==UNIT_TYPES[1]){//metrics to us
				//1 m = 39.3701 in
				return value*39.3701f;
			}else if(savedUnit==UNIT_TYPES[1] && nowUnit==UNIT_TYPES[0]){//us to metrics
				//1 in = 0.0254 m
				return value*0.0254f;
			}
		}
		return value;
	}
	private float roundOFF(float value, int decimals){
		String s;
		if(decimals==1){
			s = "#.#";
		}else if(decimals==2){
			s = "#.##";
		}else if(decimals==3){
			s = "#.###";
		}else if(decimals==4){
			s = "#.####";
		}else{
			s = "#.#####";
		}
		DecimalFormat df = new DecimalFormat(s);
		df.setRoundingMode(RoundingMode.CEILING);
		String val = df.format(value);
		float result = 0;
		try{
			result = Float.parseFloat(val);
		}catch(Exception e){
			
		}
		
		return result;
	}
	
	private float translateWeight(float value,int savedUnit,int nowUnit){
		if(savedUnit!=nowUnit){
			if(savedUnit==UNIT_TYPES[0] && nowUnit==UNIT_TYPES[1]){//metrics to us
				//1 kg = 2.20462 lbs
				return value*2.20462f;
			}else if(savedUnit==UNIT_TYPES[1] && nowUnit==UNIT_TYPES[0]){//us to metrics
				//1 lbs = 0.453592 kg
				return value*0.453592f;
			}
		}
		return value;
	}
	
	private float translateBMI(float weight, float height, int nowUnit){
		return calculateBMI(weight,height,nowUnit);
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
		
		user.unit = UNIT_TYPE;
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
