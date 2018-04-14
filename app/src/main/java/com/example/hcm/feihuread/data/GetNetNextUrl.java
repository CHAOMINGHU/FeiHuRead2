package com.example.hcm.feihuread.data;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Administrator on 2018/3/24 0024.
 */

public class GetNetNextUrl {
    String URL;

    public GetNetNextUrl() {

    }
    public GetNetNextUrl(String URL) {
        this.URL=URL;
    }
    public String getURL() {
        return URL;
    }
    public  void getData(final UrlCallBack urlCallBack){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Document document = null;
                boolean flag = false;
//                try {
//                    document = Jsoup
//                            .connect("http://www.biquge5200.com/75_75597/")
//                            .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
//                            .timeout(5000).get();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                do {
                    try {
                        document = Jsoup
                                .connect(URL)
                                .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
                                .timeout(5000).get();
                        //flag = false;
                    } catch (IOException e) {
                        // TODO �Զ����ɵ� catch ��
                        e.printStackTrace();
                        flag = true;
                    }
                } while (flag);
                // 拿到所有章节  Elements eid = document.select("#list dl dt:nth-of-type(2)~dd");
                //拿到第一个章节
                Elements eid = document.select("#list dl dt:nth-of-type(2)+dd");
                  String   next    = eid.select("a").attr("href");
                //String next=eid.text().toString();

                Log.e("FUCK", "第一章链接:" + next);


            }
        }).start();

    }
    public interface UrlCallBack {

        void getBoolHref(String url);
//        //书名、作者、介绍、封面图
//        void getBookInfo(String title, String author, String intro, String image,String href);
//
//        //书名和他的链接
//        void getBookInfo1(String title1, String href1);
    }
}
