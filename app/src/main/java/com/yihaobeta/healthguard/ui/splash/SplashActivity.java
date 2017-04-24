package com.yihaobeta.healthguard.ui.splash;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.orhanobut.logger.Logger;
import com.yihaobeta.healthguard.R;
import com.yihaobeta.healthguard.base.BaseActivity;
import com.yihaobeta.healthguard.common.RxJavaUtils;
import com.yihaobeta.healthguard.ui.main.MainActivity;
import com.yihaobeta.healthguard.utils.NetWorkUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by yihaobeta on 2017/3/24.
 */

public class SplashActivity extends BaseActivity {

    @BindView(R.id.btn_skip)
    Button mBtn_skip;
    private boolean mIsSkip = false;

    @Override
    protected int attachLayout() {
        return R.layout.layout_activity_splash;
    }

    @Override
    protected void initDagger() {

    }

    @Override
    protected void init() {
        Logger.d("wifi--" + NetWorkUtils.isWifiConnected(this));
    }

    @Override
    protected void setListener() {
        mBtn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skip();
            }
        });
    }

    @Override
    protected void handleLogic() {
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .map(new Func1<Long, Integer>() {
                    @Override
                    public Integer call(Long aLong) {
                        return 5 - aLong.intValue();
                    }
                })
                .take(6)
                .compose(RxJavaUtils.<Integer>applySchedulers())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        skip();
                    }

                    @Override
                    public void onError(Throwable e) {
                        skip();
                    }

                    @Override
                    public void onNext(Integer integer) {
                        mBtn_skip.setText("跳过 " + integer);
                    }
                });

    }

    private void skip() {
        if (!mIsSkip) {
            mIsSkip = true;
            finish();
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.hold, R.anim.zoom_in_exit);
        }
    }

    @Override
    public void onBackPressed() {
        // 不响应后退键
        return;
    }
}
