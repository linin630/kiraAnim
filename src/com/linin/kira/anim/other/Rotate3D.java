package com.linin.kira.anim.other;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Rotate3D extends Animation{
	private float mFromDegree,mToDegree,mCenterX,mCenterY;
	private Camera mCamera;

	public Rotate3D(float fromDegree, float toDegree, float centerX, float centerY){
		this.mFromDegree=fromDegree;
		this.mToDegree=toDegree;
		this.mCenterX=centerX;
		this.mCenterY=centerY;
	}
	private boolean isY=true;
	/**
	 * 设置为垂直翻转//再设置一次可变回
	 */
	public Rotate3D setX3D(){
		isY=!isY;
		return this;
	}

	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		// TODO Auto-generated method stub
		super.initialize(width, height, parentWidth, parentHeight);
		mCamera=new Camera();//初始化Camera
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		super.applyTransformation(interpolatedTime, t);
		final float FromDegree = mFromDegree;   
		float degrees = FromDegree + (mToDegree - mFromDegree)   
				* interpolatedTime;   
		final float centerX = mCenterX;   
		final float centerY = mCenterY;   
		final Matrix matrix = t.getMatrix();   

		if (degrees <= -76.0f) {   
			degrees = -90.0f;   
			mCamera.save();   
			if(isY){
				mCamera.rotateY(degrees);   
			}else{
				mCamera.rotateX(degrees);   
			}
			mCamera.getMatrix(matrix);   
			mCamera.restore();   
		} else if(degrees >=76.0f){   
			degrees = 90.0f;   
			mCamera.save();   
			if(isY){
				mCamera.rotateY(degrees);   
			}else{
				mCamera.rotateX(degrees);   
			}  
			mCamera.getMatrix(matrix);   
			mCamera.restore();   
		}else{   
			mCamera.save();   
			//这里很重要哦。   
			if(isY){
				mCamera.translate(0, 0, centerX);   
				mCamera.rotateY(degrees);   
				mCamera.translate(0, 0, -centerX);  
			}else{
				mCamera.translate(0, 0, centerY); 
				mCamera.rotateX(degrees);   
				mCamera.translate(0, 0, -centerY);  
			} 
			mCamera.getMatrix(matrix);   
			mCamera.restore();   
		}   

		matrix.preTranslate(-centerX, -centerY);   
		matrix.postTranslate(centerX, centerY);   
	}
}
