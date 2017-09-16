package com.eyck.androidmvpsample.presenter;

/**
 * Created by Eyck on 2017/9/2.
 */

public interface IPresenter<V> {
    void attachView(V v);

    void detachView();
}
