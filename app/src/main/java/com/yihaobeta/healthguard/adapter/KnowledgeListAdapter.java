package com.yihaobeta.healthguard.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yihaobeta.healthguard.R;
import com.yihaobeta.healthguard.beans.KnowledgeList;
import com.yihaobeta.healthguard.common.ConstantValue;
import com.yihaobeta.healthguard.utils.GlideUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by yihaobeta on 2017/3/10.
 * 健康知识列表适配器
 */

public class KnowledgeListAdapter extends BaseQuickAdapter<KnowledgeList.KnowledgeSummary, BaseViewHolder> {

    Context mContext;
    SimpleDateFormat mFormat = new SimpleDateFormat("yy-MM-dd HH:mm");

    public KnowledgeListAdapter(Context context) {
        super(R.layout.layout_knowledge_list_item, new ArrayList<KnowledgeList.KnowledgeSummary>());
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, KnowledgeList.KnowledgeSummary item) {

        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_source, item.getDescription());
        helper.setText(R.id.tv_time, mFormat.format(item.getTime()));
        GlideUtils.showPicture(ConstantValue.BASE_URL_IMG + item.getImg()
                , (ImageView) helper.getView(R.id.iv_img));

    }
}
