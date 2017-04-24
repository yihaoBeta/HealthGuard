package com.yihaobeta.healthguard.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yihaobeta.healthguard.R;
import com.yihaobeta.healthguard.beans.MedicineList;
import com.yihaobeta.healthguard.common.ConstantValue;
import com.yihaobeta.healthguard.utils.GlideUtils;

import java.util.List;

/**
 * 药品列表适配器
 * Created by yihaobeta on 2017/3/12.
 */

public class MedicineListAdapter extends BaseQuickAdapter<MedicineList.MedicineSummary, BaseViewHolder> {
    private Context mContext;

    public MedicineListAdapter(Context context, List<MedicineList.MedicineSummary> data) {
        super(R.layout.layout_item_medicine_list, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MedicineList.MedicineSummary item) {
        helper.setText(R.id.tv_title, item.getName());
        helper.setText(R.id.tv_description, item.getDescription());
        helper.setText(R.id.tv_type, item.getType());
        helper.setText(R.id.tv_price, item.getPrice() == 0 ? mContext.getString(R.string.price_unknown) : mContext.getString(R.string.price) + item.getPrice());
        GlideUtils.showPicture(ConstantValue.BASE_URL_IMG + item.getImg()
                , (ImageView) helper.getView(R.id.iv_img));
    }
}
