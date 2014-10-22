package com.xuwen.base;

import java.util.Random;

import com.xuwen.ball.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Ball {

	// С��ǰ��x��λ�ã������Ļ�����Ͻ�
	private int currX;
	// С��ǰ��Y���λ�ã������Ļ�����Ͻ�
	private int currY;
	// ��Ҫ��������С��ķ���,1��ʾ������-1��ʾ������
	private int direX;
	// ��Ҫ��������С��ķ���,1��ʾ������-1��ʾ������
	private int direY;
	// С������Σ������������ʾ
	private Bitmap ball;
	private boolean isCrashed = false;
	//������isCrashed���ʹ���ж�С������ٴ���ײ������
	private int distance = -1;
	private Context context;

	public Ball(Context con) {
		context = con;
		// ��ʼ��������С���λ��
		currY = 0;
		// ��x���ϵ�λ���������,Ĭ�Ϸ�ΧΪ150�����ⳬ����Ļ����
		Random r = new Random();
		currX = (int) r.nextInt(150);
		boolean b = r.nextBoolean();
		// С��ķ����������
		if(b) direX = 1;
		else direX = -1;
		direY = 1;
		ball = Bitmap.createBitmap(BitmapFactory.decodeResource(
				con.getResources(), R.drawable.ball));
	}

	// ����������ķ�Χ��С���ʼ���ֵ�x���λ��
	public Ball(Context con, int width) {
		context = con;
		// ��ʼ��������С���λ��
		currY = 0;
		// ��x���ϵ�λ���������,Ĭ�Ϸ�ΧΪ150�����ⳬ����Ļ����
		Random r = new Random();
		currX = (int) r.nextInt(width);
		boolean b = r.nextBoolean();
		// С��ķ����������
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
    //��Y���ϸı�С��ľ��Է���
	//ֻҪ��1��-1����
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
