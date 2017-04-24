package com.yihaobeta.healthguard.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yihaobeta.healthguard.R;
import com.yihaobeta.healthguard.beans.CookBookClassify;
import com.yihaobeta.healthguard.utils.AssetsHelper;
import com.yihaobeta.healthguard.utils.GsonHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yihaobeta on 2017/3/12.
 * 菜谱分类适配器
 * 集成选中状态
 */

public class CookBookClassifyAdapter extends RecyclerView.Adapter<CookBookClassifyAdapter.MyViewHolder> implements View.OnClickListener {


    private LayoutInflater mInflater;
    private List<CookBookClassify> mDataSet;
    private List<Boolean> clickStateList;//纪录被点击的item
    private Context mContext;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public CookBookClassifyAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mDataSet = GsonHelper.convertEntities(AssetsHelper.readData(context, "cookbook_classify"), CookBookClassify.class);
        clickStateList = new ArrayList<>();
        for (int i = 0; i < mDataSet.size(); i++) {
            if (i == 0) {
                clickStateList.add(true);
            } else {
                clickStateList.add(false);
            }
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_item_cookbook_classify, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        viewHolder.tv_title = (TextView) view.findViewById(R.id.tv_cookbook_classify_title);
        viewHolder.ll_parent = (LinearLayout) view.findViewById(R.id.ll_parent);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if (position < mDataSet.size()) {
            holder.tv_title.setText(mDataSet.get(position).getName());
            //将数据保存在itemView的Tag中，以便点击时进行获取
            holder.itemView.setTag(mDataSet.get(position));
            if (clickStateList.get(position)) {
                holder.ll_parent.setBackgroundColor(mContext.getResources().getColor(R.color.window_background));
            } else {
                holder.ll_parent.setBackgroundColor(mContext.getResources().getColor(R.color.grgray));
            }
        }
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < clickStateList.size(); i++) {
                        clickStateList.set(i, false);
                    }
                    clickStateList.set(position, true);
                    notifyDataSetChanged();
                    mOnItemClickListener.onItemClick(holder.itemView, mDataSet.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //使用getTag方法获取数据
            mOnItemClickListener.onItemClick(view, (CookBookClassify) view.getTag());

        }


    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, CookBookClassify item);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_parent;
        TextView tv_title;

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}