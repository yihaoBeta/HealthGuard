package com.yihaobeta.healthguard.adapter;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.yihaobeta.healthguard.R;
import com.yihaobeta.healthguard.common.ConstantValue;
import com.yihaobeta.healthguard.database.entity.UniversalDetailEntity;
import com.yihaobeta.healthguard.utils.GlideUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yihaobeta on 2017/3/24.
 * 收藏列表适配器
 * 集成多选功能
 */

public class FavoriteListAdapter extends BaseQuickAdapter<UniversalDetailEntity, BaseViewHolder> {
    private Context mContext;
    //缓存选中的状态
    private Map<Integer, Boolean> checkStateMap = new HashMap<>();
    //缓存选中的数据
    private List<UniversalDetailEntity> mSelectedList = new ArrayList<>();
    private boolean isMultiSelectMode = false;

    public FavoriteListAdapter(List<UniversalDetailEntity> data, Context context) {
        super(R.layout.layout_favorite_list_item, data);
        mContext = context;
    }

    private void fillCheckStateMap(boolean state) {
        for (int i = 0; i < getItemCount(); i++) {
            checkStateMap.put(i, state);
        }
    }


    @Override
    protected void convert(final BaseViewHolder helper, final UniversalDetailEntity item) {
        final int position = helper.getAdapterPosition();
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_description, item.getDescription());
        CheckBox checkBox = helper.getView(R.id.cb_select);
        GlideUtils.showPicture(ConstantValue.BASE_URL_IMG + item.getImgUrl()
                , (ImageView) helper.getView(R.id.iv_img));
        checkBox.setVisibility(isMultiSelectMode ? View.VISIBLE : View.INVISIBLE);
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.checkbox_scale_in);
        if (isMultiSelectMode) {
            checkBox.startAnimation(animation);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                checkStateMap.put(position, isChecked);
                if (isChecked)
                    mSelectedList.add(item);
                else
                    mSelectedList.remove(item);
            }
        });

        if (checkStateMap.get(position) == null) {
            checkStateMap.put(position, false);
        }
        checkBox.setChecked(checkStateMap.get(position));
    }

    @Override
    public void addData(UniversalDetailEntity data) {
        super.addData(data);
        fillCheckStateMap(false);
    }

    @Override
    public void addData(List<UniversalDetailEntity> newData) {
        super.addData(newData);
        fillCheckStateMap(false);
    }

    public List<UniversalDetailEntity> getSelectedList() {
        return mSelectedList;
    }

    public void enableMultiSelectMode(boolean enable) {
        isMultiSelectMode = enable;
        fillCheckStateMap(false);
        mSelectedList.clear();
        notifyDataSetChanged();
    }

    //点击item选中CheckBox
    public void setSelectItem(int position) {
        //对当前状态取反
        if (checkStateMap.get(position)) {
            checkStateMap.put(position, false);
        } else {
            checkStateMap.put(position, true);
        }
        notifyItemChanged(position);
    }

    public void selectAll() {
        if (isMultiSelectMode) {
            fillCheckStateMap(true);
            notifyDataSetChanged();
        }
    }

    public void selectNone() {
        if (isMultiSelectMode) {
            fillCheckStateMap(false);
            notifyDataSetChanged();
        }
    }

    public boolean isMultiSelectMode() {
        return isMultiSelectMode;
    }

    public void confirmSelectedListRemove() {
        /**
         * 倒序删除的原因：remove()以后，mData会变少，而保存的map序号不变，导致
         * 问题出现
         * 此处可优化
         **/
        for (int i = checkStateMap.size() - 1; i >= 0; i--) {
            Logger.d(checkStateMap.get(i));
            if (checkStateMap.get(i)) {
                remove(i);
            }
        }
    }
}
