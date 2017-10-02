package com.example.witold.wicioguitartuner.DependencyInjection;

import com.example.witold.wicioguitartuner.AudioUtils.AudioRecorder.AudioRecorder;
import com.example.witold.wicioguitartuner.AudioUtils.AudioRecorder.DefaultParameters;
import com.example.witold.wicioguitartuner.DependencyInjection.Scopes.ApplicationScope;
import com.example.witold.wicioguitartuner.Utils.RxBus.RxBus;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Witold on 02.10.2017.
 */
@Module
public class RxBusModule {
    @Provides
    @ApplicationScope
    public RxBus provideRxBus(){
        return new RxBus();
    }
}
