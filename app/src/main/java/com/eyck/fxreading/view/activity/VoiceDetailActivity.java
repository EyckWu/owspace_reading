package com.eyck.fxreading.view.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
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
import com.eyck.fxreading.player.IPlayer;
import com.eyck.fxreading.player.PlayState;
import com.eyck.fxreading.player.PlayerService;
import com.eyck.fxreading.presenter.DetailContract;
import com.eyck.fxreading.presenter.DetailPresenter;
import com.eyck.fxreading.utils.AppUtil;
import com.eyck.fxreading.utils.TimeUtil;
import com.eyck.fxreading.utils.tool.AnalysisHTML;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.orhanobut.logger.Logger;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VoiceDetailActivity extends AppCompatActivity implements IPlayer.Callback ,DetailContract.View, ObservableScrollViewCallbacks {

    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.button_play_last)
    AppCompatImageView buttonPlayLast;
    @Bind(R.id.button_play_toggle)
    AppCompatImageView buttonPlayToggle;
    @Bind(R.id.button_play_next)
    AppCompatImageView buttonPlayNext;
    @Bind(R.id.layout_play_controls)
    LinearLayout layoutPlayControls;
    @Bind(R.id.text_view_progress)
    TextView textViewProgress;
    @Bind(R.id.seek_bar)
    AppCompatSeekBar seekBar;
    @Bind(R.id.text_view_duration)
    TextView textViewDuration;
    @Bind(R.id.layout_progress)
    LinearLayout layoutProgress;
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

    private PlayerService mPlayerService;

    Handler mHandler = new Handler();

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mPlayerService = ((PlayerService.LocalBinder)service).getService();
            register();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            unRegister();
            mPlayerService = null;
        }
    };
    private int mParallaxImageHeight;
    private String song;
    private Timer timer;

    private void register() {
        mPlayerService.registerCallback(this);
    }

    private void unRegister() {
        if (mPlayerService != null) {
            mPlayerService.unregisterCallback(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("onCreate");
        setContentView(R.layout.activity_voice_detail);
        ButterKnife.bind(this);
        initPresenter();
        initView();
        bindPlayerService();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.d("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.d("onStop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.d("onStart");
        Bundle bundle = getIntent().getExtras();
        Item item = bundle.getParcelable(Constant.ITEM);
        if(item != null) {
            Glide.with(this).load(item.getThumbnail()).centerCrop().into(image);
            newsTopLeadLine.setVisibility(View.VISIBLE);
            newsTopImgUnderLine.setVisibility(View.VISIBLE);
            newsTopType.setText(this.getResources().getString(R.string.voice));
            newsTopDate.setText(item.getUpdate_time());
            newsTopTitle.setText(item.getTitle());
            newsTopAuthor.setText(item.getAuthor());
            newsTopLead.setText(item.getLead());
            newsTopLead.setLineSpacing(1.5f, 1.8f);
            presenter.getDetail(item.getId());
        }
    }

    private void bindPlayerService() {
        this.bindService(new Intent(this,PlayerService.class),serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unRegister();
        mHandler.removeCallbacks(mRunnable);
        ButterKnife.unbind(this);
        super.onDestroy();
        Logger.d("onDestroy");
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
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                cancelTimer();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mPlayerService.seekTo(getSeekDuration(seekBar.getProgress()));
                playTimer();
            }


        });
    }

    private void playTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(mPlayerService == null) {
                    return;
                }
                if(mPlayerService.isPlaying()) {
                    mHandler.post(mRunnable);
                }
            }
        },0,1000);
    }

    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timer = null;
    }

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if(mPlayerService.isPlaying()) {
                if(isFinishing()) {
                    return;
                }
                int progress = (int) (seekBar.getMax()*((float)mPlayerService.getProgress()/(float)mPlayerService.getDuration()));
                updateProgressTextWithProgress(mPlayerService.getProgress());
                if (progress >= 0 && progress <= seekBar.getMax()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        seekBar.setProgress(progress, true);
                    } else {
                        seekBar.setProgress(progress);
                    }
                }
            }
        }
    };

    private void updateProgressTextWithProgress(int progress) {
        textViewProgress.setText(TimeUtil.formatDuration(progress));
    }

    private  void updateDuration(){
        textViewDuration.setText(TimeUtil.formatDuration(mPlayerService.getDuration()));
    }

    private int getSeekDuration(int progress) {
        return (int) (getCurrentDuration() * ((float) progress / seekBar.getMax()));
    }

    private int getCurrentDuration() {
        int duration=0;
        if (mPlayerService != null){
            duration = mPlayerService.getDuration();
        }
        return duration;
    }


    private void initWebViewSetting() {
        WebSettings localWebSettings = this.webView.getSettings();
        localWebSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        localWebSettings.setJavaScriptEnabled(true);
        localWebSettings.setSupportZoom(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        localWebSettings.setUseWideViewPort(true);
        localWebSettings.setLoadWithOverviewMode(true);
    }

    private void initPresenter() {
        DaggerDetailComponent.builder()
                .netComponent(FXApplication.getInstance().getNetComponent())
                .detailModule(new DetailModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.button_play_last, R.id.button_play_toggle, R.id.button_play_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_play_last://
                break;
            case R.id.button_play_toggle://bofang
                playToggle();
                break;
            case R.id.button_play_next:
                break;
        }
    }

    private void playToggle() {
        if(mPlayerService == null || song == null){
            return ;
        }
        if(mPlayerService.isPlaying()) {
            if(song.equals(mPlayerService.getSong())) {
                mPlayerService.pause();
                buttonPlayToggle.setImageResource(R.drawable.ic_play);
            }else{
                mPlayerService.play(song);
                buttonPlayToggle.setImageResource(R.drawable.ic_pause);
            }
        }else{
            if (song.equals(mPlayerService.getSong())) {
                mPlayerService.play();
            } else {
                mPlayerService.play(song);
            }
            buttonPlayToggle.setImageResource(R.drawable.ic_pause);
        }
    }

    @Override
    public void onComplete(PlayState state) {
        cancelTimer();
    }

    @Override
    public void onPlayStatusChanged(PlayState status) {
        switch (status) {
            case INIT:

                break;
            case PREPARE:

                break;
            case PLAYING:
                updateDuration();
                playTimer();
                buttonPlayToggle.setImageResource(R.drawable.ic_pause);
                break;
            case PAUSE:
                cancelTimer();
                buttonPlayToggle.setImageResource(R.drawable.ic_play);
                break;
            case ERROR:

                break;
            case COMPLETE:
                cancelTimer();
                buttonPlayToggle.setImageResource(R.drawable.ic_play);
                seekBar.setProgress(0);
                break;

        }
    }

    @Override
    public void onPosition(int position) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void updateListUI(DetailEntity detailEntity) {
        song = detailEntity.getFm();
        Logger.d(song);
        Logger.d(detailEntity.toString());
        if (detailEntity.getParseXML() == 1) {
            int i = detailEntity.getLead().trim().length();
            AnalysisHTML analysisHTML = new AnalysisHTML();
            analysisHTML.loadHtml(this, detailEntity.getContent(), analysisHTML.HTML_STRING, newsParseWeb, i);
            newsTopType.setText(this.getResources().getString(R.string.voice));
        } else {
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
