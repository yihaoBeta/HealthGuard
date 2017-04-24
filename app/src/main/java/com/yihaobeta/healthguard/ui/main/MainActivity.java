package com.yihaobeta.healthguard.ui.main;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yihaobeta.healthguard.R;
import com.yihaobeta.healthguard.base.BaseActivity;
import com.yihaobeta.healthguard.common.FragmentController;
import com.yihaobeta.healthguard.dagger.component.DaggerMainActivityComponent;
import com.yihaobeta.healthguard.dagger.modules.MainActivityModule;
import com.yihaobeta.healthguard.view.BottomNavigationViewEx;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Observer;

import static com.yihaobeta.healthguard.base.BaseApplication.getAppComponent;


public class MainActivity extends BaseActivity {
    @BindView(R.id.bottomNav)
    BottomNavigationViewEx bottomNav;

    @Inject
    FragmentController mFragmentManager;
    private long mExitTime = 0;


    @Override
    protected int attachLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initDagger() {
        DaggerMainActivityComponent.builder()
                .appComponent(getAppComponent())
                .mainActivityModule(new MainActivityModule(this, R.id.fl_content))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {
        mFragmentManager.showFragment(0);
        bottomNav.enableAnimation(false);
        bottomNav.enableShiftingMode(false);
        bottomNav.enableItemShiftingMode(false);
        requestPermissions();
    }

    private void requestPermissions() {
        new RxPermissions(this).request(Manifest.permission.CAMERA, Manifest.permission.VIBRATE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            Logger.d("权限获取完成");
                        } else {
                            Logger.d("权限没有授权");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }

    @Override
    protected void setListener() {
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab_information:
                        mFragmentManager.showFragment(0);
                        break;
                    case R.id.tab_cookbook:
                        mFragmentManager.showFragment(1);
                        break;
                    case R.id.tab_medicine:
                        mFragmentManager.showFragment(2);
                        break;
                    case R.id.tab_profile:
                        mFragmentManager.showFragment(3);
                        break;

                }

                return true;
            }
        });
    }

    @Override
    protected void handleLogic() {
        bottomNav.setCurrentItem(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FragmentController.onDestroy();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    /**
     * 退出
     */
    private void exit() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        exit();
    }
}