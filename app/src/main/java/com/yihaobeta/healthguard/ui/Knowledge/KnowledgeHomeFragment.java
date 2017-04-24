package com.yihaobeta.healthguard.ui.Knowledge;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.yihaobeta.healthguard.R;
import com.yihaobeta.healthguard.adapter.ViewPagerAdapter;
import com.yihaobeta.healthguard.base.BaseFragment;
import com.yihaobeta.healthguard.dagger.component.DaggerKnowledgeHomeComponent;
import com.yihaobeta.healthguard.dagger.modules.KnowledgeHomeModule;
import com.yihaobeta.healthguard.ui.Knowledge.Knowledgelist.KnowledgeListFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by yihaobeta on 2017/3/10.
 */

public class KnowledgeHomeFragment extends BaseFragment {

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Inject
    ViewPagerAdapter mPagerAdapter;


    @Override
    protected void setListener() {
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected int attachLayout() {
        return R.layout.layout_fragment_knowledge_home;
    }

    @Override
    protected void handleLogic() {
        handleToolBar(mToolBar, false, getString(R.string.tab_information), null);
        setHasOptionsMenu(true);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(6);
        mTabLayout.setupWithViewPager(mViewPager);
        initTabsAndFragment();
    }

    @Override
    protected void initDagger() {
        DaggerKnowledgeHomeComponent.builder()
                .appComponent(getAppComponent())
                .knowledgeHomeModule(new KnowledgeHomeModule(getFragmentManager()))
                .build()
                .inject(this);
    }

    public void initTabsAndFragment() {
        List<android.support.v4.app.Fragment> fragments = new ArrayList<>();
        String[] titles = getContext().getResources().getStringArray(R.array.tab_item_name);
        for (int i = 0; i < titles.length; i++) {
            fragments.add(KnowledgeListFragment.newInstance(i));
        }
        mPagerAdapter.setItems(fragments, titles);
    }
}
