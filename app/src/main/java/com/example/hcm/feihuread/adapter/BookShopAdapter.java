package com.example.hcm.feihuread.adapter;

import java.util.List;
import java.util.Map;

import com.bumptech.glide.Glide;
import com.example.hcm.feihuread.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BookShopAdapter extends BaseAdapter
{

	private List<Map<String, Object>> datas;
	private Context mContext;
	private LayoutInflater inflater;           //��ͼ����
	final int TYPE_1 = 0;
	final int TYPE_2 = 1;
	final int TYPE_3 = 2;

	public BookShopAdapter(List<Map<String, Object>> datas, Context mContext)
	{
		this.datas = datas;
		this.mContext = mContext;
		 inflater = LayoutInflater.from(mContext);   
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
	public int getItemViewType(int position)
	{
		int p=position;
		if(p<2)
			return TYPE_1;
		else if(p==datas.size()-1)
			return TYPE_3;
		else 
			return TYPE_2;
	
	}
	
	@Override
	public int getViewTypeCount()
	{
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder1 h1 = null;
		ViewHolder2 h2 = null;
	    ViewHolder3 h3 = null;
	    int type=getItemViewType(position);
		if (convertView == null)
		{
			// ʹ���Զ�����б�������Ϊlayout
//			convertView = LayoutInflater.from(mContext).inflate(
//					R.layout.books_main, null);
			 inflater = LayoutInflater.from(mContext);
			 switch (type)
			{
			case TYPE_1:
				  convertView = inflater.inflate(R.layout.books_main,parent, false);
				// ����findview�Ĵ���
					h1 = new ViewHolder1();
					// ��ʼ�����ֵ�Ԫ��
					h1.title = (TextView) convertView.findViewById(R.id.txt_title);
					h1.author=(TextView) convertView.findViewById(R.id.txt_autor);
					h1.intro=(TextView) convertView.findViewById(R.id.txt_intro);
					h1.cover=(ImageView) convertView.findViewById(R.id.cover);
					convertView.setTag(h1);
				break;
			case TYPE_2:
				  convertView = inflater.inflate(R.layout.book_lunbo, parent, false);
				  h2=new ViewHolder2();
				//  h2.lunbo=(ImageView) convertView.findViewById(R.id.tv_lunbo);

				  break;
			case TYPE_3:
				  convertView = inflater.inflate(R.layout.book_wuxia,parent, false);
				  h3=new ViewHolder3();
				  //h3.wuxia= (TextView) convertView.findViewById(R.id.txt_wuxia);
				  convertView.setTag(h3);
				  break;
				default:
				break;
			}
			
		}
		else
		{
			switch (type)
			{
			case TYPE_1:
				h1 = (ViewHolder1) convertView.getTag();
				break;
			case TYPE_2:
				h2 = (ViewHolder2) convertView.getTag();
				break;
			case TYPE_3:
				h3 = (ViewHolder3) convertView.getTag();
			default:
				break;
			}
		}
			switch (type)
			{
			case TYPE_1:
				// �Ӵ�����������ṩ���ݲ��Ұ󶨵�ָ����view��
				h1.title.setText(datas.get(position).get("title").toString());
			    h1.author.setText(datas.get(position).get("author").toString());
			    h1.intro.setText(datas.get(position).get("intro").toString());
			    String cover=datas.get(position).get("cover").toString();
			    Glide
			    .with(mContext)
			    .load(cover)
			    .placeholder(R.mipmap.ic_launcher)
			    .into(h1.cover);
				break;
            case TYPE_2:
				
				break;
            case TYPE_3:
            	h3.wuxia.setText("������ְ�");
	            break;

				
			default:
				break;
			}
		
		return convertView;
	}

	static class ViewHolder1
	{
		TextView title;
		TextView author;
		TextView intro;
		ImageView cover;
	}
	 public class ViewHolder2 {
		 ImageView lunbo;
	    }

	    public class ViewHolder3 {
	        TextView wuxia;
	    }
}
