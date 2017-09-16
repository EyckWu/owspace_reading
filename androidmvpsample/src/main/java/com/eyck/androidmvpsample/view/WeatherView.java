package com.eyck.androidmvpsample.view;

import com.eyck.androidmvpsample.model.entity.Weather;

/**
 * Created by Eyck on 2017/9/6.
 */

public interface WeatherView {
    void showLoading();

    void hideLoading();

    void showError();

    void setWeatherInfo(Weather weather);
}
