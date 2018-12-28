package com.test.newshop1.ui.categoryActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class CategoryFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Integer> parentIds = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    CategoryFragmentPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    public void addPages(Integer id, String title) {
        parentIds.add(id);
        titles.add(title);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return SubCatFragment.newInstance(parentIds.get(position));
    }

    @Override
    public int getCount() {
        return parentIds == null ? 0 : parentIds.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
