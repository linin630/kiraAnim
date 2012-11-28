package com.linin.anim;

import com.nineoldandroids.animation.ObjectAnimator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ScaleActivity extends Activity implements OnClickListener{
	Button bb;
	View vv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scale);
		bb=(Button) findViewById(R.id.bb);
		bb.setOnClickListener(this);
		vv=findViewById(R.id.rl);
	}
	@Override
	public void onClick(View arg0) {
		ObjectAnimator.ofFloat(bb, "translationX", 0,-50,-50,10,0).setDuration(1000).start();
		ObjectAnimator.ofFloat(bb, "translationY", 0,-50,-50,10,0).setDuration(1000).start();
		ObjectAnimator.ofFloat(bb, "scaleX", 1,1.3f,1.3f,0.9f,1).setDuration(1000).start();
		ObjectAnimator.ofFloat(bb, "scaleY", 1,1.3f,1.3f,0.9f,1).setDuration(1000).start();

		ObjectAnimator.ofFloat(vv, "translationX", 0,0,0,20,0,-30,-30,10,0).setDuration(2000).start();
		ObjectAnimator.ofFloat(vv, "translationY", 0,0,0,20,0,-30,-30,10,0).setDuration(2000).start();
	}
}
