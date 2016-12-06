package com.example.witold.wicioguitartuner.AudioAnalysis;

import com.example.witold.wicioguitartuner.SingleFrequency;

import java.util.Comparator;

/**
 * Created by Witold on 2016-12-04.
 */
public class FreqComparator implements Comparator<SingleFrequency> {
    public int compare(SingleFrequency s1, SingleFrequency s2)
    {
        return s1.getFreqValue() < s2.getFreqValue() ? -1 : 1;
    }
}
