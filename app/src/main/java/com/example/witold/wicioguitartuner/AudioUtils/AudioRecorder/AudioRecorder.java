package com.example.witold.wicioguitartuner.AudioUtils.AudioRecorder;

import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.util.Log;

import com.example.witold.wicioguitartuner.AudioUtils.AudioAnalysis.AudioAnalysis;
import com.example.witold.wicioguitartuner.AudioUtils.AudioAnalysis.Complex;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Sound recording provider.
 */
public class AudioRecorder {

    private int sampleSize;
    private int bufferSize;
    private int minimalLoudness;
    private boolean isRecording = false;
    private PublishSubject<Complex[]> sampleSubject;

    public AudioRecorder(int sampleSize, int bufferSize, int minimalLoudness) {
        this.sampleSize = sampleSize;
        this.bufferSize = bufferSize;
        this.minimalLoudness = minimalLoudness;
        initializeSampleSubject();
    }

    private void initializeSampleSubject(){
        sampleSubject = PublishSubject.create();
    }

    public Observable<Complex[]> getSampleObservable(){
        return sampleSubject;
    }

    public Observable<Complex[]> getRecordingObservable() {
        return Observable.create(new ObservableOnSubscribe<Complex[]>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Complex[]> e) throws Exception {
                isRecording = true;
                try {
                    final double[] sample = new double[sampleSize];
                    int sampleIndex = 0;
                    boolean loudness = false;

                    AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, DefaultParameters.RECORDER_SAMPLERATE, DefaultParameters.RECORDER_CHANNELS, DefaultParameters.RECORDER_AUDIO_ENCODING, bufferSize);
                    final short[] buffer = new short[bufferSize];
                    audioRecord.startRecording();
                    while (isRecording) { //record until the sample size is bug enough;

                        final int numberOfReadBytes = audioRecord.read(buffer, 0, bufferSize);
                        float totalAbsValue = 0.0f;

                        for (int i = 0; i < numberOfReadBytes; i += 2) //sprawdza głośność tylko.
                        {
                            totalAbsValue += Math.abs(buffer[i]) / (numberOfReadBytes / 2);
                        }

                        if (totalAbsValue > minimalLoudness) //jeżeli głośność jest wystarczająca to dodaje nagrany buffor do próbki którą będziemy analizować.
                        {
                            if (!loudness) {
                                loudness = true;
                            }
                            for (int i = 0; i < bufferSize; i += 1) {
                                sample[sampleIndex] = buffer[i];
                                sampleIndex++;
                            }
                        } else {
                            if (loudness) {
                                loudness = false;
                            }
                        }
                        if (sampleIndex >= sampleSize) {
                            sampleIndex = 0;
                            sampleSubject.onNext(AudioAnalysis.getComplexResult(sample, sampleSize));
                        }
                    }
                    audioRecord.stop();
                    audioRecord.release();
                    e.onComplete();
                } catch (Exception exception) {
                    e.onError(exception);
                }
            }
        });
    }

    public void startRecording(){
        getRecordingObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(complexes -> {
            sampleSubject.onNext(complexes);
        });
    }

    public void stopRecording() {
        isRecording = false;
    }
}
