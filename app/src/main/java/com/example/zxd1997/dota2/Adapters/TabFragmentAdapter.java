package com.example.zxd1997.dota2.Adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import java.util.List;

public class TabFragmentAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragments;

    public TabFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void myDestroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
