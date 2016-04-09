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

    /**
     * 是否是debug模式，debug模式不进行联网直接返回成功
     * @return
     */
    @Override
    public boolean isDebug() {
        return false;
    }

    /**
     * 当可以联网但是服务器没返回的错误
     * 返回值暂时没有用到
     */
    @Override
    public boolean onDeError() {
        return false;
    }

    /**
     * 对url的处理，比如加上服务器地址
     */
    @Override
    public String getUrl(String url) {
        return url;
    }

    /**
     *对返回值的打印
     */
    @Override
    public void doLog(String response) {
        Log.e("==",response);
    }
}
