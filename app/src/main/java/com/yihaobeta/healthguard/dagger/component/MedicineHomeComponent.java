package com.yihaobeta.healthguard.dagger.component;

import com.yihaobeta.healthguard.dagger.modules.MedicineHomeModule;
import com.yihaobeta.healthguard.dagger.scope.FragmentScope;
import com.yihaobeta.healthguard.ui.medicine.MedicineHomeFragment;

import dagger.Component;

/**
 * Created by yihaobeta on 2017/4/22 0022.
 */

@Component(dependencies = AppComponent.class, modules = MedicineHomeModule.class)
@FragmentScope
public interface MedicineHomeComponent {
    void inject(MedicineHomeFragment fragment);
}
