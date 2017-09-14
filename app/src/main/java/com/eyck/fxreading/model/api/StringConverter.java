package com.eyck.fxreading.model.api;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Eyck on 2017/9/1.
 */

public class StringConverter implements Converter<ResponseBody,String> {
    @Override
    public String convert(ResponseBody value) throws IOException {
        return value.string();
    }
}
