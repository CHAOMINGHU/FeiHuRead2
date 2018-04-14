package com.example.hcm.feihuread.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bumptech.glide.Glide;
import com.example.hcm.feihuread.R;
import com.example.hcm.feihuread.popuwindow.CustomPopWindow;
import com.example.hcm.feihuread.view.BannerView;
import com.youth.banner.Banner;

import java.util.Arrays;

/**
 * Created by hcm on 2018/3/22.
 */

public class BookrackFragment extends Fragment {
    CustomPopWindow popWindow = null;
    CustomPopWindow popWindow2 = null;
        private Banner banner;
    BannerView bv;
        //设置图片资源:url或本地资源
        String[] images= new String[] {
                "http://r.i.biquge5200.com/cover/aHR0cDovL3FpZGlhbi5xcGljLmNuL3FkYmltZy8zNDk1NzMvMTAwNDYwODczOC8xODA=",
                "http://r.i.biquge5200.com/cover/aHR0cDovL3FpZGlhbi5xcGljLmNuL3FkYmltZy8zNDk1NzMvMTAwNTk4Njk5NC8xODA=",
                "http://r.i.biquge5200.com/cover/aHR0cDovL3FpZGlhbi5xcGljLmNuL3FkYmltZy8zNDk1NzMvMTAwMzM1NDYzMS8xODA=",
                "http://r.i.biquge5200.com/cover/aHR0cDovL3FpZGlhbi5xcGljLmNuL3FkYmltZy8zNDk1NzMvMTAwMzMwNzU2OC8xODA=",
               };

        //设置图片标题:自动对应
        String[] titles=new String[]{"圣墟","我是至尊","一念永恒","不朽凡人"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.book_lunbo,null);
        return  view;
    }
    @Override
    public void onStart() {
        super.onStart();
       // initBanner();
        bv=new BannerView(getLayoutInflater(),getView(),getContext(),titles,images);
        bv.initBanner();

    }
}

