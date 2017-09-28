package com.example.witold.wicioguitartuner.BaseInterfaces;


/**
 * Created by Witold on 26.08.2017.
 */

public interface BasePresenter<T extends BaseView> {
    void onViewAttached(T view);
    void onViewDetached();
}
