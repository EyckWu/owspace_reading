package com.eyck.fxreading.view.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.eyck.fxreading.R;
import com.eyck.fxreading.app.FXApplication;
import com.eyck.fxreading.di.components.DaggerArtComponent;
import com.eyck.fxreading.di.modules.ArtModule;
import com.eyck.fxreading.model.entity.Constant;
import com.eyck.fxreading.model.entity.Item;
import com.eyck.fxreading.presenter.ArtContract;
import com.eyck.fxreading.presenter.ArtPresenter;
import com.eyck.fxreading.utils.AppUtil;
import com.eyck.fxreading.view.adapter.ArtRecycleViewAdapter;
import com.eyck.fxreading.view.base.BaseActivity;
import com.eyck.fxreading.view.widget.DividerItemDecoration;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArtActivity extends BaseActivity implements ArtContract.View{

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.recycleView)
    RecyclerView recycleView;
    @Bind(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;

    @Inject
    ArtPresenter presenter;

    private ArtRecycleViewAdapter artRecycleViewAdapter;


    private int page = 1;
    private int mode = 1;
    private boolean isRefresh;
    private boolean hasMore=true;
    private String deviceId;
    private int lastVisibleItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art);
        ButterKnife.bind(this);
        mode = getIntent().getIntExtra(Constant.MODE, 1);
        initPresenter();
        initView();
    }

    private void initView() {
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        String t = getIntent().getStringExtra(Constant.TITLE);
        title.setText(t);
        deviceId = AppUtil.getDeviceId(this);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        artRecycleViewAdapter = new ArtRecycleViewAdapter(this);
        recycleView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recycleView.addItemDecoration(new DividerItemDecoration(this));
        recycleView.setAdapter(artRecycleViewAdapter);
        SinaRefreshView headerView = new SinaRefreshView(this);
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
//        TextHeaderView headerView = (TextHeaderView) View.inflate(this,R.layout.header_tv,null);
        refreshLayout.setHeaderView(headerView);

        LoadingView loadingView = new LoadingView(this);
        refreshLayout.setBottomView(loadingView);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                loadData(page, mode, artRecycleViewAdapter.getLastItemId(),deviceId, artRecycleViewAdapter.getLastItemCreateTime());
            }

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page=1;
                isRefresh=true;
                hasMore = true;
                loadData(page, mode, "0", deviceId, "0");
            }
        });
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && !isRefresh && hasMore && (lastVisibleItem+1  == artRecycleViewAdapter.getItemCount())){
                    loadData(page, mode, artRecycleViewAdapter.getLastItemId(),deviceId, artRecycleViewAdapter.getLastItemCreateTime());
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            }
        });


        page=1;
        isRefresh=true;
        hasMore = true;
        loadData(page, mode, "0", deviceId, "0");
    }

    private void loadData(int page, int mode, String pageId, String deviceId, String createTime) {
        presenter.getListByPage(page, mode, pageId, deviceId, createTime);
    }

    private void initPresenter() {
        DaggerArtComponent.builder()
                .artModule(new ArtModule(this))
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
        hasMore = false;
        if (!isRefresh){
            //显示没有更多
            artRecycleViewAdapter.setHasMore(false);
            artRecycleViewAdapter.notifyItemChanged(artRecycleViewAdapter.getItemCount()-1);
        }
    }

    @Override
    public void updateListUI(List<Item> itemList) {
        Logger.d(itemList.toString());
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefreshing();
        page++;
        if (isRefresh) {
            artRecycleViewAdapter.setHasMore(true);
            artRecycleViewAdapter.setError(false);
            isRefresh = false;
            artRecycleViewAdapter.replaceAllData(itemList);
        } else {
            artRecycleViewAdapter.setArtList(itemList);
        }
    }

    @Override
    public void showOnFailure() {
        if (!isRefresh){
            //显示失败
            artRecycleViewAdapter.setError(true);
            artRecycleViewAdapter.notifyItemChanged(artRecycleViewAdapter.getItemCount()-1);
        }else{
            Toast.makeText(this,"~~~~(>_<)~~~~刷新失败",Toast.LENGTH_SHORT).show();
        }
    }
}
