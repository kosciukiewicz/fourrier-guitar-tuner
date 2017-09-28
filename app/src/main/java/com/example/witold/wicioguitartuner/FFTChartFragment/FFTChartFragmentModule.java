package com.example.witold.wicioguitartuner.FFTChartFragment;

import com.example.witold.wicioguitartuner.DependencyInjection.Scopes.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Witold on 28.09.2017.
 */
@Module
public abstract class FFTChartFragmentModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract FFTChartFragment fftChartFragment();

    @FragmentScoped
    @Binds
    abstract FFTChartContract.Presenter presenter(FFTChartPresenter presenter);
}
