package com.example.witold.wicioguitartuner.DependencyInjection;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Witold on 26.08.2017.
 */

@Module
public abstract class AppModule {
    //expose Application as an injectable context
    @Binds
    abstract Context bindContext(Application application);
}
