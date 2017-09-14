package com.eyck.fxreading.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.eyck.fxreading.model.entity.Item;
import com.eyck.fxreading.view.fragment.MainFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eyck on 2017/9/12.
 */

public class VerticalPagerAdapter extends FragmentStatePagerAdapter{
    private List<Item> dataList=new ArrayList<>();
    public VerticalPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return MainFragment.instance(dataList.get(position));
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    public void setDataSet(List<Item> datas){
        dataList.clear();
        dataList.addAll(datas);
        notifyDataSetChanged();
    }

    public String getLastItemId(){
        if(dataList.size() == 0) {
            return "0";
        }
        Item item = dataList.get(dataList.size() - 1);
        return item.getId();
    }
    public String getLastItemCreateTime(){
        if (dataList.size()==0){
            return "0";
        }
        Item item = dataList.get(dataList.size()-1);
        return item.getCreate_time();
    }
}
