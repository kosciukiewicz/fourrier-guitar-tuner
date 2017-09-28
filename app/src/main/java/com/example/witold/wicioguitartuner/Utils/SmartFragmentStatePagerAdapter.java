package com.example.witold.wicioguitartuner.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.example.witold.wicioguitartuner.AmplitudeChartFragment.AmplitudeChartFragment;
import com.example.witold.wicioguitartuner.FFTChartFragment.FFTChartFragment;
import com.example.witold.wicioguitartuner.TunerFragment.TunerFragment;

/**
 * Created by Witold on 28.09.2017.
 */

public class SmartFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
        private int NUM_ITEMS = 3;
        private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        public SmartFragmentStatePagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Register the fragment when the item is instantiated
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        // Unregister when the item is inactive
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }

        // Returns the fragment for the position (if instantiated)
        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {
                case 0: return TunerFragment.newInstance("FirstFragment, Instance 1");
                case 1: return FFTChartFragment.newInstance("ThirdFragment, Instance 1");
                case 2: return AmplitudeChartFragment.newInstance("SecondFragment, Instance 1");
                default: return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
}
