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
import io.reactivex.annotations.NonNull;

/**
 * Sound recording provider.
 */
public class AudioRecorder {

    private static AudioRecorder audioRecorderInstance;
    private int sampleSize;
    private int bufferSize;
    private int minimalLoudness;
    private boolean isRecording = false;
    private AudioRecorderCallback audioRecorderCallback;

    public AudioRecorder(int sampleSize, int bufferSize, int minimalLoudness) {
        this.sampleSize = sampleSize;
        this.bufferSize = bufferSize;
        this.minimalLoudness = minimalLoudness;
    }


    public Observable<double[]> startRecording() {
        return Observable.create(new ObservableOnSubscribe<double[]>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<double[]> e) throws Exception {
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
                            e.onNext(sample);
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

    public void stopRecording() {
        isRecording = false;
    }


    public void wrapRecorder(AudioRecorderCallback recordingCallback) {
        this.audioRecorderCallback = recordingCallback;
    }

    protected void calculate(final double[] data, int size) //po udanym nagraniu próbki liczy AudioAnalysis
    {
        final Complex[] complexResult = new Complex[size]; //najpierw zamiana na wartości zespolone
        for (int i = 0; i < size; i++) {
            complexResult[i] = new Complex(data[i], 0.0);
        }
        final Complex[] complexResultFromFFT = AudioAnalysis.fft(AudioAnalysis.hanningWindow(complexResult, complexResult.length));
    }

    protected int getMax(Complex[] data) //zwraca kubełek a nie częstotliwość
    {
        int index = 0;
        double max = Math.abs(data[0].re);
        int size = data.length;
        for (int i = 0; i < size / 4; i++) {
            if (max < Math.abs(data[i].re)) {
                max = Math.abs(data[i].re);
                index = i;
            }
        }
        return index;
    }

    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(Void result) {
    }

}
