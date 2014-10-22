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
	//当两小球的距离小于20时,认为两球相碰
		//private final int  CRASH_BALL = 20;
		//当距离边界的距离小于等于10时认为相撞
		//此数值可以更改，可以通过获取图片高宽获得
		private final int CRASH_BORDER;
		//小球的移动速度,在X轴和Y轴上的移动距离均为这么多
		private int v;
		//产生小球的时间间隔
		private int newballtime;
		//移动小球的时间间隔
		private int move_timeGap;
		
		//用来记录产生了多少小球，方便遍历
		private ArrayList<Ball> ballNum = new ArrayList<Ball>();
		//显示小球的画布，surfaceview
		private SurfaceView game_interface;
		private SurfaceHolder sfh;
		
		private Timer ball_produce;
		private Factory task;
		//随机产生小球的计数
		private int count = 100;
		
		//构造函数的参数
		private Activity activity;
		//屏幕的宽度和高度
		private int w,h;
		//返回给Activity的view
		private View view;
		//定义一个Bar对象
		private Bar bar;
		private boolean isGameing = true;
		private boolean isStop = false;
		//游戏进行秒数
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
			
			//设置背景透明 	
			game_interface.setZOrderOnTop(true);
			sfh.setFormat(PixelFormat.TRANSLUCENT);
			Random r = new Random();
			v = r.nextInt(5)+5;
			newballtime = 0;//表示1秒
			move_timeGap = 10;
			//注意时间和速度的搭配，否则易出现抖动效果或则闪烁
			//抖动和闪烁效果，需要注意。。。希望可以有更好的方法。。。。
			
			//实例化bar对象
			bar = new Bar(activity, w/2-100, h/2);
			
			ball_produce = new Timer();
			task = new Factory();
			//启动开始运行
			ball_produce.schedule(task, newballtime, move_timeGap);
			pauseBitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(
					activity.getResources(), R.drawable.pause));
						
		}
		private void changeDireX(Ball b){
			//如果靠近右边屏幕反向                                                                               -1在此处只是表示反向，不表方向
	          if(w-b.getCurrX() <= CRASH_BORDER) b.setAbsDireX(-1);
	          //若果靠近左边屏幕且方向为负，则反向
	          if(b.getCurrX() <= 0 && b.getDireX() == -1) b.setAbsDireX(1);
		}
		private void changeDireY(Ball b){
			if(Math.abs(h-b.getCurrY()) <= CRASH_BORDER) b.setAbsDireY(-1);
			if(b.getCurrY() <= 0 ) b.setAbsDireY(1);
			
		}
		
		
		private void mPaint(ArrayList<Ball> ball){


			for(int i = 0; i < ball.size()-1; i++){
				//以下为小球的反弹效果
				//ball.get(i).setOffest(v);
				if(!ball.get(i).getIsCrashed()){
				for(int j = i; j <ball.size(); j++ ){
					if(j+1 <ball.size()){
						if(Math.abs(ball.get(i).getCurrY() - ball.get(j+1).getCurrY()) <= CRASH_BORDER
								&&Math.abs(ball.get(i).getCurrX() - ball.get(j+1).getCurrX()) <= CRASH_BORDER
								&& (!ball.get(j+1).getIsCrashed())){
							
							
                            //X不同Y同
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
				//判断每一个小球和bar之间的距离以及反弹
				//暂时只靠路bar的上面bar的下面的碰撞
				//小球在bar的上面
				
				//先判断小球的反弹效果，在考虑小球和边界的碰撞
				//changeDireX(ball.get(i));changeDireY(ball.get(i));

			}

			Paint p = new Paint();
			Paint p1 = new Paint();
			Typeface font = Typeface.create("楷体", Typeface.BOLD);
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
			//绘制bar的位置
			
			sfh.unlockCanvasAndPost(canvas);
			}
			if(!isGameing){ 
				ball_produce.cancel();
				ballNum.clear();
				
				//死亡后跳转
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
			     //设置bar的位置
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
					
					//用此构造函数时考虑到随机产生的小球可能在最外面引起异常所以。。。宽度减一些
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
