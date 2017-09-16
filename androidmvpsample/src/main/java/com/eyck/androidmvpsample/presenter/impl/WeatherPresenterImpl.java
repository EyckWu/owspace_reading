package com.eyck.androidmvpsample.presenter.impl;

import com.eyck.androidmvpsample.model.WeatherModel;
import com.eyck.androidmvpsample.model.entity.Weather;
import com.eyck.androidmvpsample.model.impl.WeatherModelImpl;
import com.eyck.androidmvpsample.presenter.OnWeatherListener;
import com.eyck.androidmvpsample.presenter.WeatherPresenter;
import com.eyck.androidmvpsample.view.WeatherView;

/**
 * Created by Eyck on 2017/9/6.
 */

public class WeatherPresenterImpl implements WeatherPresenter,OnWeatherListener{

    private WeatherView weatherView;
    private WeatherModel weatherModel;

    public WeatherPresenterImpl(WeatherView weatherView) {
        this.weatherView = weatherView;
        weatherModel = new WeatherModelImpl();
    }


    @Override
    public void onSuccess(Weather weather) {
        weatherView.hideLoading();
        weatherView.setWeatherInfo(weather);
    }

    @Override
    public void onError() {
        weatherView.hideLoading();
        weatherView.showError();
    }

    @Override
    public void getWeather(String cityNo) {
        weatherView.showLoading();
        weatherModel.loadWeather(cityNo, this);
    }
}
