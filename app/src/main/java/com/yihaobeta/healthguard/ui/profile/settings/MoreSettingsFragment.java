package com.yihaobeta.healthguard.ui.profile.settings;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.yihaobeta.healthguard.R;
import com.yihaobeta.healthguard.common.ConstantValue;
import com.yihaobeta.healthguard.utils.PreferencesUtils;

/**
 * Created by yihaobeta on 2017/4/4.
 */

public class MoreSettingsFragment extends PreferenceFragment {
    private boolean noPicMode = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

    }

    @Override
    public void onStart() {
        noPicMode = PreferencesUtils.isNoPicMode();
        super.onStart();
    }

    @Override
    public void onStop() {
        if (noPicMode != PreferencesUtils.isNoPicMode()) {
            Intent intent = new Intent(ConstantValue.ACTION_NO_PIC_SETTING_CHANGED);
            getActivity().sendBroadcast(intent);
        }
        super.onStop();
    }
}
