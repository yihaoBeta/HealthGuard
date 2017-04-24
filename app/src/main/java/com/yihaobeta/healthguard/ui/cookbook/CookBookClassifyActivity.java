package com.yihaobeta.healthguard.ui.cookbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.yihaobeta.healthguard.R;
import com.yihaobeta.healthguard.adapter.CookBookClassifyAdapter;
import com.yihaobeta.healthguard.base.BaseActivity;
import com.yihaobeta.healthguard.beans.CookBookClassify;
import com.yihaobeta.healthguard.dagger.component.DaggerCookBookClassifyComponent;
import com.yihaobeta.healthguard.dagger.modules.CookBookClassifyModule;

import butterknife.BindView;

/**
 * Created by yihaobeta on 2017/3/14.
 */

public class CookBookClassifyActivity extends BaseActivity {

    final FragmentManager mManager = getSupportFragmentManager();
    @BindView(R.id.rv_cookbook_classify)
    RecyclerView rv_classify;
    @BindView(R.id.tool_bar)
    Toolbar mToolbar;
    CookBookClassifyAdapter mAdapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, CookBookClassifyActivity.class);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_right_entry, R.anim.hold);
    }

    @Override
    protected int attachLayout() {
        return R.layout.layout_activity_cookbook_classify;
    }

    @Override
    protected void initDagger() {
        DaggerCookBookClassifyComponent.builder()
                .cookBookClassifyModule(new CookBookClassifyModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {
        handleToolBar(mToolbar, true, getString(R.string.tab_cookbook), "详细分类");
//        mAdapter = new CookBookClassifyAdapter(this);
        rv_classify.setLayoutManager(new LinearLayoutManager(this));
        rv_classify.setItemAnimator(new DefaultItemAnimator());
        rv_classify.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rv_classify.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        mAdapter.setOnItemClickListener(new CookBookClassifyAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, CookBookClassify item) {
                CookBookListFragment fragment = CookBookListFragment.newInstance(item.getId());
                FragmentTransaction ft = mManager.beginTransaction();
                ft.replace(R.id.fl_content_cookbook_list_classify, fragment);
                ft.commitAllowingStateLoss();
            }
        });
    }

    @Override
    protected void handleLogic() {
        CookBookListFragment fragment = CookBookListFragment.newInstance(1);
        FragmentTransaction ft = mManager.beginTransaction();
        ft.replace(R.id.fl_content_cookbook_list_classify, fragment);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
