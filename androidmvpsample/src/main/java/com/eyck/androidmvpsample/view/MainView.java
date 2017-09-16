package com.eyck.androidmvpsample.view;

import com.eyck.androidmvpsample.model.MainModelBean;

/**
 * Created by Eyck on 2017/9/2.
 */

public interface MainView {
    void showData(MainModelBean mainModelBean);

    void showProgress();

    void hideProgress();

}
