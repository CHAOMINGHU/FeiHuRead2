package com.example.hcm.feihuread.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.hcm.feihuread.R;
import com.example.hcm.feihuread.adapter.BookTypeWitchSelectedAdapter;
import com.example.hcm.feihuread.data.GetBookTypeWhitchSelected;
import com.example.hcm.feihuread.read.BookTypeAbout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/4/6 0006.
 */

public class BookTypeWitchSelected extends Activity {
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
    private void initNetData()
    {


        bt.setBookName(bookName);
        bt.setBookHref("asdasd");
        bt.setTheNewstChapter("mm");
        bt.setUpdateData("jj");
        list.add(bt);
        return;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_type_whitch_selected);

        getDetailData();
        initView();
    }
//    private void initBook(){
//        for (int i = 0; i < 10; i++) {
//            BookTypeAbout book01 = new BookTypeAbout();
//
//            book01.setBookName("dsad"+i);
//            book01.setBookHref("asdasd");
//            book01.setTheNewstChapter("mm");
//            book01.setUpdateData("jj"+i);
//            list.add(book01);
////            Book book02 = new Book("Book"+i,R.drawable.icon02);
////            mlsit.add(book02);
////            Book book03 = new Book("Book"+i,R.drawable.icon03);
////            mlsit.add(book03);
//        }
//    }
    private void initView() {
        rv = findViewById(R.id.rv_type);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);

   //     initBook();



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
