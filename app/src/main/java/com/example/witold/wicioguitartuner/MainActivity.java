package com.example.witold.wicioguitartuner;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.witold.wicioguitartuner.AudioUtils.AudioRecorder.AudioRecorderRxWrapper;
import com.example.witold.wicioguitartuner.Utils.RxBus.RxBus;
import com.example.witold.wicioguitartuner.Utils.RxBus.StartRecordingEvent;
import com.example.witold.wicioguitartuner.Utils.SmartFragmentStatePagerAdapter;
import com.example.witold.wicioguitartuner.AudioUtils.AudioRecorder.AudioRecorder;


import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import devlight.io.library.ntb.NavigationTabBar;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends DaggerAppCompatActivity {

    SmartFragmentStatePagerAdapter adapter;

    Button buttonStart;
    ViewPager pager;
    Button buttonFinish;
    TextView currentFrequency;
    ImageView imageViewStatus;

    @Inject
    AudioRecorderRxWrapper audioRecorder;

    @Inject
    RxBus rxBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new SmartFragmentStatePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        initializeComponents();
        initializeNavBar();
        Log.d("Main", audioRecorder + "");
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
                ).title("AudioAnalysis Chart")
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
                initializeAudioRecorderAndSubscribeObservable();
                rxBus.setEvent(new StartRecordingEvent());
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

    private void initializeAudioRecorderAndSubscribeObservable()   //Initialize audioRecorder
    {
        audioRecorder.getRecorderObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<double[]>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull double[] doubles) {
                        Log.d("Main", "Leci nastepny sample jeden");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(getApplicationContext(), "Koniec nagrywania", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void stopAndResetRecorder()
    {
        audioRecorder.stopRecording();
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
}
