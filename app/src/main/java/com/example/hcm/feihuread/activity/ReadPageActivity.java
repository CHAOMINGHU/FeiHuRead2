package com.example.hcm.feihuread.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.hcm.feihuread.R;
import com.example.hcm.feihuread.adapter.BookChapterAdapter;
import com.example.hcm.feihuread.adapter.MyLocalTxtAdapter;
import com.example.hcm.feihuread.data.GetChapterData;
import com.example.hcm.feihuread.db.MyPage;
import com.example.hcm.feihuread.model.BookChapterDetail;
import com.example.hcm.feihuread.popuwindow.CustomPopWindow;

import com.example.hcm.feihuread.utils.CharsetDetector;
import com.example.hcm.feihuread.utils.ToastUtil;
import com.example.hcm.feihuread.view.FlipperLayout;
import com.example.hcm.feihuread.view.ReadView;

import org.litepal.crud.DataSupport;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2018/4/2 0002.
 */

public class ReadPageActivity extends Activity implements View.OnClickListener, FlipperLayout.TouchListener, View.OnTouchListener ,BookChapterAdapter.OnRecyclerViewItemClickListener  {
    FlipperLayout rootLayout;
    private static ReadView readView1;
    private static ReadView readView2;
    private DrawerLayout dl_layout;
    private TextView btn_next;
    private ImageView catalogue;
    private View recoverView;
    private View view1, v1;
    private View view2, v2;
    private static String textData = "";
    private static String txtUrl = "";
    private String url;
    private String nextUrl;
    private static int index;
    private String txt = "123";
    private static final int MSG_DRAW_TEXT = 1;
    static CharBuffer buffer;
    private static boolean oneIsLayout;
    private static MyHandler mHandler;
    private static StringBuffer sb;
    private static InputStream in;
    private float downX;
    private float downY;
    private boolean flag = false;
    private RecyclerView rv;
    private List<BookChapterDetail> allChapterList=new ArrayList<>();
    private BookChapterAdapter bcAdapter;
    //布局管理器
    LinearLayoutManager layoutManager;

    @Override
    public void onItemClick(View view) {

    }

    @Override
    public void onItemLongClick(View view) {

    }

    private static class MyHandler extends Handler {
        private WeakReference<ReadPageActivity> mActivity = null;

        public MyHandler(ReadPageActivity activity) {
            mActivity = new WeakReference<ReadPageActivity>(activity);
        }


        @Override
        public void handleMessage(Message msg) {
            ReadPageActivity activity = mActivity.get();
            super.handleMessage(msg);
            if (activity != null) {
                switch (msg.what) {
                    case MSG_DRAW_TEXT:
                        buffer.position(0);
                        readView1.setText(buffer.toString());
                        Log.e("FUCK THE NEXTURL1", readView1.getText().toString());
                        ViewTreeObserver vto1 = readView1.getViewTreeObserver();
                        vto1.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                if (oneIsLayout)
                                    return;
                                int charNum = readView1.getCharNum();
                                MyPage page = new MyPage();
                                page.setPageSize(charNum);
                                page.setStartPosition(charNum);
                                page.setId(1);
                                if (isSavePage(1)) {
                                    page.update(1);
                                } else {
                                    page.save();
                                }

                                buffer.position(charNum);
                                readView2.setText(buffer.toString());
                                Log.e("FUCK THE NEXTURL2", readView2.getText().toString());
                                oneIsLayout = true;
                            }
                        });
                        ViewTreeObserver vto2 = readView2.getViewTreeObserver();
                        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                int charNum = readView2.getCharNum();
                                if (charNum == 0)
                                    return;
                                MyPage page = new MyPage();
                                page.setPageSize(charNum);
                                page.setStartPosition(charNum + getStartPosition(1));
                                page.setId(2);

                                if (isSavePage(2)) {
                                    page.update(2);
                                } else {
                                    page.save();
                                }
                            }
                        });
                        break;
                    case 2:
                        textData = msg.getData().getString("tc");
                        txtUrl = msg.getData().getString("txtUrl");
                        //设置字符缓冲区
                        buffer = CharBuffer.allocate(textData.length());
                        if (textData != null)
                            in = getStringStream(textData.trim());
                        new ReadingThread().start();
                        // textContent.setText(textData);

                        break;
                }
            }
        }
    }

    public static InputStream getStringStream(String sInputString) {
        if (sInputString != null && !sInputString.trim().equals("")) {
            try {
                ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
                return tInputStringStream;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    private static boolean isSavePage(int pageNo) {
        return DataSupport.find(MyPage.class, pageNo) != null;
    }

    private static int getStartPosition(int pageNo) {
        if (pageNo < 1) {

            return 0;
        }

        if (isSavePage(pageNo)) {
            return DataSupport.find(MyPage.class, pageNo).getStartPosition();
        }
        return 0;
    }


    @SuppressLint({"InflateParams", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initByTitleBar();
        setContentView(R.layout.f_main);

        mHandler = new MyHandler(this);
        mHandler.postDelayed(ReadingThread.currentThread(), 1000 * 60 * 5);
        rootLayout = (FlipperLayout) findViewById(R.id.container);

        recoverView = LayoutInflater.from(ReadPageActivity.this).inflate(R.layout.view_new, null);
        view1 = LayoutInflater.from(ReadPageActivity.this).inflate(R.layout.view_new, null);
        view2 = LayoutInflater.from(ReadPageActivity.this).inflate(R.layout.view_new, null);
        v1 = findViewById(R.id.mv1);
        v2 = findViewById(R.id.mv2);
        dl_layout = (DrawerLayout) findViewById(R.id.dl_layout);
        btn_next = findViewById(R.id.btn_next);
        catalogue = findViewById(R.id.catalogue);
        catalogue.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        rootLayout.initFlipperViews(ReadPageActivity.this, view2, view1, recoverView);

        readView1 = (ReadView) view1.findViewById(R.id.textview);
        readView2 = (ReadView) view2.findViewById(R.id.textview);

        Intent intent = getIntent();
        nextUrl = intent.getStringExtra("a");

        allChapterList= (List<BookChapterDetail>) intent.getSerializableExtra("datas");
        initIdVeiw();
        getReadContent();
        rootLayout.setOnTouchListener((View.OnTouchListener) this);


    }
    private void initIdVeiw() {
        rv = findViewById(R.id.lv_menu);
        //tv = findViewById(R.id.activity_hwtxtplay_readerView);
        layoutManager = new LinearLayoutManager(this);
      //  Collections.reverse(allChapterList); // 倒序排列
        bcAdapter = new BookChapterAdapter(this, allChapterList);
        bcAdapter.setOnItemClickListener(this);
        rv.setAdapter(bcAdapter);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        rv.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                buffer.clear();
                nextUrl = txtUrl; //https://www.biquge5200.com/42_42714/16445334.html

                Log.e("FUCK THE NEXTURL", txtUrl);
                getReadContent();
                rootLayout.removeAllViews();
                rootLayout.initFlipperViews(ReadPageActivity.this, view2, view1, recoverView);
                break;
            case R.id.catalogue:
                ToastUtil.getLongToastByString(this, "你点击了目录");
                //如果抽屉关闭就打开，如果抽屉打开就关闭
                if (dl_layout.isDrawerOpen(Gravity.LEFT)) {
                    dl_layout.closeDrawer(Gravity.LEFT);
                } else {//如果已经是关闭状态
                    dl_layout.openDrawer(Gravity.LEFT);
                }
                break;
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public View createView(final int direction, final int index) {
        String a;
        View newView;

        this.index = index;
        if (direction == FlipperLayout.TouchListener.MOVE_TO_LEFT) { //��һҳ
            buffer.position(getStartPosition(index));
            newView = LayoutInflater.from(this).inflate(R.layout.view_new, null);
            final ReadView readView = (ReadView) newView.findViewById(R.id.textview);

            readView.setText(buffer.toString());
            Log.e("FUCK THE NEXTURL3", readView.getText().toString());
            a = buffer.toString();
            ViewTreeObserver vto2 = readView.getViewTreeObserver();
            vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    int charNm = readView.getCharNum();

                    MyPage page = new MyPage();
                    page.setPageSize(charNm);
                    //ToastUtil.getLongToastByString(MainActivity.this,"����"+charNm);
                    page.setStartPosition(getStartPosition(index) + charNm);
                    page.setBookId(index + 1);
                    page.setId(index + 1);

                    if (isSavePage(index + 1)) {
                        page.update(index + 1);
                    } else {
                        page.save();
                    }
                }
            });
        } else {
            buffer.position(getStartPosition(index - 2));
            newView = LayoutInflater.from(this).inflate(R.layout.view_new, null);
            final ReadView readView = (ReadView) newView.findViewById(R.id.textview);
            readView.setText(buffer);

        }

        return newView;
    }

    /*
    * 返回值是判断是否有下一页
    * */
    @Override
    public boolean whetherHasNextPage() throws IOException {

        buffer.position(getStartPosition(index));
        if (buffer.toString().length()<1) {
            //ToastUtil.getLongToastByString(ReadPageActivity.this, "没有下一页了");

            return false;

        } else
            return true;
    }

    @Override
    public boolean currentIsLastPage() {
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent motionEvent) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float screenWidth = dm.widthPixels;
        float screenHight = dm.heightPixels;
        float mMiddleX = screenWidth / 2;
        float mMiddleY = screenHight / 2;
        float x = motionEvent.getX();
        float y = motionEvent.getY();

        switch (motionEvent.getAction()) {

            case MotionEvent.ACTION_DOWN:
                downX = motionEvent.getX();
                downY = motionEvent.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float distanceX = motionEvent.getX() - downX;
                float distanceY = motionEvent.getY() - downY;
                if (distanceX > 0 || distanceY > 0) {
                    flag = true;
                }
                break;
            case MotionEvent.ACTION_UP:

                if (x > mMiddleX / 3 * 2 && x < (screenWidth - mMiddleX / 3 * 2) && y > mMiddleY / 3 * 2 && y < (screenHight - mMiddleY / 3 * 2) && flag == false) {
                    //  ToastUtil.getLongToastByString(this,"点击了"+index+"页");
                    Log.e("FUCK ONCLICK", "点击了" + index + "");
                    if (v1.getVisibility() == View.VISIBLE && v2.getVisibility() == View.VISIBLE) {
                        v1.setVisibility(View.GONE);
                        v2.setVisibility(View.GONE);
                    } else {
                        v1.setVisibility(View.VISIBLE);
                        v2.setVisibility(View.VISIBLE);
                    }
                }
                flag = false;
                break;


        }
        return false;
    }

    private static class ReadingThread extends Thread {

        public BufferedReader reader = null;

        public void run() {


            //AssetManager assets = getAssets();
            try {
                // in = assets.open("text.txt");

                Charset charset = CharsetDetector.detect(in);

                reader = new BufferedReader(new InputStreamReader(in, charset));

                reader.read(buffer);
                // buffer.put(textData);

           /*   String s;
                sb = new StringBuffer();
                while((s=reader.readLine()) != null){
                    sb.append(s+"\n");
               }
                System.out.println("READ: "+reader.toString()+"\n Buff: "+buffer.toString()+"\n SB: "+sb.toString());*/
                mHandler.obtainMessage(MSG_DRAW_TEXT).sendToTarget();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void getReadContent() {
        GetChapterData data = new GetChapterData(this, nextUrl);
        data.getDataResult(new GetChapterData.MyResult() {

            @Override
            public void onFail(boolean isFail) {
                // TODO Auto-generated method stub

            }

            @Override
            public void getResult(String txtData, String txtChapter, boolean isFail,
                                  int time, String nextUrl) {
                // TODO Auto-generated method stub

                Message msg = mHandler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("tc", txtData);
                bundle.putString("txtUrl", nextUrl);
                msg.setData(bundle);
                msg.what = 2;
                mHandler.sendMessage(msg);

            }

            @Override
            public void getProgress(int i) {
                // TODO Auto-generated method stub

            }

            @Override
            public void getChapters(String data) {
                // TODO Auto-generated method stub

            }
        });
    }

    /*
     *
     */
    private void initByTitleBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        Window window = getWindow();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(flag, flag);
    }


}

