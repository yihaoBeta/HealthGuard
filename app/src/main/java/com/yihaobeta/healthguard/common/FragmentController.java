package com.yihaobeta.healthguard.common;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.yihaobeta.healthguard.ui.Knowledge.KnowledgeHomeFragment;
import com.yihaobeta.healthguard.ui.cookbook.CookBookHomeFragment;
import com.yihaobeta.healthguard.ui.medicine.MedicineHomeFragment;
import com.yihaobeta.healthguard.ui.profile.ProfileHomeFragment;

import java.util.ArrayList;


/**
 * 主界面Fragment控制器
 */
public class FragmentController {

    private static FragmentController controller;
    private static boolean isReload;
    private int containerId;
    private FragmentManager fm;
    private ArrayList<Fragment> fragments;

    private FragmentController(FragmentActivity activity, int containerId) {
        this.containerId = containerId;
        fm = activity.getSupportFragmentManager();
        initFragment();
    }

    public static FragmentController getInstance(FragmentActivity activity, int containerId, boolean isReload) {
        FragmentController.isReload = isReload;
        if (controller == null) {
            controller = new FragmentController(activity, containerId);
        }
        return controller;
    }

    public static void onDestroy() {
        controller = null;
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        if (isReload) {

            fragments.add(new KnowledgeHomeFragment());
            fragments.add(new CookBookHomeFragment());
            fragments.add(new MedicineHomeFragment());
            fragments.add(new ProfileHomeFragment());

            FragmentTransaction ft = fm.beginTransaction();
            for (int i = 0; i < fragments.size(); i++) {
                ft.add(containerId, fragments.get(i), "" + i);
            }
            ft.commit();

        } else {
            for (int i = 0; i < 5; i++) {
                fragments.add(fm.findFragmentByTag(i + ""));
            }
        }
    }

    public void showFragment(int position) {
        hideFragments();
        Fragment fragment = fragments.get(position);
        FragmentTransaction ft = fm.beginTransaction();
        //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.show(fragment);
        ft.commitAllowingStateLoss();
    }

    public void hideFragments() {
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                ft.hide(fragment);
            }
        }
        ft.commitAllowingStateLoss();
    }

    public Fragment getFragment(int position) {
        return fragments.get(position);
    }
}