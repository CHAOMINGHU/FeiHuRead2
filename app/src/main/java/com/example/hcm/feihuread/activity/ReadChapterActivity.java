/*
package com.example.hcm.feihuread.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hcm.feihuread.R;
import com.example.hcm.feihuread.adapter.BookChapterAdapter;
import com.example.hcm.feihuread.data.GetChapterData;
import com.example.hcm.feihuread.utils.ACache;


public class ReadChapterActivity extends Activity implements AdapterView.OnItemClickListener
{
	private int i;
	@SuppressWarnings("unused")
	private long time;
	@SuppressWarnings("unused")
	private int cacheCount;
	private ACache mCache;
	private TextView txtNumber;
	@SuppressWarnings("unused")
	private TextView txtContent;
	@SuppressWarnings("unused")
	private Button save, read;
	private ListView lv_chapters;
	@SuppressWarnings("unused")
	private String txtData = null;
	private String txtChapter="";
	private String cacheChapter="";
	@SuppressWarnings("unused")
	private String cache;
	private boolean isFail;
	boolean isClear = false;
	private BookChapterAdapter chapterAdapter;
	Map<String, Object> map ;
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	Handler handler = new Handler(new Handler.Callback()
	{
		@Override
		public boolean handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case 1:
				isFail = msg.getData().getBoolean("boolean");
				txtChapter = msg.getData().getString("chapters");
				i = msg.getData().getInt("progress");
				txtNumber.setText(i + "");
				cacheCount=msg.getData().getInt("pb");
					for(int k=0;k<100;k++)
					{
						cacheChapter = mCache.getAsString("cache"+k);
						initCacheData();
					}	
					initNetData();
				// ����������
				lv_chapters.setAdapter(chapterAdapter);
				break;
			case 2:
				break;
			case 3:
				break;
			default:
				break;
			}
			return false;
		}
	});

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mCache = ACache.get(this);
		//initByTitleBar();
		setContentView(R.layout.chapter_list);
		initByIdView();
		initByCallBack();
		
	}
	*/
/*
	 * ��ʼ����������
	 * 
	 * *//*

	private void initCacheData()
	{
		
		if(cacheChapter==null)
			return;
		if(isFail==true){
			map = new HashMap<String, Object>();
			map.put("chapters", cacheChapter.substring(10).replace("}", ""));
			//Toast.makeText(MainActivity.this,"DataOfCache=" + cacheChapter,Toast.LENGTH_SHORT).show();
			list.add(map);
		}
		return;
		 
	}
	
	*/
/*
	 * ��ʼ����������
	 *//*

	private void initNetData()
	{
//			if (txtChapter == null || txtChapter == "")
//				return;
//			map.put("chapters", txtChapter);
//			list.add(map);
		if(isFail==false)
		{
			map = new HashMap<String, Object>();
			if (txtChapter == null || txtChapter == "")
			return;
		map.put("chapters", txtChapter);
		list.add(map); 
			
//			 map = new HashMap<String, Object>();
//			 map.put("chapters", "���˵����Ź���������"+i);
//			 
//			 
//			 list.add(map);
		}
		
			return;
	}

	*/
/*
	 * ��ʼ���ص�����
	 *//*

	private void initByCallBack()
	{
		GetChapterData data = new GetChapterData(this,"http://www.biquge5200.com/31_31746/12331189.html");
		data.getDataResult(new GetChapterData.MyResult()
		{
			@Override
			public void getResult(String txtData, String txtChapter,
					boolean isFail, int i,String nextUrl)
			{
				// txtContent.setText(txtData);
				Message msg = handler.obtainMessage();
				Bundle bundle = new Bundle();
				bundle.putString("str", txtData);
				bundle.putBoolean("boolean", isFail);
				bundle.putString("chapters", txtChapter);
				bundle.putInt("progress", i);
				msg.setData(bundle);
				msg.what = 1;
				handler.sendMessage(msg);
			}

			@Override
			public void onFail(boolean isFail){}

			@Override
			public void getProgress(int i)
			{
				Message msg = handler.obtainMessage();
				Bundle bundle = new Bundle();
				bundle.putInt("pb", i);
				msg.setData(bundle);
				msg.what = 1;
				handler.sendMessage(msg);
			}
			@Override
			public void getChapters(String data){}});
	}
	*/
/*
	 * ��ʼ��״̬��
	 *//*

	@SuppressWarnings("unused")
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

	*/
/*
	 * ��ʼ����ͼid��ͼ
	 *//*

	@SuppressLint("CutPasteId") private void initByIdView()
	{
		lv_chapters = (ListView) this.findViewById(R.id.lv_chapters);
		txtContent = (TextView) this.findViewById(R.id.txt_num);
		txtNumber = (TextView) this.findViewById(R.id.txt_num);
		save = (Button) this.findViewById(R.id.save);
		read = (Button) this.findViewById(R.id.read);
		chapterAdapter = new BookChapterAdapter(list, this);
		lv_chapters.setOnItemClickListener(this);
	}

	*/
/*
	 * ��������
	 *//*

	public void save(View v)
	{
		
        	for(int k=0;k<list.size();k++)
        	{
        		mCache.put("cache"+k, list.get(k).toString(), 300);
        		
        	}
    		Toast.makeText(ReadChapterActivity.this,  "��"+list.size()+"��"+ "����ɹ�",Toast.LENGTH_LONG).show();
        	mCache.put("c", list.size()+"", 300);
	}
	*/
/*
	 * �������
	 *//*

	public void clear(View v)
	{

		if (TextUtils.isEmpty(cacheChapter) == true)// (txtChapter.toString().length()==0)
		{
			Toast.makeText(ReadChapterActivity.this, "������՟o�����", Toast.LENGTH_LONG).show();
			return;
		}
		for(int k=0;k<list.size();k++)
		{
			mCache.remove("cache"+k);
		}
		isClear = true;
		Toast.makeText(ReadChapterActivity.this, "�������ɹ�", Toast.LENGTH_LONG).show();
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id)
	{
//		Intent intent=new Intent(this,ReadContent.class);
//		intent.putExtra("chapter", parent.getId());
//		startActivity(intent);
	Toast.makeText(ReadChapterActivity.this, position+"", Toast.LENGTH_LONG).show();
		
	}
	
}
*/
