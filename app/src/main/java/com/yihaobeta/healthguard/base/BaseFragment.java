package com.yihaobeta.healthguard.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yihaobeta.healthguard.dagger.component.AppComponent;

import butterknife.ButterKnife;
import rx.Subscription;

/**
 * description: Fragment基类
 * author: yihaoBeta
 * date: 2017/4/13 0013 12:55
 * update: 2017/4/13 0013
 * version: v1.0
 */

public abstract class BaseFragment extends android.support.v4.app.Fragment {

    private View mRootView;
    private boolean bIsFirstCreate = false;
    private Context mContext;
    private Subscription mSubscription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null)
            mRootView = inflater.inflate(attachLayout(), null);

        ButterKnife.bind(this, mRootView);
        initDagger();
        initViews();
        setListener();
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }

        return mRootView;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint() && mRootView != null && !bIsFirstCreate) {
            bIsFirstCreate = true;
            handleLogic();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (getUserVisibleHint() && mRootView != null && !bIsFirstCreate) {
            bIsFirstCreate = true;
            handleLogic();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && isVisible() && mRootView != null && !bIsFirstCreate) {
            bIsFirstCreate = true;
            handleLogic();
        } else {
            super.setUserVisibleHint(isVisibleToUser);
        }
    }

    protected AppComponent getAppComponent() {
        return BaseApplication.getAppComponent();
    }

    protected void handleToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title, String subTitle) {
        ((BaseActivity) getActivity()).handleToolBar(toolbar, homeAsUpEnabled, title, subTitle);
    }

    /**
     * 设置监听操作
     */
    protected abstract void setListener();

    /**
     * 初始化views
     */
    protected abstract void initViews();

    /**
     * 设置layout
     *
     * @return
     */
    @LayoutRes
    protected abstract int attachLayout();

    /**
     * 逻辑处理
     */
    protected abstract void handleLogic();

    /**
     * 初始化dagger
     */
    protected abstract void initDagger();

    public void setSubscription(Subscription subscription) {
        this.mSubscription = subscription;
    }

    protected void unsubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
    }

    @Override
    public void onDestroy() {
        unsubscribe();
        super.onDestroy();
    }
}
