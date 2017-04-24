package com.yihaobeta.healthguard.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yihaobeta.healthguard.R;
import com.yihaobeta.healthguard.beans.CookBookList;
import com.yihaobeta.healthguard.common.ConstantValue;
import com.yihaobeta.healthguard.utils.GlideUtils;

import java.util.List;

/**
 * Created by yihaobeta on 2017/3/14.
 * 菜谱列表适配器
 */

public class CookBookListAdapter extends BaseQuickAdapter<CookBookList.CookBookSummary, BaseViewHolder> {

    public CookBookListAdapter(List<CookBookList.CookBookSummary> data) {
        super(R.layout.layout_cookbook_list_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CookBookList.CookBookSummary item) {
        helper.setText(R.id.tv_title, item.getName());
        helper.setText(R.id.tv_food, item.getFood());
        helper.setText(R.id.tv_description, item.getDescription());

        GlideUtils.showPicture(ConstantValue.BASE_URL_IMG + item.getImg()
                , (ImageView) helper.getView(R.id.iv_img));
    }
}
