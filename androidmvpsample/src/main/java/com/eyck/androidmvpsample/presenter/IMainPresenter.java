package com.eyck.androidmvpsample.presenter;

import com.eyck.androidmvpsample.model.MainModelBean;

/**
 * Created by Eyck on 2017/9/2.
 */

public interface IMainPresenter {
    void loadDataSuccess(MainModelBean mainModelBean);

    void loadDataFailure();
}
