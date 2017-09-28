package com.example.witold.wicioguitartuner.DependencyInjection;

import android.app.Application;

import com.example.witold.wicioguitartuner.DependencyInjection.Scopes.ApplicationScope;
import com.example.witold.wicioguitartuner.GuitarTunerApplication;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by Witold on 26.08.2017.
 */
@ApplicationScope
@Component(modules = {
        AudioRecorderModule.class,
        AppModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class})

public interface ApplicationComponent extends AndroidInjector<DaggerApplication> {

    void inject(GuitarTunerApplication guitarTunerApplication);

    @Override
    void inject(DaggerApplication instance);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ApplicationComponent.Builder application(Application application);
        ApplicationComponent build();
    }}
