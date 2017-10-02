package com.example.witold.wicioguitartuner.TunerFragment;

import com.example.witold.wicioguitartuner.AudioUtils.SingleFrequency;
import com.example.witold.wicioguitartuner.BaseInterfaces.BasePresenter;
import com.example.witold.wicioguitartuner.BaseInterfaces.BaseView;

/**
 * Created by Witold on 28.09.2017.
 */

public class TunerContract {
    interface TunerView extends BaseView{
         void setNoteTextView(SingleFrequency frequency);
    }

    interface Presenter extends BasePresenter<TunerView>{

    }
}
