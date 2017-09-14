package com.eyck.fxreading.presenter;

import com.eyck.fxreading.model.entity.DetailEntity;

/**
 * Created by Eyck on 2017/9/12.
 */

public interface DetailContract {
    interface Presenter{
        void getDetail(String itemId);
    }
    interface View{
        void showLoading();
        void dismissLoading();
        void updateListUI(DetailEntity detailEntity);
        void showOnFailure();

    }
}
