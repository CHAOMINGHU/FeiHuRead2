package com.example.hcm.feihuread.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hcm.feihuread.R;
import com.example.hcm.feihuread.data.GetSearchData;
import com.example.hcm.feihuread.data.SearchDataFunction;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hcm on 2018/4/17.
 */

public class SearchActivity extends AppCompatActivity{
    SearchView searchView;
    private ListView mListView;
    String mUrl = "https://www.biquge5200.com/modules/article/search.php?searchkey=";
    StringBuilder sb;
    MyHandler myHandler;
    static List<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;

    private static class MyHandler extends Handler {
        private WeakReference<SearchActivity> mActivity = null;

        private MyHandler(SearchActivity activity) {
            mActivity = new WeakReference<SearchActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SearchActivity searchActivity = mActivity.get();
            if (searchActivity != null) {
                switch (msg.what) {
                    case 1:
                        String data = msg.getData().getString("data");
                        list.add(data);
                        break;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.book_search_activity);
        myHandler = new MyHandler(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mListView = (ListView) findViewById(R.id.listView);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_view, menu);
        //找到searchView
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        doChangeSearch();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        mListView.setAdapter(adapter);
        mListView.setTextFilterEnabled(true);
        return super.onCreateOptionsMenu(menu);
    }

    private void doChangeSearch() {
        // 设置搜索文本监听
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法

            @Override
            public boolean onQueryTextSubmit(String query) {

               /* if (!TextUtils.isEmpty(query)) {
                    mListView.setFilterText(query);
                } else {
                    mListView.clearTextFilter();
                }*/
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                mUrl = "https://www.biquge5200.com/modules/article/search.php?searchkey="+newText;
//                sb = new StringBuilder(mUrl);
//                sb.append(newText);
//                mUrl = sb.toString();
                if (list.size() > 0)
                    list.removeAll(list);
                GetSearchData getSearchData = new GetSearchData(SearchActivity.this, mUrl);
                getSearchData.getDataFunction(new SearchDataFunction() {
                    @Override
                    public void getSearchData(String datas) {
                        Message message = Message.obtain();
                        Bundle bundle = new Bundle();
                        bundle.putString("data", datas);
                        message.setData(bundle);
                        message.what = 1;
                        myHandler.handleMessage(message);
                    }
                });
                adapter.notifyDataSetChanged();
                return false;
            }
        });

    }

}