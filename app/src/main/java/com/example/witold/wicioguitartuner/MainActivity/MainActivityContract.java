package com.example.witold.wicioguitartuner.MainActivity;

import com.example.witold.wicioguitartuner.BaseInterfaces.BasePresenter;
import com.example.witold.wicioguitartuner.BaseInterfaces.BaseView;

/**
 * Created by Witold on 05.10.2017.
 */

public class MainActivityContract {
    interface MainActivityView extends BaseView {
        void setRecordedFrequencyTextView(float freq);
    }

    interface Presenter extends BasePresenter<MainActivityView> {
        void subscribeAudioRecorder();
        void startAudioRecorder();
        void finnishAudioRecorder();
    }
}
