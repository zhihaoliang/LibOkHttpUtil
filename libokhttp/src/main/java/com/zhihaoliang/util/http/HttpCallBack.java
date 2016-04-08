package com.zhihaoliang.util.http;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Intellij IDEA
 * User:haoliangzhi
 * DATA:2016/3/11
 * TIME:23:48
 */
public abstract class HttpCallBack {
    /**
     * 如果不符合联网条件
     */
    public abstract void onConnnectFaile(int state);
    /**
     * 符合联网条件
     */
    public abstract void onConnnectSucess();

    public void onBefore(Request request) {
    }

    public void onAfter() {
    }

    public abstract void onError(Call call, Exception e);

    public abstract void onResponse(String response);

    public void inProgress(float progress) {
    }
}
