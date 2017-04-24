package com.yihaobeta.healthguard.utils;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.yihaobeta.healthguard.R;
import com.yihaobeta.healthguard.adapter.KnowledgeListAdapter;
import com.yihaobeta.healthguard.base.BaseApplication;
import com.yihaobeta.healthguard.beans.KnowledgeList;
import com.yihaobeta.healthguard.common.ConstantValue;
import com.yihaobeta.healthguard.ui.Knowledge.knowledgedetail.KnowledgeDetailActivity;

import java.util.List;

/**
 * description:广告轮播工具
 * author: yihaoBeta
 * date: 2017/3/11 15:52
 * update: 2017/3/11
 * version: v1.0
 */
public final class SliderHelper {

    private static final String SLIDER_KEY = "SliderKey";
    private static final String SLIDER_TITLE = "SliderTitle";
    private static SliderHelper sInstance;
    private Context mContext;
    private View headerView;

    private SliderHelper(Context context) {
        this.mContext = context;
    }

    public static SliderHelper getInstance(Context context) {
        if (null == sInstance) {
            synchronized (SliderHelper.class) {
                if (null == sInstance) {
                    sInstance = new SliderHelper(context);
                }
            }
        }
        return sInstance;
    }

    /**
     * @param context
     * @param sliderLayout
     * @param datas
     */
    private void initAdSlider(final Context context, SliderLayout sliderLayout, List<KnowledgeList.KnowledgeSummary> datas) {
        for (KnowledgeList.KnowledgeSummary information : datas) {
            TextSliderView textSliderView = new TextSliderView(context);
            // initialize a SliderLayout
            if (!NetWorkUtils.isWifiConnected(context) && PreferencesUtils.isNoPicMode()) {
                textSliderView.image(R.drawable.ic_default);
            } else {
                textSliderView.image(ConstantValue.BASE_URL_IMG + information.getImg());
            }
            textSliderView.description(information.getTitle())
                    //.image(ConstantValue.BASE_URL_IMG+information.getImg())
                    .empty(R.drawable.ic_default)
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            int id = slider.getBundle().getInt(SLIDER_KEY);
                            String title = slider.getBundle().getString(SLIDER_TITLE);
                            KnowledgeDetailActivity.start(context, id, title);
                        }
                    });

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putInt(SLIDER_KEY, information.getId());
            textSliderView.getBundle().putString(SLIDER_TITLE, information.getTitle());
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(4000);
    }

    public View createHeaderView(KnowledgeListAdapter adapter) {
        headerView = LayoutInflater.from(BaseApplication.getContext()).inflate(R.layout.layout_header_knowledge_list, null);
        SliderLayout mAdSlider = (SliderLayout) headerView.findViewById(R.id.slider_ads);
        initAdSlider(mContext, mAdSlider, adapter.getData().subList(0, 4));
        return headerView;
    }

    public View getHeaderView() {
        return headerView;
    }
}
