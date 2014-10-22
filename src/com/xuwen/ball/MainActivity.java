package com.xuwen.ball;

import com.xuwen.fragment.Start;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;

//小球的一些属性，罗列的比较少，如果需要修改，请到该属性中修改


public class MainActivity extends FragmentActivity {
    
	
	
	private FragmentManager manager;
	private FragmentTransaction trans;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		

		DisplayMetrics ds = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(ds);
		
		int w = ds.widthPixels;
		int h = ds.heightPixels;
		manager = getSupportFragmentManager();
		trans = manager.beginTransaction();
		trans.replace(R.id.container, new Start(this,w,h)).commit();
		
	}
	

}
