package com.example.witold.wicioguitartuner.MainActivity;

import com.example.witold.wicioguitartuner.DependencyInjection.Scopes.ActivityScoped;
import com.example.witold.wicioguitartuner.Utils.SmartFragmentStatePagerAdapter.SmartFragmentAdapterModule;
import com.example.witold.wicioguitartuner.Utils.SmartFragmentStatePagerAdapter.SmartFragmentStatePagerAdapter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Witold on 05.10.2017.
 */
@Module (includes = SmartFragmentAdapterModule.class)
public abstract class MainActivityModule {
    @ActivityScoped
    @Binds
    abstract MainActivityContract.Presenter presenter(MainActivityPresenter presenter);
}
