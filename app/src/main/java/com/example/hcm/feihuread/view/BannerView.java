package com.example.hcm.feihuread.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hcm.feihuread.R;
import com.youth.banner.Banner;

/**
 * Created by Administrator on 2018/4/14 0014.
 */

public class BannerView {
    private Banner banner;
    private LayoutInflater inflater;
    private View getView;
    String[] titles;
    String[] images;
    Context context;

    public BannerView(LayoutInflater inflater, View getView, Context context, String[] titles, String[] images) {
        this.inflater = inflater;
        this.getView = getView;
        this.context = context;
        this.titles = titles;
        this.images = images;
    }

    public void initBanner() {
        View view = inflater.inflate(R.layout.book_lunbo, null);
        banner = getView.findViewById(R.id.banner);

        //设置样式,默认为:Banner.NOT_INDICATOR(不显示指示器和标题)
        //可选样式如下:
        //1. Banner.CIRCLE_INDICATOR    显示圆形指示器
        //2. Banner.NUM_INDICATOR   显示数字指示器
        //3. Banner.NUM_INDICATOR_TITLE 显示数字指示器和标题
        //4. Banner.CIRCLE_INDICATOR_TITLE  显示圆形指示器和标题
        banner.setBannerStyle(Banner.CIRCLE_INDICATOR_TITLE);

        //设置轮播样式（没有标题默认为右边,有标题时默认左边）
        //可选样式:
        //Banner.LEFT   指示器居左
        //Banner.CENTER 指示器居中
        //Banner.RIGHT  指示器居右
        banner.setIndicatorGravity(Banner.CENTER);

        //设置轮播要显示的标题和图片对应（如果不传默认不显示标题）
        banner.setBannerTitle(titles);

        //设置是否自动轮播（不设置则默认自动）
        banner.isAutoPlay(true);

        //设置轮播图片间隔时间（不设置默认为2000）
        banner.setDelayTime(5000);
        //设置图片资源:可选图片网址/资源文件，默认用Glide加载,也可自定义图片的加载框架
        //所有设置参数方法都放在此方法之前执行
        //banner.setImages(images);

        //自定义图片加载框架
        banner.setImages(images, new Banner.OnLoadImageListener() {
            @Override
            public void OnLoadImage(ImageView view, Object url) {
                view.setScaleType(ImageView.ScaleType.FIT_XY);
                System.out.println("加载中");
                Glide.with(context)
                        .load(url)
                        .into(view);
                System.out.println("加载完");
            }
        });
        //设置点击事件，下标是从1开始
        banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {//设置点击事件
            @Override
            public void OnBannerClick(View view, int position) {
                Toast.makeText(context, "你点击了：" + position, Toast.LENGTH_LONG).show();
            }
        });
    }
}
