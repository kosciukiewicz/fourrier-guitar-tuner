package com.example.witold.wicioguitartuner.AudioUtils.AudioRecorder;

/**
 * Created by Witold on 02.10.2017.
 */

public interface AudioRecorderCallback {
    void onRecord(double[] sample);
    void onStop();
    void onFinish();
}
