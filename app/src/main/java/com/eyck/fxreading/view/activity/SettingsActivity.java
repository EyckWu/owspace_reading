package com.eyck.fxreading.view.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eyck.fxreading.BuildConfig;
import com.eyck.fxreading.R;

import java.io.File;
import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {

    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.push_toggle)
    Switch pushToggle;
    @Bind(R.id.cacheSize)
    TextView cacheSize;
    @Bind(R.id.cacheLayout)
    RelativeLayout cacheLayout;
    @Bind(R.id.about)
    RelativeLayout about;
    @Bind(R.id.feedback)
    RelativeLayout feedback;
    @Bind(R.id.version_tv)
    TextView versionTv;
    @Bind(R.id.checkUpgrade)
    RelativeLayout checkUpgrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        initView();
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
        versionTv.setText(BuildConfig.VERSION_NAME);
        File file = Glide.getPhotoCacheDir(this);
        DecimalFormat fnum = new DecimalFormat("##0.00");
        String dd = fnum.format(getDirSize(file));
        cacheSize.setText(dd + "M");
    }

    private float getDirSize(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                float size = 0;
                for (File f : children) {
                    size += getDirSize(f);
                }
                return size;
            } else {
                float size = (float) file.length() / 1024 / 1024;
                return size;
            }
        } else {
            return 0.0f;
        }
    }


    @OnClick({R.id.push_toggle, R.id.cacheLayout, R.id.feedback, R.id.checkUpgrade})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.push_toggle:
                break;
            case R.id.cacheLayout:
                break;
            case R.id.feedback:
                break;
            case R.id.checkUpgrade:
                break;
        }
    }
}
