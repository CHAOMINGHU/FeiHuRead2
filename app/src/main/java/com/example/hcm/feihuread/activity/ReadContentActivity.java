package com.example.hcm.feihuread.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hcm.feihuread.R;
import com.example.hcm.feihuread.data.GetChapterData;
/*
* 1.ReadContent 需要获得两个数据(1.文本内容  2.下一章的超链接)
* 2.默认加载第一章后，ScrollView滑动到底部，将下一章的超链接传进getChapterData，用StringBulider将字符串拼接展示作为新的文本内容，返回第一步
*
* */
public class ReadContentActivity extends Activity implements View.OnTouchListener
{
	private String textData;
	private TextView textContent;
	private String url;
	private String nextUrl;
	private ScrollView scoll;
	View.OnTouchListener onTouchListener;
	private View v1,v2;
	View childView ;
    Handler handler=new Handler(new Handler.Callback()
	{

		@Override
		public boolean handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case 1:
				textData=msg.getData().getString("tc");
				textContent.setText(textData);
				break;

			default:
				break;
			}
			return false;
		}
	});

	GetChapterData data=new GetChapterData(this,url);
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//初始化标题、布局、视图id、
		initByTitleBar();
		setContentView(R.layout.readcentent);
		initIdView();
		initOnTouchListener();
		getIntentData();
		getReadContent();
	
	}

	private void getIntentData() {
		Intent intent=getIntent();
		nextUrl=intent.getStringExtra("a");
	}


	private void initOnTouchListener() {
		 onTouchListener=new View.OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_UP:
						if (childView  != null && childView .getMeasuredHeight() <= scoll.getScrollY() + scoll.getHeight()) {

							Toast.makeText(ReadContentActivity.this,"下面没有内容",Toast.LENGTH_LONG).show();

						} else if (scoll.getScrollY() == 0) {
							Toast.makeText(ReadContentActivity.this,"上面没有内容",Toast.LENGTH_LONG).show();
						}
						break;
				}
				return false;
			}
		};
	}

	private void initIdView() {
		textContent=(TextView) this.findViewById(R.id.read_content);
		scoll=(ScrollView) findViewById(R.id.scoll);
		v1=findViewById(R.id.v1);
		v2=findViewById(R.id.v2);
		childView = 	scoll.getChildAt(0);
		scoll.setOnTouchListener(onTouchListener);
	}

	private void getReadContent()
	{
		GetChapterData data=new GetChapterData(this,nextUrl);
		data.getDataResult(new GetChapterData.MyResult()
		{

			@Override
			public void onFail(boolean isFail)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void getResult(String txtData, String txtChapter, boolean isFail,
					int time)
			{
				// TODO Auto-generated method stub
				Message msg=handler.obtainMessage();
				Bundle bundle=new Bundle();
				bundle.putString("tc", txtData);
				msg.setData(bundle);
				msg.what=1;
				handler.sendMessage(msg);

			}

			@Override
			public void getProgress(int i)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void getChapters(String data)
			{
				// TODO Auto-generated method stub
				
			}
		});
	}
	/*
	 * ��ʼ��״̬��
	 */
	private void initByTitleBar()
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
		{
			WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
			localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
		}
		int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN; // ����ȫ������ //
		Window window = getWindow();
		requestWindowFeature(Window.FEATURE_NO_TITLE); // ���ر�����
		window.setFlags(flag, flag); // ���õ�ǰ����Ϊȫ����ʾ
	}


	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		float screenWidth = dm.widthPixels;
		float screenHight = dm.heightPixels;
		float mMiddleX = screenWidth / 2;
		float mMiddleY = screenHight / 2;
		float x = motionEvent.getX();
		float y = motionEvent.getY();
		if (x > mMiddleX / 3 * 2 && x < (screenWidth - mMiddleX / 3 * 2)
				&& y > mMiddleY / 2
				&& y < (screenHight - mMiddleY / 2)
				) {
			if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

				return false;
			}
		}
		return false;
	}
}
