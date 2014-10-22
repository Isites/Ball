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
	        //�ر�sso��Ȩ
	        oks.disableSSOWhenAuthorize();
	        
	        
	        getCapture();
	        
	        // ����ʱNotification��ͼ�������
	        oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
	        // title���⣬ӡ��ʼǡ����䡢��Ϣ��΢�š���������QQ�ռ�ʹ��
	        oks.setTitle(getString(R.string.share));
	        // titleUrl�Ǳ�����������ӣ�������������QQ�ռ�ʹ��
	        oks.setTitleUrl("http://sharesdk.cn");
	        // text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
	        oks.setText("��������ȵ�С��Ϸ'Crazy Bounce'������ģ����һ������ɣ�����");
	        // imagePath��ͼƬ�ı���·����Linked-In�����ƽ̨��֧�ִ˲���
	        oks.setImagePath("/sdcard/share.jpg");
	        // url����΢�ţ��������Ѻ�����Ȧ����ʹ��
	        oks.setUrl("http://sharesdk.cn");
	        // comment���Ҷ�������������ۣ�������������QQ�ռ�ʹ��
	        oks.setComment("���ǲ��������ı�");
	        // site�Ƿ�������ݵ���վ���ƣ�����QQ�ռ�ʹ��
	        oks.setSite(getString(R.string.app_name));
	        // siteUrl�Ƿ�������ݵ���վ��ַ������QQ�ռ�ʹ��
	        oks.setSiteUrl("http://sharesdk.cn");

	        // ��������GUI
	        oks.show(activity);
	   }
	   private void getCapture(){
		    //1.����Bitmap   	       
		    Bitmap Bmp = Bitmap.createBitmap( w, h, Config.ARGB_8888 );
		   //2.��ȡ��Ļ   
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
