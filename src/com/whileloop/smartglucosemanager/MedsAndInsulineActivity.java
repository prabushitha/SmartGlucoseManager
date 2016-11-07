package com.whileloop.smartglucosemanager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
public class MedsAndInsulineActivity extends Activity implements OnItemSelectedListener{
	public MedsAndInsulineActivity() {
		
	}
	Spinner ni_spinner1;
	Spinner ni_spinner2;
	Spinner ni_spinner3;
	
	Spinner li_spinner1;
	EditText li_edit1;
	Spinner li_spinner2;
	
	Spinner ri_spinner1;
	EditText ri_edit1;
	Spinner ri_spinner2;
	
	Spinner pi_spinner1;
	EditText pi_edit1;
	Spinner pi_spinner2;
	
	Spinner ii_spinner1;
	Spinner ii_spinner2;
	Spinner ii_spinner3;
	
	EditText writingMedi;
	
	Medication medication;
	MedsAndInsulineActivity mia;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meds_and_insuline);
		
		mia = this;
		medication = Medication.getMedication(this);
		boolean isMedicationNotSet = false;
		if(medication==null){
			medication = new Medication();
			medication.resetValues();
			isMedicationNotSet = true;
		}
		
				writingMedi = (EditText)findViewById(R.id.writing_medi_meds);
				//set max/min range for edit texts
				li_edit1 = (EditText)findViewById(R.id.li_edit1);
				ri_edit1 = (EditText)findViewById(R.id.ri_edit1);
				pi_edit1 = (EditText)findViewById(R.id.pi_edit1);
				
				li_edit1.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "300")});
				ri_edit1.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "200")});
				pi_edit1.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "200")});
				
				//not insuline
				ni_spinner1 = (Spinner)findViewById(R.id.ni_spinner1);
				ni_spinner2 = (Spinner)findViewById(R.id.ni_spinner2);
				ni_spinner3 = (Spinner)findViewById(R.id.ni_spinner3);
				
				//long acting insuline
			    li_spinner1 = (Spinner)findViewById(R.id.li_spinner1);
				li_spinner2 = (Spinner)findViewById(R.id.li_spinner2);
				
				//rapid acting insuline
			    ri_spinner1 = (Spinner)findViewById(R.id.ri_spinner1);
				ri_spinner2 = (Spinner)findViewById(R.id.ri_spinner2);
				
				//premix insuline
			    pi_spinner1 = (Spinner)findViewById(R.id.pi_spinner1);
			    pi_spinner2 = (Spinner)findViewById(R.id.pi_spinner2);
				

		
		
	    
		//not insuline
		
		List<String> ni_sp1_cat = new ArrayList<String>();
		
		ni_sp1_cat.add("None");
		ni_sp1_cat.add("Albiglutide (Tanzeum)");
		ni_sp1_cat.add("Dulaglutide (Trulicity)");
		ni_sp1_cat.add("Exenatide (Byetta)");
		ni_sp1_cat.add("Exenatide long acting (Bydureon)");
		ni_sp1_cat.add("Liraglutide (Victoza)");

	    ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ni_sp1_cat);
	    dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    ni_spinner1.setAdapter(dataAdapter1);
	    
	    
	    //long acting insuline
		
		List<String> li_sp1_cat = new ArrayList<String>();
		li_sp1_cat.add("None");
		li_sp1_cat.add("Degludec (Tresiba)");
		li_sp1_cat.add("Detemir (Levemir)");
		li_sp1_cat.add("Glargine (Lantus)");
		li_sp1_cat.add("Toujeo");
		li_sp1_cat.add("NPH");

	    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, li_sp1_cat);
	    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    li_spinner1.setAdapter(dataAdapter2);
	    
	    
	  //rapid acting insuline
		
		List<String> ri_sp1_cat = new ArrayList<String>();
		ri_sp1_cat.add("None");
		ri_sp1_cat.add("Aspart (Novolog)");
		ri_sp1_cat.add("Humulin R u 500");
		ri_sp1_cat.add("Glulisine (Apidra)");
		ri_sp1_cat.add("Lispro (Humalog) u 100");
		ri_sp1_cat.add("Lispro (Humalog) u 200");
		ri_sp1_cat.add("Regular insulin");

	    ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ri_sp1_cat);
	    dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    ri_spinner1.setAdapter(dataAdapter3);
	    
	    
	  //premix insuline
	   
		/*
		 * 
		  	Humalog 75/25    0-200 u x1, x2, x 3, x4
			Humalog 50/50    0-200 u x1, x2, x 3, x4
			Humulin 70/30     0-200 u x1, x2, x 3, x4
           	Novolog 70/30  0-200 u x1, x2, x 3, x4
           	Novolin 70/30    0-200 u x1, x2, x 3, x4

		 */
		List<String> pi_sp1_cat = new ArrayList<String>();
		pi_sp1_cat.add("None");
		pi_sp1_cat.add("Humalog 75/25");
		pi_sp1_cat.add("Humalog 50/50");
		pi_sp1_cat.add("Humulin 70/30");
		pi_sp1_cat.add("Novolog 70/30");
		pi_sp1_cat.add("Novolin 70/30");

	    ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pi_sp1_cat);
	    dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    pi_spinner1.setAdapter(dataAdapter4);
	    
	    
	  //premix insuline
	    ii_spinner1 = (Spinner)findViewById(R.id.ii_spinner1);
	    ii_spinner2 = (Spinner)findViewById(R.id.ii_spinner2);
	    ii_spinner3 = (Spinner)findViewById(R.id.ii_spinner3);
		/*
		 * 
		  	Humalog 75/25    0-200 u x1, x2, x 3, x4
			Humalog 50/50    0-200 u x1, x2, x 3, x4
			Humulin 70/30     0-200 u x1, x2, x 3, x4
           	Novolog 70/30  0-200 u x1, x2, x 3, x4
           	Novolin 70/30    0-200 u x1, x2, x 3, x4

		 */
		List<String> ii_sp1_cat = new ArrayList<String>();
		ii_sp1_cat.add("None");
		ii_sp1_cat.add("Afrezza");

	    ArrayAdapter<String> dataAdapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ii_sp1_cat);
	    dataAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    ii_spinner1.setAdapter(dataAdapter5);
	    
	    
	    
	    
	    
	  //setting values
	    if(isMedicationNotSet){
	    	li_edit1.setText("");
	    	ri_edit1.setText("");
	    	pi_edit1.setText("");
	    	
		    ni_spinner1.setOnItemSelectedListener(this);
		    li_spinner1.setOnItemSelectedListener(this);
		    ri_spinner1.setOnItemSelectedListener(this);
		    pi_spinner1.setOnItemSelectedListener(this);
		    ii_spinner1.setOnItemSelectedListener(this);
	    }else{
	    	writingMedi.setText(medication.writing_medi);
	    	
	    	ni_spinner1.setSelection(medication.cat1[0]);
	    	//ni_spinner2.setSelection(medication.cat1[1]);
	    	//ni_spinner3.setSelection(medication.cat1[2]);
	    	//set_ni_sp2_3_cat(medication.cat1[0],medication.cat1[1],medication.cat1[2]);
	    	
	    	li_spinner1.setSelection(medication.cat2[0]);
	    	li_edit1.setText(medication.cat2[1]+"");
	    	//li_spinner2.setSelection(medication.cat2[2]);
	    	//set_li_sp2_cat(medication.cat2[0],medication.cat2[2]);
	    	
	    	ri_spinner1.setSelection(medication.cat3[0]);
	    	ri_edit1.setText(medication.cat3[1]+"");
	    	//ri_spinner2.setSelection(medication.cat3[2]);
	    	//set_ri_sp2_cat(medication.cat3[0],medication.cat3[2]);
	    	
	    	pi_spinner1.setSelection(medication.cat4[0]);
	    	pi_edit1.setText(medication.cat4[1]+"");
	    	//pi_spinner2.setSelection(medication.cat4[2]);
	    	//set_pi_sp2_cat(medication.cat4[0],medication.cat4[2]);
	    	
	    	ii_spinner1.setSelection(medication.cat5[0]);
	    	//ii_spinner2.setSelection(medication.cat5[1]);
	    	//ii_spinner3.setSelection(medication.cat5[2]);
	    	//set_ii_sp2_3_cat(medication.cat5[0],medication.cat5[1],medication.cat5[2]);
	    	
	    	OnItemSelectedListener tempListener = new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub

					Log.v("ViewId Selected:",view.getId()+" Spinner1 RId:"+R.id.ni_spinner1);
					int spinnerId = parent.getId();
					if(spinnerId == R.id.ni_spinner1){
						Log.v("Item Selected",position+"");
						set_ni_sp2_3_cat(position,medication.cat1[1],medication.cat1[2]);
						ni_spinner1.setOnItemSelectedListener(mia);
					}
					else if(spinnerId == R.id.li_spinner1){
						Log.v("Item Selected",position+"");
						set_li_sp2_cat(position,medication.cat2[2]);
						li_spinner1.setOnItemSelectedListener(mia);
					}else if(spinnerId == R.id.ri_spinner1){
						Log.v("Item Selected",position+"");
						set_ri_sp2_cat(position,medication.cat3[2]);
						 ri_spinner1.setOnItemSelectedListener(mia);
					}else if(spinnerId == R.id.pi_spinner1){
						Log.v("Item Selected",position+"");
						set_pi_sp2_cat(position,medication.cat4[2]);
						pi_spinner1.setOnItemSelectedListener(mia);
					}else if(spinnerId == R.id.ii_spinner1){
						Log.v("Item Selected",position+"");
						set_ii_sp2_3_cat(position,medication.cat5[1],medication.cat5[2]);
						ii_spinner1.setOnItemSelectedListener(mia);
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub
					
				}
			};
		    ni_spinner1.setOnItemSelectedListener(tempListener);
		    li_spinner1.setOnItemSelectedListener(tempListener);
		    ri_spinner1.setOnItemSelectedListener(tempListener);
		    pi_spinner1.setOnItemSelectedListener(tempListener);
		    ii_spinner1.setOnItemSelectedListener(tempListener);
	    }
	    
	    
	    
	    
	}
	
	public void onClickSave(View view) {
		Medication medication = new Medication();
		
		medication.cat1[0] = ni_spinner1.getSelectedItemPosition();
		medication.cat1[1] = ni_spinner2.getSelectedItemPosition();
		medication.cat1[2] = ni_spinner3.getSelectedItemPosition();
		
		medication.cat2[0] = li_spinner1.getSelectedItemPosition();
		try{
			medication.cat2[1] = Integer.parseInt(li_edit1.getText().toString());
		}catch(Exception e){
			medication.cat2[1] = 0;
		}
		medication.cat2[2] = li_spinner2.getSelectedItemPosition();
		
		medication.cat3[0] = ri_spinner1.getSelectedItemPosition();
		try{
			medication.cat3[1] = Integer.parseInt(ri_edit1.getText().toString());
		}catch(Exception e){
			medication.cat3[1] = 0;
		}
		medication.cat3[2] = ri_spinner2.getSelectedItemPosition();
		
		medication.cat4[0] = pi_spinner1.getSelectedItemPosition();
		try{
			medication.cat4[1] = Integer.parseInt(pi_edit1.getText().toString());
		}catch(Exception e){
			medication.cat4[1] = 0;
		}
		medication.cat4[2] = pi_spinner2.getSelectedItemPosition();
		
		medication.cat5[0] = ii_spinner1.getSelectedItemPosition();
		medication.cat5[1] = ii_spinner2.getSelectedItemPosition();
		medication.cat5[2] = ii_spinner3.getSelectedItemPosition();
		
		medication.writing_medi = writingMedi.getText().toString();
		Medication.saveMedication(this, medication);
		
		Intent i = new Intent(this,MedsAndInsulineHomeActivity.class);
		startActivity(i);
	}

	public void set_ni_sp2_3_cat(int sp1_pos, int selected2,int selected3){
		List<String> ni_sp2_cat = new ArrayList<String>();
		List<String> ni_sp3_cat = new ArrayList<String>();
		if(sp1_pos==1){
			ni_sp2_cat.add("30 mg");
			ni_sp3_cat.add("once weekly");
		}else if(sp1_pos==2){
			ni_sp2_cat.add("0.75 mg");
			ni_sp2_cat.add("1.5 mg");
			ni_sp3_cat.add("weekly");
		}else if(sp1_pos==3){
			ni_sp2_cat.add("5 mcg");
			ni_sp2_cat.add("10 mcg");
			ni_sp3_cat.add("x1 per day");
			ni_sp3_cat.add("x2 per day");
		}else if(sp1_pos==4){
			ni_sp2_cat.add("2 mg");
			ni_sp3_cat.add("weekly");
		}else if(sp1_pos==5){
			//0.6 mg, 1.2 mg,1.8 mg x1 per day
			ni_sp2_cat.add("0.6 mg");
			ni_sp2_cat.add("1.2 mg");
			ni_sp2_cat.add("1.8 mg");
			ni_sp3_cat.add("x1 per day");
		}else{
			ni_sp2_cat.add("None");
			ni_sp3_cat.add("None");
		}
	    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ni_sp2_cat);
	    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    ni_spinner2.setAdapter(dataAdapter2);
	    
	    ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ni_sp3_cat);
	    dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    ni_spinner3.setAdapter(dataAdapter3);
	    
	    if(selected2!=-1){
	    	ni_spinner2.setSelection(selected2);
	    }
	    if(selected3!=-1){
	    	ni_spinner3.setSelection(selected3);
	    }
	}
	
	public void set_li_sp2_cat(int sp1_pos, int selected2){
		List<String> li_sp2_cat = new ArrayList<String>();
		if(sp1_pos==1 || sp1_pos==2 || sp1_pos==3 || sp1_pos==4 || sp1_pos==5){
			li_sp2_cat.add("x1 per day");
			li_sp2_cat.add("x2 per day");
		}else{
			li_sp2_cat.add("None");
		}
	    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, li_sp2_cat);
	    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    li_spinner2.setAdapter(dataAdapter2);
	    
	    if(selected2!=-1){
	    	li_spinner2.setSelection(selected2);
	    }
	}
	
	public void set_ri_sp2_cat(int sp1_pos, int selected2){
		List<String> ri_sp2_cat = new ArrayList<String>();
		if(sp1_pos==1 || sp1_pos==2 || sp1_pos==3 || sp1_pos==4 || sp1_pos==5 || sp1_pos==6){
			ri_sp2_cat.add("x1 per day");
			ri_sp2_cat.add("x2 per day");
			ri_sp2_cat.add("x3 per day");
			ri_sp2_cat.add("x4 per day");
		}else{
			ri_sp2_cat.add("None");
		}
	    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ri_sp2_cat);
	    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    ri_spinner2.setAdapter(dataAdapter2);
	    
	    if(selected2!=-1){
	    	ri_spinner2.setSelection(selected2);
	    }
	}
	public void set_pi_sp2_cat(int sp1_pos, int selected2){
		List<String> pi_sp2_cat = new ArrayList<String>();
		if(sp1_pos==1 || sp1_pos==2 || sp1_pos==3 || sp1_pos==4 || sp1_pos==5){
			pi_sp2_cat.add("x1 per day");
			pi_sp2_cat.add("x2 per day");
			pi_sp2_cat.add("x3 per day");
			pi_sp2_cat.add("x4 per day");
		}else{
			pi_sp2_cat.add("None");
		}
	    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pi_sp2_cat);
	    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    pi_spinner2.setAdapter(dataAdapter2);
	    
	    if(selected2!=-1){
	    	pi_spinner2.setSelection(selected2);
	    }
	}
	public void set_ii_sp2_3_cat(int sp1_pos, int selected2, int selected3){
		List<String> ii_sp2_cat = new ArrayList<String>();
		List<String> ii_sp3_cat = new ArrayList<String>();
		if(sp1_pos==1){
			ii_sp2_cat.add("4u");
			ii_sp2_cat.add("8u");
			ii_sp2_cat.add("12u");
			ii_sp3_cat.add("x1 per day");
			ii_sp3_cat.add("x2 per day");
			ii_sp3_cat.add("x3 per day");
		}else{
			ii_sp2_cat.add("None");
			ii_sp3_cat.add("None");
		}
		
		
	    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ii_sp2_cat);
	    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    ii_spinner2.setAdapter(dataAdapter2);
	    
	    ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ii_sp3_cat);
	    dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    ii_spinner3.setAdapter(dataAdapter3);
	    
	    if(selected2!=-1){
	    	ii_spinner2.setSelection(selected2);
	    }
	    if(selected3!=-1){
	    	ii_spinner3.setSelection(selected3);
	    }
	}
	
	
	public void onBackPress(View v){
		onBackPressed();
	}
	@Override
     public void onBackPressed(){
		Intent i = new Intent(this,MedsAndInsulineHomeActivity.class);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.meds_and_insuline, menu);
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
	
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Log.v("ViewId Selected:",view.getId()+" Spinner1 RId:"+R.id.ni_spinner1);
		int spinnerId = parent.getId();
		if(spinnerId == R.id.ni_spinner1){
			Log.v("Item Selected",position+"");
			set_ni_sp2_3_cat(position,-1,-1);//medication.cat1[1],medication.cat1[2]);
		}
		else if(spinnerId == R.id.li_spinner1){
			Log.v("Item Selected",position+"");
			set_li_sp2_cat(position,-1);//,medication.cat2[2]);
		}else if(spinnerId == R.id.ri_spinner1){
			Log.v("Item Selected",position+"");
			set_ri_sp2_cat(position,-1);//,medication.cat3[2]);
		}else if(spinnerId == R.id.pi_spinner1){
			Log.v("Item Selected",position+"");
			set_pi_sp2_cat(position,-1);//,medication.cat4[2]);
		}else if(spinnerId == R.id.ii_spinner1){
			Log.v("Item Selected",position+"");
			set_ii_sp2_3_cat(position,-1,-1);//,medication.cat5[1],medication.cat5[2]);
		}
		
		
		
		
		
		
	}
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
}
