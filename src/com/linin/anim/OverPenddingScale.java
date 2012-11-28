package com.linin.anim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class OverPenddingScale extends Activity implements OnClickListener{
	
	Button b1,b2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.over_scale);
		init();
	}
	
	public void init(){
    	b1=(Button) findViewById(R.id.ob1);
    	b2=(Button) findViewById(R.id.ob2);
    	b1.setOnClickListener(this);
    	b2.setOnClickListener(this);
    }

	public void onClick(View v) {
		startActivity(new Intent(this,OverPenddingScale.class));
		switch(v.getId()){
		case R.id.ob1:
			overridePendingTransition(R.anim.in_scale_y, R.anim.out_scale_y);
			break;
		case R.id.ob2:
			overridePendingTransition(R.anim.in_scale_x, R.anim.out_scale_x);
			break;
		}
		finish();
	}
}
