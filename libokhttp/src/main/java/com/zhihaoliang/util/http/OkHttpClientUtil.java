package com.zhihaoliang.util.http;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;

/**
 * Created by Intellij IDEA
 * User:haoliangzhi
 * DATA:2016/3/11
 * TIME:10:21
 */
public abstract  class OkHttpClientUtil {

    /**
     * 表示当先手机处于无法联网的状态下
     */
    public static  final int ST_NO_NET = 0;
    /**
     * 表示文件不存在（例如在上传文件的请求中）
     */
    public static final int ST_NO_FILE =1;
    /**
     * 表示文件不存在（例如在上传文件的请求中）
     */
    public static final int ST_DEFECT_URL =2;

    public  void initInstance(String tag, boolean showResponse) {
        OkHttpUtils.getInstance().debug(tag, showResponse).setConnectTimeout(30, TimeUnit.SECONDS);
    }

    /**
     * Get 请求
     * @param url          请求的地址
     * @param httpCallBack 请求的结果的处理
     * @param context 建议用ApplicationContext
     */
    public void httpGet(Context context,String url, HttpCallBack httpCallBack) {
        url = getUrl(url);
        if(!connectCensor(context, httpCallBack,url)){
            return;
        }
        if(httpCallBack != null){
            httpCallBack.onConnnectSucess();
        }

        if(doDebug(httpCallBack)){
            return;
        }
        OkHttpUtils.get().url(url).build().execute(new InnerHttpCallBack(httpCallBack));
    }

    /**
     * Post Json 请求
     * @param url          请求的地址
     * @param httpCallBack 请求的结果的处理
     * @param object       传递的Bean类
     * @param context 建议用ApplicationContext
     */
    public void httpPostJson(Context context,String url, HttpCallBack httpCallBack, Object object) {
        url = getUrl(url);
        if(!connectCensor(context, httpCallBack,url)){
            return;
        }
        if(httpCallBack != null){
            httpCallBack.onConnnectSucess();
        }

        if(doDebug(httpCallBack)){
            return;
        }
        OkHttpUtils
                .postString()
                .url(url)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(new Gson().toJson(object))
                .build()
                .execute(new InnerHttpCallBack(httpCallBack));
    }

    /**
     * Post Form表单 请求
     *
     * @param url          请求的地址
     * @param httpCallBack 请求的结果的处理
     * @param object       传递的Bean类
     * @param context 建议用ApplicationContext
     */
    public void httpPostForm(Context context,String url, HttpCallBack httpCallBack, Object object) {

        HashMap<String, String> hashMap = objToHash(object);
        httpPostForm(context, url, httpCallBack, hashMap);
    }

    /**
     * Post Form表单 请求
     *
     * @param url          请求的地址
     * @param httpCallBack 请求的结果的处理
     * @param params      传递的
     * @param context 建议用ApplicationContext
     */
    public void httpPostForm(Context context,String url, HttpCallBack httpCallBack, Map<String, String> params) {
        url = getUrl(url);

        if(!connectCensor(context, httpCallBack,url)){
            return;
        }
        if(httpCallBack != null){
            httpCallBack.onConnnectSucess();
        }
        if(doDebug(httpCallBack)){
            return;
        }
         OkHttpUtils.post().url(url).params(params).build().execute(new InnerHttpCallBack(httpCallBack));
    }

    /**
     * 文件上传 请求
     *
     * @param url          请求的地址
     * @param httpCallBack 请求的结果的处理
     * @param files    要上传的的文件路径列表
     * @param context 建议用ApplicationContext
     */
    public void uploadFile(Context context,String url, HttpCallBack httpCallBack,  Map<String, String> params,File...files ) {
        url = getUrl(url);
        if(files == null || files.length==0){
           if(httpCallBack != null){
               httpCallBack.onConnnectFaile(ST_NO_FILE);
               return;
           }
        }

        for (int i=0;i<files.length;i++){
            if(files[i] == null){
                httpCallBack.onConnnectFaile(ST_NO_FILE);
                return;
            }
            if(!files[i].exists()){
                httpCallBack.onConnnectFaile(ST_NO_FILE);
                return;
            }
        }

        if(!connectCensor(context, httpCallBack,url)){
            return;
        }

        if(httpCallBack != null){
            httpCallBack.onConnnectSucess();
        }

        if(doDebug(httpCallBack)){
            return;
        }

        PostFormBuilder postFormBuilder = OkHttpUtils.post().url(url).params(params);
        for (int i=0 ;i<files.length;i++){
            postFormBuilder.addFile(files[i].getName(),files[i].getPath(),files[i]);
        }
        postFormBuilder.build() .execute(new InnerHttpCallBack(httpCallBack));
    }


    private static HashMap<String, String> objToHash(Object obj)  {
        if(obj == null){
            return null;
        }
        HashMap<String, String> hashMap = new HashMap();
        Class clazz = obj.getClass();
        List<Class> clazzs = new ArrayList();

        do {
            clazzs.add(clazz);
            clazz = clazz.getSuperclass();
        } while (!clazz.equals(Object.class));

        for (Class iClazz : clazzs) {
            Field[] fields = iClazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object objVal = field.get(obj);
                    if (objVal != null) {
                        hashMap.put(field.getName(), objVal.toString());
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }

        return hashMap;
    }



    private class InnerHttpCallBack extends StringCallback {

        HttpCallBack mHttpCallBack;

        InnerHttpCallBack(HttpCallBack httpCallBack) {
            mHttpCallBack = httpCallBack;
        }

        @Override
        public void onBefore(Request request) {
            super.onBefore(request);
            if (mHttpCallBack != null) {
                mHttpCallBack.onBefore(request);
            }
        }

        @Override
        public void onAfter() {
            super.onAfter();
            if (mHttpCallBack != null) {
                mHttpCallBack.onAfter();
            }
        }

        @Override
        public void onError(Call call, Exception e) {
            if (mHttpCallBack != null) {
                onDeError();
                mHttpCallBack.onError(call, e);
            }
        }

        @Override
        public void onResponse(String response) {
            doLog(response);
            if (mHttpCallBack != null) {
                mHttpCallBack.onResponse(response);
            }
        }

        @Override
        public void inProgress(float progress) {
            if (mHttpCallBack != null) {
                mHttpCallBack.inProgress(progress);
            }
        }
    }

    private boolean doDebug(final HttpCallBack httpCallBack){
        if(isDebug()){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            httpCallBack.onResponse(null);
            return  true;
        }
        return  false;
    }

    private boolean connectCensor(Context context, HttpCallBack httpCallBack,String url){
        if(TextUtils.isEmpty(url) || !url.startsWith("http")){
            if(httpCallBack != null){
                httpCallBack.onConnnectFaile(ST_DEFECT_URL);
            }
            return  false;
        }

        if(!isNetwork(context)){
            if(httpCallBack != null){
                httpCallBack.onConnnectFaile(ST_NO_NET);
            }
            return  false;
        }

       return true;
    }

    private boolean isNetwork(Context context){
        if(isDebug()){
            return  true;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) return false;
        return true;
    }


    /**
     * 是否是调试装填
     */
    public abstract  boolean isDebug();

    /**
     * 当可以联网但是服务器没返回的错误
     */
    public abstract boolean onDeError();

    /**
     * 对url的处理
     */
    public abstract String getUrl(String url);

    /**
     * 对返回值的打印可以写空方法
     */
    public abstract void doLog(String response);
}
