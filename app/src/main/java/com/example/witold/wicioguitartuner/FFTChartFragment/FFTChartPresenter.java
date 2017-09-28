package com.example.witold.wicioguitartuner.FFTChartFragment;

import android.util.Log;

import com.example.witold.wicioguitartuner.AudioProvider.AudioRecorder;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Witold on 28.09.2017.
 */

public class FFTChartPresenter implements FFTChartContract.Presenter {
    private FFTChartContract.FFTChartView fftChartView;
    private AudioRecorder audioRecorder;

    @Inject
    public FFTChartPresenter(AudioRecorder audioRecorder){
        this.audioRecorder = audioRecorder;
    }

    @Override
    public void onViewAttached(FFTChartContract.FFTChartView view) {
        this.fftChartView = view;
        Timber.tag("FFTChart Presenter").d(audioRecorder + "");
    }

    @Override
    public void onViewDetached() {

    }
}
