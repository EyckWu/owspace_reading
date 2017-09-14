package com.eyck.fxreading.presenter;

import com.eyck.fxreading.model.api.ApiService;
import com.eyck.fxreading.model.entity.Item;
import com.eyck.fxreading.model.entity.Result;
import com.eyck.fxreading.utils.TimeUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Eyck on 2017/9/12.
 */

public class ArtPresenter implements ArtContract.Presenter{

    private ArtContract.View view;
    private ApiService apiService;

    @Inject
    public ArtPresenter(ArtContract.View view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    @Override
    public void getListByPage(int page, int model, String pageId, String deviceId, String createTime) {
        apiService.getList("api","getList",page,model,pageId,createTime,"android","1.3.0", TimeUtil.getCurrentSeconds(), deviceId,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result.Data<List<Item>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showOnFailure();
                    }

                    @Override
                    public void onNext(Result.Data<List<Item>> listData) {
                        int size = listData.getDatas().size();
                        if(size>0){
                            view.updateListUI(listData.getDatas());
                        }else {
                            view.showNoMore();
                        }
                    }
                });
    }
}
