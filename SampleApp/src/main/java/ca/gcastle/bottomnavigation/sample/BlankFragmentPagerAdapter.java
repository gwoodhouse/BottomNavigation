package ca.gcastle.bottomnavigation.sample;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Adapter for the ViewPager.
 *
 * Created by adammcneilly on 4/28/16.
 */
public class BlankFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private String[] titles;

    public BlankFragmentPagerAdapter(FragmentManager manager, String[]titles) {
        super(manager);
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return BlankFragment.newInstance(titles[position], position);
    }

    @Override
    public int getCount() {
        return titles.length;
    }
}