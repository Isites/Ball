package com.xuwen.base;

import com.xuwen.ball.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Bar {

	//bar�ĵ�ǰX��λ��
	private int currX;
	//bar�ĵ�ǰY��λ��
	private int currY;
	//bar��ͼƬ��Դ
	private Bitmap bar;
	
	public Bar(Context con,int x,int y){
		//��ʼ��bar�ĳ�ʼλ��
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
	//�ı�bar��λ��,��Ҫ�ı�bar����Ե�ǰλ�õ�ƫ����
	public void setLocation(int fingerX,int fingerY){
		currX = fingerX;
		currY = fingerY;
	}
	
}
