package com.example.witold.wicioguitartuner.AudioUtils.AudioRecorder;

import android.util.Log;

import com.example.witold.wicioguitartuner.AudioUtils.AudioAnalysis.AudioAnalysis;
import com.example.witold.wicioguitartuner.AudioUtils.AudioAnalysis.Complex;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

/**
 * Created by Witold on 02.10.2017.
 */

public class AudioRecorderRxWrapper {
    Observable<double[]> recorderObservable;
    AudioRecorder audioRecorder;
    private PublishSubject<Complex[]> fftSampleSubject;

    public AudioRecorderRxWrapper(AudioRecorder audioRecorder) {
        this.audioRecorder = audioRecorder;
        initializeFFTSampleSubject();
        Log.d("AudioRecorderRXWrapper", "initialized");
    }

    private void initializeFFTSampleSubject() {
        fftSampleSubject = PublishSubject.create();
        subscribeAmplitudeSampleObservable();
    }

    private void subscribeAmplitudeSampleObservable() {
        getAmplitudeSampleObservable()
                .map(complexes -> AudioAnalysis.hanningWindow(complexes, complexes.length))
                .map(complexes -> AudioAnalysis.fft(complexes))
                .subscribe(fftSample ->
                        fftSampleSubject.onNext(fftSample)
                );
    }

    public Observable<Complex[]> getAmplitudeSampleObservable() {
        return audioRecorder.getSampleObservable();
    }

    public Observable<Complex[]> getFFTSampleObservable() {
        return fftSampleSubject;
    }

    public void startRecording() {
        audioRecorder.startRecording();
    }

    public void stopRecording() {
        audioRecorder.stopRecording();
    }
}
