package com.example.warehouse.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> listFragment = new ArrayList<>();
    private final ArrayList<String> listFragmentTitle = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return listFragment.get(i);
    }

    @Override
    public int getCount() {
        return listFragmentTitle.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listFragmentTitle.get(position);
    }

    public void addFragment(Fragment fragment, String title){
        listFragment.add(fragment);
        listFragmentTitle.add(title);
    }
}
