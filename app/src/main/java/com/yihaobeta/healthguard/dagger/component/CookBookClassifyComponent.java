package com.yihaobeta.healthguard.dagger.component;

import com.yihaobeta.healthguard.dagger.modules.CookBookClassifyModule;
import com.yihaobeta.healthguard.dagger.scope.ActivityScope;
import com.yihaobeta.healthguard.ui.cookbook.CookBookClassifyActivity;

import dagger.Component;

/**
 * Created by yihaobeta on 2017/4/22 0022.
 */
@ActivityScope
@Component(modules = CookBookClassifyModule.class)
public interface CookBookClassifyComponent {
    void inject(CookBookClassifyActivity activity);
}
