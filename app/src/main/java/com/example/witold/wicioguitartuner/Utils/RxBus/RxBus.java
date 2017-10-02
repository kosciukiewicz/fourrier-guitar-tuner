package com.example.witold.wicioguitartuner.Utils.RxBus;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Simple event bus implementation just to provide comunication between MainActivity and pager fragments.
 */

public class RxBus {
    private PublishSubject<StartRecordingEvent> subject = PublishSubject.create();

    public void setEvent(StartRecordingEvent startRecordingEvent){
        subject.onNext(startRecordingEvent);
    }

    public Observable<StartRecordingEvent> getEventObservable(){
        return subject;
    }
}
