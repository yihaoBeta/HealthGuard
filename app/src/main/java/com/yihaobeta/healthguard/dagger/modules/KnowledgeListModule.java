package com.yihaobeta.healthguard.dagger.modules;

import com.yihaobeta.healthguard.adapter.KnowledgeListAdapter;
import com.yihaobeta.healthguard.dagger.scope.FragmentScope;
import com.yihaobeta.healthguard.ui.Knowledge.Knowledgelist.KnowledgeListFragment;
import com.yihaobeta.healthguard.ui.Knowledge.Knowledgelist.KnowledgeListPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yihaobeta on 2017/3/10.
 */

@Module
public class KnowledgeListModule {

    private KnowledgeListFragment mView;

    public KnowledgeListModule(KnowledgeListFragment view) {
        mView = view;
    }

    @Provides
    KnowledgeListFragment getView() {
        return mView;
    }

    @FragmentScope
    @Provides
    public KnowledgeListPresenter providePresenter(KnowledgeListFragment view) {
        return new KnowledgeListPresenter(view);
    }

    @FragmentScope
    @Provides
    public KnowledgeListAdapter provideAdapter() {
        return new KnowledgeListAdapter(mView.getContext());
    }
}
