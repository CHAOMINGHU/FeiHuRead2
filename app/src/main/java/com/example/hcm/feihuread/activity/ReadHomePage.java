package com.example.hcm.feihuread.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hcm.feihuread.R;
import com.example.hcm.feihuread.adapter.MyRecyclerAdapter;
import com.example.hcm.feihuread.data.GetNetNextUrl;
import com.example.hcm.feihuread.data.GetNetTxtData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressLint("CutPasteId")
@SuppressWarnings("unused")
public class ReadHomePage extends Activity
{
	private TextView txtTitle,txtAuthor,txtIntro;
	private TextView wuxia;
	private RecyclerView lv;

//	private  BookShopAdapter adapter;

	Map<String, Object> map ;
	String title,author,intro,imgUrl;
	String title1,href,imgUrl1;
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	MyRecyclerAdapter adapter;
	Handler handler=new Handler(new Handler.Callback()
	{

		@Override
		public boolean handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case 1:
				 title=msg.getData().getString("title");
				 if(title==null){
					 Log.e("TAG","空指针异常");
				 	return false;
				 }
				 author=msg.getData().getString("author");
				 intro=msg.getData().getString("intro");
				 imgUrl=msg.getData().getString("cover");
				//txtTitle.setText(txtTitle.getText()+"\n"+title);
					initNetData();
					// ����������
				lv.setAdapter(adapter);
				adapter.setOnItemClickListner(new MyRecyclerAdapter.OnItemClickListner() {
					@Override
					public void onItemClick(int position) {
						Toast.makeText(ReadHomePage.this,"点击了"+position,Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onItemLongClick(int position) {
						Toast.makeText(ReadHomePage.this,"长按了"+list.get(position),Toast.LENGTH_SHORT).show();
					}
				});

				break;
				case  2:
					title1=msg.getData().getString("title1");
					imgUrl1=msg.getData().getString("cover1");
					//href=msg.getData().getString("href");
					initNetData1();
					lv.setAdapter(adapter);
					adapter.setOnItemClickListner(new MyRecyclerAdapter.OnItemClickListner() {
						@Override
						public void onItemClick(int position) {
							Toast.makeText(ReadHomePage.this,"点击了"+list.get(position).get("title"),Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onItemLongClick(int position) {
							Toast.makeText(ReadHomePage.this,"长按了"+list.get(position),Toast.LENGTH_SHORT).show();
						}
					});
					break;

			default:

				break;
			}
			return false;
		}
	});

	private void initNetData()
	{

		map = new HashMap<String, Object>();
		map.put("title", title);
		map.put("author", author);
		map.put("intro", intro);
		map.put("cover", imgUrl);
		if(map.size()==0){
			Log.e("TAG","空指针异常");
			return;
		}
		list.add(map);
			return;
	}
	private void initNetData1(){
		map=new HashMap<>();
		map.put("title1",title1);
		list.add(map);
		return;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_homepage);
		initCallBackData();
		initViewById();

	}
	private void initData() {
		//下面的2代表的一行的size是4
		final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
		gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
			//返回position对应的条目所占的size
			@Override
			public int getSpanSize(int position) {
				if (position < 4)
					//这里返回4，指的是当position满足上面条件时，一个条目占得size是4
					//也就是说这个条目占一行，因为上面设置的一行的size是4

					return 2;
				else if (4 <= position && position < 8)
					//这里返回2，指的是当position满足上面条件时，一个条目占得size是2
					// 也就是说这个条目占半行，因为上面设置的一行的size是4
					return 1;
				else
					//这里返回1，指的是当position满足上面条件时，一个条目占得size是1
					// 也就是说这个条目占1/4行，因为上面设置的一行的size是4
					return 4;
			}
		});
		//用来添加分割线
		//mRecy.addItemDecoration();
		//设置管理
		lv.setLayoutManager(gridLayoutManager);
		adapter = new MyRecyclerAdapter(list,this);

		//设置适配器
		//lv.setAdapter(adapter);
	}


	private void initViewById()
	{

		txtTitle=(TextView) findViewById(R.id.txt_title);
		txtAuthor=(TextView) findViewById(R.id.txt_autor);
		txtIntro=(TextView) findViewById(R.id.txt_intro);
		//wuxia=findViewById(R.id.txt_wuxia);
		lv= (RecyclerView) findViewById(R.id.lv);
		initData();
		//adapter=new BookShopAdapter(list, this);
		//adapter=new MyBookAdapter(list,this);

	}
	private void initCallBackData()
	{
		GetNetNextUrl getNetNextUrl=new GetNetNextUrl();
		getNetNextUrl.getData(new GetNetNextUrl.UrlCallBack() {
			@Override
			public void getBoolHref(String url) {
				Message msg=handler.obtainMessage();
				Bundle bundle=new Bundle();
				bundle.putString("url", href);
				msg.setData(bundle);
				msg.what=1;
				handler.sendMessage(msg);
			}
		});
		GetNetTxtData data=new GetNetTxtData();
		data.Get(new GetNetTxtData.DataCallBack()
		{
			@Override
			public void getBookInfo(String title, String author, String intro,String image,String href)
			{
				// TODO Auto-generated method stub
				Message msg=handler.obtainMessage();
				Bundle bundle=new Bundle();
				bundle.putString("title", title);
				bundle.putString("author", author);
				bundle.putString("intro", intro);
				bundle.putString("cover", image);
				msg.setData(bundle);
				msg.what=1;
				handler.sendMessage(msg);
			}

			@Override
			public void getBookInfo1(String title1, String href) {
				Message msg=handler.obtainMessage();
				Bundle bundle=new Bundle();
				bundle.putString("title1", title1);
				bundle.putString("href", href);
//				bundle.putString("cover1", cover1);
				msg.setData(bundle);
				msg.what=2;
				handler.sendMessage(msg);
			}

			@Override
			public void getBookTypeHref(String bookHref) {

			}
		});
	}

}


