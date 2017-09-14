package com.eyck.fxreading.utils.tool;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by Eyck on 2017/9/12.
 */

public class RxBus {
    private static volatile RxBus instance;

    private final Subject<Object,Object> bus;

    private RxBus(){
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    public static RxBus getInstance(){
        if(instance == null) {
            synchronized (RxBus.class){
                if(instance == null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    public void postEvent(Object event){
        bus.onNext(event);
    }

    public <T> Observable<T> toObservable(Class<T> eventType){//泛型方法
        return bus.ofType(eventType);
    }

}
