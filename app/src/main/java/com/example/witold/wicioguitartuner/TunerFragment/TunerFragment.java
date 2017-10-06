package com.example.witold.wicioguitartuner.TunerFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.witold.wicioguitartuner.AudioUtils.AudioAnalysis.FrequencySet;
import com.example.witold.wicioguitartuner.AudioUtils.SingleFrequency;
import com.example.witold.wicioguitartuner.R;
import com.github.pavlospt.roundedletterview.RoundedLetterView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.DaggerFragment;


public class TunerFragment extends DaggerFragment implements TunerContract.TunerView {

    @BindView(R.id.noteView)
    RoundedLetterView noteView;
    @BindView(R.id.textViewClosestFreqValue)
    TextView freqValue;
    @BindView(R.id.imageViewArrowUp)
    ImageView arrowUp;
    @BindView(R.id.imageViewArrowDown)
    ImageView arrowDown;

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
        tunerPresenter.subscribeAudioRecorder();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tuner, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
    }

    @Override
    public void setArrowsTooHigh() {
        arrowUp.setVisibility(View.INVISIBLE);
        arrowDown.setVisibility(View.VISIBLE);
    }

    @Override
    public void setArrowsTooLow() {
        arrowUp.setVisibility(View.VISIBLE);
        arrowDown.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setArrowsEqual() {
        arrowUp.setVisibility(View.INVISIBLE);
        arrowDown.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setNoteTextView(SingleFrequency frequency) {
        noteView.setTitleText(frequency.getNote());
        freqValue.setText("( " + String.valueOf(frequency.getFreqValue()) + "Hz )");
    }

    public static TunerFragment newInstance(String text) {
        TunerFragment f = new TunerFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }
}
