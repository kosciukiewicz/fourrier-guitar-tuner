package com.witold.dagger_mvp_taks_app.DependencyInjection;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Witold on 26.08.2017.
 */

@Module
public abstract class AppModule {
    //expose Application as an injectable context
    @Binds
    abstract Context bindContext(Application application);
}
