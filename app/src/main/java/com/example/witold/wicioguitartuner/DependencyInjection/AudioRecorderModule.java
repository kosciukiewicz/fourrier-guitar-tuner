package com.example.witold.wicioguitartuner.DependencyInjection;

import com.example.witold.wicioguitartuner.AudioProvider.AudioRecorder;
import com.example.witold.wicioguitartuner.AudioProvider.DefaultParameters;
import com.example.witold.wicioguitartuner.DependencyInjection.Scopes.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Witold on 28.09.2017.
 */

@Module
public class AudioRecorderModule {

    @Provides
    @ApplicationScope
    public AudioRecorder provideAudioRecorder(){
        return new AudioRecorder(DefaultParameters.SAMPLE_SIZE, DefaultParameters.BUFFER_SIZE, DefaultParameters.MINIMAL_LOUDNESS);
    }
}
