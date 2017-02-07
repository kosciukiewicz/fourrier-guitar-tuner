package com.example.witold.wicioguitartuner;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.pavlospt.roundedletterview.RoundedLetterView;

import org.w3c.dom.Text;

public class TunerFragment extends Fragment {
    RoundedLetterView noteView;
    TextView freqValue;
    ImageView arrowUp;
    ImageView arrowDown;
    public TunerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tuner, container, false);
        initializeComponents(view);
        return view;
    }

    private void initializeComponents(View view)
    {
        noteView = (RoundedLetterView) view.findViewById(R.id.noteView);
        freqValue = (TextView) view.findViewById(R.id.textViewClosestFreqValue) ;
        arrowUp = (ImageView) view.findViewById(R.id.imageViewArrowUp);
        arrowDown = (ImageView) view.findViewById(R.id.imageViewArrowDown);
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
        noteView.setTitleText(frequency.note);
        freqValue.setText("( "+String.valueOf(frequency.freqValue) + "Hz )");
    }
}
