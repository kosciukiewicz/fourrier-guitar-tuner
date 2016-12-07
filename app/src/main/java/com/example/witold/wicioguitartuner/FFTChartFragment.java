package com.example.witold.wicioguitartuner;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.witold.wicioguitartuner.AudioAnalysis.Complex;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;


public class FFTChartFragment extends Fragment {
    static LineChart chart;


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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fftchart, container, false);
        chart = (LineChart) view.findViewById(R.id.chartFFT);
        return view;
    }

    public void initializeChart(Complex[] dataObjects, int maxChartValue) {

        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < maxChartValue; i++) {
            entries.add(new Entry(((float) DefaultParameters.RECORDER_SAMPLERATE / DefaultParameters.SAMPLE_SIZE) * i, (float) Math.abs(dataObjects[i].re)));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Frequency"); // add entries to dataset
        dataSet.setColor(getResources().getColor(R.color.colorAccent));
        dataSet.setValueTextColor(getResources().getColor(R.color.colorPrimaryDark));
        dataSet.setDrawCircles(false);
        LineData lineData = new LineData(dataSet);
        YAxis leftAxis = chart.getAxisLeft();
        YAxis rightAxis = chart.getAxisRight();
        XAxis axis = chart.getXAxis();
        axis.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        rightAxis.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        leftAxis.setDrawLabels(false);
        chart.getLegend().setEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
        chart.setData(lineData);
        chart.invalidate();
    }
}
