package com.example.hcm.feihuread.activity;

        import android.annotation.SuppressLint;
        import android.app.Activity;
        import android.content.Intent;
        import android.graphics.drawable.ColorDrawable;
        import android.os.Build;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Message;
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
        import android.widget.PopupWindow;

        import com.example.hcm.feihuread.R;
        import com.example.hcm.feihuread.data.GetChapterData;
        import com.example.hcm.feihuread.db.MyPage;
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
        import java.lang.ref.WeakReference;
        import java.nio.CharBuffer;
        import java.nio.charset.Charset;

/**
 * Created by Administrator on 2018/4/2 0002.
 */

public class ReadPageActivity extends Activity implements View.OnClickListener, FlipperLayout.TouchListener, View.OnTouchListener{
    CustomPopWindow popWindow = null;
    CustomPopWindow popWindow2 = null;
    FlipperLayout rootLayout;
    static ReadView readView1;
    static ReadView readView2;
    View recoverView ;
    View view1,v1;
    View view2,v2 ;
    private static String textData="";
    private String url;
    private String nextUrl;
    int index;
    String txt = "123";
    private static final int MSG_DRAW_TEXT = 1;
    static CharBuffer buffer = CharBuffer.allocate(8000);
    static boolean oneIsLayout;
    static MyHandler mHandler;
    static StringBuffer sb;
    private static class  MyHandler extends  Handler
    {
        private WeakReference<ReadPageActivity> mActivity = null;
        public  MyHandler(ReadPageActivity activity)
        {
            mActivity=new WeakReference<ReadPageActivity>(activity);
        }



        @Override
        public void handleMessage(Message msg) {
            ReadPageActivity activity=mActivity.get();
            super.handleMessage(msg);
            if(activity!=null){
                switch (msg.what) {
                    case MSG_DRAW_TEXT:


                        buffer.position(0);

                        //����һҳ���ı�
                        readView1.setText(buffer.toString());

                        //���ڶ�ҳ���ı�
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

                                //����һҳ�����ݴ洢�����ݿ��У���������ݲ�����
                                if (isSavePage(1)) {
                                    page.update(1);
                                } else {
                                    page.save();
                                }

                                buffer.position(charNum);
                                readView2.setText(buffer.toString());

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

                                //���ڶ�ҳ�����ݴ洢�����ݿ���, ��������ݲ�����
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
                        if(textData!=null)
                        new ReadingThread().start();
                        // textContent.setText(textData);
                        break;

                }
            }

        }
    }
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//
//        }
//    };

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

    //��ҳ�Ƿ�洢
    private static boolean isSavePage(int pageNo) {
        return DataSupport.find(MyPage.class, pageNo) != null;
    }

    //��ȡ��ҳ�Ľ���λ��
    private static int getStartPosition(int pageNo) {
        if (pageNo < 1) {

            return 0;
        }

        if (isSavePage(pageNo)) {
            return DataSupport.find(MyPage.class, pageNo).getStartPosition();
        }
        return 0;
    }



    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initByTitleBar();
        setContentView(R.layout.f_main);

        mHandler=new MyHandler(this);
        mHandler.postDelayed(ReadingThread.currentThread(),1000*60*5);
        rootLayout = (FlipperLayout) findViewById(R.id.container);

        recoverView = LayoutInflater.from(ReadPageActivity.this).inflate(R.layout.view_new, null);
        view1 = LayoutInflater.from(ReadPageActivity.this).inflate(R.layout.view_new, null);
        view2 = LayoutInflater.from(ReadPageActivity.this).inflate(R.layout.view_new, null);
        v1=findViewById(R.id.mv1);
        v2=findViewById(R.id.mv2);
        rootLayout.initFlipperViews(ReadPageActivity.this, view2, view1, recoverView);

        readView1 = (ReadView) view1.findViewById(R.id.textview);
        readView2 = (ReadView) view2.findViewById(R.id.textview);

        Intent intent = getIntent();
        nextUrl = intent.getStringExtra("a");

        getReadContent();
        rootLayout.setOnTouchListener((View.OnTouchListener) this);

    }

    @Override
    public void onClick(View v) {

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
    * 返回值是判断是否有下一章
    * */
    @Override
    public boolean whetherHasNextPage() {

        buffer.position(getStartPosition(index));
        if (buffer.toString().length() < 40) {
            ToastUtil.getLongToastByString(ReadPageActivity.this, "没有下一页了");
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
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float screenWidth=dm.widthPixels;
        float screenHight=dm.heightPixels;
        float mMiddleX=screenWidth/2;
        float mMiddleY=screenHight/2;
        float x= motionEvent.getX();
        float y=motionEvent.getY();
        if(x>mMiddleX / 3 * 2 && x< (screenWidth -mMiddleX / 3 * 2 )
                && y > mMiddleY / 2
                && y < ( screenHight - mMiddleY / 2)
                ){
            if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                ToastUtil.getLongToastByString(this,"点击了"+index+"页");
                if (v1.getVisibility() == View.VISIBLE && v2.getVisibility() == View.VISIBLE) {
                    v1.setVisibility(View.GONE);
                    v2.setVisibility(View.GONE);
                } else {
                    v1.setVisibility(View.VISIBLE);
                    v2.setVisibility(View.VISIBLE);
                }
            }

        }

        return false;
    }

    private  static class ReadingThread extends Thread {
        InputStream in = null;
       public BufferedReader reader = null;
        public void run() {


            //AssetManager assets = getAssets();
            try {
                // in = assets.open("text.txt");
                in = getStringStream(textData);
                Charset charset = CharsetDetector.detect(in);

                reader = new BufferedReader(new InputStreamReader(in, charset));
               reader.read(buffer);

              /*  String s;
                sb = new StringBuffer();
                while((s=reader.readLine()) != null){
                    sb.append(s+"\n");*/
               // }

                System.out.println(reader.toString());

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
                                  int time) {
                // TODO Auto-generated method stub

                Message msg = mHandler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("tc", txtData);
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

