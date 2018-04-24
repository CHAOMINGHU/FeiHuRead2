package com.example.hcm.feihuread.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.hcm.feihuread.R;
import com.example.hcm.feihuread.adapter.MyLocalTxtAdapter;
import com.example.hcm.feihuread.inter.MyItemClickListener;
import com.example.hcm.feihuread.model.BookInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hcm on 2018/4/18.
 */

public class SearchLocalTxtActivity extends Activity implements MyItemClickListener {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    //6.0以后需要动态加入权限
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    BookInfo bookInfo;
    //扫描按钮
    private Button bt_open;
    final MyHandeler myHandeler = new MyHandeler(SearchLocalTxtActivity.this);
    //初始化列表
    final static List<BookInfo> datas = new ArrayList<>();
    //加载本地列表
    MyLocalTxtAdapter myAdapter;
    private RecyclerView rv;
    //布局管理器
    LinearLayoutManager layoutManager;

    @Override
    public void onItemClick(View view, int postion) {
        String filePath = "/storage/emulated/0/hcm/a.txt";
        Intent intent=new Intent(this,ReadLocalBookActivity.class);
        intent.putExtra("a", filePath);
        startActivity(intent);
    }

    private static class MyHandeler extends Handler {

        WeakReference<SearchLocalTxtActivity> mainActivityWeakReference;

        public MyHandeler(SearchLocalTxtActivity activity) {
            mainActivityWeakReference = new WeakReference<SearchLocalTxtActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (null != mainActivityWeakReference) {
                final SearchLocalTxtActivity _actitity = mainActivityWeakReference.get();
                switch (msg.what) {
                    case 1:
                        final String a = msg.getData().getString("name");
                        final String b = msg.getData().getString("content");
                        break;
                }
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_local_txt_activity);
        verifyStoragePermissions(this);
        initIdVeiw();
        queryBook();


    }

    private void queryBook() {
        bt_open.setOnClickListener(new View.OnClickListener() {//打开按钮监听
            public void onClick(View arg0) {
                //若输入的文件夹名为空
                // 获得SD卡根目录路径 "/sdcard"
                File sdDir = Environment.getExternalStorageDirectory();
                // File sdDir = Environment.getDataDirectory().getParentFile();
                final File path = new File(sdDir + File.separator);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 判断SD卡是否存在，并且是否具有读写权限
                        if (Environment.getExternalStorageState().
                                equals(Environment.MEDIA_MOUNTED)) {
                            final File[] files = path.listFiles();// 读取文件夹下文件
                            Message msg = Message.obtain();
                            Bundle bundle = new Bundle();
                            String a = getFileName(files);
                            bundle.putString("name", a);
                            bundle.putString("content", getFileContent(files));
                            msg.what = 1;
                            msg.setData(bundle);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });
                        }
                    }
                }).start();
            }

        });
    }

    private void initIdVeiw() {
        rv = findViewById(R.id.rv);
        //tv = findViewById(R.id.activity_hwtxtplay_readerView);
        layoutManager = new LinearLayoutManager(this);
        myAdapter = new MyLocalTxtAdapter(this, datas);
        myAdapter.setOnItemClickListener(this);
        rv.setAdapter(myAdapter);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        rv.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));
        bt_open = (Button) findViewById(R.id.But_Open);
    }

    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //读取指定目录下的所有TXT文件的文件内容
    protected String getFileContent(File[] files) {
        String content = "";
        if (files != null) {    // 先判断目录是否为空，否则会报空指针
            for (File file : files) {
                //检查此路径名的文件是否是一个目录(文件夹)
                if (file.isDirectory()) {
                    Log.i("zeng", "若是文件目录。继续读1" +
                            file.getName().toString() + file.getPath().toString());
                    getFileContent(file.listFiles());
                    Log.i("zeng", "若是文件目录。继续读2" +
                            file.getName().toString() + file.getPath().toString());
                } else {
                    if (file.getName().endsWith(".txt")) {//格式为txt文件
                        try {
                            InputStream instream = new FileInputStream(file);
                            if (instream != null) {
                                InputStreamReader inputreader =
                                        new InputStreamReader(instream, "GBK");
                                BufferedReader buffreader =
                                        new BufferedReader(inputreader);
                                String line = "";
                                //分行读取
                                while ((line = buffreader.readLine()) != null) {
                                    content += line + "\n";
                                }
                                instream.close();
                            }
                        } catch (java.io.FileNotFoundException e) {
                            Log.d("TestFile", "The File doesn't not exist.");
                        } catch (IOException e) {
                            Log.d("TestFile", e.getMessage());
                        }

                    }
                }

            }

        }
        return content;
    }

    //读取指定目录下的所有TXT文件的文件名
    private String getFileName(File[] files) {
        String str = "";
        if (files != null) {    // 先判断目录是否为空，否则会报空指针
            for (File file : files) {
                if (file.isDirectory()) {//检查此路径名的文件是否是一个目录(文件夹)
                    Log.i("zeng", "若是文件目录。继续读1"
                            + file.getName().toString() + file.getPath().toString());
                    getFileName(file.listFiles());
                    Log.i("zeng", "若是文件目录。继续读2"
                            + file.getName().toString() + file.getPath().toString());
                } else {
                    String fileName = file.getName();
                    String filelj = file.getAbsolutePath();
                    if (fileName.endsWith(".txt")) {
                        String s = fileName.substring(0, fileName.lastIndexOf(".")).toString();
                        Log.i("zeng", "文件名txt：：   " + s);
                        str = fileName.substring(0, fileName.lastIndexOf(".")) + "\n" + "路径：" + filelj + "\n";
                        bookInfo = new BookInfo();
                        bookInfo.setBookName(str);
                        datas.add(bookInfo);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                myAdapter.notifyDataSetChanged();
                            }
                        });

                    }

                }

            }

        }
        return str;
    }


}
