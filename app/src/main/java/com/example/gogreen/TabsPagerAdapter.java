package com.example.gogreen;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {
    private final Context mContext;

    public TabsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return UserFragment.newInstance();
            case 1:
                return FriendsListFragment.newInstance();
            default:
                return null;
        }
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return LoginActivity.getUserLogged().getName();
        else
            return "Amigos";

    }

    @Override
    public int getCount() {
        return 2;
    }
}