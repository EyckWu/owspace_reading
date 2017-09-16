package com.eyck.androidmvpsample.presenter;

import com.eyck.androidmvpsample.model.MainModel;
import com.eyck.androidmvpsample.model.MainModelBean;
import com.eyck.androidmvpsample.view.MainView;

/**
 * Created by Eyck on 2017/9/2.
 */

public class MainPresenter implements IPresenter<MainView>,IMainPresenter {

    private MainView mainView;
    private MainModel mainModel;

    public MainPresenter(MainView mainView) {
        attachView(mainView);
        mainModel = new MainModel(this);

    }

    @Override
    public void loadDataSuccess(MainModelBean mainModelBean) {
        mainView.showData(mainModelBean);
        mainView.hideProgress();
    }

    public void loadData() {
        mainView.showProgress();
        mainModel.loadData();
    }


    @Override
    public void loadDataFailure() {
        mainView.hideProgress();
    }

    @Override
    public void attachView(MainView v) {
        this.mainView = v;
    }

    @Override
    public void detachView() {
        this.mainView = null;
    }
}
