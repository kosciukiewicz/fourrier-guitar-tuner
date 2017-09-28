package com.example.witold.wicioguitartuner.AmplitudeChartFragment;

import com.example.witold.wicioguitartuner.DependencyInjection.Scopes.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Witold on 28.09.2017.
 */
@Module
public abstract class AmplitudeChartFragmentModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract AmplitudeChartFragment amplitudeChartFragment();

    @FragmentScoped
    @Binds
    abstract AmplitudeChartFragmentContract.Presenter amplitudePresenter(AmplitudeChartPresenter presenter);
}
