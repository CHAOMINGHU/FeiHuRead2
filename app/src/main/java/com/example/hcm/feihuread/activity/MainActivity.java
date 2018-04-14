package com.example.hcm.feihuread.activity;


import java.util.ArrayList;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hcm.feihuread.R;
import com.example.hcm.feihuread.adapter.MyFragmentPagerAdapter;
import com.example.hcm.feihuread.fragment.BookHomePageFragment;
import com.example.hcm.feihuread.fragment.BookrackFragment;

@SuppressLint("CutPasteId")
@SuppressWarnings("unused")
public class MainActivity extends FragmentActivity {
    private ArrayList<Fragment> fragmentlist;
    private ViewPager mviewpager;
    private View viewbar;
    private int currIndex;//当前页卡编号
    TextView pager1;
    TextView pager2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initByTitleBar();
        setContentView(R.layout.activity_main);

        initView();
        initBar();
        InitViewPager();
    }

    //初始化标签
    private void initView() {
        pager1 = (TextView) findViewById(R.id.id_page1);
        pager2 = (TextView) findViewById(R.id.id_page2);
        pager1.setOnClickListener(new txListner(0));
        pager2.setOnClickListener(new txListner(1));
    }

    //监听页面切换
    private class txListner implements View.OnClickListener {
        private int index = 0;

        public txListner(int i) {
            index = i;
//            if(index==0){
//                pager1.setTextColor(Color.BLUE);
//                pager2.setTextColor(Color.GRAY);
//            }else{
//                pager2.setTextColor(Color.BLUE);
//                pager1.setTextColor(Color.GRAY);
//            }


        }


        public void onClick(View v) {
            mviewpager.setCurrentItem(index);
        }
    }

    //页面切换时滚动条移动
    public void initBar() {
        viewbar = (View) super.findViewById(R.id.id_bar);
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        // 得到显示屏宽度
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        // 设置滚动条宽度为屏幕宽度的1/2
        int tabLineLength = metrics.widthPixels / 2;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewbar.getLayoutParams();
        lp.width = tabLineLength;
        viewbar.setLayoutParams(lp);
    }

    /*
  * 初始化ViewPager
  */
    public void InitViewPager() {
        mviewpager = (ViewPager) findViewById(R.id.id_viewpager);
        fragmentlist = new ArrayList<Fragment>();
        Fragment BookrackFragment = new BookrackFragment();
        Fragment BookHomePageFragment = new BookHomePageFragment();
        fragmentlist.add(BookrackFragment);
        fragmentlist.add(BookHomePageFragment);
        //给ViewPager设置适配器
        mviewpager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentlist));
        mviewpager.setCurrentItem(0);//设置当前显示标签页为第一页
        mviewpager.setOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
            // 取得该控件的实例
            LinearLayout.LayoutParams ll = (android.widget.LinearLayout.LayoutParams) viewbar.getLayoutParams();

            if (currIndex == arg0) {
//                pager1.setTextColor(Color.BLUE);
//                pager2.setTextColor(Color.GRAY);
                ll.leftMargin = (int) (currIndex * viewbar.getWidth() + arg1
                        * viewbar.getWidth());
            } else if (currIndex > arg0) {
//                pager2.setTextColor(Color.BLUE);
//                pager1.setTextColor(Color.GRAY);
                ll.leftMargin = (int) (currIndex * viewbar.getWidth() - (1 - arg1) * viewbar.getWidth());
            }
            viewbar.setLayoutParams(ll);
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onPageSelected(int arg0) {

            if (arg0 == 0) {
                pager1.setTextColor(Color.parseColor("#3F51B5"));
                pager2.setTextColor(Color.GRAY);
            } else {
                pager2.setTextColor(Color.parseColor("#3F51B5"));
                pager1.setTextColor(Color.GRAY);
            }

            currIndex = arg0;
        }
    }

    private void initByTitleBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN; // ����ȫ������ //
        Window window = getWindow();
        requestWindowFeature(Window.FEATURE_NO_TITLE); // ���ر�����
        window.setFlags(flag, flag); // ���õ�ǰ����Ϊȫ����ʾ
    }
}