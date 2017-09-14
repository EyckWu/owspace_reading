package com.eyck.fxreading.di.modules;

import com.eyck.fxreading.presenter.ArtContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Eyck on 2017/9/12.
 */

@Module
public class DailyModule {
    private ArtContract.View mView;

    public DailyModule(ArtContract.View view){
        this.mView = view;
    }

    @Provides
    public ArtContract.View provideView(){
        return mView;
    }
}
