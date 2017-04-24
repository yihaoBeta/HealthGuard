package com.yihaobeta.healthguard.ui.cookbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.yihaobeta.healthguard.R;
import com.yihaobeta.healthguard.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by yihaobeta on 2017/3/15.
 */

public class CookBookListActivity extends BaseActivity {


    @BindView(R.id.tool_bar)
    Toolbar mToolbar;
    private int mId;
    private String mTitle;

    public static void start(Context context, int id, String name) {
        Intent intent = new Intent(context, CookBookListActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("title", name);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_right_entry, R.anim.hold);
    }

    @Override
    protected int attachLayout() {
        return R.layout.layout_activity_cookbook_list;
    }

    @Override
    protected void initDagger() {

    }

    @Override
    protected void init() {
        mId = getIntent().getIntExtra("id", 0);
        mTitle = getIntent().getStringExtra("title");
        handleToolBar(mToolbar, true, getString(R.string.tab_cookbook), mTitle);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void handleLogic() {
        CookBookListFragment fragment = CookBookListFragment.newInstance(mId);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_cookbook_list_content, fragment);
        ft.commit();
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
