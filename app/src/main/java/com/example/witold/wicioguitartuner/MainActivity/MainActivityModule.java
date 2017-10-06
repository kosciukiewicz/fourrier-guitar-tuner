package com.example.witold.wicioguitartuner.MainActivity;

import com.example.witold.wicioguitartuner.DependencyInjection.Scopes.ActivityScoped;
import com.example.witold.wicioguitartuner.MainActivity.SmartFragmentStatePagerAdapter.SmartFragmentAdapterModule;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Witold on 05.10.2017.
 */
@Module (includes = SmartFragmentAdapterModule.class)
public abstract class MainActivityModule {
    @ActivityScoped
    @Binds
    abstract MainActivityContract.Presenter presenter(MainActivityPresenter presenter);
}
