package com.eyck.fxreading.presenter;

import com.eyck.fxreading.model.entity.Item;

import java.util.List;

/**
 * Created by Eyck on 2017/9/12.
 */

public interface ArtContract {
    interface Presenter{
        void getListByPage(int page, int model, String pageId,String deviceId,String createTime);
    }
    interface View{
        void showLoading();
        void dismissLoading();
        void showNoData();
        void showNoMore();
        void updateListUI(List<Item> itemList);
        void showOnFailure();

    }
}