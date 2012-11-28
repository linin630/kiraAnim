package com.linin.kira.anim;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;

/**
 * Kira动画工厂
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
	 * 设置放入动画工厂的View
	 * @param view
	 * @return
	 */
	public static KiraAnimFactory setView(View view){
		return new KiraAnimFactory(view);
	}
	/**
	 * 加入动画效果，如果动画效果没有设置时间会同时发生，最好有设置！
	 * @param anim
	 */
	public KiraAnimFactory setAnims(Animation ...anim){
		for(Animation a:anim){
			anims.add(a);
		}
		return this;
	}
	/**
	 * 调用这个方法即可使其View依次发生动画
	 */
	public void start(){
		startDelayed(0);
	}
	/**
	 * 调用这个方法可以延迟time时间后开始依次发生动画
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
