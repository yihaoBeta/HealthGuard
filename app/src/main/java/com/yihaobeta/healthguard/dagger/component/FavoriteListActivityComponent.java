package com.yihaobeta.healthguard.dagger.component;

import com.yihaobeta.healthguard.dagger.modules.FavoriteListActivityModule;
import com.yihaobeta.healthguard.dagger.scope.ActivityScope;
import com.yihaobeta.healthguard.ui.profile.favorite.FavoriteListActivity;

import dagger.Component;

/**
 * Created by yihaobeta on 2017/4/22 0022.
 */

@ActivityScope
@Component(modules = FavoriteListActivityModule.class)
public interface FavoriteListActivityComponent {
    void inject(FavoriteListActivity activity);
}
