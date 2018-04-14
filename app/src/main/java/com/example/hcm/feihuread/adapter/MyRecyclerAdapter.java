package com.example.hcm.feihuread.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.example.hcm.feihuread.R;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Map<String, Object>> datas;
    private Context mContext;
    final int TYPE_T=3;
    final int TYPE_H = 4;
    final int TYPE_1 = 0;
    final int TYPE_2 = 1;
    final int TYPE_3 = 2;
    int i = 0;
    private View mHeaderView;
    private OnItemClickListner onItemClickListner;

    public MyRecyclerAdapter(List<Map<String, Object>> datas, Context mContext) {
        this.datas = datas;
        this.mContext = mContext;

    }

    public View getmHeaderView() {
        return mHeaderView;
    }

    public void setmHeaderView(View mHeaderView) {
        this.mHeaderView = mHeaderView;
        notifyItemInserted(0);//插入下标0位置
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        //跟据position对应的条目返回去对应的样式（Type）
        if ( getItemCount()==0||position==4||position==11) {
            return 3;
        }
        else if (position < 4)
        {
            return 0;
        }
        else if (5 <= position && position < 11) {
            return 1;
        } else return 2;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
//        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
//        if(manager instanceof GridLayoutManager) {
//            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
//            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                @Override
//                public int getSpanSize(int position) {
//                    return getItemViewType(position)==3 ? gridManager.getSpanCount() : 1;
//                }
//            });
//        }

    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {

//        if (holder != null) {
//            Glide.clear(holder.itemView);
//        }
        super.onViewRecycled(holder);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        //根据viewType生成viewHolder
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.books_main, null);
                viewHolder = new VH(view);
                break;
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_wuxia, null);
                // view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_wuxia, null);
                viewHolder = new VH1(view);
                break;
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_three, null);
                //view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_lunbo, null);
                viewHolder = new VH2(view);

                break;
            case 3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_type_title, null);
                //view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_lunbo, null);
                viewHolder = new VH3(view);

                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //根据条目的类型给holder中的控件填充数据
        int itemViewType = getItemViewType(position);
        String title = "", author = "", intro = "", cover = "", href = "";
        String title1 = "", cover1 = "", href1 = "";
        if (position < 4) {
            title = datas.get(position).get("title").toString();
            author = datas.get(position).get("author").toString();
            intro = datas.get(position).get("intro").toString();
            cover = datas.get(position).get("cover").toString();
            href = datas.get(position).get("href").toString();
        } else {
            title1 = datas.get(position).get("title1").toString();
//            cover1=datas.get(position).get("cover1").toString();
        }
        // Log.e("FUCK",title1);
        switch (itemViewType) {
            case 0:
                //   VH vh = (VH) holder;
//                vh.mTextView.setText("类型1");
                VH vh = (VH) holder;

                vh.cover.setTag(R.id.imageid,cover);
                if(vh.cover.getTag(R.id.imageid)!=null&&cover==vh.cover.getTag(R.id.imageid))
                {
                    Glide
                            .with(mContext)
                            .load(cover)
                            .crossFade()//增加图片显示时候的淡入淡出动画
                            .placeholder(R.mipmap.local_cover)
                            .into(vh.cover);
                    vh.mTxtTitle.setText(title);
                    vh.mTxtAuthor.setText(author);
                    vh.mTxtInTro.setText(intro);
                }

                vh.cover.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListner.onItemClick(position);
                    }
                });
                vh.cover.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        onItemClickListner.onItemLongClick(position);
                        return true;
                    }
                });
                break;
            case 1:

                VH1 vh1 = (VH1) holder;
                if(position==5)
                    vh1.cover1.setImageResource(R.mipmap.xh);
                else if (position==6)
                    vh1.cover1.setImageResource(R.mipmap.xx);
                else  if(position==7)
                    vh1.cover1.setImageResource(R.mipmap.ds);
                else  if(position==8)
                    vh1.cover1.setImageResource(R.mipmap.cy);
                else  if(position==9)
                    vh1.cover1.setImageResource(R.mipmap.wy);
                else
                    vh1.cover1.setImageResource(R.mipmap.kh);
                vh1.cover1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListner.onItemClick(position);
                    }
                });
//                vh1.cover1.setTag(R.id.imageid,cover1);
//                if(vh1.cover1.getTag(R.id.imageid)!=null&&cover==vh1.cover1.getTag(R.id.imageid))
//                {
//                    Glide
//                            .with(mContext)
//                            .load(cover1)
//                            .crossFade()//增加图片显示时候的淡入淡出动画
//                            .signature(new StringSignature(UUID.randomUUID().toString()))
//                            .dontAnimate()
//                            .placeholder(R.mipmap.local_cover)
//                            .into(vh1.cover1);
//                    vh1.mTextView.setText(title1);
//                }

                break;
            case 2:
                VH2 vh2 = (VH2) holder;

                vh2.cover1.setTag(R.id.imageid,cover1);
                if(vh2.cover1.getTag(R.id.imageid)!=null&&cover==vh2.cover1.getTag(R.id.imageid))
                {
                    Glide
                            .with(mContext)
                            .load(cover1)
                            .crossFade()//增加图片显示时候的淡入淡出动画
                            .signature(new StringSignature(UUID.randomUUID().toString()))
                            .placeholder(R.mipmap.local_cover)
                            .into(vh2.cover1);
                    vh2.mTextView.setText(title1);
                }

                break;
            case 3:
                VH3 vh3 = (VH3) holder;
                if(position<5){
                    vh3.mTextView.setText("小说分类");
                }else {
                    vh3.mTextView.setText("主页推荐");
                }

                break;
        }
    }

    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;

        //onItemClickListner.onItemClick();
    }

    public interface OnItemClickListner {
        public void onItemClick(int position);

        public void onItemLongClick(int position);
    }

    public static class VH extends RecyclerView.ViewHolder {
        // TextView mTextView;
        TextView mTxtTitle, mTxtAuthor, mTxtInTro;
        ImageView cover;

        public VH(View itemView) {
            super(itemView);
            //    mTextView = (TextView) itemView.findViewById(R.id.txt_title);
            mTxtTitle = (TextView) itemView.findViewById(R.id.txt_title);
            mTxtAuthor = itemView.findViewById(R.id.txt_autor);
            mTxtInTro = itemView.findViewById(R.id.txt_intro);
            cover = itemView.findViewById(R.id.cover);
            cover.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }

    public class VH1 extends RecyclerView.ViewHolder {
        TextView mTextView;
        ImageView cover1;

        public VH1(View itemView) {
            super(itemView);
           // mTextView = (TextView) itemView.findViewById(R.id.txt_wuxia);
            cover1 = itemView.findViewById(R.id.cover1);
            // cover1.setScaleType(ImageView.ScaleType.FIT_CENTER);
            // mTextView = (TextView) itemView.findViewById(R.id.txt_wuxia);
        }
    }

    public class VH2 extends RecyclerView.ViewHolder {
        TextView mTextView;
        ImageView cover1;
        // ImageView mTextView;

        public VH2(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.txt_title2);
            cover1 = itemView.findViewById(R.id.cover2);
            cover1.setScaleType(ImageView.ScaleType.FIT_CENTER);
            //  mTextView = (ImageView) itemView.findViewById(R.id.tv_lunbo);
        }
    }
    public class VH3 extends RecyclerView.ViewHolder {
        TextView mTextView;

        // ImageView mTextView;

        public VH3(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.book_type);

        }
    }

}