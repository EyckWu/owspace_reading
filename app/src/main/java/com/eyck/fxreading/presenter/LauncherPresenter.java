package com.eyck.fxreading.presenter;

import com.eyck.fxreading.app.FXApplication;
import com.eyck.fxreading.model.api.ApiService;
import com.eyck.fxreading.model.entity.LauncherEntity;
import com.eyck.fxreading.utils.NetUtil;
import com.eyck.fxreading.utils.OkHttpImageDownloader;
import com.eyck.fxreading.utils.TimeUtil;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by Eyck on 2017/8/30.
 */

public class LauncherPresenter implements LauncherContract.Presenter {

    private LauncherContract.View view;

    private ApiService apiService;

    @Inject
    public LauncherPresenter(LauncherContract.View view,ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
        Logger.d("apiService:"+apiService);
    }

    @Override
    public void getLauncher(String deviceId) {
        String client = "android";
        String version = "1.3.0";
        Long time = TimeUtil.getCurrentSeconds();
        apiService.getLauncher(client,version,time,deviceId)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<LauncherEntity>() {
                    @Override
                    public void onCompleted() {
                        Logger.d("load launcher onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("load launcher onError");
                    }

                    @Override
                    public void onNext(LauncherEntity launcherEntity) {
                        if(NetUtil.isWifi(FXApplication.getInstance().getApplicationContext())) {
                            if(launcherEntity != null) {
                                List<String> imgs = launcherEntity.getImages();
                                for (String url:imgs){
                                    OkHttpImageDownloader.download(url);
                                }
                            }
                        }else {
                            Logger.d("不是WIFI环境,就不去下载图片了");
                        }
                    }
                });

    }
}
