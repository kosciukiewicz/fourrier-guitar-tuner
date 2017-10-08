package com.example.witold.wicioguitartuner.MainActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import com.example.witold.wicioguitartuner.AudioUtils.AudioAnalysis.AudioAnalysis;
import com.example.witold.wicioguitartuner.AudioUtils.AudioRecorder.AudioRecorderRxWrapper;
import com.example.witold.wicioguitartuner.AudioUtils.AudioRecorder.DefaultParameters;
import com.example.witold.wicioguitartuner.FFTChartFragment.FFTChartContract;


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
    private CompositeDisposable subscriptions;

    @Inject
    Context context;

    @Inject
    public MainActivityPresenter(AudioRecorderRxWrapper audioRecorderRxWrapper) {
        this.audioRecorderRxWrapper = audioRecorderRxWrapper;
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
        Disposable subscription = audioRecorderRxWrapper.getFFTSampleObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(AudioAnalysis::getMax)
                .subscribe(
                        // onNext
                        this::processBucket,
                        // onError
                        throwable -> throwable.printStackTrace());
        subscriptions.add(subscription);
    }

    @Override
    public void finnishAudioRecorder() {
        audioRecorderRxWrapper.stopRecording();
        mainActivityView.setRecordingIcon(false);
    }

    @Override
    public void processAudioRecordPermissionRequestResult(int[] grantResults) {
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startAudioRecorder();
        } else {
            mainActivityView.showToastMessage(DefaultParameters.PERMISSION_DENIED_MESSAGE);
        }
    }

    @Override
    public void checkAudioRecordPermissionStartAudioRecorder() {
        int audioRecordPermissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO);
        if(audioRecordPermissionCheck == PackageManager.PERMISSION_DENIED){
            mainActivityView.requestPermission(Manifest.permission.RECORD_AUDIO);
        }else {
            startAudioRecorder();
        }
    }

    private void startAudioRecorder() {
        subscribeAudioRecorder();
        audioRecorderRxWrapper.startRecording();
        mainActivityView.setRecordingIcon(true);
    }

    private void processBucket(int bucket) {
        float freq = bucket * ((float) DefaultParameters.RECORDER_SAMPLERATE) / DefaultParameters.SAMPLE_SIZE;
        mainActivityView.setRecordedFrequencyTextView(freq);

    }
}
