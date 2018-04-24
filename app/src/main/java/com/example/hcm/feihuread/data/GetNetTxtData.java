package com.example.hcm.feihuread.data;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import android.util.Log;

import com.example.hcm.feihuread.model.BookDetail;

public class GetNetTxtData {
    String URL = "http://www.biquge5200.com/";
    String title, author, intro, cover, href;
    String title1, href1, cover1;
    int i;
    String Url;
    String Type;


    public void Get(final DataCallBack data) {

        // jsoup������ȡ����
        new Thread(new Runnable() {
            @SuppressWarnings({"unused"})
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Document document = null;
                Document document1 = null;
                boolean flag = false;
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
                ArrayList<BookDetail> list = new ArrayList<BookDetail>();
                Elements ListClass = document.getElementsByAttributeValue("class", "item");
                for (Element div : ListClass) {
                    i++;
                    title = div.select("a").text();
                    Log.e("TAG", title);
                    href = div.select("a").attr("href");
                    Log.e("TAG", href);
                    author = div.select("span").text();
                    intro = div.select("dd").text();
                    // Elements img = div.getElementsByAttributeValue("class","image");
                    cover = div.select("img").attr("src");
                    data.getBookInfo(title, author, intro, cover, href);
                }
                //获取分类的超链接
                Element typeList = document.getElementsByAttributeValue("class", "nav").first();
                Elements tl = typeList.getElementsByTag("a");
                Elements eid = typeList.select(" ul li:nth-of-type(2)~dd");
                Log.e("TAG", "link: " + eid.toString());
                for (Element link : tl) {
                    String bookTypeUrl = "http:" + link.attr("href");
                    String bookTypeName = link.text().trim();
                   // Log.e("TAG", "网址：" + bookTypeUrl + "类型： " + bookTypeName);

                }

                Element singerListDiv = document.getElementsByAttributeValue("class", "novelslist").first();
                Elements links = singerListDiv.getElementsByTag("a");

                for (Element link : links) {
                    href1 = link.attr("href");
                    String title1 = link.text().trim();
                    //System.out.println(href);
//                    Log.e("TAG", "网址：" + href1);
//                    Log.e("TAG", "书名：" + title1);
                   /* try {
                        document1 = Jsoup
                                .connect(href)
                                .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
                                .timeout(2000).get();
                    } catch (IOException e) {
                        // TODO �Զ����ɵ� catch ��
                        e.printStackTrace();

                    }
                    if (document1.getElementById("fmimg") == null) {
                        Log.e("TAG", "nsiofhasoifhaoifnfason：" + "88行为空");
                        return;
                    }
                    Element id = document1.getElementById("fmimg");*/
                    data.getBookInfo1(title1, href1);
                }
            }
        }).start();

    }

    public interface DataCallBack {
        //书名、作者、介绍、封面图
        void getBookInfo(String title, String author, String intro, String image, String href);

        //书名和他的链接
        void getBookInfo1(String title1, String href1);

        void getBookTypeHref(String bookHref);
    }
}

