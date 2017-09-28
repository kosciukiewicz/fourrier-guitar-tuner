package com.example.witold.wicioguitartuner;

import com.example.witold.wicioguitartuner.DependencyInjection.ApplicationComponent;
import com.example.witold.wicioguitartuner.DependencyInjection.DaggerApplicationComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import timber.log.Timber;

/**
 * Created by Witold on 28.09.2017.
 */

public class GuitarTunerApplication extends DaggerApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().application(this).build();
        applicationComponent.inject(this);
        return applicationComponent;
    }
}
