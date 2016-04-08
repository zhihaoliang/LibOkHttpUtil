package com.zhihaoliang.util.http.zokhttpsimple;

import android.util.Log;

import com.zhihaoliang.util.http.OkHttpClientUtil;

/**
 * Created by Administrator on 2016/4/7 0007.
 */
public class MyOkHttpClientUtil extends OkHttpClientUtil {

    private static OkHttpClientUtil sInstance;

    /**
     * 单例模式中的懒加载
     */
    public static OkHttpClientUtil initInstance() {
        if (sInstance == null) {
            synchronized (MyOkHttpClientUtil.class) {
                if (sInstance == null) {
                    sInstance = new MyOkHttpClientUtil();
                    sInstance.initInstance("com.zhihaoliang.util.http.zokhttpsimple",true);
                }
            }
        }
        return sInstance;
    }


    @Override
    public boolean isDebug() {
        return false;
    }

    @Override
    public boolean onDeError() {
        return false;
    }

    @Override
    public String getUrl(String url) {
        return url;
    }

    @Override
    public void doLog(String response) {
        Log.e("==",response);
    }
}
