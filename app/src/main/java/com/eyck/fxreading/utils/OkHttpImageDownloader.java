package com.eyck.fxreading.utils;

import com.eyck.fxreading.model.utils.HttpUtils;
import com.orhanobut.logger.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Eyck on 2017/9/2.
 */

public class OkHttpImageDownloader {
    public static void download(String url) {
        Request request = new Request.Builder().url(url).build();
        HttpUtils.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.d(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                FileUtil.createSdDir();
                String url = response.request().url().toString();
                int index = url.indexOf("/");
                String pic = url.substring(index + 1);
                if(FileUtil.isFileExist(pic)) {
                    return;
                }
                Logger.d("pic="+pic);
                FileOutputStream fos = new FileOutputStream(FileUtil.createFile(pic));
                InputStream in = response.body().byteStream();
                byte[] buf = new byte[1024];
                int len = 0;
                while ((len = in.read(buf))!=-1){
                    fos.write(buf,0,len);
                }
                fos.flush();
                in.close();
                fos.close();
            }
        });
    }
}
