package com.yihaobeta.healthguard.dagger.component;

import android.content.Context;

import com.yihaobeta.healthguard.dagger.modules.AppModule;
import com.yihaobeta.healthguard.view.EmptyView;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by yihaobeta on 2017/3/10.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    Context getContext();

    EmptyView getEmptyView();
}
