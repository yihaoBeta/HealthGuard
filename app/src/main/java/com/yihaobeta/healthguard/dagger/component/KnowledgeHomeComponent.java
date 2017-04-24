package com.yihaobeta.healthguard.dagger.component;

import com.yihaobeta.healthguard.dagger.modules.KnowledgeHomeModule;
import com.yihaobeta.healthguard.dagger.scope.FragmentScope;
import com.yihaobeta.healthguard.ui.Knowledge.KnowledgeHomeFragment;

import dagger.Component;

/**
 * Created by yihaobeta on 2017/3/11.
 */
@FragmentScope
@Component(dependencies = AppComponent.class, modules = KnowledgeHomeModule.class)
public interface KnowledgeHomeComponent {
    void inject(KnowledgeHomeFragment fragment);
}
