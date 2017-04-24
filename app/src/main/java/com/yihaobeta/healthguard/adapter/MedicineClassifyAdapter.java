package com.yihaobeta.healthguard.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yihaobeta.healthguard.R;
import com.yihaobeta.healthguard.beans.MedicineClassify;
import com.yihaobeta.healthguard.utils.AssetsHelper;
import com.yihaobeta.healthguard.utils.GsonHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yihaobeta on 2017/3/12.
 * 药品分类适配器，集成选中模式
 */

public class MedicineClassifyAdapter extends RecyclerView.Adapter<MedicineClassifyAdapter.MyViewHolder> implements View.OnClickListener {


    private LayoutInflater mInflater;
    private List<MedicineClassify> mDataSet;
    private List<Boolean> clickStateList;//纪录被点击的item
    private Context mContext;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public MedicineClassifyAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mDataSet = GsonHelper.convertEntities(AssetsHelper.readData(context, "medicine_classify"), MedicineClassify.class);
        clickStateList = new ArrayList<>();
        for (int i = 0; i < mDataSet.size(); i++) {
            //默认第一个为选中状态
            if (i == 0) {
                clickStateList.add(true);
            } else {
                clickStateList.add(false);
            }
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_item_medicine, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        viewHolder.tv_title = (TextView) view.findViewById(R.id.tv_medicine_classify_name);
        viewHolder.view_divider = view.findViewById(R.id.view_divider);
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
                holder.tv_title.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                holder.view_divider.setVisibility(View.VISIBLE);
            } else {
                holder.tv_title.setTextColor(Color.parseColor("#000000"));
                holder.view_divider.setVisibility(View.GONE);
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
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(view, (MedicineClassify) view.getTag());

        }


    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, MedicineClassify item);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title;
        View view_divider;

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}