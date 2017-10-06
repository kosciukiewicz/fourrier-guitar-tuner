package com.example.witold.wicioguitartuner.MainActivity.SmartFragmentStatePagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
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

        TunerFragment tunerFragment;

        FFTChartFragment fftChartFragment;

        AmplitudeChartFragment amplitudeChartFragment;

        private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        public SmartFragmentStatePagerAdapter(FragmentManager fragmentManager, TunerFragment tunerFragment, AmplitudeChartFragment amplitudeChartFragment, FFTChartFragment fftChartFragment){
            super(fragmentManager);
            this.tunerFragment = tunerFragment;
            this.fftChartFragment = fftChartFragment;
            this.amplitudeChartFragment = amplitudeChartFragment;
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
                case 0: return tunerFragment;
                case 1: return fftChartFragment;
                case 2: return amplitudeChartFragment;
                default: return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
}
