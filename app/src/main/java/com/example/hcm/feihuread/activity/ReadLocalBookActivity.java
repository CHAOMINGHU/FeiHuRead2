package com.example.hcm.feihuread.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.hcm.feihuread.R;
import com.hw.txtreaderlib.bean.TxtMsg;
import com.hw.txtreaderlib.interfaces.ILoadListener;
import com.hw.txtreaderlib.main.TxtReaderView;

/**
 * Created by hcm on 2018/4/23.
 */

public class ReadLocalBookActivity extends AppCompatActivity {
    TxtReaderView tv;
    String urlPath;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localtxtcontent);
        initIdView();
        initIntentData();
        initOpenBook();
    }

    private void initOpenBook() {
        tv.loadTxtFile(urlPath, new ILoadListener() {
            @Override
            public void onSuccess() {
                tv.setTextSize(80);
            }

            @Override
            public void onFail(TxtMsg txtMsg) {

            }

            @Override
            public void onMessage(String s) {

            }
        });
    }

    private void initIntentData() {
        Intent intent=getIntent();
        urlPath=intent.getStringExtra("a");
    }

    private void initIdView() {
        tv=findViewById(R.id.hw_txt);
    }

}
