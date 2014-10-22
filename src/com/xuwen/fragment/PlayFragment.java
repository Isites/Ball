package com.xuwen.fragment;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.xuwen.ball.R;
import com.xuwen.base.Ball;
import com.xuwen.base.Bar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

@SuppressLint("ValidFragment")
public class PlayFragment extends Fragment{
	//����С��ľ���С��20ʱ,��Ϊ��������
		//private final int  CRASH_BALL = 20;
		//������߽�ľ���С�ڵ���10ʱ��Ϊ��ײ
		//����ֵ���Ը��ģ�����ͨ����ȡͼƬ�߿���
		private final int CRASH_BORDER;
		//С����ƶ��ٶ�,��X���Y���ϵ��ƶ������Ϊ��ô��
		private int v;
		//����С���ʱ����
		private int newballtime;
		//�ƶ�С���ʱ����
		private int move_timeGap;
		
		//������¼�����˶���С�򣬷������
		private ArrayList<Ball> ballNum = new ArrayList<Ball>();
		//��ʾС��Ļ�����surfaceview
		private SurfaceView game_interface;
		private SurfaceHolder sfh;
		
		private Timer ball_produce;
		private Factory task;
		//�������С��ļ���
		private int count = 100;
		
		//���캯���Ĳ���
		private Activity activity;
		//��Ļ�Ŀ�Ⱥ͸߶�
		private int w,h;
		//���ظ�Activity��view
		private View view;
		//����һ��Bar����
		private Bar bar;
		private boolean isGameing = true;
		private boolean isStop = false;
		//��Ϸ��������
		private int second = -1;
		
		private Bitmap pauseBitmap;
		
		public PlayFragment(Activity activity ,int w,int h){
			super();
			CRASH_BORDER = new Ball(activity).getBallWidth();
			this.activity = activity;
			this.w = w;
			this.h = h;
		}
		
		public void onCreate(Bundle savedInstanceState){
			super.onCreate(savedInstanceState);
			init();
			
			view.setOnTouchListener(touch);
		}
		public View onCreateView(LayoutInflater inflater,ViewGroup container,
				Bundle savedInstanceState){
				view.setLayoutParams(new LayoutParams(w,h));
				
			return view;
		}
		
		private void init(){
			
			view = LayoutInflater.from(activity).inflate(R.layout.playfragment, null);
			
			game_interface = (SurfaceView)view.findViewById(R.id.geme_canvas);
			sfh = game_interface.getHolder();
			
			//���ñ���͸�� 	
			game_interface.setZOrderOnTop(true);
			sfh.setFormat(PixelFormat.TRANSLUCENT);
			Random r = new Random();
			v = r.nextInt(5)+5;
			newballtime = 0;//��ʾ1��
			move_timeGap = 10;
			//ע��ʱ����ٶȵĴ��䣬�����׳��ֶ���Ч��������˸
			//��������˸Ч������Ҫע�⡣����ϣ�������и��õķ�����������
			
			//ʵ����bar����
			bar = new Bar(activity, w/2-100, h/2);
			
			ball_produce = new Timer();
			task = new Factory();
			//������ʼ����
			ball_produce.schedule(task, newballtime, move_timeGap);
			pauseBitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(
					activity.getResources(), R.drawable.pause));
						
		}
		private void changeDireX(Ball b){
			//��������ұ���Ļ����                                                                               -1�ڴ˴�ֻ�Ǳ�ʾ���򣬲�����
	          if(w-b.getCurrX() <= CRASH_BORDER) b.setAbsDireX(-1);
	          //�������������Ļ�ҷ���Ϊ��������
	          if(b.getCurrX() <= 0 && b.getDireX() == -1) b.setAbsDireX(1);
		}
		private void changeDireY(Ball b){
			if(Math.abs(h-b.getCurrY()) <= CRASH_BORDER) b.setAbsDireY(-1);
			if(b.getCurrY() <= 0 ) b.setAbsDireY(1);
			
		}
		
		
		private void mPaint(ArrayList<Ball> ball){


			for(int i = 0; i < ball.size()-1; i++){
				//����ΪС��ķ���Ч��
				//ball.get(i).setOffest(v);
				if(!ball.get(i).getIsCrashed()){
				for(int j = i; j <ball.size(); j++ ){
					if(j+1 <ball.size()){
						if(Math.abs(ball.get(i).getCurrY() - ball.get(j+1).getCurrY()) <= CRASH_BORDER
								&&Math.abs(ball.get(i).getCurrX() - ball.get(j+1).getCurrX()) <= CRASH_BORDER
								&& (!ball.get(j+1).getIsCrashed())){
							
							
                            //X��ͬYͬ
	                        if(ball.get(i).getDireX() != ball.get(j+1).getDireX() && 
	                        		ball.get(i).getDireY() == ball.get(j+1).getDireY()){
	                        	ball.get(i).setdireX(-1);
							    ball.get(j+1).setdireX(-1);
							    ball.get(i).setIsCrashed(true);
							    ball.get(j+1).setIsCrashed(true);
	                        }
	                        else if(ball.get(i).getDireX() == ball.get(j+1).getDireX()
	                        		&&ball.get(i).getDireY() != ball.get(j+1).getDireY()){ 
								    ball.get(i).setdireY(-1);
								    ball.get(j+1).setdireY(-1);
								    ball.get(i).setIsCrashed(true);
								    ball.get(j+1).setIsCrashed(true);
		                        }
	                        else{
	                        	    ball.get(i).setdireY(-1);
	                        	    ball.get(i).setdireX(-1);
								    ball.get(j+1).setdireY(-1);
								    ball.get(i).setIsCrashed(true);
								    ball.get(j+1).setIsCrashed(true);
	                        }
	                        ball.get(i).setOffest(v);
	                        ball.get(j+1).setOffest(v);
						}
					}
				}
			    }
				//�ж�ÿһ��С���bar֮��ľ����Լ�����
				//��ʱֻ��·bar������bar���������ײ
				//С����bar������
				
				//���ж�С��ķ���Ч�����ڿ���С��ͱ߽����ײ
				//changeDireX(ball.get(i));changeDireY(ball.get(i));

			}

			Paint p = new Paint();
			Paint p1 = new Paint();
			Typeface font = Typeface.create("����", Typeface.BOLD);
			p1.setColor(Color.WHITE);
			p1.setTypeface(font);
			p1.setTextSize(100);
			
			Canvas canvas = sfh.lockCanvas();
			if(canvas != null){
			canvas.drawColor(Color.TRANSPARENT,Mode.CLEAR);

			for(Ball b : ball){

				if(b.getCurrX() <= (bar.getCurrX()+bar.getBarWidth())
						&& b.getCurrX() >= bar.getCurrX()-b.getBallWidth()){
				  
					if(bar.getCurrY() - b.getCurrY() <= CRASH_BORDER
							&&bar.getCurrY() - b.getCurrY() > 0 ){
						b.setAbsDireY(-1);
					}
					else if(b.getCurrY() - bar.getCurrY() <= bar.getBarHeight()
							&&b.getCurrY() - bar.getCurrY()>0){
						b.setAbsDireY(1);
					}
				}
				changeDireX(b);changeDireY(b);
				b.setOffest(v);
				int currX = b.getCurrX();
				int currY = b.getCurrY();
				canvas.drawText(second+":"+(100-count),w/2-120, h/4, p1);
				
				canvas.drawBitmap(b.getBall(), currX, currY, p);
			    canvas.drawBitmap(bar.getBar(), bar.getCurrX(), bar.getCurrY(),p);
				canvas.drawBitmap(pauseBitmap, w-pauseBitmap.getWidth(), h-pauseBitmap.getHeight(), p);
				
				
				gameOver(b);
			}
			//����bar��λ��
			
			sfh.unlockCanvasAndPost(canvas);
			}
			if(!isGameing){ 
				ball_produce.cancel();
				ballNum.clear();
				
				//��������ת
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
	            transaction.replace(R.id.container, new Restart(this.getActivity(),w,h));
	            transaction.commit();
				
				return;
				
			}
		}
		
		private void gameOver(Ball b){
			if(b.getCurrY() > 3*h/4){
				//ball_produce.cancel();
				//ballNum.clear();
				isGameing = false;
			}
		}
		private void gamePause(MotionEvent e){
			RectF pause = new RectF(w-pauseBitmap.getWidth(), h-pauseBitmap.getHeight(),
					w, h);
			if(pause.contains(e.getRawX(), e.getRawY())){
				if(!isStop){
					isStop = true;
				}
				else{
					isStop = false;
				}
			}
		}
		
private View.OnTouchListener touch = new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				// TODO Auto-generated method stub
				
				 int x=(int)event.getX();
			     int y=(int)event.getY();
			     //����bar��λ��
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						if(isGameing) gamePause(event);
						break;

					}
					if(bar.getCurrY() >= 3*h/4 && y > 3*h/4)
				    	 bar.setLocation(x-75, 3*h/4);
				    else bar.setLocation(x-75, y-120);
			
	
				return true;
			}
		};
		
		
		
		class Factory extends TimerTask {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(!isStop) count--;
				if(count == 0){
					
					//�ô˹��캯��ʱ���ǵ����������С������������������쳣���ԡ�������ȼ�һЩ
					Ball b = new Ball(activity,w - CRASH_BORDER);
					ballNum.add(b);
					count = 100;
					second++;
				}
				
				if(ballNum.size() > 0){
					if(!isStop)
					  mPaint(ballNum);
				}
			}

		}
		
}
