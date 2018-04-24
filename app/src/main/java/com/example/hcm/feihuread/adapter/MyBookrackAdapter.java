package com.example.hcm.feihuread.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hcm.feihuread.R;
import com.example.hcm.feihuread.db.MyBookrack;
import com.example.hcm.feihuread.utils.ImageTools;


import java.util.List;

/**
 * Created by hcm on 2018/3/26.
 */

public class MyBookrackAdapter extends BaseAdapter
{
    private LayoutInflater mInflater;
    private Context mContext;
    private List<MyBookrack> mDatas;

    public MyBookrackAdapter(Context context, List<MyBookrack> mDatas)
    {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount()
    {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder = null;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.book_rack_list_item, parent,
                    false);
            viewHolder = new ViewHolder();
            viewHolder.mTextView = (TextView) convertView
                    .findViewById(R.id.item_text);
           viewHolder.mImageView=convertView.findViewById(R.id.item_img);
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
            viewHolder.mTextView.setText( mDatas.get(position).getBookName());
            String imgs=mDatas.get(position).getBookCover();


            viewHolder.mImageView.setImageBitmap(ImageTools.convertStringToIcon(imgs));


        return convertView;
    }

    private final class ViewHolder
    {
        TextView mTextView;
        ImageView mImageView;
    }

}