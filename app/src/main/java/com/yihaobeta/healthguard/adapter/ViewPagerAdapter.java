package com.yihaobeta.healthguard.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by long on 2016/6/2.
 * ViewPager适配器
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    List<String> mTitles;
    List<Fragment> fragments;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
        mTitles = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    //设置数据（List类型）
    public void setItems(List<Fragment> fragments, List<String> mTitles) {
        this.fragments = fragments;
        this.mTitles = mTitles;
        notifyDataSetChanged();
    }

    //设置数据（数组类型）
    public void setItems(List<Fragment> fragments, String[] mTitles) {
        this.fragments = fragments;
        this.mTitles = Arrays.asList(mTitles);
        notifyDataSetChanged();
    }

    //添加单个数据
    public void addItem(Fragment fragment, String title) {
        fragments.add(fragment);
        mTitles.add(title);
        notifyDataSetChanged();
    }

    //删除指定位置数据
    public void delItem(int position) {
        mTitles.remove(position);
        fragments.remove(position);
        notifyDataSetChanged();
    }

    //根据title删除数据
    public int delItem(String title) {
        int index = mTitles.indexOf(title);
        if (index != -1) {
            delItem(index);
        }
        return index;
    }

    //交换两项数据的位置
    public void swapItems(int fromPos, int toPos) {
        Collections.swap(mTitles, fromPos, toPos);
        Collections.swap(fragments, fromPos, toPos);
        notifyDataSetChanged();
    }

    //修改某个位置的title
    public void modifyTitle(int position, String title) {
        mTitles.set(position, title);
        notifyDataSetChanged();
    }
}
