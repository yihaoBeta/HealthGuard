package com.yihaobeta.healthguard.dagger.component;

import com.yihaobeta.healthguard.dagger.modules.KnowledgeDetailModule;
import com.yihaobeta.healthguard.dagger.scope.ActivityScope;
import com.yihaobeta.healthguard.ui.Knowledge.knowledgedetail.KnowledgeDetailActivity;

import dagger.Component;

/**
 * Created by yihaobeta on 2017/3/11.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = KnowledgeDetailModule.class)
public interface KnowledgeDetailComponent {
    void inject(KnowledgeDetailActivity activity);
}
