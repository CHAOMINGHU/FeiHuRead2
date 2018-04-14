package com.example.hcm.feihuread.data;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.Inflater;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;







import android.R.bool;
import android.app.Activity;
import android.content.Context;
import android.graphics.Shader.TileMode;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.hcm.feihuread.read.TextRead;
import com.example.hcm.feihuread.utils.NetUtil;

public class GetChapterData
{
	 public TextRead tr;
	 int i ;
	 String URL = "";
	 boolean flag  = false;
	 Context context;
	 public GetChapterData(Context context,String URL)
	 {
		 this.context=context;
		 this.URL=URL;
	 }
//	public GetChapterData()
//	{
//		// TODO Auto-generated constructor stub
//	}
	// ���ݽ���ص�����
	public  void getDataResult(final MyResult myResult)
	{
		
		// jsoup������ȡ����
		new Thread(new Runnable()
		{
			@SuppressWarnings({ "unused", "static-access" })
			@Override
			public void run()
			{
				// TODO Auto-generated method stub
				Document document = null;
				tr=new TextRead();
				String txtContet  = "over";
				String   title="1";
				i=0;
				while (i <1)
				{
					do
					{
						try
						{
							
							document = Jsoup
									.connect(URL)
									.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
									.timeout(5000).get();
							        //flag = false;
						} catch (IOException e)
						{
							// TODO �Զ����ɵ� catch ��
							e.printStackTrace();
							NetUtil net=new NetUtil();
							if(net.isNetworkAvalible(context)==true)
								 flag=true;
							else
								 myResult.getResult("meiyou", "meiyou", true, 0);
							//flag = true;
						    myResult.onFail(flag);
						   
						}
					} while (flag);
					if(document==null||txtContet==null)
					{
						return;
					}
				    title = document.title();
				    tr.setTitle(title);
					String   text  = document.select("#content").text();
					txtContet += "\n\n" + text + "\n" + "\n";
					tr.setTextContent(txtContet);
					Elements nextdiv = document.select(".bottem2");
					String   next    = nextdiv.toString();
					String[] nexturl = next.split("下一章");
					next    = nexturl[0];
					nexturl = next.split("\u2192");
					next    = nexturl[1];
					next    = next.substring(12, next.length() - 2);
					URL= next;
					i+=1;
				
					tr.setTextChapter(i);
					//myResult.getChapters(title);
					myResult.getResult(tr.getTextContent(), tr.getTitle(), false, tr.getTextChapter());
				}
				tr.setCountChapter(i);
				 myResult.onFail(flag);
				 myResult.getProgress(tr.getCountChapter());
			}
			
		}).start();
	}



	// ���ݽ���ص��ӿ�
	public interface MyResult
	{
		// ���ص��ı�����
		void getResult(String txtData, String txtChapter, boolean isFail, int time);

		// ����ʧ��
		void onFail(boolean isFail);

		// ���ؽ���
		void getProgress(int i);
		
		//�����½�����
		void getChapters(String data);
		
	}
}
