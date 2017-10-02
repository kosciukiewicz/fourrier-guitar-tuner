package com.example.witold.wicioguitartuner.TunerFragment;

import com.example.witold.wicioguitartuner.AudioUtils.AudioRecorder.AudioRecorder;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Witold on 28.09.2017.
 */

public class TunerPresenter implements TunerContract.Presenter {
    private TunerContract.TunerView tunerView;
    private AudioRecorder audioRecorder;

    @Inject
    public TunerPresenter(AudioRecorder audioRecorder) {
        this.audioRecorder = audioRecorder;
    }

    @Override
    public void onViewAttached(TunerContract.TunerView view) {
        this.tunerView = view;
        Timber.tag("Tuner Presenter").d(audioRecorder + "");
    }

    @Override
    public void onViewDetached() {

    }
}
