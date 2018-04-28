package com.example.hcm.feihuread.data;

import android.content.Context;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Administrator on 2018/4/6 0006.
 */

public class GetBookTypeWhitchSelected {
    String mUrl;
    Context mContext;
    boolean flag=false;

    public GetBookTypeWhitchSelected(Context mContext, String mUrl) {
        this.mContext = mContext;
        this.mUrl = mUrl;

    }
    public void getDataFunction(final  MyResult myResult){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Document document=null;
                do {
                    try{
                        document= Jsoup
                                .connect(mUrl)
                                .timeout(5000).get();
                    }catch (IOException e){
                        e.printStackTrace();
                        flag=true;
                    }
                }while (flag==true);
                //   Elements eid = document.select("#list dl dt:nth-of-type(2)+dd");
                Elements eid = document.select("#newscontent ul~li");
                Log.e("TAG","瓦达西瓦："+eid.text());
                Element singerListDiv = document.getElementsByAttributeValue("id", "newscontent").first();
                Elements links=singerListDiv.select(".s2 a");
               // Elements links = links0.getElementsByTag("a");
                for (Element link : links) {
                    String bookName=link.text().trim();
                    String bookHref=link.attr("href");
                    Log.e("TAG","书名："+bookName+"网址："+bookHref);
                    myResult.getBookDetail(bookName.toString().trim());                             //把获得的数据加入回调接口
                }

            }
        }).start();


    }
    public interface MyResult{
        void getBookDetail(String bookName);

    }
}
