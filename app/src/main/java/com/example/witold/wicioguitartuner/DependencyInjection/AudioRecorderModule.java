package com.example.witold.wicioguitartuner.DependencyInjection;

import android.media.AudioRecord;

import com.example.witold.wicioguitartuner.AudioUtils.AudioRecorder.AudioRecorder;
import com.example.witold.wicioguitartuner.AudioUtils.AudioRecorder.AudioRecorderRxWrapper;
import com.example.witold.wicioguitartuner.AudioUtils.AudioRecorder.DefaultParameters;
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

    @Provides
    @ApplicationScope
    public AudioRecorderRxWrapper provideAudioRecorderRxWrapper(AudioRecorder audioRecorder){
        return new AudioRecorderRxWrapper(audioRecorder);
    }
}
