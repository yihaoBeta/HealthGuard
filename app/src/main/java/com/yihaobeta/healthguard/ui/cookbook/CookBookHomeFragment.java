package com.yihaobeta.healthguard.ui.cookbook;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yihaobeta.healthguard.R;
import com.yihaobeta.healthguard.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yihaobeta on 2017/3/10.
 */

public class CookBookHomeFragment extends BaseFragment {


    @BindView(R.id.tool_bar)
    Toolbar mToolbar;

    @Override
    protected void setListener() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int attachLayout() {
        return R.layout.layout_fragment_cookbook_home;
    }

    @Override
    protected void handleLogic() {
        handleToolBar(mToolbar, false, getString(R.string.tab_cookbook), null);
        Fragment hotListFragment = CookBookListFragment.newInstance(1);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_content_cooklist, hotListFragment);
        ft.commit();
    }

    @Override
    protected void initDagger() {

    }

    @OnClick({R.id.btn_beauty, R.id.btn_healthcare, R.id.btn_slim, R.id.btn_more})
    void onClickBtn(View view) {
        switch (view.getId()) {
            case R.id.btn_beauty:
                CookBookListActivity.start(getContext(), 1, "美容菜谱");
                break;
            case R.id.btn_healthcare:
                CookBookListActivity.start(getContext(), 15, "养生菜谱");
                break;
            case R.id.btn_slim:
                CookBookListActivity.start(getContext(), 10, "瘦身菜谱");
                break;
            case R.id.btn_more:
                CookBookClassifyActivity.start(getContext());
                break;
            default:
                break;
        }
    }

}
