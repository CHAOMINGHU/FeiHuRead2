package com.example.hcm.feihuread.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.example.hcm.feihuread.R;
import com.example.hcm.feihuread.adapter.MyBookrackAdapter;
import com.example.hcm.feihuread.db.MyBookrack;
import com.example.hcm.feihuread.utils.ImageTools;
import com.example.hcm.feihuread.utils.ToastUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by hcm on 2018/3/22.
 */

public class BookrackFragment extends Fragment {

    List<MyBookrack> datas;
    MyBookrackAdapter adapter;
    private Button btn_create, btn_add, btn_delete;
    private EditText et_add, et_delete;
    private GridView gv;
    MyBookrack myBookrack;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_rack, null);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();

        initViewId();
        datas = DataSupport.findAll(MyBookrack.class);
        createOnclick();
        addOnclick();
        deleteOnclick();
        bindAdapter();
    }
    private void bindAdapter() {
        adapter = new MyBookrackAdapter(getContext(), datas);
        gv.setAdapter(adapter);
    }

    private void deleteOnclick() {
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataSupport.deleteAll(MyBookrack.class);
                datas.clear();
                adapter.notifyDataSetChanged();
            }
        });
    }


    private void addOnclick() {
        //获取图片的bitmap对象
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.fucklayout);

        final String s = ImageTools.convertIconToString(bitmap);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myBookrack = new MyBookrack();
                myBookrack.setBookName("狗蛋");
                myBookrack.setBookAuthor("qq");
                myBookrack.setBookCover(s);
                myBookrack.setBookHref("opp");
                myBookrack.save();
//               byte[] a=myBookrack.getBookCover().getBytes();
                if (myBookrack.save()) {
                    ToastUtil.getLongToastByString(getContext(), "加入书架数据库成功");
                } else {
                    ToastUtil.getLongToastByString(getContext(), "加入书架数据库失败");
                }
                datas.clear();
                datas.addAll(DataSupport.findAll(MyBookrack.class));
                String ss = datas.get(0).getBookCover();
                byte[] c = ss.getBytes();
                Log.e("TAG", c.length + "");
                adapter.notifyDataSetChanged();

            }
        });
    }

    private void createOnclick() {

    }
    private void initViewId() {
        btn_add = getView().findViewById(R.id.btn_add);
        btn_delete = getView().findViewById(R.id.btn_delete);
        btn_create = getView().findViewById(R.id.btn_create);
        et_add = getView().findViewById(R.id.et_add);
        et_delete = getView().findViewById(R.id.et_delete);
        gv = getView().findViewById(R.id.gv);
    }
}
