package com.example.witold.wicioguitartuner.TunerFragment;

import com.example.witold.wicioguitartuner.DependencyInjection.Scopes.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Witold on 28.09.2017.
 */
@Module
public abstract class TunerFragmentModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract TunerFragment tunerFragment();

    @FragmentScoped
    @Binds
    abstract TunerContract.Presenter presenter(TunerPresenter presenter);

}
