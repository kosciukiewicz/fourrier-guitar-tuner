package com.example.witold.wicioguitartuner.AudioUtils.AudioRecorder;

import android.media.AudioFormat;

/**
 * Created by Witold on 2016-11-30.
 */
public class DefaultParameters {
    public static final int RECORDER_SAMPLERATE = 8000;
    public static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    public static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    public static final int SAMPLE_SIZE = 8192;
    public static final int MAX_FOURIER_CHART_FREQ = 1200;
    public static final int BUFFER_SIZE = 2048;
    public static final int MINIMAL_LOUDNESS = 50;
    public static final int REQUEST_PERMISSION_CODE = 1;
    public static final String PERMISSION_DENIED_MESSAGE = "You need to grant recording permission first!";
}
