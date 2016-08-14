package com.whileloop.customuis;

import com.whileloop.smartglucosemanager.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

public class WeekCheckBox extends CheckBox {

	public WeekCheckBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	@Override
    public void setChecked(boolean t){
        if(t)
        {
            this.setBackgroundResource(R.drawable.select);
        }
        else
        {
            this.setBackgroundResource(R.drawable.deselect);
        }
        super.setChecked(t);
    }

}
