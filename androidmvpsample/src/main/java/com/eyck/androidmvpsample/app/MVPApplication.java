package com.eyck.androidmvpsample.app;

import android.app.Application;

import com.eyck.androidmvpsample.util.volley.VolleyRequest;

/**
 * Created by Eyck on 2017/9/6.
 */

public class MVPApplication extends Application{

    private static MVPApplication instance;

    public MVPApplication() {
        instance = this;
    }

    public static Application getContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        VolleyRequest.buildRequestQueue(this);
    }
}
