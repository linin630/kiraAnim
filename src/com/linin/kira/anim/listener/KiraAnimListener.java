package com.linin.kira.anim.listener;

public interface KiraAnimListener {

	/**
	 * 返回true的话每次都调用；返回false的话只调用一次（当监听器方法里调用了KiraAnim的动画方法时，会造成死循环，可返回false解决）
	 * @return
	 */
	public boolean onKiraAnimEnd();
}
