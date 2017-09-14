package com.eyck.fxreading.di.components;

import com.eyck.fxreading.di.modules.NetModules;
import com.eyck.fxreading.model.api.ApiService;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Eyck on 2017/9/1.
 */

@Component(modules = NetModules.class)
@Singleton
public interface NetComponent {
    ApiService getApiService();
    OkHttpClient getOkHttp();
    Retrofit getRetrofit();
}
