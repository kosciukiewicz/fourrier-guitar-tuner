package com.example.witold.wicioguitartuner;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.witold.wicioguitartuner.AudioAnalysis.Complex;
import com.example.witold.wicioguitartuner.AudioAnalysis.FrequencySet;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity extends AppCompatActivity {

    SmartFragmentStatePagerAdapter adapter;

    FrequencySet frequencySet;
    public static int sampleSize = 8192;
    public static int bufferSize = 2048;
    public static int maxChartValue = 1200;
    Button buttonStart;
    ViewPager pager;
    Button buttonFinish;
    TextView currentFrequency;
    ImageView imageViewStatus;
    AudioRecorder audioRecorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new SmartFragmentStatePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        initializeComponents();
        initializeNavBar();
        Toast.makeText(getApplicationContext(), "" + adapter.registeredFragments.size(),Toast.LENGTH_SHORT).show();
    }

    private void initializeNavBar() {
        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_guitar),
                        (R.color.colorAccent)
                ).title("Heart")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_fourier),
                        (R.color.colorAccent)
                ).title("Cup")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_amplitube),
                        (R.color.colorAccent)
                ).title("Diploma")
                        .build()
        );

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(pager, 1);
    }

    private void initializeComponents()
    {
        buttonStart = (Button) findViewById(R.id.buttonStartRecording);
        buttonFinish = (Button) findViewById(R.id.buttonFinishRecording);
        currentFrequency = (TextView) findViewById(R.id.textViewCurrentFreq);
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
        ((TunerFragment)adapter.getRegisteredFragment(0)).setNoteTextView(frequency);
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
        currentFrequency.setText(String.format("%1.2f Hz", bucket*((float)DefaultParameters.RECORDER_SAMPLERATE)/sampleSize));
        setNoteText(frequencySet.findClosest(bucket));
    }

    public void initializeChart(Complex[] amplitube, Complex[] dataObjects)
    {
        ((FFTChartFragment) adapter.getRegisteredFragment(1)).initializeChart(dataObjects, DefaultParameters.MAX_FOURIER_CHART_FREQ);
        if(adapter.getRegisteredFragment(2) != null) {
            ((ChartFragment)adapter.getRegisteredFragment(2)).initializeChart(amplitube, amplitube.length);
        }
        else
        {
            adapter.instantiateItem(pager, 2);
            ((ChartFragment)adapter.getRegisteredFragment(2)).initializeChart(amplitube, amplitube.length);
        }
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

    private class SmartFragmentStatePagerAdapter extends FragmentStatePagerAdapter  {
        private int NUM_ITEMS = 3;
        private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        public SmartFragmentStatePagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Register the fragment when the item is instantiated
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        // Unregister when the item is inactive
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }

        // Returns the fragment for the position (if instantiated)
        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }
        @Override
        public Fragment getItem(int pos) {
            switch(pos) {
                case 0: return TunerFragment.newInstance("FirstFragment, Instance 1");
                case 1: return FFTChartFragment.newInstance("ThirdFragment, Instance 1");
                case 2: return ChartFragment.newInstance("SecondFragment, Instance 1");
                default: return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
    }
}
