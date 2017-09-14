package com.eyck.fxreading.app;

import android.app.Application;
import android.content.Context;

import com.eyck.fxreading.BuildConfig;
import com.eyck.fxreading.di.components.DaggerNetComponent;
import com.eyck.fxreading.di.components.NetComponent;
import com.eyck.fxreading.di.modules.NetModules;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by Eyck on 2017/8/30.
 */

public class FXApplication extends Application {

    private static FXApplication instance;

    public static FXApplication get(Context context){
        return (FXApplication)context.getApplicationContext();
    }

    private NetComponent netComponent;



    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initLogger();
        initNet();
        initDatabase();
        initTypeFace();
    }

    private void initTypeFace() {

    }

    private void initDatabase() {

    }

    private void initNet() {
        netComponent = DaggerNetComponent.builder()
                .netModules(new NetModules())
                .build();
    }

    private void initLogger() {
        LogLevel logLevel;
        if(!BuildConfig.API_ENV) {
            logLevel = LogLevel.FULL;
        }else {
            logLevel = LogLevel.NONE;
        }
        Logger.init("FXReading")
                .methodCount(3)
                .logLevel(logLevel);
    }

    public static FXApplication getInstance() {
        return instance;
    }

    public NetComponent getNetComponent() {
        return netComponent;
    }
}
