package com.example.hcm.feihuread.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hcm.feihuread.R;
import com.example.hcm.feihuread.model.BookChapterDetail;
import com.example.hcm.feihuread.model.BookTypeAbout;

import java.util.List;

/**
 * Created by Administrator on 2018/4/6 0006.
 */

public class BookChapterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
	private List<BookChapterDetail> datas;
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
	public BookChapterAdapter(Context context,List<BookChapterDetail> datas) {
		mContext=context;
		this.datas=datas;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = null;
		RecyclerView.ViewHolder viewHolder = null;
		view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_chapters,parent, false);
		viewHolder = new VHTYPE(view);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		String chapterTitle=datas.get(position).getCurrentChapterTitle();
		BookChapterAdapter.VHTYPE vh = (BookChapterAdapter.VHTYPE) holder;
		vh.mChapterTitle.setText(chapterTitle.trim());
	}

	@Override
	public int getItemCount() {
		return datas.size();
	}

	public static class VHTYPE extends RecyclerView.ViewHolder {
		TextView mChapterTitle;

		// ImageView mChapterTitle;

		public VHTYPE(View itemView) {
			super(itemView);
			mChapterTitle = (TextView) itemView.findViewById(R.id.txt_content);
		}
	}

}
