package com.example.witold.wicioguitartuner.MainActivity;

import com.example.witold.wicioguitartuner.AudioUtils.AudioAnalysis.AudioAnalysis;
import com.example.witold.wicioguitartuner.AudioUtils.AudioRecorder.AudioRecorderRxWrapper;
import com.example.witold.wicioguitartuner.AudioUtils.AudioRecorder.DefaultParameters;
import com.example.witold.wicioguitartuner.FFTChartFragment.FFTChartContract;
import com.example.witold.wicioguitartuner.Utils.RxBus.RxBus;
import com.example.witold.wicioguitartuner.Utils.RxBus.StartRecordingEvent;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Witold on 05.10.2017.
 */

public class MainActivityPresenter implements MainActivityContract.Presenter {
    private MainActivityContract.MainActivityView mainActivityView;
    private AudioRecorderRxWrapper audioRecorderRxWrapper;
    private RxBus rxBus;
    private CompositeDisposable subscriptions;

    @Inject
    public MainActivityPresenter(AudioRecorderRxWrapper audioRecorderRxWrapper, RxBus rxBus) {
        this.audioRecorderRxWrapper = audioRecorderRxWrapper;
        this.rxBus = rxBus;
        subscriptions = new CompositeDisposable();
    }

    @Override
    public void onViewAttached(MainActivityContract.MainActivityView view) {
        this.mainActivityView = view;
    }

    @Override
    public void onViewDetached() {
        this.subscriptions.clear();
    }

    @Override
    public void subscribeAudioRecorder() {
        Disposable subscription = audioRecorderRxWrapper.getRecorderObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(sample -> AudioAnalysis.getComplexResult(sample, sample.length))
                .map(complexes -> AudioAnalysis.hanningWindow(complexes, complexes.length))
                .map(AudioAnalysis::fft)
                .map(AudioAnalysis::getMax)
                .subscribe(
                        // onNext
                        this::processBucket,
                        // onError
                        throwable -> throwable.printStackTrace());
        subscriptions.add(subscription);
    }

    @Override
    public void startAudioRecorder() {
        subscribeAudioRecorder();
        sendStartAudioRecordingEvent();
    }

    @Override
    public void finnishAudioRecorder() {
        audioRecorderRxWrapper.stopRecording();
    }

    private void processBucket(int bucket){
        float freq = bucket*((float) DefaultParameters.RECORDER_SAMPLERATE)/ DefaultParameters.SAMPLE_SIZE;
        mainActivityView.setRecordedFrequencyTextView(freq);

    }

    private void sendStartAudioRecordingEvent(){
        rxBus.setEvent(new StartRecordingEvent());
    }
}
