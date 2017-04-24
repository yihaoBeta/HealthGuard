package com.yihaobeta.healthguard.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.yihaobeta.healthguard.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 可复用的空视图view，实现多种提示信息及重试机制
 * Created by yihaobeta on 2017/3/11.
 */

public class EmptyView extends FrameLayout {
    public static final int STATUS_HIDE = 1001;
    public static final int STATUS_LOADING = 1;
    public static final int STATUS_NO_NET = 2;
    public static final int STATUS_NO_DATA = 3;
    @BindView(R.id.tv_net_error)
    TextView mTvEmptyMessage;
    @BindView(R.id.rl_empty_container)
    View mRlEmptyContainer;
    @BindView(R.id.empty_loading)
    SpinKitView mEmptyLoading;
    @BindView(R.id.empty_layout)
    FrameLayout mEmptyLayout;
    private Context mContext;
    private OnRetryListener mOnRetryListener;
    private int mEmptyStatus = STATUS_LOADING;
    private int mBgColor;

    public EmptyView(@NonNull Context context) {
        this(context, null);
    }

    public EmptyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.EmptyLayout);
        try {
            mBgColor = a.getColor(R.styleable.EmptyLayout_background_color, Color.WHITE);
        } finally {
            a.recycle();
        }
        View.inflate(mContext, R.layout.empty_layout, this);
        ButterKnife.bind(this);
        mEmptyLayout.setBackgroundColor(Color.WHITE);
        changeView();
    }

    public void hide() {
        mEmptyStatus = STATUS_HIDE;
        changeView();
    }

    /**
     * 获取状态
     *
     * @return 状态
     */
    public int getEmptyStatus() {
        return mEmptyStatus;
    }

    /**
     * 设置状态
     *
     * @param emptyStatus
     */
    public void setEmptyStatus(@EmptyStatus int emptyStatus) {
        mEmptyStatus = emptyStatus;
        changeView();
    }

    /**
     * 设置异常消息
     *
     * @param msg 显示消息
     */
    public void setEmptyMessage(String msg) {
        mTvEmptyMessage.setText(msg);
    }

    public void hideErrorIcon() {
        mTvEmptyMessage.setCompoundDrawables(null, null, null, null);
    }


    public void setLoadingIcon(Sprite d) {
        mEmptyLoading.setIndeterminateDrawable(d);
    }

    private void changeView() {
        switch (mEmptyStatus) {
            case STATUS_LOADING:
                setVisibility(VISIBLE);
                mRlEmptyContainer.setVisibility(GONE);
                mEmptyLoading.setVisibility(VISIBLE);
                break;
            case STATUS_NO_DATA:
                setVisibility(VISIBLE);
                Drawable noDataImg = mContext.getResources().getDrawable(R.drawable.no_data);
                mTvEmptyMessage.setCompoundDrawablesWithIntrinsicBounds(null, noDataImg, null, null);
                mEmptyLoading.setVisibility(GONE);
                mRlEmptyContainer.setVisibility(VISIBLE);
                break;
            case STATUS_NO_NET:
                setVisibility(VISIBLE);
                Drawable noNetImg = mContext.getResources().getDrawable(R.mipmap.no_net);
                mTvEmptyMessage.setCompoundDrawablesWithIntrinsicBounds(null, noNetImg, null, null);
                mEmptyLoading.setVisibility(GONE);
                mRlEmptyContainer.setVisibility(VISIBLE);
                break;
            case STATUS_HIDE:
                setVisibility(GONE);
                break;
        }
    }

    public void setRetryListener(OnRetryListener retryListener) {
        this.mOnRetryListener = retryListener;
    }

    @OnClick(R.id.tv_net_error)
    public void onClick() {
        if (mOnRetryListener != null) {
            mOnRetryListener.onRetry();
        }
    }

    /**
     * 点击重试监听器
     */
    public interface OnRetryListener {
        void onRetry();
    }


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATUS_LOADING, STATUS_NO_NET, STATUS_NO_DATA})
    public @interface EmptyStatus {
    }
}
