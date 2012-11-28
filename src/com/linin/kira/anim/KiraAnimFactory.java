package com.linin.kira.anim;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;

/**
 * Kira��������
 * @author linin630@gmail.com
 *
 */
@SuppressLint("HandlerLeak") public class KiraAnimFactory {
	private View mView;
	private ArrayList<Animation> anims;
	public KiraAnimFactory(View v){
		anims=new ArrayList<Animation>();
		mView = v;
	}
	/**
	 * ���÷��붯��������View
	 * @param view
	 * @return
	 */
	public static KiraAnimFactory setView(View view){
		return new KiraAnimFactory(view);
	}
	/**
	 * ���붯��Ч�����������Ч��û������ʱ���ͬʱ��������������ã�
	 * @param anim
	 */
	public KiraAnimFactory setAnims(Animation ...anim){
		for(Animation a:anim){
			anims.add(a);
		}
		return this;
	}
	/**
	 * ���������������ʹ��View���η�������
	 */
	public void start(){
		startDelayed(0);
	}
	/**
	 * ����������������ӳ�timeʱ���ʼ���η�������
	 * @param time
	 */
	public void startDelayed(int time){
		int animIndex=0;
		for(Animation a:anims){
			mHandler.sendEmptyMessageDelayed(animIndex, (long) time);
			time+=a.getDuration();
			animIndex++;
		}
	}
	
	private Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			mView.startAnimation(anims.get(msg.what));
		}
	};
}
