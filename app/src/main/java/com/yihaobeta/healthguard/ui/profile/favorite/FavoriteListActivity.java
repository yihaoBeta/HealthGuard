package com.yihaobeta.healthguard.ui.profile.favorite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.yihaobeta.healthguard.R;
import com.yihaobeta.healthguard.adapter.ViewPagerAdapter;
import com.yihaobeta.healthguard.base.BaseActivity;
import com.yihaobeta.healthguard.common.ConstantValue;
import com.yihaobeta.healthguard.dagger.component.DaggerFavoriteListActivityComponent;
import com.yihaobeta.healthguard.dagger.modules.FavoriteListActivityModule;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by yihaobeta on 2017/3/24.
 */

public class FavoriteListActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    Toolbar mToolbar;

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @Inject
    ViewPagerAdapter adapter;

    public static void launch(Context context) {
        Intent intent = new Intent(context, FavoriteListActivity.class);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.zoom_out_entry, R.anim.hold);
    }

    @Override
    protected int attachLayout() {
        return R.layout.layout_activity_favoritelist;
    }

    @Override
    protected void initDagger() {
        DaggerFavoriteListActivityComponent.builder()
                .favoriteListActivityModule(new FavoriteListActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);
        handleToolBar(mToolbar, true, "我的收藏", null);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void handleLogic() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(FavoriteListFragment.newInstance(ConstantValue.TYPE_KNOWLEDGE));
        fragments.add(FavoriteListFragment.newInstance(ConstantValue.TYPE_COOKBOOK));
        fragments.add(FavoriteListFragment.newInstance(ConstantValue.TYPE_MEDICINE));
        mTabLayout.setupWithViewPager(mViewPager);
        adapter.setItems(fragments, new String[]{getString(R.string.tab_information), getString(R.string.tab_cookbook), getString(R.string.tab_medicine)});
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.zoom_out_exit);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
