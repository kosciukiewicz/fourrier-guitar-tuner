package com.example.witold.wicioguitartuner.MainActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.witold.wicioguitartuner.R;
import com.example.witold.wicioguitartuner.Utils.SmartFragmentStatePagerAdapter.SmartFragmentStatePagerAdapter;


import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;
import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity extends DaggerAppCompatActivity implements MainActivityContract.MainActivityView {

    @BindView(R.id.ntb_horizontal)
    NavigationTabBar navigationTabBar;
    @BindView(R.id.buttonStartRecording)
    Button buttonStart;
    @BindView(R.id.buttonFinishRecording)
    Button buttonFinish;
    @BindView(R.id.viewPager)
    ViewPager fragmentPager;
    @BindView(R.id.textViewRecordedFrequency)
    TextView recordedFrequency;
    @BindView(R.id.imageViewStatus)
    ImageView imageViewStatus;

    @Inject
    MainActivityPresenter mainActivityPresenter;

    @Inject
    SmartFragmentStatePagerAdapter adapter;

    @OnClick(R.id.buttonStartRecording)
    public void onButtonStartClick(View v) {
        mainActivityPresenter.startAudioRecorder();
    }

    @OnClick(R.id.buttonFinishRecording)
    public void onButtonFinnishClick(View v) {
        mainActivityPresenter.finnishAudioRecorder();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initializeFragmentPager();
        initializeNavBar();
    }

    @Override
    public void onResume() {
        super.onResume();
        mainActivityPresenter.onViewAttached(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainActivityPresenter.onViewDetached();
    }

    @Override
    public void setRecordedFrequencyTextView(float freq) {
        recordedFrequency.setText(String.format("%1.2f Hz",freq));
    }

    private void initializeFragmentPager(){
        fragmentPager.setAdapter(adapter);
    }

    private void initializeNavBar() {
        ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
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
        navigationTabBar.setViewPager(fragmentPager, 1);
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
