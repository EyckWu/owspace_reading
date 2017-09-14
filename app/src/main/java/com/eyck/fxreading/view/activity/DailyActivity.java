package com.eyck.fxreading.view.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.eyck.fxreading.R;
import com.eyck.fxreading.app.FXApplication;
import com.eyck.fxreading.di.components.DaggerDailyComponent;
import com.eyck.fxreading.di.modules.DailyModule;
import com.eyck.fxreading.model.entity.Item;
import com.eyck.fxreading.presenter.ArtContract;
import com.eyck.fxreading.presenter.ArtPresenter;
import com.eyck.fxreading.utils.AppUtil;
import com.eyck.fxreading.view.adapter.DailyViewPagerAdapter;
import com.eyck.fxreading.view.base.BaseActivity;
import com.eyck.fxreading.view.widget.VerticalViewPager;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DailyActivity extends BaseActivity implements ArtContract.View{

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.view_pager)
    VerticalViewPager viewPager;

    @Inject
    ArtPresenter presenter;


    private int page=1;
    private static final int MODE = 4;
    private boolean isLoading=true;
    private String deviceId;

    private DailyViewPagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);
        ButterKnife.bind(this);
        initPresenter();
        initView();
        deviceId = AppUtil.getDeviceId(this);
        presenter.getListByPage(page,MODE,"0", deviceId,"0");
    }

    private void initView() {
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(" ");
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        pagerAdapter = new DailyViewPagerAdapter(getSupportFragmentManager(),this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new VerticalViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (pagerAdapter.getCount() <= position +2 && !isLoading){
                    Logger.e("page="+page+",getLastItemId="+pagerAdapter.getLastItemId());
                    presenter.getListByPage(page, 0, pagerAdapter.getLastItemId(),deviceId,pagerAdapter.getLastItemCreateTime());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initPresenter() {
        DaggerDailyComponent.builder()
                .dailyModule(new DailyModule(this))
                .netComponent(FXApplication.getInstance().getNetComponent())
                .build()
                .inject(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showNoMore() {
        Toast.makeText(this,"没有更多数据了",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateListUI(List<Item> itemList) {
        isLoading = false;
        pagerAdapter.setArtList(itemList);
        page++;
    }

    @Override
    public void showOnFailure() {
        if (pagerAdapter.getCount()==0){
            showNoData();
        }else{
            Toast.makeText(this,"加载数据失败，请检查您的网络",Toast.LENGTH_SHORT).show();
        }
    }
}
