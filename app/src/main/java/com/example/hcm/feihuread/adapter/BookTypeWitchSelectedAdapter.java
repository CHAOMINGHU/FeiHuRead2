package com.example.hcm.feihuread.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hcm.feihuread.R;
import com.example.hcm.feihuread.read.BookTypeAbout;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/6 0006.
 */

public class BookTypeWitchSelectedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<BookTypeAbout> datas;
    private Context mContext;

    //自定义监听事件
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view);
        void onItemLongClick(View view);
    }
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }
    //适配器初始化
    public BookTypeWitchSelectedAdapter(Context context,List<BookTypeAbout> datas) {
        mContext=context;
        this.datas=datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_type_witch_selected_item, null);
        viewHolder = new VHTYPE(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    String bookName=datas.get(position).getBookName();
        VHTYPE vh = (VHTYPE) holder;
        vh.mTextView.setText(bookName);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class VHTYPE extends RecyclerView.ViewHolder {
        TextView mTextView;

        // ImageView mTextView;

        public VHTYPE(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_type_witch_selected);

        }
    }

}
