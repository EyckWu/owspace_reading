package com.eyck.fxreading.view.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.eyck.fxreading.R;
import com.eyck.fxreading.app.FXApplication;
import com.eyck.fxreading.di.components.DaggerMainComponent;
import com.eyck.fxreading.di.modules.MainModule;
import com.eyck.fxreading.model.entity.Event;
import com.eyck.fxreading.model.entity.Item;
import com.eyck.fxreading.presenter.MainContract;
import com.eyck.fxreading.presenter.MainPresenter;
import com.eyck.fxreading.utils.AppUtil;
import com.eyck.fxreading.utils.PreferenceUtils;
import com.eyck.fxreading.utils.TimeUtil;
import com.eyck.fxreading.utils.tool.RxBus;
import com.eyck.fxreading.view.adapter.VerticalPagerAdapter;
import com.eyck.fxreading.view.base.BaseActivity;
import com.eyck.fxreading.view.fragment.LeftMenuFragment;
import com.eyck.fxreading.view.fragment.RightMenuFragment;
import com.eyck.fxreading.view.widget.LunarDialog;
import com.eyck.fxreading.view.widget.VerticalViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends BaseActivity implements MainContract.View{



    @Bind(R.id.view_pager)
    VerticalViewPager viewPager;
    @Bind(R.id.left_slide)
    ImageView leftSlide;
    @Bind(R.id.right_slide)
    ImageView rightSlide;
    @Bind(R.id.title_bar)
    RelativeLayout titleBar;

    @Inject
    MainPresenter presenter;
    private SlidingMenu slidingMenu;
    private LeftMenuFragment leftMenuFragment;
    private RightMenuFragment rightMenuFragment;
    private VerticalPagerAdapter verticalPagerAdapter;

    private int page = 1;
    private boolean isLoading = true;
    private long mLastClickTime;

    private Subscription subscription;

    private String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initMenu();
        initPage();
        deviceId = AppUtil.getDeviceId(this);
        String getLunar= PreferenceUtils.getPrefString(this,"getLunar",null);
        if (!TimeUtil.getDate("yyyyMMdd").equals(getLunar)){
            loadRecommend();
        }
        loadData(1, 0, "0", "0");
    }

    private void loadData(int page, int mode, String pageId, String createTime) {
        isLoading = true;
        presenter.getListByPage(page, mode, pageId, deviceId, createTime);
    }

    private void loadRecommend() {
        presenter.getRecommend(deviceId);
    }

    @Override
    public void onBackPressed() {
        if (slidingMenu.isMenuShowing() || slidingMenu.isSecondaryMenuShowing()) {
            slidingMenu.showContent();
        } else {
            if (System.currentTimeMillis() - mLastClickTime <= 2000L) {
                super.onBackPressed();
            } else {
                mLastClickTime = System.currentTimeMillis();
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private void initPage() {
        verticalPagerAdapter = new VerticalPagerAdapter(getSupportFragmentManager());
        DaggerMainComponent.builder()
                .mainModule(new MainModule(this))
                .netComponent(FXApplication.get(this).getNetComponent())
                .build()
                .inject(this);
        viewPager.setAdapter(verticalPagerAdapter);
        viewPager.addOnPageChangeListener(new VerticalViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (verticalPagerAdapter.getCount() <= position + 2 && !isLoading) {
                    if (isLoading){
                        Toast.makeText(MainActivity.this,"正在努力加载...",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Logger.i("page=" + page + ",getLastItemId=" + verticalPagerAdapter.getLastItemId());
                    loadData(page, 0, verticalPagerAdapter.getLastItemId(), verticalPagerAdapter.getLastItemCreateTime());
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initMenu() {
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        // 设置触摸屏幕的模式
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        // 设置渐入渐出效果的值
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.setFadeEnabled(true);
        slidingMenu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.left_menu);
        leftMenuFragment = new LeftMenuFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.left_menu,leftMenuFragment).commit();
        slidingMenu.setSecondaryMenu(R.layout.right_menu);
        rightMenuFragment = new RightMenuFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.right_menu, rightMenuFragment).commit();
        subscription = RxBus.getInstance().toObservable(Event.class)
                .subscribe(new Action1<Event>() {
                    @Override
                    public void call(Event event) {
                        slidingMenu.showContent();
                    }
                });
    }

    @OnClick({R.id.left_slide, R.id.right_slide})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_slide:
                slidingMenu.showMenu();
                leftMenuFragment.startAnim();
                break;
            case R.id.right_slide:
                slidingMenu.showSecondaryMenu();
                rightMenuFragment.startAnim();
                break;
        }
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
        Toast.makeText(this, "没有更多数据了", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateListUI(List<Item> itemList) {
        isLoading = false;
        verticalPagerAdapter.setDataSet(itemList);
        page++;
    }

    @Override
    public void showOnFailure() {
        Toast.makeText(this, "加载数据失败，请检查您的网络", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLunar(String content) {
        Logger.e("showLunar:"+content);
        PreferenceUtils.setPrefString(this,"getLunar",TimeUtil.getDate("yyyyMMdd"));
        LunarDialog lunarDialog = new LunarDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_lunar,null);
        ImageView imageView = (ImageView)view.findViewById(R.id.image_iv);
        Glide.with(this).load(content).into(imageView);
        lunarDialog.setContentView(view);
        lunarDialog.show();
    }

    @Override
    protected void onDestroy() {
        if (subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
        super.onDestroy();
    }
}
