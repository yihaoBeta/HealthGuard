package com.yihaobeta.healthguard.dagger.component;

import com.yihaobeta.healthguard.dagger.modules.HistoryModule;
import com.yihaobeta.healthguard.dagger.scope.ActivityScope;
import com.yihaobeta.healthguard.ui.profile.history.HistoryActivity;

import dagger.Component;

/**
 * Created by yihaobeta on 2017/4/22 0022.
 */
@Component(dependencies = AppComponent.class, modules = HistoryModule.class)
@ActivityScope
public interface HistoryComponent {
    void inject(HistoryActivity activity);
}
