package com.example.hcm.feihuread.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hcm.feihuread.R;
import com.example.hcm.feihuread.inter.MyItemClickListener;

import com.example.hcm.feihuread.model.*;

import java.util.List;

/**
 * Created by hcm on 2018/4/13.
 */

public class MyLocalTxtAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    private List<BookInfo> datas;
    private MyItemClickListener myItemClickListener;

    public MyLocalTxtAdapter(Context mContext, List<BookInfo> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        view = LayoutInflater.from(mContext).inflate(R.layout.rv_locallist_item, null);
        viewHolder = new MyHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder= (MyHolder) holder;
        myHolder.bookName.setText((CharSequence) datas.get(position).getBookName());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
    public void setOnItemClickListener(MyItemClickListener listener){
        myItemClickListener=listener;
    }
    class MyHolder extends RecyclerView.ViewHolder {




        public MyItemClickListener getMyItemClickListener() {
            return myItemClickListener;
        }

        TextView bookName;

        public MyHolder(View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.tv_book_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(myItemClickListener!=null)
                    {
                        myItemClickListener.onItemClick(view,getPosition());
                    }
                }
            });
        }

    }

}
