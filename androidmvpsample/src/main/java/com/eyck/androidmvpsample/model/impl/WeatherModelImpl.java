package com.eyck.androidmvpsample.model.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.eyck.androidmvpsample.model.WeatherModel;
import com.eyck.androidmvpsample.model.entity.Weather;
import com.eyck.androidmvpsample.presenter.OnWeatherListener;
import com.eyck.androidmvpsample.util.volley.VolleyRequest;

/**
 * Created by Eyck on 2017/9/6.
 */

public class WeatherModelImpl implements WeatherModel {
    @Override
    public void loadWeather(String cityNo, final OnWeatherListener listener) {
        VolleyRequest.newInstance().newGsonRequest("http://www.weather.com.cn/data/sk/" + cityNo + ".html",
                Weather.class, new Response.Listener<Weather>() {
                    @Override
                    public void onResponse(Weather weather) {
                        if (weather != null) {
                            listener.onSuccess(weather);
                        } else {
                            listener.onError();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError();
                    }
                });
    }
}
