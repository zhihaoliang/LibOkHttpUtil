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

    /**
     * 联网之前的UI操作
     */
    public void onBefore(Request request) {
    }

    /**
     * 联网之后的操作
     */
    public void onAfter() {
    }

    /**
     * 联网错误的操作
     * @param call
     * @param e
     */
    public abstract void onError(Call call, Exception e);

    /**
     * 联网成功的操作
     * @param response
     */
    public abstract void onResponse(String response);

    /**
     * 联网进度的展示
     * @param progress
     */
    public void inProgress(float progress) {
    }
}
