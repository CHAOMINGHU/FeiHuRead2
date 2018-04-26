package com.example.hcm.feihuread.data;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import android.content.Context;

import com.example.hcm.feihuread.model.TextRead;
import com.example.hcm.feihuread.utils.NetUtil;

public class GetChapterData {
    public TextRead tr;
    int i;
    String URL = "";
    boolean flag = false;
    Context context;

    public GetChapterData(Context context, String URL) {
        this.context = context;
        this.URL = URL;
    }

    public void getDataResult(final MyResult myResult) {
/**
 * JSOUP爬取步骤
 * 1.获得当前章节链接
 * 2.根据当前链接自动爬取下章
 */
        new Thread(new Runnable() {
            @SuppressWarnings({"unused", "static-access"})
            @Override
            public void run() {/**/
                // TODO Auto-generated method stub
                Document document = null;
                tr = new TextRead();
                String txtContet = "over";
                String title = "1";
                i = 0;
                while (i < 1) {
                    do {
                        try {

                            document = Jsoup
                                    .connect(URL)
                                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
                                    .timeout(5000).get();
                            //flag = false;
                        } catch (IOException e) {

                            e.printStackTrace();
                            NetUtil net = new NetUtil();
                            if (net.isNetworkAvalible(context) == true)
                                flag = true;
                            else
                                myResult.getResult("meiyou", "meiyou", true, 0,"");
                            //flag = true;
                            myResult.onFail(flag);

                        }
                    } while (flag);
                    if (document == null || txtContet == null) {
                        return;
                    }
                    title = document.title();
                    tr.setTitle(title);
                    String text = document.select("#content").text();
                    txtContet += "\n\n" + text + "\n" + "\n";
                    tr.setTextContent(txtContet);
                    Elements nextdiv = document.select(".bottem2");
                    String next = nextdiv.toString();
                    String[] nexturl = next.split("下一章");
                    next = nexturl[0];
                    nexturl = next.split("\u2192");
                    next = nexturl[1];
                    next = next.substring(12, next.length() - 2);
                    URL = next;
                    i += 1;

                    tr.setTextChapter(i);
                    //myResult.getChapters(title);
                    myResult.getResult(tr.getTextContent(), tr.getTitle(), false, tr.getTextChapter(),URL);
                }
                tr.setCountChapter(i);
                myResult.onFail(flag);
                myResult.getProgress(tr.getCountChapter());
            }

        }).start();
    }


    public interface MyResult {

        void getResult(String txtData, String txtChapter, boolean isFail, int time,String nextUrl);

        void onFail(boolean isFail);


        void getProgress(int i);


        void getChapters(String data);

    }
}
