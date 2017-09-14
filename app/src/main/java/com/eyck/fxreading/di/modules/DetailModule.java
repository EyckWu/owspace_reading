package com.eyck.fxreading.di.modules;

import com.eyck.fxreading.presenter.DetailContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Eyck on 2017/9/12.
 */

@Module
public class DetailModule {
    private DetailContract.View mView;

    public DetailModule(DetailContract.View view){
        this.mView = view;
    }

    @Provides
    public DetailContract.View provideView(){
        return mView;
    }
}
