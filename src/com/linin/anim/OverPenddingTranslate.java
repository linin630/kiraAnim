package com.linin.anim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class OverPenddingTranslate extends Activity implements OnClickListener{
	
	Button b1,b2,b3,b4,b5,b6,b7,b8;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.over_translate);
		init();
	}
	
	public void init(){
    	b1=(Button) findViewById(R.id.ob1);
    	b2=(Button) findViewById(R.id.ob2);
    	b3=(Button) findViewById(R.id.ob3);
    	b4=(Button) findViewById(R.id.ob4);
    	b5=(Button) findViewById(R.id.ob5);
    	b6=(Button) findViewById(R.id.ob6);
    	b7=(Button) findViewById(R.id.ob7);
    	b8=(Button) findViewById(R.id.ob8);
    	b1.setOnClickListener(this);
    	b2.setOnClickListener(this);
    	b3.setOnClickListener(this);
    	b4.setOnClickListener(this);
    	b5.setOnClickListener(this);
    	b6.setOnClickListener(this);
    	b7.setOnClickListener(this);
    	b8.setOnClickListener(this);
    }

	public void onClick(View v) {
		startActivity(new Intent(this,OverPenddingTranslate.class));
		switch(v.getId()){
		case R.id.ob1:
			overridePendingTransition(R.anim.in_translate_left, R.anim.stay);
			break;
		case R.id.ob2:
			overridePendingTransition(R.anim.in_translate_right, R.anim.stay);
			break;
		case R.id.ob3:
			overridePendingTransition(R.anim.in_translate_left, R.anim.out_translate_right);
			break;
		case R.id.ob4:
			overridePendingTransition(R.anim.in_translate_right, R.anim.out_translate_left);
			break;
		case R.id.ob5:
			overridePendingTransition(R.anim.in_translate_top, R.anim.stay);
			break;
		case R.id.ob6:
			overridePendingTransition(R.anim.in_translate_bottom, R.anim.stay);
			break;
		case R.id.ob7:
			overridePendingTransition(R.anim.in_translate_bottom, R.anim.out_translate_top);
			break;
		case R.id.ob8:
			overridePendingTransition(R.anim.in_translate_top, R.anim.out_translate_bottom);
			break;
		}
		finish();
	}
}
