package com.yihaobeta.healthguard.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by yihaobeta on 2017/3/9.
 * activity抽象基类
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected Subscription mSubscription;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(attachLayout());
        ButterKnife.bind(this);
        initDagger();
        init();
        setListener();
        handleLogic();
    }

    //设置layout
    @LayoutRes
    protected abstract int attachLayout();

    //初始化dagger
    protected abstract void initDagger();

    //初始化相关操作
    protected abstract void init();

    //设置监听操作
    protected abstract void setListener();

    //逻辑处理部分
    protected abstract void handleLogic();

    /**
     * ToolBar的操作
     *
     * @param toolbar         toolbar实例
     * @param homeAsUpEnabled
     * @param title           一级标题
     * @param subTitile       二级标题
     */
    protected void handleToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title, String subTitile) {
        if (title != null)
            toolbar.setTitle(title);
        if (subTitile != null)
            toolbar.setSubtitle(subTitile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
    }

    protected void setSubscription(Subscription subscription) {
        this.mSubscription = subscription;
    }

    protected void unsubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
    }

    @Override
    protected void onDestroy() {
        unsubscribe();
        super.onDestroy();
    }
}
