package com.example.witold.wicioguitartuner.FFTChartFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.witold.wicioguitartuner.AudioUtils.AudioAnalysis.Complex;
import com.example.witold.wicioguitartuner.AudioUtils.AudioRecorder.DefaultParameters;
import com.example.witold.wicioguitartuner.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;


public class FFTChartFragment extends DaggerFragment implements FFTChartContract.FFTChartView {
    @BindView(R.id.chartFFT)
    LineChart chart;

    @Inject
    FFTChartPresenter fftChartPresenter;

    @Inject
    public FFTChartFragment() {
        // Required empty public constructor
    }

    public static FFTChartFragment newInstance(String text) {
        FFTChartFragment f = new FFTChartFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializePresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fftchart, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void showDataOnChart(Complex[] data, int maxChartValue) {
        initializeChart();

        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < maxChartValue; i++) {
            entries.add(new Entry(((float) DefaultParameters.RECORDER_SAMPLERATE / DefaultParameters.SAMPLE_SIZE) * i, (float) Math.abs(data[i].re)));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Amplitude"); // add entries to dataset
        dataSet.setColor(getResources().getColor(R.color.colorAccent));
        dataSet.setValueTextColor(getResources().getColor(R.color.colorPrimaryDark));
        dataSet.setDrawCircles(false);
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();
    }

    private void initializePresenter() {
        fftChartPresenter.onViewAttached(this);
        fftChartPresenter.subscribeAudioRecorder();
    }

    private void initializeChart() {
        YAxis leftAxis = chart.getAxisLeft();
        YAxis rightAxis = chart.getAxisRight();
        XAxis axis = chart.getXAxis();
        axis.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        rightAxis.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        leftAxis.setDrawLabels(false);
        chart.getLegend().setEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
    }
}
