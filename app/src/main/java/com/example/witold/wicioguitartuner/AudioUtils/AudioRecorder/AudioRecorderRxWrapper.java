package com.example.witold.wicioguitartuner.AudioUtils.AudioRecorder;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import timber.log.Timber;

/**
 * Created by Witold on 02.10.2017.
 */

public class AudioRecorderRxWrapper {
    Observable<double[]> recorderObservable;
    AudioRecorder audioRecorder;

    public AudioRecorderRxWrapper(AudioRecorder audioRecorder) {
        this.audioRecorder = audioRecorder;
        Log.d("AudioRecorderRXWrapper", "initialized");
    }

    private Observable<double[]> initializeAndGetObservable() {
        return Observable.create(new ObservableOnSubscribe<double[]>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<double[]> emitter) throws Exception {
                audioRecorder.wrapRecorder(new AudioRecorderCallback() {
                    @Override
                    public void onRecord(double[] sample) {
                        Log.e("sdfsdfsdf", "sdfsdfsdfsfd");
                        emitter.onNext(sample);
                    }

                    @Override
                    public void onFinish() {
                        emitter.onComplete();
                    }

                    @Override
                    public void onStop() {

                    }
                });
            }
        });
    }

    public Observable<double[]> getRecorderObservable() {
        return audioRecorder.startRecording();
    }

    public void stopRecording() {
        audioRecorder.stopRecording();
    }
}
