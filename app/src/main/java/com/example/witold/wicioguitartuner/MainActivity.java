package com.example.witold.wicioguitartuner;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.witold.wicioguitartuner.AudioAnalysis.Complex;
import com.example.witold.wicioguitartuner.AudioAnalysis.FrequencySet;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FrequencySet frequencySet;
    public static int sampleSize = 8192;
    public static int bufferSize = 2048;
    public static int maxChartValue = 1200;
    TextView noteText;
    Button buttonStart;
    Button buttonFinish;
    TextView maxTextView;
    ImageView imageViewStatus;
    AudioRecorder audioRecorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();
    }

    private void initializeComponents()
    {
        noteText = (TextView) findViewById(R.id.textViewNote);
        buttonStart = (Button) findViewById(R.id.buttonStartRecording);
        buttonFinish = (Button) findViewById(R.id.buttonFinishRecording);
        maxTextView = (TextView) findViewById(R.id.textViewFreq);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeAudioRecorded();
            }
        });
        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAndResetRecorder();
            }
        });
        imageViewStatus = (ImageView) findViewById(R.id.imageViewStatus);
        frequencySet = new FrequencySet();
    }

    private void initializeAudioRecorded()   //Initialize audioRecorder
    {
        audioRecorder = new AudioRecorder(this,sampleSize, bufferSize, 50);
        audioRecorder.StartRecording();
    }

    private void stopAndResetRecorder()
    {
        if(audioRecorder!=null)
        audioRecorder.StopRecording();
    }
    public void setNoteText(SingleFrequency frequency)
    {
        noteText.setText(frequency.note);
    }

    public void setRecording(boolean visibility)
    {
        if(visibility)
        {
            imageViewStatus.setVisibility(View.VISIBLE);
        }
        else
        {
            imageViewStatus.setVisibility(View.INVISIBLE);
        }
    }
    public void setMaxFreq(int bucket)
    {
        maxTextView.setText(String.format("%1.2f Hz", bucket*((float)DefaultParameters.RECORDER_SAMPLERATE)/sampleSize));
        setNoteText(frequencySet.findClosest(bucket));
    }

    public void initializeChart(Complex[] dataObjects)
    {
        LineChart chart = (LineChart) findViewById(R.id.chart);

        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < maxChartValue; i++) {
            entries.add(new Entry(((float)DefaultParameters.RECORDER_SAMPLERATE/sampleSize)*i, (float)Math.abs(dataObjects[i].re)));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
        dataSet.setColor(R.color.colorAccent);
        dataSet.setValueTextColor(R.color.colorAccent);

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();
    }

    private Complex[] getDSin()
    {
        Complex[] complexResult = new Complex[4*2048];
        for(int i= 0; i < 4*2048; i++)
        {
            complexResult[i] = new Complex(Math.sin(Math.toRadians(i)),0.0);
        }
        return complexResult;
    }
}
