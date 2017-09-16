package com.eyck.androidmvpsample.model;

import com.eyck.androidmvpsample.presenter.IMainPresenter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


/**
 * Created by Eyck on 2017/9/2.
 */

public class MainModel {

    IMainPresenter mIMainPresenter;

    String json = "{\n" +
            "    \"city\": \"鍖椾含\",\n" +
            "    \"cityid\": \"101010100\",\n" +
            "    \"temp\": \"10\",\n" +
            "    \"WD\": \"涓滃崡椋�\",\n" +
            "    \"WS\": \"2绾�\",\n" +
            "    \"SD\": \"26%\",\n" +
            "    \"WSE\": \"2\",\n" +
            "    \"time\": \"10:25\",\n" +
            "    \"isRadar\": \"1\",\n" +
            "    \"Radar\": \"JC_RADAR_AZ9010_JB\",\n" +
            "    \"njd\": \"鏆傛棤瀹炲喌\",\n" +
            "    \"qy\": \"1012\"\n" +
            "}";

    public MainModel(IMainPresenter iMainPresenter) {
        this.mIMainPresenter = iMainPresenter;
    }

    public void loadData(){
//        MainModelBean mainModelBean = new MainModelBean();
//        try {
//            JSONObject jsonObject = new JSONObject(json);
//            JSONObject weatherinfo = jsonObject.getJSONObject("weatherinfo");
//            mainModelBean.setCity(weatherinfo.getString("city"));
//            mainModelBean.setWd(weatherinfo.getString("WD"));
//            mainModelBean.setWs(weatherinfo.getString("WS"));
//            mainModelBean.setTime(weatherinfo.getString("time"));
//            mIMainPresenter.loadDataSuccess(mainModelBean);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get("http://www.weather.com.cn/adat/sk/101010100.html", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    MainModelBean mainModelBean = new MainModelBean();
                    JSONObject weatherinfo = response.getJSONObject("weatherinfo");
                    mainModelBean.setCity(weatherinfo.getString("city"));
                    mainModelBean.setWd(weatherinfo.getString("WD"));
                    mainModelBean.setWs(weatherinfo.getString("WS"));
                    mainModelBean.setTime(weatherinfo.getString("time"));
                    mIMainPresenter.loadDataSuccess(mainModelBean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                mIMainPresenter.loadDataFailure();
            }
        });
    }
}
