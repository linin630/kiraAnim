package com.linin.anim;

import com.linin.kira.anim.KiraAnim3DUnlimited;
import com.linin.kira.anim.KiraAnimFactory;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

public class Rotate3DActivity extends Activity implements OnClickListener{
	private Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11;
	private View view,view2;
	
	private KiraAnim3DUnlimited ka3d,ka3d2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rotate3d);
		init();
		
		ka3d=new KiraAnim3DUnlimited();
		view=findViewById(R.id.view);
		view2=findViewById(R.id.view2);
		View view3=findViewById(R.id.view3);
		View view4=findViewById(R.id.view4);
		ka3d.addView(view,view2,view3,view4);
		ka3d.setCurrentView(0);

		ka3d2=new KiraAnim3DUnlimited();
		ka3d2.addView(findViewById(R.id.ll),findViewById(R.id.ll2),findViewById(R.id.ll3));
		ka3d2.setCurrentView(0);
	}
	
	public void init(){
    	b1=(Button) findViewById(R.id.button1);
    	b2=(Button) findViewById(R.id.button2);
    	b3=(Button) findViewById(R.id.button3);
    	b4=(Button) findViewById(R.id.button4);
    	b5=(Button) findViewById(R.id.button5);
    	b6=(Button) findViewById(R.id.button6);
    	b7=(Button) findViewById(R.id.button7);
    	b8=(Button) findViewById(R.id.button8);
    	b9=(Button) findViewById(R.id.button9);
    	b10=(Button) findViewById(R.id.button10);
    	b11=(Button) findViewById(R.id.button11);
    	b1.setOnClickListener(this);
    	b2.setOnClickListener(this);
    	b3.setOnClickListener(this);
    	b4.setOnClickListener(this);
    	b5.setOnClickListener(this);
    	b6.setOnClickListener(this);
    	b7.setOnClickListener(this);
    	b8.setOnClickListener(this);
    	b9.setOnClickListener(this);
    	b10.setOnClickListener(this);
    	b11.setOnClickListener(this);
    }

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.button1:
			ka3d.nextView();
			break;
		case R.id.button2:
			ka3d.previousView();
			break;
		case R.id.button3:
			ka3d2.nextView();
			break;
		case R.id.button4:
			ka3d2.nextView();
			break;
		case R.id.button5:
			ka3d.setX3D(false);
			ka3d2.setX3D(false);
			break;
		case R.id.button6:
			ka3d.setX3D(true);
			ka3d2.setX3D(true);
			break;
		case R.id.button7:
			break;
		case R.id.button8:
			break;
		case R.id.button9:
			ka3d2.nextView();
			break;
		case R.id.button10:
			break;
		case R.id.button11:
			Animation t1 = new TranslateAnimation(0.1f, -50.0f,0.1f,-50.0f);
			t1.setDuration(500);
			KiraAnimFactory.setView(b11).setAnims(t1,t1,t1).start();
			break;
		}
	}
}
