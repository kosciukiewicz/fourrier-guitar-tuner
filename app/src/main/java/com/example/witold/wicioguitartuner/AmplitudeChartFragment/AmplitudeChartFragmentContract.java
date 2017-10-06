package com.example.witold.wicioguitartuner.AmplitudeChartFragment;

import com.example.witold.wicioguitartuner.AudioUtils.AudioAnalysis.Complex;
import com.example.witold.wicioguitartuner.BaseInterfaces.BasePresenter;
import com.example.witold.wicioguitartuner.BaseInterfaces.BaseView;

/**
 * Created by Witold on 28.09.2017.
 */

public class AmplitudeChartFragmentContract {
    interface AmplitudeChartView extends BaseView{
        void showDataOnChart(Complex[] data, int maxChartValue);
    }

    interface Presenter extends BasePresenter<AmplitudeChartView>{
        void subscribeAudioRecorder();
    }
}
