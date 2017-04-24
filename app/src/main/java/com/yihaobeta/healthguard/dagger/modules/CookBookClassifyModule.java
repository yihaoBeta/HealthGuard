package com.yihaobeta.healthguard.dagger.modules;

import android.content.Context;

import com.yihaobeta.healthguard.adapter.CookBookClassifyAdapter;
import com.yihaobeta.healthguard.dagger.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yihaobeta on 2017/4/22 0022.
 */

@Module
public class CookBookClassifyModule {
    private Context mContext;

    public CookBookClassifyModule(Context context) {
        mContext = context;
    }

    @Provides
    @ActivityScope
    public CookBookClassifyAdapter provideAdapter() {
        return new CookBookClassifyAdapter(mContext);
    }
}
