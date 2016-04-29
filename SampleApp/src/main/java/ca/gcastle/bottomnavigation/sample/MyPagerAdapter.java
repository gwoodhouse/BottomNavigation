package ca.gcastle.bottomnavigation.sample;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Adapter for the ViewPager.
 *
 * Created by adammcneilly on 4/28/16.
 */
public class MyPagerAdapter extends FragmentStatePagerAdapter {
    private String[] titles;

    public MyPagerAdapter(FragmentManager manager, String[]titles) {
        super(manager);
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return MyFragment.newInstance(titles[position]);
    }

    @Override
    public int getCount() {
        return titles.length;
    }
}
