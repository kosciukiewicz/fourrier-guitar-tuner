package com.example.witold.wicioguitartuner.MainActivity;

import com.example.witold.wicioguitartuner.DependencyInjection.Scopes.ActivityScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Witold on 05.10.2017.
 */
@Module
public abstract class MainActivityModule {
    @ActivityScoped
    @Binds
    abstract MainActivityContract.Presenter presenter(MainActivityPresenter presenter);
}
