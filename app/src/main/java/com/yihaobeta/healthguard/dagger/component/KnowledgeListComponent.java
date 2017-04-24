package com.yihaobeta.healthguard.dagger.component;

import com.yihaobeta.healthguard.dagger.modules.KnowledgeListModule;
import com.yihaobeta.healthguard.dagger.scope.FragmentScope;
import com.yihaobeta.healthguard.ui.Knowledge.Knowledgelist.KnowledgeListFragment;

import dagger.Component;

/**
 * Created by yihaobeta on 2017/3/10.
 */

@Component(dependencies = AppComponent.class, modules = KnowledgeListModule.class)
@FragmentScope
public interface KnowledgeListComponent {
    void inject(KnowledgeListFragment fragment);
}
