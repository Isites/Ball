package com.xuwen.base;

import java.util.Random;

import com.xuwen.ball.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Ball {

	// 小球当前的x轴位置，相对屏幕的左上角
	private int currX;
	// 小球当前的Y轴的位置，相对屏幕的右上角
	private int currY;
	// 主要用来描述小球的方向,1表示正方向，-1表示负方向
	private int direX;
	// 主要用来描述小球的方向,1表示正方向，-1表示负方向
	private int direY;
	// 小球的外形，界面上面的显示
	private Bitmap ball;
	private boolean isCrashed = false;
	//用来和isCrashed结合使用判断小球可以再次相撞的条件
	private int distance = -1;
	private Context context;

	public Ball(Context con) {
		context = con;
		// 初始产生产生小球的位置
		currY = 0;
		// 在x轴上的位置随机产生,默认范围为150，避免超出屏幕外面
		Random r = new Random();
		currX = (int) r.nextInt(150);
		boolean b = r.nextBoolean();
		// 小球的方向随机产生
		if(b) direX = 1;
		else direX = -1;
		direY = 1;
		ball = Bitmap.createBitmap(BitmapFactory.decodeResource(
				con.getResources(), R.drawable.ball));
	}

	// 产生随机数的范围，小球初始出现的x轴的位置
	public Ball(Context con, int width) {
		context = con;
		// 初始产生产生小球的位置
		currY = 0;
		// 在x轴上的位置随机产生,默认范围为150，避免超出屏幕外面
		Random r = new Random();
		currX = (int) r.nextInt(width);
		boolean b = r.nextBoolean();
		// 小球的方向随机产生
		if(b) direX = 1;
		else direX = -1;
		direY = 1;

		ball = Bitmap.createBitmap(BitmapFactory.decodeResource(
				con.getResources(), R.drawable.ball));
	}

	
	public boolean getIsCrashed(){
		return isCrashed;
	}
	
	public Bitmap getBall() {
		return ball;
	}

	public int getCurrX() {
		return currX;
	}

	public int getCurrY() {
		return currY;
	}

	public int getDireX() {
		return direX;
	}

	public int getDireY() {
		return direY;
	}
	
	public int getScale(){
		return ball.getWidth();
	}

	public void setdireX(int dire) {
		direX *= dire;
	}

	public void setdireY(int dire) {
		direY *= dire;
	}

	public void setOffest(int offest) {
		currX += offest * direX;
		currY += offest * direY;
		
		if(isCrashed){
			
			if((distance += offest) > getBallWidth()){
				setIsCrashed(false);
			}
		}
		
		
	}
	
	public void setIsCrashed(boolean isCrash){
		
		if(isCrash) ball = Bitmap.createBitmap(BitmapFactory.decodeResource(
				context.getResources(), R.drawable.ball_shocked));
		else ball = Bitmap.createBitmap(BitmapFactory.decodeResource(
				context.getResources(), R.drawable.ball));
		
		isCrashed = isCrash;
		distance = 0;
	}
    //在Y轴上改变小球的绝对方向
	//只要用1和-1即可
	public void setAbsDireY(int dire){
		direY = dire;
	}
	public void setAbsDireX(int dire){
		direX = dire;
	}
	
	public int getBallWidth() {
		return ball.getWidth();
	}

	public int getBallHeight() {
		return ball.getHeight();
	}

}
