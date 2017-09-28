package com.witold.dagger_mvp_taks_app.DependencyInjection;
import com.witold.dagger_mvp_taks_app.DependencyInjection.Scopes.ActivityScoped;
import com.witold.dagger_mvp_taks_app.MainActivity.MainActivity;
import com.witold.dagger_mvp_taks_app.Tasks.TasksModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Class to provide all acitivities. Dagger generates all subcomponents.
 */
@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = TasksModule.class)
    abstract MainActivity mainActivity();

}
