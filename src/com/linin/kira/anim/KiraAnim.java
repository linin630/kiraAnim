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
 * ��Ҫ�ǿ��ƿؼ����֡���ʧ�Ķ������ṩ�˸��ֳ��֡���ʧ�ķ��������Է���ĵ���
 * @author linin630@gmail.com
 *
 */
@SuppressLint("HandlerLeak") public class KiraAnim {
	//����
	public static final int MODE_UP=0,MODE_DOWN=1,MODE_LEFT=2,MODE_RIGHT=3;
	private static final int SET_VISIBLE=0,SET_INVISIBLE=1;
	private static final int END=110;
	private static final int START_ANIM=119;
	
	private Activity act;
	
	private KiraAnimListener listener=null;
	
	private int act_w,act_h;
	
	private boolean isAnim=false,isRunning=false;//�Ƿ����˶���//modeģʽר��//isRunningָ�Ƿ����ڶ���
	
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
	 * ���ؼ�һ�����ƶ������棬���ŵ�Ч����ֻ�贫��ģʽ��ÿ��������ʱ�䣬Ҫ�ƶ��Ŀؼ�id����˳��
	 * Ϊ�����кõ�Ч��������Ŀؼ����������Ϊinvisible
	 * @param direction ����
	 * @param animTime ����ʱ��
	 * @param spaceTime ÿ���������
	 * @param moveId Ҫ���������Ŀؼ�id
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
	 * ��moveIn�෴���ÿؼ��˳�����Ŀ���Ч��
	 * @param direction ����
	 * @param animTime ����ʱ��
	 * @param spaceTime ÿ���������
	 * @param moveId Ҫ���������Ŀؼ�id
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
	 * ����һ��layoutId���ܽ����ӿؼ�ȫ����˳����ʾ�������ȽϷ���ķ���
	 * ��������������Բ������������ӿؼ���visible
	 * @param direction ����
	 * @param animTime ����ʱ��
	 * @param spaceTime ÿ���������
	 * @param layoutId Ҫ�������������пؼ��ĸ�layout
	 * @param act ����this����
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
	 * ����һ��layoutId���ܽ����ӿؼ�ȫ����˳���׳�����
	 * @param direction ����
	 * @param animTime ����ʱ��
	 * @param spaceTime ÿ���������
	 * @param layoutId Ҫ�������������пؼ��ĸ�layout
	 * @param act ����this����
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
	 * ��������ǽ��ؼ��ӵ�ǰλ���ƶ���ָ������ָ�������
	 * ����������ƶ��ؼ���id��ʱ�䡢�ƶ����롢�ƶ�����
	 * @param viewId Ҫ�ƶ��Ŀؼ�id
	 * @param time ����ʱ��
	 * @param distance �ƶ�����
	 * @param direction �ƶ�����
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
	 * ʹ��ʾ�ؼ����ֵķ���   �����������ʾ�ؼ�id����ʾ�ӳ�ʱ��
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
	 * ʹ��ʾ�ؼ���ʧ�ķ���   �����������ʧ�ؼ�id����ʧ�ӳ�ʱ��
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
	 * �̶�ģʽ��ֻ�ṩ4�й̶���ʾ��ʽ���贫�� �����ƶ��ؼ�id����ʾ�ؼ�id��ʱ��
	 * @param direction ���򣨷����������ĸ�����
	 * @param moveId Ҫ�ƶ��Ŀؼ�id
	 * @param visibleId ������ʾ�����Ŀؼ�id�����ò㲼�ַŵ��ײ���
	 * @param time ����ʱ��
	 * @return
	 */
	public boolean mode(int direction,int moveId,int visibleId,int time){
		if(time<0)return false;
		if(isRunning)return false;//��������ܶ�������ִ�����淽��
		
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

	private boolean bl=true;//����handlerר��
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
			case END://������������õĻص�����
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
	 * ���ü�����
	 * @param listener ������ʵ��������
	 */
	public void setKiraAnimListener(KiraAnimListener listener){
		this.listener=listener;
	}
	//���ü���������������������ӳ�ʱ��
	private void listenEnd(int time){
		handler.sendEmptyMessageDelayed(END, time);
	}
	
	/**
	 * ����Ϊ���´��룬�������ܣ�����Ч��������Ч����
	 * ����ʱ������Ч��= =���Ϸ��������о�͸��Camera��˵������
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