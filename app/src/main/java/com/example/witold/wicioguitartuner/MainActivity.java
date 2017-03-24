package com.example.witold.wicioguitartuner;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity extends AppCompatActivity {

    SmartFragmentStatePagerAdapter adapter;

    Button buttonStart;
    ViewPager pager;
    Button buttonFinish;
    TextView currentFrequency;
    ImageView imageViewStatus;
    AudioRecorder audioRecorder;

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new SmartFragmentStatePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        EventBus.getDefault().register(this);
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
                ).title("Tuner")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_fourier),
                        (R.color.colorAccent)
                ).title("FFT Chart")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_amplitube),
                        (R.color.colorAccent)
                ).title("Signal Chart")
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
    }

    private void initializeAudioRecorded()   //Initialize audioRecorder
    {
        if(audioRecorder==null) {
            audioRecorder = new AudioRecorder(this, DefaultParameters.SAMPLE_SIZE, DefaultParameters.BUFFER_SIZE, 50);
            audioRecorder.StartRecording();
        }
    }

    private void stopAndResetRecorder()
    {
        if(audioRecorder!=null) {
            audioRecorder.StopRecording();
            audioRecorder = null;
        }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setMaxFreq(FrequencyDataMessage message)
    {
        float freq = message.getBucket()*((float)DefaultParameters.RECORDER_SAMPLERATE)/ DefaultParameters.SAMPLE_SIZE;
        currentFrequency.setText(String.format("%1.2f Hz",freq));
    }

    /*
     pager adapter to move between three fragments
     */
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

    public static class FrequencyDataMessage
    {
        private int bucket;

        public FrequencyDataMessage(int bucket)
        {
            this.bucket = bucket;
        }

        public int getBucket()
        {
            return bucket;
        }
    }

}
