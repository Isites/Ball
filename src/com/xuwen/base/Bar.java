package com.xuwen.base;

import com.xuwen.ball.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Bar {

	//bar的当前X轴位置
	private int currX;
	//bar的当前Y轴位置
	private int currY;
	//bar的图片资源
	private Bitmap bar;
	
	public Bar(Context con,int x,int y){
		//初始化bar的初始位置
		currX = x;
		currY = y;
		bar = Bitmap.createBitmap(BitmapFactory.decodeResource(
				con.getResources(), R.drawable.bar));
	}
	public int getCurrX(){
		return currX;
	}
	public int getCurrY(){
		return currY;
	}
	public Bitmap getBar(){
		return bar;
	}
	public int getBarWidth(){
		return bar.getWidth();
	}
	public int getBarHeight(){
		return bar.getHeight();
	}
	//改变bar的位置,主要改变bar的相对当前位置的偏移量
	public void setLocation(int fingerX,int fingerY){
		currX = fingerX;
		currY = fingerY;
	}
	
}
