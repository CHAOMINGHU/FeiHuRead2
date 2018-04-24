package com.example.hcm.feihuread.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.hcm.feihuread.R;
import com.example.hcm.feihuread.adapter.BookTypeWitchSelectedAdapter;
import com.example.hcm.feihuread.data.GetBookTypeWhitchSelected;
import com.example.hcm.feihuread.model.BookTypeAbout;
import com.example.hcm.feihuread.utils.DividerItemDecorations;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/6 0006.
 */

public class BookTypeWitchSelectedActivity extends Activity {
    RecyclerView rv;
    List<BookTypeAbout> list =new ArrayList<>();
    BookTypeWitchSelectedAdapter adapter;
    BookTypeAbout bt ;
    String mHref = "http://www.biquge5200.com/xuanhuanxiaoshuo/";
    String bookName="";
    Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    bt = new BookTypeAbout();
                    bookName = msg.getData().getString("bookName");
                    initNetData();
                    adapter=new BookTypeWitchSelectedAdapter(getApplicationContext(),list);
                    rv.setAdapter(adapter);
                    break;
            }
            return false;
        }
    });
    public void initNetData()
    {
        bt.setBookName(bookName);
        bt.setBookHref("asdasd");
        bt.setTheNewstChapter("mm");
        bt.setUpdateData("jj");
        list.add(bt);
        return;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_type_whitch_selected);
        getDetailData();
        initView();
    }
    private void initView() {
        rv = findViewById(R.id.rv_type);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.addItemDecoration(new DividerItemDecorations());
    }
    GetBookTypeWhitchSelected data = new GetBookTypeWhitchSelected(this, mHref);
    private void getDetailData() {
        data.getDataFunction(new GetBookTypeWhitchSelected.MyResult() {
            @Override
            public void getBookDetail(String bookName) {
                Message msg = handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("bookName", bookName);
                msg.setData(bundle);
                msg.what = 1;
                handler.sendMessage(msg);
            }
        });

    }
}
