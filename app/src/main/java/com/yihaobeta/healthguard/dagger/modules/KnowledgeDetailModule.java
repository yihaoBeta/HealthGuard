package com.yihaobeta.healthguard.dagger.modules;

import com.yihaobeta.healthguard.base.IDetailView;
import com.yihaobeta.healthguard.dagger.scope.ActivityScope;
import com.yihaobeta.healthguard.ui.Knowledge.knowledgedetail.KnowledgeDetailPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yihaobeta on 2017/3/11.
 */
@Module
public class KnowledgeDetailModule {
    private IDetailView mIView;

    public KnowledgeDetailModule(IDetailView IView) {
        mIView = IView;
    }

    @Provides
    @ActivityScope
    public KnowledgeDetailPresenter providePresenter() {
        return new KnowledgeDetailPresenter(mIView);
    }
}
