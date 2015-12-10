package com.ganesh.friendsstream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		mHandler.sendEmptyMessageDelayed(1, 5000);
	}

	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				mHandler.sendEmptyMessageDelayed(1, 5000);

				break;
			case 1:
				Intent i = null;
				i = new Intent(SplashScreen.this, MainActivity.class);
				startActivity(i);
				finish();
				break;
			}
		};
	};
}
