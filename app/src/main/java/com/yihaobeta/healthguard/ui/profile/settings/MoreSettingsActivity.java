package com.yihaobeta.healthguard.ui.profile.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by yihaobeta on 2017/4/4.
 */

public class MoreSettingsActivity extends Activity {
    public static void start(Context context) {
        Intent intent = new Intent(context, MoreSettingsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MoreSettingsFragment()).commit();
    }
}
