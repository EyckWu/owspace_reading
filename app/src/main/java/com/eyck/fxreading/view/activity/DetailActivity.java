package com.eyck.fxreading.view.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eyck.fxreading.BuildConfig;
import com.eyck.fxreading.R;
import com.eyck.fxreading.app.FXApplication;
import com.eyck.fxreading.di.components.DaggerDetailComponent;
import com.eyck.fxreading.di.modules.DetailModule;
import com.eyck.fxreading.model.entity.Constant;
import com.eyck.fxreading.model.entity.DetailEntity;
import com.eyck.fxreading.model.entity.Item;
import com.eyck.fxreading.presenter.DetailContract;
import com.eyck.fxreading.presenter.DetailPresenter;
import com.eyck.fxreading.utils.AppUtil;
import com.eyck.fxreading.utils.tool.AnalysisHTML;
import com.eyck.fxreading.view.base.BaseActivity;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends BaseActivity implements DetailContract.View, ObservableScrollViewCallbacks {

    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.news_top_img_under_line)
    View newsTopImgUnderLine;
    @Bind(R.id.news_top_type)
    TextView newsTopType;
    @Bind(R.id.news_top_date)
    TextView newsTopDate;
    @Bind(R.id.news_top_title)
    TextView newsTopTitle;
    @Bind(R.id.news_top_author)
    TextView newsTopAuthor;
    @Bind(R.id.news_top_lead)
    TextView newsTopLead;
    @Bind(R.id.news_top_lead_line)
    View newsTopLeadLine;
    @Bind(R.id.news_top)
    LinearLayout newsTop;
    @Bind(R.id.news_parse_web)
    LinearLayout newsParseWeb;
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.scrollView)
    ObservableScrollView scrollView;
    @Bind(R.id.favorite)
    ImageView favorite;
    @Bind(R.id.write)
    ImageView write;
    @Bind(R.id.share)
    ImageView share;
    @Bind(R.id.toolBar)
    Toolbar toolBar;

    @Inject
    DetailPresenter presenter;
    private int mParallaxImageHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        initPresenter();
        initView();
    }

    private void initPresenter() {
        DaggerDetailComponent.builder()
                .detailModule(new DetailModule(this))
                .netComponent(FXApplication.getInstance().getNetComponent())
                .build()
                .inject(this);
    }

    private void initView() {
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        toolBar.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.primary)));
        scrollView.setScrollViewCallbacks(this);
        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.parallax_image_height);
    }

    private void initWebViewSetting() {
        WebSettings settings = this.webView.getSettings();
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle bundle = getIntent().getExtras();
        Item item = bundle.getParcelable(Constant.ITEM);
        if (item != null){
            Glide.with(this).load(item.getThumbnail()).centerCrop().into(image);
            int mode = Integer.valueOf(item.getModel());
            newsTopLeadLine.setVisibility(View.VISIBLE);
            newsTopImgUnderLine.setVisibility(View.VISIBLE);
            newsTopType.setText(this.getResources().getString(R.string.word));
            newsTopDate.setText(item.getUpdate_time());
            newsTopTitle.setText(item.getTitle());
            newsTopAuthor.setText(item.getAuthor());
            newsTopLead.setText(item.getLead());
            newsTopLead.setLineSpacing(1.5f,1.8f);
            presenter.getDetail(item.getId());
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.favorite, R.id.write, R.id.share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.favorite:
                break;
            case R.id.write:
                break;
            case R.id.share:
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
    public void updateListUI(DetailEntity detailEntity) {
        if(detailEntity.getParseXML() == 1) {
            int length = detailEntity.getLead().trim().length();
            AnalysisHTML analysisHTML = new AnalysisHTML();
            analysisHTML.loadHtml(this,detailEntity.getContent(),analysisHTML.HTML_STRING,newsParseWeb,length);
            newsTopType.setText(this.getResources().getText(R.string.word));
        }else{
            initWebViewSetting();
            newsParseWeb.setVisibility(View.GONE);
            image.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            newsTop.setVisibility(View.GONE);
            webView.loadUrl(addParams2WezeitUrl(detailEntity.getHtml5(), false));
        }
    }

    public String addParams2WezeitUrl(String url, boolean paramBoolean) {
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append(url);
        localStringBuffer.append("?client=android");
        localStringBuffer.append("&device_id=" + AppUtil.getDeviceId(this));
        localStringBuffer.append("&version=" + BuildConfig.VERSION_NAME);
        if (paramBoolean)
            localStringBuffer.append("&show_video=0");
        else {
            localStringBuffer.append("&show_video=1");
        }
        return localStringBuffer.toString();
    }

    @Override
    public void showOnFailure() {

    }

    @Override
    public void onScrollChanged(int i, boolean b, boolean b1) {
        int baseColor = getResources().getColor(R.color.primary);
        float alpha = Math.min(1, (float) i / mParallaxImageHeight);
        toolBar.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }
}
