package com.example.hcm.feihuread.adapter;

import java.util.List;
import java.util.Map;

import com.example.hcm.feihuread.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BookChapterAdapter extends BaseAdapter
{

	private List<Map<String, Object>> datas;
	private Context mContext;
	 public BookChapterAdapter(List<Map<String, Object>> datas, Context mContext) {  
	        this.datas = datas;  
	        this.mContext = mContext;  
	    }  

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return datas.size();
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return datas.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		final ViewHolder holder;
		if(convertView==null)
		{
			//ʹ���Զ�����б�������Ϊlayout
			convertView=LayoutInflater.from(mContext).inflate(R.layout.book_chapters, null);
			//����findview�Ĵ���
			holder=new ViewHolder();
			//��ʼ�����ֵ�Ԫ��
			holder.mTextView=(TextView) convertView.findViewById(R.id.txt_content);
		    convertView.setTag(holder);
		}
		else
			holder=(ViewHolder) convertView.getTag();
		//�Ӵ�����������ṩ���ݲ��Ұ󶨵�ָ����view��
		holder.mTextView.setText(datas.get(position).get("chapters").toString());
		return convertView;
	}
	static class ViewHolder
	{
		TextView mTextView;
	}
}
