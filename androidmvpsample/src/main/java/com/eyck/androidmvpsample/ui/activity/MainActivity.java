package com.eyck.androidmvpsample.ui.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eyck.androidmvpsample.R;
import com.eyck.androidmvpsample.model.entity.Weather;
import com.eyck.androidmvpsample.model.entity.WeatherInfo;
import com.eyck.androidmvpsample.presenter.MainPresenter;
import com.eyck.androidmvpsample.presenter.WeatherPresenter;
import com.eyck.androidmvpsample.presenter.impl.WeatherPresenterImpl;
import com.eyck.androidmvpsample.view.WeatherView;

public class MainActivity extends AppCompatActivity implements WeatherView, View.OnClickListener {
    private ProgressBar mProgressBar;
    private TextView text;
    private MainPresenter mMainPresenter;

    private Dialog loadingDialog;
    private EditText cityNOInput;
    private TextView city;
    private TextView cityNO;
    private TextView temp;
    private TextView wd;
    private TextView ws;
    private TextView sd;
    private TextView wse;
    private TextView time;
    private TextView njd;

    private WeatherPresenter weatherPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        cityNOInput = (EditText) findViewById(R.id.et_city_no);
        city = (TextView) findViewById(R.id.tv_city);
        cityNO = (TextView) findViewById(R.id.tv_city_no);
        temp = (TextView) findViewById(R.id.tv_temp);
        wd = (TextView) findViewById(R.id.tv_WD);
        ws = (TextView) findViewById(R.id.tv_WS);
        sd = (TextView) findViewById(R.id.tv_SD);
        wse = (TextView) findViewById(R.id.tv_WSE);
        time = (TextView) findViewById(R.id.tv_time);
        njd = (TextView) findViewById(R.id.tv_njd);

        findViewById(R.id.btn_go).setOnClickListener(this);

        weatherPresenter = new WeatherPresenterImpl(this); //传入WeatherView
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setTitle("加载天气中...");
    }

    @Override
    public void showLoading() {
        loadingDialog.show();
    }

    @Override
    public void hideLoading() {
        loadingDialog.dismiss();
    }

    @Override
    public void showError() {
        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setWeatherInfo(Weather weather) {
        WeatherInfo info = weather.getWeatherinfo();
        city.setText(info.getCity());
        cityNO.setText(info.getCityid());
        temp.setText(info.getTemp());
        wd.setText(info.getWD());
        ws.setText(info.getWS());
        sd.setText(info.getSD());
        wse.setText(info.getWS());
        time.setText(info.getTemp());
        njd.setText(info.getNjd());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_go:
                weatherPresenter.getWeather(cityNOInput.getText().toString().trim());
                break;
        }
    }

//    private void initView() {
//        text = (TextView) findViewById(R.id.text);
//        mProgressBar = (ProgressBar) findViewById(R.id.mProgressBar);
//        mMainPresenter = new MainPresenter(this);
//        //制造延迟效果
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(MainActivity.this, "mMainPresenter.loadData()", Toast.LENGTH_SHORT).show();
//                mMainPresenter.loadData();
//            }
//        }, 2000);
//    }
//
//    @Override
//    protected void onDestroy() {
//        mMainPresenter.detachView();
//        super.onDestroy();
//    }
//
//    @Override
//    public void showData(MainModelBean mainModelBean) {
//        String showData = getResources().getString(R.string.city) + mainModelBean.getCity()
//                + getResources().getString(R.string.wd) + mainModelBean.getWd()
//                + getResources().getString(R.string.ws) + mainModelBean.getWs()
//                + getResources().getString(R.string.time) + mainModelBean.getTime();
//        text.setText(showData);
//    }
//
//    @Override
//    public void showProgress() {
//        mProgressBar.setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    public void hideProgress() {
//        mProgressBar.setVisibility(View.GONE);
//    }
//
//    @Override
//    public void loadDataSuccess(MainModelBean mainModelBean) {
//        showData(mainModelBean);
//    }
//
//    @Override
//    public void loadDataFailure() {
//
//    }
}
