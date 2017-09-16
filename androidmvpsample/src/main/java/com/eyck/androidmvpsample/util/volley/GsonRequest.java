package com.eyck.androidmvpsample.util.volley;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;


/**
 * Created by Eyck on 2017/9/6.
 */

public class GsonRequest<T> extends Request<T> {

    private final Gson mGson = new Gson();
    private Response.Listener<T> mListener;

    private Class<T> mClass;
    private TypeToken<T> mTypeToken;

    public GsonRequest(int method, String url, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorListener){
        this(method,url,errorListener);
        mClass = clazz;
        mListener = listener;
    }

    public GsonRequest(int method, String url, TypeToken<T> typeToken, Response.Listener<T> listener, Response.ErrorListener errorListener){
        this(method,url,errorListener);
        mTypeToken = typeToken;
        mListener = listener;
    }

    public GsonRequest(String url, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, clazz, listener, errorListener);
    }

    public GsonRequest(String url, TypeToken<T> typeToken, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, errorListener);
        mTypeToken = typeToken;
        mListener = listener;
    }

    public GsonRequest(int method, String url, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            if(mTypeToken == null) {
                return Response.success(mGson.fromJson(jsonString,mClass),HttpHeaderParser.parseCacheHeaders(response));
            }else {
                return (Response<T>) Response.success(mGson.fromJson(jsonString,mTypeToken.getType()),HttpHeaderParser.parseCacheHeaders(response));
            }
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
}
