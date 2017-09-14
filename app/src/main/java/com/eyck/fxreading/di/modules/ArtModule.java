package com.eyck.fxreading.di.modules;

import com.eyck.fxreading.presenter.ArtContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Eyck on 2017/9/12.
 */

@Module
public class ArtModule {
    private ArtContract.View mView;

    public ArtModule(ArtContract.View mView) {
        this.mView = mView;
    }
    @Provides
    public ArtContract.View provideView(){
        return mView;
    }
}
