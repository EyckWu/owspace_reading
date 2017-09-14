package com.eyck.fxreading.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Eyck on 2017/9/6.
 */
@Module
public class ApplicationModule {
    private Context mContext;

    public ApplicationModule(Context context){
        this.mContext = context;
    }


    @Provides
    @Singleton
    public Context providerContext(){
        return mContext;
    }
}
