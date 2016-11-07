package com.whileloop.smartglucosemanager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class OralActivity extends Activity implements OnItemSelectedListener{
	Spinner om1_spinner1;
	Spinner om1_spinner2;
	Spinner om1_spinner3;
	
	Spinner om2_spinner1;
	Spinner om2_spinner2;
	Spinner om2_spinner3;
	
	Spinner om3_spinner1;
	Spinner om3_spinner2;
	Spinner om3_spinner3;
	
	Spinner om4_spinner1;
	Spinner om4_spinner2;
	Spinner om4_spinner3;
	
	Spinner om5_spinner1;
	Spinner om5_spinner2;
	Spinner om5_spinner3;
	
	EditText writingMedi;
	
	Oral oral;
	OralActivity oa;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_oral);
		oa = this;
		oral = Oral.getOral(this);
		boolean isOralNotSet = false;
		if(oral==null){
			oral = new Oral();
			oral.resetValues();
			isOralNotSet = true;
		}
		
		writingMedi = (EditText)findViewById(R.id.writing_medi_oral);
		//spinners
		om1_spinner1 = (Spinner)findViewById(R.id.om1_spinner1);
		om1_spinner2 = (Spinner)findViewById(R.id.om1_spinner2);
		om1_spinner3 = (Spinner)findViewById(R.id.om1_spinner3);
		
		om2_spinner1 = (Spinner)findViewById(R.id.om2_spinner1);
		om2_spinner2 = (Spinner)findViewById(R.id.om2_spinner2);
		om2_spinner3 = (Spinner)findViewById(R.id.om2_spinner3);
		
		om3_spinner1 = (Spinner)findViewById(R.id.om3_spinner1);
		om3_spinner2 = (Spinner)findViewById(R.id.om3_spinner2);
		om3_spinner3 = (Spinner)findViewById(R.id.om3_spinner3);
		
		om4_spinner1 = (Spinner)findViewById(R.id.om4_spinner1);
		om4_spinner2 = (Spinner)findViewById(R.id.om4_spinner2);
		om4_spinner3 = (Spinner)findViewById(R.id.om4_spinner3);
		
		om5_spinner1 = (Spinner)findViewById(R.id.om5_spinner1);
		om5_spinner2 = (Spinner)findViewById(R.id.om5_spinner2);
		om5_spinner3 = (Spinner)findViewById(R.id.om5_spinner3);
		
		
		//oral medications
		
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

			    ArrayAdapter<String> dataAdapter0 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, om_sp1_cat);
			    dataAdapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    om1_spinner1.setAdapter(dataAdapter0);
			    om2_spinner1.setAdapter(dataAdapter0);
			    om3_spinner1.setAdapter(dataAdapter0);
			    om4_spinner1.setAdapter(dataAdapter0);
			    om5_spinner1.setAdapter(dataAdapter0);
			    
			    
			  //setting values
			    if(isOralNotSet){
			    	om1_spinner1.setOnItemSelectedListener(this);
			    	om2_spinner1.setOnItemSelectedListener(this);
			    	om3_spinner1.setOnItemSelectedListener(this);
			    	om4_spinner1.setOnItemSelectedListener(this);
			    	om5_spinner1.setOnItemSelectedListener(this);
			    }else{
			    	writingMedi.setText(oral.writing_medi);
			    	
			    	om1_spinner1.setSelection(oral.cat0[0]);
			    	
			    	om2_spinner1.setSelection(oral.cat1[0]);
			    	
			    	om3_spinner1.setSelection(oral.cat2[0]);
			    	
			    	om4_spinner1.setSelection(oral.cat3[0]);
			    	
			    	om5_spinner1.setSelection(oral.cat4[0]);

			    	
			    	
			    	OnItemSelectedListener tempListener = new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> parent, View view,
								int position, long id) {
							// TODO Auto-generated method stub

							Log.v("ViewId Selected:",view.getId()+" Spinner1 RId:"+R.id.ni_spinner1);
							int spinnerId = parent.getId();
							if(spinnerId==R.id.om1_spinner1){
								Log.v("Item Selected",position+"");
								set_om_sp2_3_cat(om1_spinner1,om1_spinner2,om1_spinner3,position,oral.cat0[1],oral.cat0[2]);
								om1_spinner1.setOnItemSelectedListener(oa);
							}
							else if(spinnerId == R.id.om2_spinner1){
								Log.v("Item Selected",position+"");
								set_om_sp2_3_cat(om2_spinner1,om2_spinner2,om2_spinner3,position,oral.cat1[1],oral.cat1[2]);
								om2_spinner1.setOnItemSelectedListener(oa);
							}
							else if(spinnerId == R.id.om3_spinner1){
								Log.v("Item Selected",position+"");
								set_om_sp2_3_cat(om3_spinner1,om3_spinner2,om3_spinner3,position,oral.cat2[1],oral.cat2[2]);
								om3_spinner1.setOnItemSelectedListener(oa);
							}else if(spinnerId == R.id.om4_spinner1){
								Log.v("Item Selected",position+"");
								set_om_sp2_3_cat(om4_spinner1,om4_spinner2,om4_spinner3,position,oral.cat3[1],oral.cat3[2]);
								om4_spinner1.setOnItemSelectedListener(oa);
							}else if(spinnerId == R.id.om5_spinner1){
								Log.v("Item Selected",position+"");
								set_om_sp2_3_cat(om5_spinner1,om5_spinner2,om5_spinner3,position,oral.cat4[1],oral.cat4[2]);
								om5_spinner1.setOnItemSelectedListener(oa);
							}
						}

						@Override
						public void onNothingSelected(AdapterView<?> parent) {
							// TODO Auto-generated method stub
							
						}
					};
					om1_spinner1.setOnItemSelectedListener(tempListener);
					om2_spinner1.setOnItemSelectedListener(tempListener);
					om3_spinner1.setOnItemSelectedListener(tempListener);
					om4_spinner1.setOnItemSelectedListener(tempListener);
					om5_spinner1.setOnItemSelectedListener(tempListener);
			    }
	}
	
	public void set_om_sp2_3_cat(Spinner sp1,Spinner sp2,Spinner sp3,int sp1_pos,int selected2,int selected3){
		List<String> om_sp2_cat = new ArrayList<String>();
		List<String> om_sp3_cat = new ArrayList<String>();
		if(sp1_pos==1){
			//25 mg,50 mg, 100 mg,      x1, x2, x3 per day
			om_sp2_cat.add("25 mg");
			om_sp2_cat.add("50 mg");
			om_sp2_cat.add("100 mg");
			
			om_sp3_cat.add("x1 per day");
			om_sp3_cat.add("x2 per day");
			om_sp3_cat.add("x3 per day");
			
		}else if(sp1_pos==2){
			om_sp2_cat.add("100 mg");
			om_sp2_cat.add("250 mg");
			
			om_sp3_cat.add("x1 per day");
		}else if(sp1_pos==3){
			//50/850, 50/1000 			1 x, 2 x per day
			om_sp2_cat.add("50/850");
			om_sp2_cat.add("50/1000");
			
			om_sp3_cat.add("x1 per day");
			om_sp3_cat.add("x2 per day");
		}else if(sp1_pos==4){
			om_sp2_cat.add("5 mg");
			om_sp2_cat.add("10 mg");
			
			om_sp3_cat.add("x1 per day");
		}else if(sp1_pos==5){
			om_sp2_cat.add("80 mg");
			
			om_sp3_cat.add("x1 per day");
			om_sp3_cat.add("x2 per day");
		}else if(sp1_pos==6){
			//2.5 mg, 5 mg 1 x, 2 x per day
			om_sp2_cat.add("2.5 mg");
			om_sp2_cat.add("5 mg");
			om_sp3_cat.add("x1 per day");
			om_sp3_cat.add("x2 per day");
		}else if(sp1_pos==7){
			//1mg, 2 mg, 3 mg, 4 mg              x1, x 2 per day
			om_sp2_cat.add("1 mg");
			om_sp2_cat.add("2 mg");
			om_sp2_cat.add("3 mg");
			om_sp2_cat.add("4 mg");
			
			om_sp3_cat.add("x1 per day");
			om_sp3_cat.add("x2 per day");
		}else if(sp1_pos==8){
			//1.25 mg, 2.5 mg, 5 mg,        x1, x2 per day
			
			om_sp2_cat.add("1.25 mg");
			om_sp2_cat.add("2.5 mg");
			om_sp2_cat.add("5 mg");
			
			om_sp3_cat.add("x1 per day");
			om_sp3_cat.add("x2 per day");
		}else if(sp1_pos==9){
			// 5 mg, 10 mg                         x1, x2 per day
			om_sp2_cat.add("5 mg");
			om_sp2_cat.add("10 mg");
			
			om_sp3_cat.add("x1 per day");
			om_sp3_cat.add("x2 per day");
		}else if(sp1_pos==10){
			//2.5 mg, 5 mg, 10 mg     x1, x2 per day
			om_sp2_cat.add("2.5 mg");
			om_sp2_cat.add("5 mg");
			om_sp2_cat.add("10 mg");
			
			om_sp3_cat.add("x1 per day");
			om_sp3_cat.add("x2 per day");
		}else if(sp1_pos==11){
			//100 mg, 300 mg                                  x1, x2 per day
			om_sp2_cat.add("100 mg");
			om_sp2_cat.add("300 mg");
			
			om_sp3_cat.add("x1 per day");
			om_sp3_cat.add("x2 per day");
		}else if(sp1_pos==12){
			//50/500,50/1000, 150/500, 150/1000   x1, x2 per day               
			om_sp2_cat.add("50/500");
			om_sp2_cat.add("50/1000");
			om_sp2_cat.add("150/500");
			om_sp2_cat.add("150/1000");
			
			om_sp3_cat.add("x1 per day");
			om_sp3_cat.add("x2 per day");
		}else if(sp1_pos==13){
			//10 mg, 25 mg    x1 per day
			om_sp2_cat.add("10 mg");
			om_sp2_cat.add("25 mg");
			
			om_sp3_cat.add("x1 per day");
		}else if(sp1_pos==14){
			//50/500, 50/1000mg           x1,x2  per day
			om_sp2_cat.add("50/500");
			om_sp2_cat.add("50/1000");
			
			om_sp3_cat.add("x1 per day");
			om_sp3_cat.add("x2 per day");
		}else if(sp1_pos==15){
			//50/500 ER, 50/1000 ER,100/1000 ER   1x,2x per day
			om_sp2_cat.add("50/500 ER");
			om_sp2_cat.add("50/1000 ER");
			om_sp2_cat.add("100/1000 ER");
			
			om_sp3_cat.add("x1 per day");
			om_sp3_cat.add("x2 per day");
		}else if(sp1_pos==16){
			//2.5/500, 2.5/850,2.5/1000                 x1,x 2 per day
			om_sp2_cat.add("2.5/500");
			om_sp2_cat.add("2.5/850");
			om_sp2_cat.add("2.5/1000");
			
			om_sp3_cat.add("x1 per day");
			om_sp3_cat.add("x2 per day");
		}else if(sp1_pos==17){
			//2.5/850, 2.5/1000    x 1 x 2 per day
			om_sp2_cat.add("2.5/850");
			om_sp2_cat.add("2.5/1000");
			
			om_sp3_cat.add("x1 per day");
			om_sp3_cat.add("x2 per day");
		}else if(sp1_pos==18){
			//5 mg  x1 per day
			om_sp2_cat.add("5 mg");
			
			om_sp3_cat.add("x1 per day");
		}else if(sp1_pos==19){
			//500 mg, 850 mg,1000 mg      x1, x2 per day
			om_sp2_cat.add("500 mg");
			om_sp2_cat.add("850 mg");
			om_sp2_cat.add("1000 mg");
			
			om_sp3_cat.add("x1 per day");
			om_sp3_cat.add("x2 per day");
		}else if(sp1_pos==20){
			//500 mg, 750 mg, 1000 mg        x1, x2 per day
			om_sp2_cat.add("500 mg");
			om_sp2_cat.add("750 mg");
			om_sp2_cat.add("1000 mg");
			
			om_sp3_cat.add("x1 per day");
			om_sp3_cat.add("x2 per day");
		}else if(sp1_pos==21){
			//15 mg, 30 mg, 45 mg        x1 x2  per day
			om_sp2_cat.add("15 mg");
			om_sp2_cat.add("30 mg");
			om_sp2_cat.add("45 mg");
			
			om_sp3_cat.add("x1 per day");
			om_sp3_cat.add("x2 per day");
		}else if(sp1_pos==22){
			//2mg, 4 mg, 8 mg      x1, x 2 per day
			om_sp2_cat.add("2 mg");
			om_sp2_cat.add("4 mg");
			om_sp2_cat.add("8 mg");
			
			om_sp3_cat.add("x1 per day");
			om_sp3_cat.add("x2 per day");
		}else if(sp1_pos==23){
			//0.5 mg, 1 mg, 2 mg     x1, x 2, x 3 per day
			om_sp2_cat.add("0.5 mg");
			om_sp2_cat.add("1 mg");
			om_sp2_cat.add("2 mg");
			
			om_sp3_cat.add("x1 per day");
			om_sp3_cat.add("x2 per day");
			om_sp3_cat.add("x3 per day");
		}else if(sp1_pos==24){
			//2.5 mg, 5 mg    x1 per day
			om_sp2_cat.add("2.5 mg");
			om_sp2_cat.add("5 mg");
			
			om_sp3_cat.add("x1 per day");
		}else if(sp1_pos==25){
			//25 mg, 50 mg, 100 mg x 1 per day
			om_sp2_cat.add("25 mg");
			om_sp2_cat.add("50 mg");
			om_sp2_cat.add("100 mg");
			
			om_sp3_cat.add("x1 per day");
		}else if(sp1_pos==26){
			//500 mg x 1 , x 2 day
			om_sp2_cat.add("500 mg");
			
			om_sp3_cat.add("x1 per day");
			om_sp3_cat.add("x2 per day");
		}else if(sp1_pos==27){
			//50 mg x 1 day
			om_sp2_cat.add("50 mg");
			
			om_sp3_cat.add("x1 per day");
		}else if(sp1_pos==28){
			//5/500, 5/1000, 10/500,10/1000  x1 per day
			om_sp2_cat.add("5/500");
			om_sp2_cat.add("5/1000");
			om_sp2_cat.add("10/500");
			om_sp2_cat.add("10/1000");
			
			om_sp3_cat.add("x1 per day");
		}else{
			om_sp2_cat.add("None");
			om_sp3_cat.add("None");
		}
	    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, om_sp2_cat);
	    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    sp2.setAdapter(dataAdapter2);
	    
	    ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, om_sp3_cat);
	    dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    sp3.setAdapter(dataAdapter3);
	    
	    if(selected2!=-1){
	    	sp2.setSelection(selected2);
	    }
	    if(selected3!=-1){
	    	sp3.setSelection(selected3);
	    }
	}
	
	//onclick
	public void onClickSave(View view) {
		Oral oral = new Oral();
		oral.cat0[0] = om1_spinner1.getSelectedItemPosition();
		oral.cat0[1] = om1_spinner2.getSelectedItemPosition();
		oral.cat0[2] = om1_spinner3.getSelectedItemPosition();
		
		oral.cat1[0] = om2_spinner1.getSelectedItemPosition();
		oral.cat1[1] = om2_spinner2.getSelectedItemPosition();
		oral.cat1[2] = om2_spinner3.getSelectedItemPosition();
		
		oral.cat2[0] = om3_spinner1.getSelectedItemPosition();
		oral.cat2[2] = om3_spinner2.getSelectedItemPosition();
		oral.cat2[2] = om3_spinner3.getSelectedItemPosition();
		
		oral.cat3[0] = om4_spinner1.getSelectedItemPosition();
		oral.cat3[0] = om4_spinner2.getSelectedItemPosition();
		oral.cat3[0] = om4_spinner3.getSelectedItemPosition();
		
		oral.cat4[0] = om5_spinner1.getSelectedItemPosition();
		oral.cat4[1] = om5_spinner2.getSelectedItemPosition();
		oral.cat4[2] = om5_spinner3.getSelectedItemPosition();
		
		oral.writing_medi = writingMedi.getText().toString();
		
		Oral.saveOral(this, oral);
		
		Intent i = new Intent(this,MedsAndInsulineHomeActivity.class);
		startActivity(i);
	}
	
	//overrides
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Log.v("ViewId Selected:",view.getId()+" Spinner1 RId:"+R.id.ni_spinner1);
		int spinnerId = parent.getId();
		if(spinnerId==R.id.om1_spinner1){
			Log.v("Item Selected",position+"");
			set_om_sp2_3_cat(om1_spinner1,om1_spinner2,om1_spinner3,position,-1,-1);//medication.cat0[1],medication.cat0[2]);
		}
		else if(spinnerId == R.id.om2_spinner1){
			Log.v("Item Selected",position+"");
			set_om_sp2_3_cat(om2_spinner1,om2_spinner2,om2_spinner3,position,-1,-1);
		}
		else if(spinnerId == R.id.om3_spinner1){
			Log.v("Item Selected",position+"");
			set_om_sp2_3_cat(om3_spinner1,om3_spinner2,om3_spinner3,position,-1,-1);
		}else if(spinnerId == R.id.om4_spinner1){
			Log.v("Item Selected",position+"");
			set_om_sp2_3_cat(om4_spinner1,om4_spinner2,om4_spinner3,position,-1,-1);
		}else if(spinnerId == R.id.om5_spinner1){
			Log.v("Item Selected",position+"");
			set_om_sp2_3_cat(om5_spinner1,om5_spinner2,om5_spinner3,position,-1,-1);
		}
		
		
		
		
		
		
	}
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	//built in methods
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
		getMenuInflater().inflate(R.menu.oral, menu);
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
