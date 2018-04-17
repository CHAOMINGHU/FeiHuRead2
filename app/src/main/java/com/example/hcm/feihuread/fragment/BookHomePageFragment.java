package com.example.hcm.feihuread.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hcm.feihuread.R;
import com.example.hcm.feihuread.activity.BookTypeWitchSelectedActivity;
import com.example.hcm.feihuread.activity.ReadPageActivity;
import com.example.hcm.feihuread.adapter.MyRecyclerAdapter;
import com.example.hcm.feihuread.data.GetNetTxtData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hcm on 2018/3/22.
 */
public class BookHomePageFragment extends Fragment {
    private TextView txtTitle, txtAuthor, txtIntro;
    private TextView wuxia;
    private RecyclerView lv;
    private Context mContext;
    Document document = null;
//	private  BookShopAdapter adapter;

    //存储数据的map
    Map<String, Object> map;
    //标题，作者，简介，图片链接
    String title = "", author = "", intro = "", imgUrl = "";
    String title1 = "", href = "", imgUrl1 = "";

    private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    MyRecyclerAdapter adapter;
    View v;
    String nextHref;


    Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    title = msg.getData().getString("title");
                    if (title == null) {
                        Log.e("TAG", "空指针异常");
                        return false;
                    }
                    author = msg.getData().getString("author");
                    intro = msg.getData().getString("intro");
                    imgUrl = msg.getData().getString("cover");
                    href = msg.getData().getString("href");

                    //nextHref=msg.getData().getString("url");
                    //txtTitle.setText(txtTitle.getText()+"\n"+title);
                    initNetData();

                    /**
                     * 给Item设置监听
                     */
                    adapter.setOnItemClickListner(new MyRecyclerAdapter.OnItemClickListner() {

                        @Override
                        public void onItemClick(int position) {
                            if (position < 5) {
                                //这本书的url

                                String url = list.get(position-1).get("href").toString();
                                Log.e("FUCK THE URL:", url);
                                //读取第一张的url
                                getData(url, position);


                            } else {
                                Intent intent = new Intent(getContext(), BookTypeWitchSelectedActivity.class);
                                startActivity(intent);

                            }

                            //  Toast.makeText(getContext(),"链接："+list.get(position).get("href"),Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onItemLongClick(int position) {
                            Toast.makeText(getContext(), "长按了" + list.get(position), Toast.LENGTH_SHORT).show();
                        }
                    });

                    break;
                case 2:

                    title1 = msg.getData().getString("title1");

                    //  imgUrl1=msg.getData().getString("cover1");
                    //href=msg.getData().getString("href");
                    initNetData1();
                    lv.setAdapter(adapter);

                    break;

                default:

                    break;
            }
            return false;
        }
    });

    /**
     * 获取回调链接中下一页的数据
     */

    public void getData(final String url, final int position) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                boolean flag = false;

                do {
                    try {
                        document = Jsoup
                                .connect(url)
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
                nextHref = eid.select("a").attr("href");
                //String next=eid.text().toString();

                // Log.e("FUCK", "第一章链接:" + nextHref);
                //                Looper.prepare();
                //Toast.makeText(mContext,nextHref,Toast.LENGTH_LONG).show();
                //                Looper.loop();
                if (nextHref != null) {
                    Intent intent = new Intent(mContext, ReadPageActivity.class);
                    intent.putExtra("a", nextHref);
                    // intent.putExtra("a","http://www.biquge5200.com/75_75597/146416975.html");
                    startActivity(intent);
                    nextHref = null;
                }

            }

        }).start();
    }

    /**
     * 初始化的网络数据，把数据放进map,加载进list
     */
    private void initNetData() {

        map = new HashMap<String, Object>();
        map.put("title", title);
        map.put("author", author);
        map.put("intro", intro);
        map.put("cover", imgUrl);
        map.put("href", href);
        if (map.size() == 0) {
            Log.e("TAG", "空指针异常");
            return;
        }
        list.add(map);
        return;
    }

    private void initNetData1() {
        map = new HashMap<>();
        map.put("title1", title1);
        //  map.put("cover1",imgUrl1);
        list.add(map);
        return;
    }

    @Override
    public void onStart() {
        super.onStart();
        // ((SimpleItemAnimator) lv.getItemAnimator()).setSupportsChangeAnimations(false);
        initCallBackData();
        initViewById();
        lv.setAdapter(adapter);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_homepage, null);

        this.mContext = getActivity();
        this.v = view;
        return view;
    }

    /**
     * 初始化数据列表，设置布局管理器
     */
    private void initData() {
        //下面的2代表的一行的size是4
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 6);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            //返回position对应的条目所占的size
            @Override
            public int getSpanSize(int position) {
                if (position == 0)
                    return 6;
                if (0 < position && position < 5)
                    //这里返回4，指的是当position满足上面条件时，一个条目占得size是4
                    //也就是说这个条目占一行，因为上面设置的一行的size是4
                    return 3;
                else if (5 == position)
                    return 6;
                else if (6 <= position && position < 12)
                    //这里返回2，指的是当position满足上面条件时，一个条目占得size是2
                    // 也就是说这个条目占半行，因为上面设置的一行的size是4
                    return 2;
                else
                    //这里返回1，指的是当position满足上面条件时，一个条目占得size是1
                    // 也就是说这个条目占1/4行，因为上面设置的一行的size是4
                    return 6;
            }
        });
        //用来添加分割线
        //mRecy.addItemDecoration();
        //设置管理
        lv.setLayoutManager(gridLayoutManager);

        adapter = new MyRecyclerAdapter(list, getContext(), getLayoutInflater());

        //设置适配器
        //lv.setAdapter(adapter);
    }

    /**
     * 初始化id
     */
    private void initViewById() {

        txtTitle = getView().findViewById(R.id.txt_title);
        txtAuthor = getView().findViewById(R.id.txt_autor);
        txtIntro = getView().findViewById(R.id.txt_intro);
        // wuxia=getView().findViewById(R.id.txt_wuxia);
        lv = getView().findViewById(R.id.lv);
        initData();
        //adapter=new BookShopAdapter(list, this);
        //adapter=new MyBookAdapter(list,this);

    }

    /**
     * 初始化回调的数据
     */
    private void initCallBackData() {

        GetNetTxtData data = new GetNetTxtData();
        data.Get(new GetNetTxtData.DataCallBack() {
            @Override
            public void getBookInfo(String title, String author, String intro, String image, String href) {
                // TODO Auto-generated method stub
                Message msg = handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                bundle.putString("author", author);
                bundle.putString("intro", intro);
                bundle.putString("cover", image);
                bundle.putString("href", href);
                msg.setData(bundle);
                msg.what = 1;
                handler.sendMessage(msg);
            }

            @Override
            public void getBookInfo1(String title1, String href) {
                Message msg = handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("title1", title1);
                bundle.putString("href", href);
//                bundle.putString("cover1", cover1);
                msg.setData(bundle);
                msg.what = 2;
                handler.sendMessage(msg);
            }

            @Override
            public void getBookTypeHref(String bookHref) {

            }
        });

    }

}