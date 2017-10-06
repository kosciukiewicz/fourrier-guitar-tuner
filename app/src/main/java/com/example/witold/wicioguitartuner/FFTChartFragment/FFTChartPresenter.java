package com.example.witold.wicioguitartuner.FFTChartFragment;

import com.example.witold.wicioguitartuner.AudioUtils.AudioAnalysis.Complex;
import com.example.witold.wicioguitartuner.AudioUtils.AudioRecorder.AudioRecorderRxWrapper;
import com.example.witold.wicioguitartuner.AudioUtils.AudioRecorder.DefaultParameters;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Witold on 28.09.2017.
 */

public class FFTChartPresenter implements FFTChartContract.Presenter {
    private FFTChartContract.FFTChartView fftChartView;
    private AudioRecorderRxWrapper audioRecorderRxWrapper;
    private CompositeDisposable subscriptions;

    @Inject
    public FFTChartPresenter(AudioRecorderRxWrapper audioRecorderRxWrapper) {
        this.audioRecorderRxWrapper = audioRecorderRxWrapper;
        subscriptions = new CompositeDisposable();
    }

    @Override
    public void onViewAttached(FFTChartContract.FFTChartView view) {
        this.fftChartView = view;
        Timber.tag("Amplitude Presenter").d(audioRecorderRxWrapper + "");
    }

    @Override
    public void onViewDetached() {
        subscriptions.clear();
    }

    @Override
    public void subscribeAudioRecorder() {
        Disposable subscription = audioRecorderRxWrapper.getFFTSampleObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onNext
                        this::processSamples,
                        // onError
                        throwable -> throwable.printStackTrace());
        subscriptions.add(subscription);
    }

    private void processSamples(Complex[] sample){
        fftChartView.showDataOnChart(sample, DefaultParameters.MAX_FOURIER_CHART_FREQ);
    }
}
