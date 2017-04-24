package com.yihaobeta.healthguard.dagger.component;

import com.yihaobeta.healthguard.dagger.modules.CookBookListModule;
import com.yihaobeta.healthguard.dagger.scope.FragmentScope;
import com.yihaobeta.healthguard.ui.cookbook.CookBookListFragment;

import dagger.Component;

/**
 * Created by yihaobeta on 2017/4/22 0022.
 */
@Component(dependencies = AppComponent.class, modules = CookBookListModule.class)
@FragmentScope
public interface CookBookListComponent {
    void inject(CookBookListFragment fragment);
}
