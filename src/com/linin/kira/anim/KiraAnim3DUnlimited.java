package com.linin.kira.anim;

import java.util.ArrayList;

import com.linin.kira.anim.other.Rotate3D;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;

/**
 * 3D无限走廊！名字好酷！
 * @author linin630@gmail.com
 *
 */
@SuppressLint("HandlerLeak") public class KiraAnim3DUnlimited {
	private ArrayList<View> views;
	private int currentView;
	
	public KiraAnim3DUnlimited(){
		views=new ArrayList<View>();
		setCurrentView(0);//默认一开始显示第一个view
		setDuration(1000);//默认动画时间一秒
	}
	
	/**
	 * 添加view到这里
	 * @param view
	 */
	public void addView(View view){
		views.add(view);
	}
	public void addView(View ...view){
		for(View v:view){
			views.add(v);
		}
	}
	
	/**
	 * 设置当前显示第几个view，默认0
	 * @param index
	 */
	public void setCurrentView(int index){
		currentView=index;
		init(currentView);
	}
	
	/**
	 * 设置每个动画的播放时间
	 * @param time
	 */
	public void setDuration(int time){
		this.duration=time;
	}
	private int duration;
	private Rotate3D getAnim(View v,int what){
		Rotate3D rotate=null;
		switch(what){
		case LEFT_IN:
			rotate=new Rotate3D(-90, 0, v.getWidth()/2, v.getHeight()/2);
			break;
		case RIGHT_OUT:
			rotate=new Rotate3D(0, 90, v.getWidth()/2, v.getHeight()/2);
			break;
		case RIGHT_IN:
			rotate=new Rotate3D(90, 0, v.getWidth()/2, v.getHeight()/2);
			break;
		case LEFT_OUT:
			rotate=new Rotate3D(0, -90, v.getWidth()/2, v.getHeight()/2);
			break;
		case TOP_IN:
			rotate=new Rotate3D(-90, 0, v.getWidth()/2, v.getHeight()/2).setX3D();
			break;
		case BOTTOM_OUT:
			rotate=new Rotate3D(0, 90, v.getWidth()/2, v.getHeight()/2).setX3D();
			break;
		case BOTTOM_IN:
			rotate=new Rotate3D(90, 0, v.getWidth()/2, v.getHeight()/2).setX3D();
			break;
		case TOP_OUT:
			rotate=new Rotate3D(0, -90, v.getWidth()/2, v.getHeight()/2).setX3D();
			break;
		}
		rotate.setFillAfter(true);
		rotate.setDuration(duration);
		return rotate;
	}
	private final static int LEFT_IN=0,RIGHT_OUT=1,RIGHT_IN=2,LEFT_OUT=3;
	private final static int TOP_IN=4,BOTTOM_OUT=5,BOTTOM_IN=6,TOP_OUT=7;
	
	public void changeView(int index){
		//比较难先放一边
	}
	
	//初始化界面，把views中的一个view设为可见，其余不可见
	private void init(int view){
		for(View v:views){
			v.setVisibility(View.INVISIBLE);
		}
		if(views.size()>0){
			views.get(view).setVisibility(View.VISIBLE);
		}
	}
	
	public void setX3D(boolean isY){
		this.isY=isY; 
	}
	private boolean isY=true;
	private boolean isAniming=false;
	private final static int CHANGE_ISANIMING=0;
	/**
	 * 动画效果到下一个view
	 */
	public void nextView(){
		if(isAniming)return ;
		
		int index=currentView+1;
		if(index>=views.size())index=0;
		
		View curView=views.get(currentView);
		View nextView=views.get(index);
		currentView=index;

		nextView.setVisibility(View.VISIBLE);
		if(isY){
			curView.startAnimation(getAnim(curView,LEFT_OUT));//当前view转不见
			nextView.startAnimation(getAnim(nextView,RIGHT_IN));//下一个view转出来
		}else{
			curView.startAnimation(getAnim(curView,TOP_OUT));
			nextView.startAnimation(getAnim(nextView,BOTTOM_IN));
		}
		
		setAfterAnim(curView,nextView);
	}
	/**
	 * 动画效果到上一个view
	 */
	public void previousView(){
		if(isAniming)return ;
		
		int index=currentView-1;
		if(index<0)index=views.size()-1;
		
		View curView=views.get(currentView);
		View preView=views.get(index);
		currentView=index;
		
		preView.setVisibility(View.VISIBLE);
		if(isY){
			curView.startAnimation(getAnim(curView,RIGHT_OUT));//当前view转不见
			preView.startAnimation(getAnim(preView,LEFT_IN));//下一个view转出来
		}else{
			curView.startAnimation(getAnim(curView,BOTTOM_OUT));
			preView.startAnimation(getAnim(preView,TOP_IN));
		}
		
		setAfterAnim(curView,preView);
	}
	/**
	 * 不用设置垂直即可下翻
	 */
	public void topView(){
		setX3D(false);
		nextView();
		setX3D(true);
	}
	/**
	 * 不用设置垂直即可上翻
	 */
	public void bottomView(){
		setX3D(false);
		previousView();
		setX3D(true);
	}
	
	
	private void setAfterAnim(View v,View newView){
		mHandler.sendEmptyMessage(CHANGE_ISANIMING);//这句跟下一句是定时改变isAniming值的
		mHandler.sendEmptyMessageDelayed(CHANGE_ISANIMING, duration);
		
		SeeAllView(newView);
		Message msg=new Message();
		msg.obj=v;
		msg.what=GONE_ALL_VIEW;
		mHandler.sendMessageDelayed(msg, duration);
	}
	private void GoneAllView(View v){
		try{
			ViewGroup vg=(ViewGroup) v;
			for(int i=0;i<vg.getChildCount();i++){
				vg.getChildAt(i).setVisibility(View.GONE);
			}
		}catch(Exception e){
		}
	}
	private void SeeAllView(View v){
		try{
			ViewGroup vg=(ViewGroup) v;
			for(int i=0;i<vg.getChildCount();i++){
				vg.getChildAt(i).setVisibility(View.VISIBLE);
			}
		}catch(Exception e){
		}
	}
	private final static int GONE_ALL_VIEW=1;
	
	
	
	private Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg){
			switch(msg.what){
			case CHANGE_ISANIMING:
				isAniming=!isAniming;
				break;
			case GONE_ALL_VIEW:
				GoneAllView((View) msg.obj);
				break;
			}
		}
	};
}
