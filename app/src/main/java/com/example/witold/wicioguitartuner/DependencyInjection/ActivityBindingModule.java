package com.example.witold.wicioguitartuner.DependencyInjection;

import com.example.witold.wicioguitartuner.AmplitudeChartFragment.AmplitudeChartFragmentModule;
import com.example.witold.wicioguitartuner.DependencyInjection.Scopes.ActivityScoped;
import com.example.witold.wicioguitartuner.FFTChartFragment.FFTChartFragmentModule;
import com.example.witold.wicioguitartuner.MainActivity;
import com.example.witold.wicioguitartuner.TunerFragment.TunerFragmentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Class to provide all acitivities. Dagger generates all subcomponents.
 */
@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = {FFTChartFragmentModule.class, AmplitudeChartFragmentModule.class, TunerFragmentModule.class})
    abstract MainActivity mainActivity();

}
