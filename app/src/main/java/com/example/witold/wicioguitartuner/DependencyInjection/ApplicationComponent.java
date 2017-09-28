package com.witold.dagger_mvp_taks_app.DependencyInjection;

import android.app.Application;

import com.squareup.picasso.Picasso;
import com.witold.dagger_mvp_taks_app.DependencyInjection.Network.NetworkModule;
import com.witold.dagger_mvp_taks_app.DependencyInjection.Picasso.PicassoModule;
import com.witold.dagger_mvp_taks_app.DependencyInjection.Retrofit.RetrofitModule;
import com.witold.dagger_mvp_taks_app.DependencyInjection.Scopes.ApplicationScope;
import com.witold.dagger_mvp_taks_app.MainApplication;

import javax.inject.Singleton;

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
        RetrofitModule.class,
        PicassoModule.class,
        AppModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class})

public interface ApplicationComponent extends AndroidInjector<DaggerApplication> {

    void inject(MainApplication application);

    @Override
    void inject(DaggerApplication instance);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ApplicationComponent.Builder application(Application application);

        ApplicationComponent build();
    }}
