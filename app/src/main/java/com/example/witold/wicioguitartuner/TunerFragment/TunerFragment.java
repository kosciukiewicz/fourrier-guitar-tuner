package com.example.witold.wicioguitartuner.TunerFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.witold.wicioguitartuner.AudioProvider.AudioAnalysis.FrequencySet;
import com.example.witold.wicioguitartuner.AudioProvider.DefaultParameters;
import com.example.witold.wicioguitartuner.AudioProvider.SingleFrequency;
import com.example.witold.wicioguitartuner.MainActivity;
import com.example.witold.wicioguitartuner.R;
import com.github.pavlospt.roundedletterview.RoundedLetterView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.DaggerFragment;


public class TunerFragment extends DaggerFragment implements TunerContract.TunerView{
    RoundedLetterView noteView;
    TextView freqValue;
    ImageView arrowUp;
    ImageView arrowDown;
    FrequencySet frequencySet;
    @Inject
    TunerPresenter tunerPresenter;

    @Inject
    public TunerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initlalizePresenter();
    }

    private void initlalizePresenter() {
        tunerPresenter.onViewAttached(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tuner, container, false);
        initializeComponents(view);
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
    }

    private void initializeComponents(View view)
    {
        noteView = (RoundedLetterView) view.findViewById(R.id.noteView);
        freqValue = (TextView) view.findViewById(R.id.textViewClosestFreqValue) ;
        arrowUp = (ImageView) view.findViewById(R.id.imageViewArrowUp);
        arrowDown = (ImageView) view.findViewById(R.id.imageViewArrowDown);
        frequencySet = new FrequencySet();
    }

    public void setArrowsTooHigh()
    {
        arrowUp.setVisibility(View.INVISIBLE);
        arrowDown.setVisibility(View.VISIBLE);
    }

    public void setArrowsTooLow()
    {
        arrowUp.setVisibility(View.VISIBLE);
        arrowDown.setVisibility(View.INVISIBLE);
    }

    public void setArrowsEqual()
    {
        arrowUp.setVisibility(View.INVISIBLE);
        arrowDown.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public static TunerFragment newInstance(String text) {
        TunerFragment f = new TunerFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }

    public void setNoteTextView(SingleFrequency frequency)
    {
        noteView.setTitleText(frequency.getNote());
        freqValue.setText("( "+String.valueOf(frequency.getFreqValue()) + "Hz )");
    }
}
