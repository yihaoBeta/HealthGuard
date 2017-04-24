package com.yihaobeta.healthguard.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yihaobeta.healthguard.R;
import com.yihaobeta.healthguard.common.ConstantValue;
import com.yihaobeta.healthguard.database.entity.UniversalDetailEntity;
import com.yihaobeta.healthguard.utils.GlideUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by yihaobeta on 2017/3/31.
 * 浏览历史列表适配器
 */

public class HistoryListAdapter extends BaseQuickAdapter<UniversalDetailEntity, BaseViewHolder> {

    private Context mContext;
    private SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm");

    public HistoryListAdapter(List<UniversalDetailEntity> data, Context context) {
        super(R.layout.layout_item_history, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, UniversalDetailEntity item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_description, item.getDescription());
        helper.setText(R.id.tv_time, sdf.format(new Date(item.getTimeStamp())));
        String type;
        switch (item.getType()) {
            case ConstantValue.TYPE_COOKBOOK:
                type = mContext.getString(R.string.tab_cookbook);
                break;
            case ConstantValue.TYPE_KNOWLEDGE:
                type = mContext.getString(R.string.tab_information);
                break;
            case ConstantValue.TYPE_MEDICINE:
                type = mContext.getString(R.string.tab_medicine);
                break;
            default:
                type = "UnKnown";
                break;

        }
        helper.setText(R.id.tv_type, type);
        GlideUtils.showPicture(ConstantValue.BASE_URL_IMG + item.getImgUrl()
                , (ImageView) helper.getView(R.id.iv_img));
    }
}
