package com.eyck.androidmvpsample.model;

import com.eyck.androidmvpsample.presenter.OnWeatherListener;

/**
 * Created by Eyck on 2017/9/6.
 */

public interface WeatherModel {

    void loadWeather(String cityNo, OnWeatherListener listener);

}
