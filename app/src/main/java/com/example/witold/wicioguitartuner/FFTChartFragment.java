package com.example.witold.wicioguitartuner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.witold.wicioguitartuner.AudioProvider.AudioAnalysis.Complex;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(FFTChartDataMessage event) {
        initializeChart(event.getDataObjects(), DefaultParameters.MAX_FOURIER_CHART_FREQ);
    };

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

    public static class FFTChartDataMessage
    {
        private Complex[] dataObjects;

        public FFTChartDataMessage(Complex[] dataObjects)
        {
            this.dataObjects = dataObjects;
        }

        public Complex[] getDataObjects() {
            return dataObjects;
        }
    }
}
