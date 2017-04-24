package com.yihaobeta.healthguard.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yihaobeta.healthguard.R;
import com.yihaobeta.healthguard.base.BaseFragment;
import com.yihaobeta.healthguard.ui.profile.favorite.FavoriteListActivity;
import com.yihaobeta.healthguard.ui.profile.history.HistoryActivity;
import com.yihaobeta.healthguard.ui.profile.settings.MoreSettingsActivity;
import com.yihaobeta.healthguard.utils.CacheUtils;
import com.yihaobeta.healthguard.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by yihaobeta on 2017/3/10.
 */

public class ProfileHomeFragment extends BaseFragment {

    @BindView(R.id.ll_favorite)
    LinearLayout ll_favorite;

    @BindView(R.id.rl_profile_clear_cache)
    RelativeLayout rl_clear_cache;

    @BindView(R.id.tv_profile_cache_size)
    TextView mTvCacheSize;

    @Override
    protected void setListener() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int attachLayout() {
        return R.layout.layout_fragment_profile;
    }

    @Override
    protected void handleLogic() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            try {
                mTvCacheSize.setText(CacheUtils.getCacheSize());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void initDagger() {

    }

    @OnClick({R.id.rl_profile_about, R.id.rl_profile_feedback, R.id.rl_profile_update, R.id.ll_settings, R.id.ll_favorite, R.id.rl_profile_clear_cache, R.id.ll_read_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_favorite:
                FavoriteListActivity.launch(getContext());
                break;
            case R.id.ll_read_history:
                HistoryActivity.start(getContext());
                break;

            case R.id.rl_profile_clear_cache:
                Observable.just(null)
                        .doOnNext(new Action1<Object>() {
                            @Override
                            public void call(Object obj) {
                                CacheUtils.clearCache();
                            }
                        })
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Object>() {
                            @Override
                            public void onCompleted() {
                                mTvCacheSize.setText(CacheUtils.getCacheSize());
                                ToastUtils.showToast(R.string.profile_item_cache_size);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Object o) {

                            }
                        });


                break;

            case R.id.ll_settings:
                MoreSettingsActivity.start(getContext());
                break;
            case R.id.rl_profile_update:
                ToastUtils.showToast(getString(R.string.already_lasted_version));
                break;
            case R.id.rl_profile_feedback:
                onContact(getContext());
                break;
            case R.id.rl_profile_about:
                Intent intent = new Intent(getContext(), AboutActivity.class);
                getActivity().startActivity(intent);
                break;
        }
    }

    public void onContact(Context context) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:yihaoBeta@163.com"));
        intent.putExtra(Intent.EXTRA_EMAIL, "");
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.welcome_contact_me));
        context.startActivity(Intent.createChooser(intent, getString(R.string.choose_email_app)));
    }
}
