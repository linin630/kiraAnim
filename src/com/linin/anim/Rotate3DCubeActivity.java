package com.linin.anim;

import com.linin.kira.anim.KiraAnim3DUnlimited;
import com.linin.kira.anim.other.AnimatorProxy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Rotate3DCubeActivity extends Activity implements OnClickListener{
	private Button up,down,left,right;
	private View v1,v2,v3,v4,v5,v6;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rotate_cube);
		init();
		
		ka3d=new KiraAnim3DUnlimited();
		ka3d.addView(v1,v2,v3,v4,v5,v6);
		ka3d.setCurrentView(0);//设置当前View，必须设置
		ka3d.setDuration(500);//默认1000
	}
	private KiraAnim3DUnlimited ka3d;
	public void init(){
		up=(Button) findViewById(R.id.up);
		down=(Button) findViewById(R.id.down);
		left=(Button) findViewById(R.id.left);
		right=(Button) findViewById(R.id.right);
		up.setOnClickListener(this);
		down.setOnClickListener(this);
		left.setOnClickListener(this);
		right.setOnClickListener(this);
		v1=findViewById(R.id.v1);
		v2=findViewById(R.id.v2);
		v3=findViewById(R.id.v3);
		v4=findViewById(R.id.v4);
		v5=findViewById(R.id.v5);
		v6=findViewById(R.id.v6);
	}
	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.up:
			ka3d.topView();
			break;
		case R.id.down:
			ka3d.bottomView();
			break;
		case R.id.left:
			ka3d.previousView();
			break;
		case R.id.right:
			ka3d.nextView();
			break;
		}
	}
}
