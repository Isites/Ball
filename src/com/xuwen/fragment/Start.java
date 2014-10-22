package com.xuwen.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.xuwen.ball.R;

@SuppressLint("ValidFragment") 
public class Start extends Fragment{
	
	private int w;
	private int h;
	private Activity activity;
	public Start(Activity activity, int w, int h){
		super();
		this.activity = activity;
		this.w = w;
		this.h = h;
	}
		public void onCreate(Bundle savedInstanceState){
			super.onCreate(savedInstanceState);
			
			
		}
		public View onCreateView(LayoutInflater inflater,ViewGroup container,
				Bundle savedInstanceState){
			View view = inflater.inflate(R.layout.start, container,  
    			    false);  
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.start, null);
            
            Button s = (Button) view.findViewById(R.id.start);
            Button share = (Button) view.findViewById(R.id.share);
            share.setOnClickListener(new OnClickListener() {
				
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					showShare();
					
				}
			});
    		s.setOnClickListener(new View.OnClickListener() {

    			public void onClick(View v) {
    				
    				FragmentTransaction transaction = getFragmentManager().beginTransaction();
    	            transaction.replace(R.id.container, new PlayFragment(getActivity(), w, h));
    	            transaction.commit();
    			}
    		});
             return view;
		}
		private void showShare() {
	        ShareSDK.initSDK(activity);
	        OnekeyShare oks = new OnekeyShare();
	        //关闭sso授权
	        oks.disableSSOWhenAuthorize();
	        
	        
	        getCapture();
	        
	        // 分享时Notification的图标和文字
	        oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
	        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
	        oks.setTitle(getString(R.string.share));
	        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
	        oks.setTitleUrl("http://sharesdk.cn");
	        // text是分享文本，所有平台都需要这个字段
	        oks.setText("这个贱萌萌的小游戏'Crazy Bounce'超好玩的，大家一起来玩吧！！！");
	        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
	        oks.setImagePath("/sdcard/share.jpg");
	        // url仅在微信（包括好友和朋友圈）中使用
	        oks.setUrl("http://sharesdk.cn");
	        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
	        oks.setComment("我是测试评论文本");
	        // site是分享此内容的网站名称，仅在QQ空间使用
	        oks.setSite(getString(R.string.app_name));
	        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
	        oks.setSiteUrl("http://sharesdk.cn");

	        // 启动分享GUI
	        oks.show(activity);
	   }
	   private void getCapture(){
		    //1.构建Bitmap   	       
		    Bitmap Bmp = Bitmap.createBitmap( w, h, Config.ARGB_8888 );
		   //2.获取屏幕   
		    View screen = activity.getWindow().getDecorView();    
		    screen.setDrawingCacheEnabled(true);    
		    Bmp = screen.getDrawingCache();
		    
		    File share = new File("/sdcard/share.jpg");
		    if(share.exists()) share.delete();
		    
		    try {
				FileOutputStream fos = new FileOutputStream(share);
				Bmp.compress(Bitmap.CompressFormat.PNG, 90, fos);
				fos.flush();
				fos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   }	
		
	


}
