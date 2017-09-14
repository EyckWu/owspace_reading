package com.eyck.fxreading.di.modules;

import com.eyck.fxreading.presenter.LauncherContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Eyck on 2017/9/1.
 */
@Module
public class LauncherModule {
    private LauncherContract.View view;

    public LauncherModule(LauncherContract.View view) {
        this.view = view;
    }

    @Provides
    public LauncherContract.View provideView(){
        return view;
    }
}
