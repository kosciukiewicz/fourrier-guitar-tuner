package com.example.witold.wicioguitartuner.AudioProvider.AudioAnalysis;

import com.example.witold.wicioguitartuner.AudioProvider.SingleFrequency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Witold on 2016-12-04.
 */
public class FrequencySet {
    private ArrayList<SingleFrequency> freqArray;
    private ArrayList<Integer> bucketArray;

    public FrequencySet() {
        initializeSet();
        bucketArray = getBucketArray(freqArray);
    }

    private void initializeSet() {
        SingleFrequency[] arrayF = {
                new SingleFrequency(41.2, "E1"),
                new SingleFrequency(82.4, "E2"),
                new SingleFrequency(165.8, "E3"),
                new SingleFrequency(329.6, "E4"),
                new SingleFrequency(659.3, "E5"),
                new SingleFrequency(1318.5, "E6"),
                new SingleFrequency(55, "A1"),
                new SingleFrequency(110, "A2"),
                new SingleFrequency(220, "A3"),
                new SingleFrequency(440, "A4"),
                new SingleFrequency(880, "A5"),
                new SingleFrequency(1760, "A6"),
                new SingleFrequency(36.7, "D1"),
                new SingleFrequency(73.4, "D2"),
                new SingleFrequency(146.8, "D3"),
                new SingleFrequency(293.7, "D4"),
                new SingleFrequency(587.3, "D5"),
                new SingleFrequency(1174.7, "D6"),
                new SingleFrequency(49, "G1"),
                new SingleFrequency(98, "G2"),
                new SingleFrequency(196, "G3"),
                new SingleFrequency(392, "G4"),
                new SingleFrequency(784, "G5"),
                new SingleFrequency(1568, "G6"),
                new SingleFrequency(61.7, "B1"),
                new SingleFrequency(123.5, "B2"),
                new SingleFrequency(246.9, "B3"),
                new SingleFrequency(493.9, "B4"),
                new SingleFrequency(987.8, "B5"),
                new SingleFrequency(1975.5, "B6")};

        freqArray = new ArrayList<>(Arrays.asList(arrayF));
        Collections.sort(freqArray, new FreqComparator());
    }

    /**
     * Method to transform frequency array to array containing matching buckets (look Fast Fourrier Transformation Algorithm)
     */
    private ArrayList<Integer> getBucketArray(ArrayList<SingleFrequency> freqArray) {
        ArrayList<Integer> bucketArray = new ArrayList<>();
        for(SingleFrequency singleFrequency : freqArray){
            bucketArray.add(singleFrequency.getBucket());
        }
        return bucketArray;
    }

    /**
     * Method to find closest sound to given frequency (as bucket)
     */
    public SingleFrequency findClosest(int bucket) {
        int index = Collections.binarySearch(bucketArray, bucket);
        index = index >= 0 ? index : (-index) - 1;
        int closestIndex;
        if(index > 0) {
            closestIndex = (bucketArray.get(index) - bucket) > (bucket - bucketArray.get(index - 1)) ? (index - 1) : index;
        }
        else closestIndex = index;
        return freqArray.get(closestIndex);
    }
}
