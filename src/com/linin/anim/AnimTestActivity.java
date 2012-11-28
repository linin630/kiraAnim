package com.linin.anim;

import com.linin.kira.anim.KiraAnim;
import com.nineoldandroids.animation.ObjectAnimator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AnimTestActivity extends Activity implements OnClickListener {
	
	Button b1,b2,b3,b4,b5;
	TextView text;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
        init();
        int w=getWindowManager().getDefaultDisplay().getWidth();
        int h=getWindowManager().getDefaultDisplay().getHeight();
        text.setText("screen_w="+w+";screen_h="+h);
        
        KiraAnim ka=new KiraAnim(this);
        ka.moveIn(KiraAnim.MODE_LEFT, 500, 250, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5);
    }
    
    public void init(){
    	b1=(Button) findViewById(R.id.button1);
    	b2=(Button) findViewById(R.id.button2);
    	b3=(Button) findViewById(R.id.button3);
    	b4=(Button) findViewById(R.id.button4);
    	b5=(Button) findViewById(R.id.button5);
    	b1.setOnClickListener(this);
    	b2.setOnClickListener(this);
    	b3.setOnClickListener(this);
    	b4.setOnClickListener(this);
    	b5.setOnClickListener(this);
    	text=(TextView) findViewById(R.id.menutext);
    }

	public void onClick(View v) {
		switch(v.getId()){
		case R.id.button1:
			startActivity(new Intent(this,OverPenddingTranslate.class));
			overridePendingTransition(R.anim.in_change_right, R.anim.out_change_left);
			break;
		case R.id.button2:
			startActivity(new Intent(this,OverPenddingScale.class));
			overridePendingTransition(R.anim.in_change_left, R.anim.out_change_right);
			break;
		case R.id.button3:
			startActivity(new Intent(this,Rotate3DActivity.class));
			break;
		case R.id.button4:
			startActivity(new Intent(this,Rotate3DCubeActivity.class));
			break;
		case R.id.button5:
			startActivity(new Intent(this,ScaleActivity.class));
			break;
		}
	}
}