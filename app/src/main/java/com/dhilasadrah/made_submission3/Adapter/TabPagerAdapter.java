package com.dhilasadrah.made_submission3.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dhilasadrah.made_submission3.Fragment.NowPlaying;
import com.dhilasadrah.made_submission3.Fragment.Upcoming;
import com.dhilasadrah.made_submission3.R;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    private Context context;

    public TabPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public CharSequence getPageTitle(int i) {
        String[] title = new String[]{
                context.getString(R.string.now_playing),
                context.getString(R.string.upcoming)
        };
        return title[i];
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment;
        switch (i) {
            case 0:
                fragment = new NowPlaying();
                break;
            case 1:
                fragment = new Upcoming();
                break;
            default:
                fragment = null;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
