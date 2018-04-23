package com.example.hcm.feihuread.data;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.hcm.feihuread.inter.SearchDataFunction;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by hcm on 2018/4/16.
 */

public class GetSearchData {
    //https://www.biquge5200.com/modules/article/search.php?searchkey=唐
    String mUrl;
    Context mContext;
    boolean flag=false;

    public GetSearchData(Context mContext, String mUrl) {
        this.mContext = mContext;
        this.mUrl = mUrl;

    }
    public void getDataFunction(final SearchDataFunction searchDataFunction){
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
                Elements singerListDiv =  document.getElementsByAttributeValue("class", "odd");

                for (Element link : singerListDiv) {
                    Elements ln = link.getElementsByTag("a");
                    String bookName=ln.text().trim();
                    String bookHref=ln.attr("href");
                    if(!TextUtils.isEmpty(bookName)&&!TextUtils.isEmpty(bookHref))
                        searchDataFunction.getSearchData(bookName);
                    Log.e("TAG","书名："+bookName+"网址："+bookHref);
                }
            }
        }).start();


    }

}
