package com.example.witold.wicioguitartuner.BaseInterfaces;


/**
 * Created by Witold on 26.08.2017.
 */

public interface IBasePresenter<T extends IBaseView> {
    void onCreate();
    void onStart();
    void onStop();
    void onPause();
    void attachView(T view);
}
