package com.eyck.androidmvpsample.presenter;

import com.eyck.androidmvpsample.model.entity.Weather;

/**
 * Created by Eyck on 2017/9/6.
 */

public interface OnWeatherListener {

    void onSuccess(Weather weather);

    void onError();
}
