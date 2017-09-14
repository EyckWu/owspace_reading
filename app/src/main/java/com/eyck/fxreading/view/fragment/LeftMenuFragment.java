package com.eyck.fxreading.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eyck.fxreading.R;
import com.eyck.fxreading.model.entity.Constant;
import com.eyck.fxreading.model.entity.Event;
import com.eyck.fxreading.utils.tool.RxBus;
import com.eyck.fxreading.view.activity.ArtActivity;
import com.eyck.fxreading.view.activity.DailyActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Eyck on 2017/9/12.
 */

public class LeftMenuFragment extends Fragment {

    @Bind(R.id.right_slide_close)
    ImageView rightSlideClose;
    @Bind(R.id.search)
    ImageView search;
    @Bind(R.id.title_bar)
    RelativeLayout titleBar;
    @Bind(R.id.logo_iv)
    ImageView logoIv;
    @Bind(R.id.title_logo_rl)
    RelativeLayout titleLogoRl;
    @Bind(R.id.home_page_tv)
    TextView homePageTv;
    @Bind(R.id.words_tv)
    TextView wordsTv;
    @Bind(R.id.voice_tv)
    TextView voiceTv;
    @Bind(R.id.video_tv)
    TextView videoTv;
    @Bind(R.id.calendar_tv)
    TextView calendarTv;
    @Bind(R.id.column_ll)
    LinearLayout columnLl;

    private List<View> mViewList = new ArrayList();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left_menu, container, false);
        ButterKnife.bind(this, view);
        loadView();
        return view;
    }

    private void loadView() {
        mViewList.add(homePageTv);
        mViewList.add(wordsTv);
        mViewList.add(voiceTv);
        mViewList.add(videoTv);
        mViewList.add(calendarTv);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void startAnim() {
        startIconAnim(search);
        startIconAnim(rightSlideClose);
        startColumnAnim();
    }

    private void startColumnAnim() {
        TranslateAnimation localTranslateAnimation = new TranslateAnimation(0F, 0.0F, 0.0F, 0.0F);
        localTranslateAnimation.setDuration(700L);
        for (int j=0;j<mViewList.size();j++){
            View localView = this.mViewList.get(j);
            localView.startAnimation(localTranslateAnimation);
            localTranslateAnimation = new TranslateAnimation(j * -35,0.0F, 0.0F, 0.0F);
            localTranslateAnimation.setDuration(700L);
        }
    }

    private void startIconAnim(View paramView) {
        ScaleAnimation localScaleAnimation = new ScaleAnimation(0.1F, 1.0F, 0.1F, 1.0F, paramView.getWidth() / 2, paramView.getHeight() / 2);
        localScaleAnimation.setDuration(1000L);
        paramView.startAnimation(localScaleAnimation);
        float f1 = paramView.getWidth() / 2;
        float f2 = paramView.getHeight() / 2;
        localScaleAnimation = new ScaleAnimation(1.0F, 0.5F, 1.0F, 0.5F, f1, f2);
        localScaleAnimation.setInterpolator(new BounceInterpolator());
    }

    @OnClick({R.id.right_slide_close, R.id.search, R.id.title_bar, R.id.logo_iv, R.id.title_logo_rl
            , R.id.home_page_tv, R.id.words_tv,R.id.voice_tv, R.id.video_tv, R.id.calendar_tv})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.right_slide_close:
                RxBus.getInstance().postEvent(new Event(1000, Constant.CLOSEMENU));
                break;
            case R.id.search:
                break;
            case R.id.title_bar:
                break;
            case R.id.logo_iv:
                break;
            case R.id.title_logo_rl:
                break;
            case R.id.home_page_tv:
                RxBus.getInstance().postEvent(new Event(1000, Constant.CLOSEMENU));
                break;
            case R.id.words_tv:
                intent = new Intent(getActivity(), ArtActivity.class);
                intent.putExtra(Constant.MODE, 1);
                intent.putExtra(Constant.TITLE, getActivity().getResources().getString(R.string.word));
                startActivity(intent);
                break;
            case R.id.voice_tv:
                intent = new Intent(getActivity(), ArtActivity.class);
                intent.putExtra(Constant.MODE, 3);
                intent.putExtra(Constant.TITLE, getActivity().getResources().getString(R.string.voice));
                startActivity(intent);
                break;
            case R.id.video_tv:
                intent = new Intent(getActivity(), ArtActivity.class);
                intent.putExtra(Constant.MODE, 2);
                intent.putExtra(Constant.TITLE, getActivity().getResources().getString(R.string.video));
                startActivity(intent);
                break;
            case R.id.calendar_tv:
                intent = new Intent(getActivity(), DailyActivity.class);
                startActivity(intent);
                break;
        }
    }

}
