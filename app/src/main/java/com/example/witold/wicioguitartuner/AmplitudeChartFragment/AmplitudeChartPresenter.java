package com.example.witold.wicioguitartuner.AmplitudeChartFragment;

import com.example.witold.wicioguitartuner.AudioUtils.AudioRecorder.AudioRecorder;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Witold on 28.09.2017.
 */

public class AmplitudeChartPresenter implements AmplitudeChartFragmentContract.Presenter{
    private AmplitudeChartFragmentContract.AmplitudeChartView amplitudeChartView;
    private AudioRecorder audioRecorder;

    @Inject
    public AmplitudeChartPresenter(AudioRecorder audioRecorder) {
        this.audioRecorder = audioRecorder;
    }

    @Override
    public void onViewAttached(AmplitudeChartFragmentContract.AmplitudeChartView view) {
        this.amplitudeChartView = view;
        Timber.tag("Amplitude Presenter").d(audioRecorder + "");
    }

    @Override
    public void onViewDetached() {

    }
}
