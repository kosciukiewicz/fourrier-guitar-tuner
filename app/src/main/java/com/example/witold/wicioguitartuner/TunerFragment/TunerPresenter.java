package com.example.witold.wicioguitartuner.TunerFragment;

import com.example.witold.wicioguitartuner.AmplitudeChartFragment.AmplitudeChartFragmentContract;
import com.example.witold.wicioguitartuner.AudioUtils.AudioAnalysis.AudioAnalysis;
import com.example.witold.wicioguitartuner.AudioUtils.AudioAnalysis.Complex;
import com.example.witold.wicioguitartuner.AudioUtils.AudioAnalysis.FrequencySet;
import com.example.witold.wicioguitartuner.AudioUtils.AudioRecorder.AudioRecorder;
import com.example.witold.wicioguitartuner.AudioUtils.AudioRecorder.AudioRecorderRxWrapper;
import com.example.witold.wicioguitartuner.AudioUtils.AudioRecorder.DefaultParameters;
import com.example.witold.wicioguitartuner.AudioUtils.SingleFrequency;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Witold on 28.09.2017.
 */

public class TunerPresenter implements TunerContract.Presenter {
    private TunerContract.TunerView tunerView;
    private AudioRecorderRxWrapper audioRecorderRxWrapper;
    private CompositeDisposable subscriptions;
    private FrequencySet frequencySet;

    @Inject
    public TunerPresenter(AudioRecorderRxWrapper audioRecorderRxWrapper) {
        this.audioRecorderRxWrapper = audioRecorderRxWrapper;
        subscriptions = new CompositeDisposable();
        frequencySet = new FrequencySet();
    }

    @Override
    public void onViewAttached(TunerContract.TunerView view) {
        this.tunerView = view;
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
                .map(AudioAnalysis::getMax)
                .subscribe(
                        // onNext
                        this::processBucket,
                        // onError
                        throwable -> throwable.printStackTrace());
        subscriptions.add(subscription);
    }

    private void processBucket(int bucket) {
        float freq = bucket * ((float) DefaultParameters.RECORDER_SAMPLERATE) / DefaultParameters.SAMPLE_SIZE;
        SingleFrequency closestFrequency = frequencySet.findClosest(bucket);
        tunerView.setNoteTextView(closestFrequency);
        setTunerArrows(freq, closestFrequency);
    }

    private void setTunerArrows(float freq, SingleFrequency closestFrequency) {
        float accuracy = freq / 120; //the bigger freq value the bigger tolrence of tuner
        if (freq - closestFrequency.getFreqValue() > accuracy) {
            tunerView.setArrowsTooHigh();
        } else {
            if (freq - closestFrequency.getFreqValue() < -accuracy) {
                tunerView.setArrowsTooLow();
            } else {
                tunerView.setArrowsEqual();
            }
        }
    }

}
