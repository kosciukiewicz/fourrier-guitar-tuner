package com.example.witold.wicioguitartuner.Utils.SmartFragmentStatePagerAdapter;

import com.example.witold.wicioguitartuner.AmplitudeChartFragment.AmplitudeChartFragment;
import com.example.witold.wicioguitartuner.FFTChartFragment.FFTChartFragment;
import com.example.witold.wicioguitartuner.MainActivity.MainActivity;
import com.example.witold.wicioguitartuner.TunerFragment.TunerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Witold on 06.10.2017.
 */
@Module
public class SmartFragmentAdapterModule {
    @Provides
    SmartFragmentStatePagerAdapter provideSmartFragmentStatePagerAdapter(MainActivity mainActivity, TunerFragment tunerFragment, AmplitudeChartFragment amplitudeChartFragment, FFTChartFragment fftChartFragment){
        return new SmartFragmentStatePagerAdapter(mainActivity.getSupportFragmentManager(), tunerFragment, amplitudeChartFragment,fftChartFragment);
    }
}
