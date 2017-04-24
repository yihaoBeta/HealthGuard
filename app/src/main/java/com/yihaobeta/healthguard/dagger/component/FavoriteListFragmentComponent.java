package com.yihaobeta.healthguard.dagger.component;

import com.yihaobeta.healthguard.dagger.modules.FavoriteListFragmentModule;
import com.yihaobeta.healthguard.dagger.scope.FragmentScope;
import com.yihaobeta.healthguard.ui.profile.favorite.FavoriteListFragment;

import dagger.Component;

/**
 * Created by yihaobeta on 2017/4/22 0022.
 */
@Component(dependencies = AppComponent.class, modules = FavoriteListFragmentModule.class)
@FragmentScope
public interface FavoriteListFragmentComponent {
    void inject(FavoriteListFragment fragment);
}
