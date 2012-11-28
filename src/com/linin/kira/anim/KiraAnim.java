package com.linin.kira.anim;

import com.linin.kira.anim.listener.KiraAnimListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * 
 * 主要是控制控件出现、消失的动画，提供了各种出现、消失的方法，可以方便的调用
 * @author linin630@gmail.com
 *
 */
@SuppressLint("HandlerLeak") public class KiraAnim {
	//常量
	public static final int MODE_UP=0,MODE_DOWN=1,MODE_LEFT=2,MODE_RIGHT=3;
	private static final int SET_VISIBLE=0,SET_INVISIBLE=1;
	private static final int END=110;
	private static final int START_ANIM=119;
	
	private Activity act;
	
	private KiraAnimListener listener=null;
	
	private int act_w,act_h;
	
	private boolean isAnim=false,isRunning=false;//是否发生了动画//mode模式专用//isRunning指是否正在动画
	
	public KiraAnim(Activity act){
		this.act=act;
		act_w=act.getWindowManager().getDefaultDisplay().getWidth();
		act_h=act.getWindowManager().getDefaultDisplay().getHeight();
	}
	
	public void dismissAll(int layoutId){
		ViewGroup vg=(ViewGroup) act.findViewById(layoutId);
		for(int i=0;i<vg.getChildCount();i++){
			getView(vg.getChildAt(i).getId()).setVisibility(View.INVISIBLE);
		}
	}
	/**
	 * 
	 * 将控件一个个移动进画面，很炫的效果，只需传入模式、每个动画的时间，要移动的控件id（按顺序）
	 * 为了能有好的效果，传入的控件最后事先设为invisible
	 * @param direction 方向
	 * @param animTime 动画时间
	 * @param spaceTime 每个动画间隔
	 * @param moveId 要发生动画的控件id
	 * @return
	 */
	public boolean moveIn(int direction,int animTime,int spaceTime,int ...moveId){
		int realTime=0;
		for(int id:moveId){
			View v=getView(id);
			TranslateAnimation ta=null;
			switch(direction){
			case MODE_UP:
				ta=new TranslateAnimation(0, 0, act_h-v.getTop(), 0);
				break;
			case MODE_DOWN:
				ta=new TranslateAnimation(0, 0, -(v.getTop()+v.getHeight()), 0);
				break;
			case MODE_LEFT:
				ta=new TranslateAnimation(-(v.getLeft()+v.getWidth()), 0, 0, 0);
				break;
			case MODE_RIGHT:
				ta=new TranslateAnimation(act_w-v.getLeft(), 0, 0, 0);
				break;
			default:
				break;
			}
			ta.setDuration(animTime);
			viewShow(id, realTime);
			startAtTime(id, ta, realTime);
			realTime+=spaceTime;
		}

		listenEnd(realTime);
		return true;
	}

	/**
	 * 
	 * 与moveIn相反，让控件退出画面的酷炫效果
	 * @param direction 方向
	 * @param animTime 动画时间
	 * @param spaceTime 每个动画间隔
	 * @param moveId 要发生动画的控件id
	 * @return
	 */
	public boolean moveOut(int direction,int animTime,int spaceTime,int ...moveId){
		int realTime=0;
		for(int id:moveId){
			View v=getView(id);
			TranslateAnimation ta=null;
			switch(direction){
			case MODE_UP:
				ta=new TranslateAnimation(0, 0, 0, -(v.getTop()+v.getHeight()));
				break;
			case MODE_DOWN:
				ta=new TranslateAnimation(0, 0, 0, act_h-v.getTop());
				break;
			case MODE_LEFT:
				ta=new TranslateAnimation(0, -(v.getLeft()+v.getWidth()), 0, 0);
				break;
			case MODE_RIGHT:
				ta=new TranslateAnimation(0,  act_w-v.getLeft(), 0, 0);
				break;
			default:
				break;
			}
			ta.setDuration(animTime);
			startAtTime(id, ta, realTime);
			realTime+=spaceTime;
			viewDismiss(id, realTime);
		}

		listenEnd(realTime);
		return true;
	}

	/**
	 * 
	 * 传入一个layoutId就能将其子控件全部按顺序显示出来，比较方便的方法
	 * 并且这个方法可以不用事先设置子控件的visible
	 * @param direction 方向
	 * @param animTime 动画时间
	 * @param spaceTime 每个动画间隔
	 * @param layoutId 要发生动画的所有控件的父layout
	 * @param act 传入this就行
	 * @return
	 */
	public boolean moveIn(int direction,int animTime,int spaceTime,int layoutId,Activity act){
		dismissAll(layoutId);
		ViewGroup vg=(ViewGroup) act.findViewById(layoutId);
		int[] moveId=new int[vg.getChildCount()];
		for(int i=0;i<vg.getChildCount();i++){
			moveId[i]=vg.getChildAt(i).getId();
		}
		moveIn(direction, animTime, spaceTime, moveId);
		
		return true;
	}

	/**
	 * 
	 * 传入一个layoutId就能将其子控件全部按顺序抛出画面
	 * @param direction 方向
	 * @param animTime 动画时间
	 * @param spaceTime 每个动画间隔
	 * @param layoutId 要发生动画的所有控件的父layout
	 * @param act 传入this就行
	 * @return
	 */
	public boolean moveOut(int direction,int animTime,int spaceTime,int layoutId,Activity act){
		ViewGroup vg=(ViewGroup) act.findViewById(layoutId);
		int[] moveId=new int[vg.getChildCount()];
		for(int i=0;i<vg.getChildCount();i++){
			moveId[i]=vg.getChildAt(i).getId();
		}
		moveOut(direction, animTime, spaceTime, moveId);
		
		return true;
	}
	

	/**
	 * 
	 * 这个方法是将控件从当前位置移动到指定方向指定距离的
	 * 传入参数：移动控件的id、时间、移动距离、移动方向
	 * @param viewId 要移动的控件id
	 * @param time 动画时间
	 * @param distance 移动距离
	 * @param direction 移动方向
	 * @return
	 */
	public boolean moveView(int viewId,int time,int distance,int direction){
		if(isRunning)return false;
		
		TranslateAnimation ta=null;
		switch(direction){
		case MODE_UP:
			ta=new TranslateAnimation(0, 0, 0, -distance);
			break;
		case MODE_DOWN:
			ta=new TranslateAnimation(0, 0, 0, distance);
			break;
		case MODE_LEFT:
			ta=new TranslateAnimation(0, -distance, 0, 0);
			break;
		case MODE_RIGHT:
			ta=new TranslateAnimation(0, distance, 0, 0);
			break;
		default:
			return false;
		}
		ta.setDuration(time);
		startAtTime(viewId, ta, 0);

		listenEnd(time);
		return true;
	}
	

	/**
	 * 
	 * 使显示控件出现的方法   传入参数：显示控件id、显示延迟时间
	 * @param visibleId
	 * @param time
	 */
	public void viewShow(int visibleId,int time){
		Message msg=new Message();
		msg.what=SET_VISIBLE;
		msg.arg1=visibleId;
		handler.sendMessageDelayed(msg, time);
	}

	/**
	 * 
	 * 使显示控件消失的方法   传入参数：消失控件id、消失延迟时间
	 * @param visibleId
	 * @param time
	 */
	public void viewDismiss(int visibleId,int time){
		Message msg=new Message();
		msg.what=SET_INVISIBLE;
		msg.arg1=visibleId;
		handler.sendMessageDelayed(msg, time);
	}
	

	/**
	 * 
	 * 固定模式，只提供4中固定显示方式；需传入 方向、移动控件id、显示控件id、时间
	 * @param direction 方向（分上下左右四个方向）
	 * @param moveId 要移动的控件id
	 * @param visibleId 打算显示出来的控件id（请用层布局放到底部）
	 * @param time 动画时间
	 * @return
	 */
	public boolean mode(int direction,int moveId,int visibleId,int time){
		if(time<0)return false;
		if(isRunning)return false;//如果正在跑动画，不执行下面方法
		
		View move=getView(moveId);
		View visible=getView(visibleId);
		visible.setVisibility(View.VISIBLE);
		int m_w=move.getWidth(),m_h=move.getHeight(),v_w=visible.getWidth(),v_h=visible.getHeight();
		float m_x=move.getLeft(),m_y=move.getTop(),v_x=visible.getLeft(),v_y=visible.getTop();
		
		TranslateAnimation ta=null;
		switch(direction){
		case MODE_UP:
			if(isAnim){
				ta=new TranslateAnimation(m_x, m_x,  m_y-(m_h-(v_y-m_y)),m_y);
				isAnim=false;
			}else{
				ta=new TranslateAnimation(m_x, m_x, m_y, m_y-(m_h-(v_y-m_y)));
				isAnim=true;
			}
			break;
		case MODE_DOWN:
			if(isAnim){
				ta=new TranslateAnimation(m_x, m_x, v_y+v_h, m_y);
				isAnim=false;
			}else{
				ta=new TranslateAnimation(m_x, m_x, m_y, v_y+v_h);
				isAnim=true;
			}
			break;
		case MODE_LEFT:
			if(isAnim){
				ta=new TranslateAnimation(m_x-(m_w-(v_x-m_x)),m_x,  m_y, m_y);
				isAnim=false;
			}else{
				ta=new TranslateAnimation(m_x, m_x-(m_w-(v_x-m_x)), m_y, m_y);
				isAnim=true;
			}
			break;
		case MODE_RIGHT:
			if(isAnim){
				ta=new TranslateAnimation(v_x+v_w, m_x, m_y, m_y);
				isAnim=false;
			}else{
				ta=new TranslateAnimation(m_x, v_x+v_w, m_y, m_y);
				isAnim=true;
			}
			break;
		default:
			return false;
		}
		ta.setDuration(time);
		ta.setFillAfter(true);
		startAtTime(moveId, ta, 0);
		
		isRunning=true;
		
		listenEnd(time);
		return true;
	}
	
	private View getView(int id){
		View result=act.findViewById(id);
		return result;
	}
	
	private void startAtTime(final int id,final Animation a,int time){
		Message msg=new Message();
		msg.obj=a;
		msg.arg1=id;
		msg.what=START_ANIM;
		handler.sendMessageDelayed(msg, time);
	}

	private boolean bl=true;//下面handler专用
	private Handler handler=new Handler(){
		@SuppressLint("HandlerLeak")
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case SET_VISIBLE:
				getView(msg.arg1).setVisibility(View.VISIBLE);
				break;
			case SET_INVISIBLE:
				getView(msg.arg1).setVisibility(View.INVISIBLE);
				break;
			case START_ANIM:
				View v=getView(msg.arg1);
				Animation a=(Animation) msg.obj;
				v.startAnimation(a);
				break;	
			case END://动画结束后调用的回调方法
				isRunning=false;
				if(listener!=null&&bl){
					bl=listener.onKiraAnimEnd();
				}else{
					bl=!bl;
				}
				break;
			case POPUP_IN:
				break;
			case POPUP_OUT:
				break;
			default:
				break;
			}
		}
	};
	

	/**
	 * 
	 * 设置监听器
	 * @param listener 监听器实例化对象
	 */
	public void setKiraAnimListener(KiraAnimListener listener){
		this.listener=listener;
	}
	//调用监听器方法，传入参数：延迟时间
	private void listenEnd(int time){
		handler.sendEmptyMessageDelayed(END, time);
	}
	
	/**
	 * 以下为更新代码，新增功能：弹出效果、弹回效果。
	 * （暂时做不出效果= =果断放弃，等研究透了Camera再说。。）
	 */
	public final static int POPUP_IN=11,POPUP_OUT=12;
//	public boolean popup(int MODE,int ...popupId){
//		switch(MODE){
//		case POPUP_IN:
//			break;
//		case POPUP_OUT:
//			break;
//		}
//		return true;
//	}
//	public boolean popup(int MODE,int layoutId,Activity act){
//		
//		return true;
//	}
}