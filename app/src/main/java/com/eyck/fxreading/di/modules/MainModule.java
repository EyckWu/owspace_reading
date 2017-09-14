package com.eyck.fxreading.di.modules;

import com.eyck.fxreading.presenter.MainContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Eyck on 2017/9/6.
 */
@Module
public class MainModule {
    private final MainContract.View mView;

    public MainModule(MainContract.View mView) {
        this.mView = mView;
    }

    @Provides
    public MainContract.View provideMainView(){
        return mView;
    }
}
