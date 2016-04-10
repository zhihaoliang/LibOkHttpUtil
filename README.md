1.这是一个对OkHttp的整合包括的方法主要有：

<pre><code>
/**
 \* Get 请求
 \* @param url          请求的地址
 \* @param httpCallBack 请求的结果的处理
 \* @param context 建议用ApplicationContext
 */
public void httpGet(Context context,String url, HttpCallBack httpCallBack) 

/**
 \* Post Json 请求
 \* @param url          请求的地址
 \* @param httpCallBack 请求的结果的处理
 \* @param object       传递的Bean类
 \* @param context 建议用ApplicationContext
 */
public void httpPostJson(Context context,String url, HttpCallBack httpCallBack, Object object)

/**
 \* Post Form表单 请求
 \* @param url          请求的地址
 \* @param httpCallBack 请求的结果的处理
 \* @param object       传递的Bean类
 \* @param context 建议用ApplicationContext
 */
public void httpPostForm(Context context,String url, HttpCallBack httpCallBack, Object object)

/**
 \* Post Form表单 请求
 \* @param url          请求的地址
 \* @param httpCallBack 请求的结果的处理
 \* @param params      传递的
 \* @param context 建议用ApplicationContext
 */
public void httpPostForm(Context context,String url, HttpCallBack httpCallBack, Map<String, String> params)

/**
\*文件上传 请求
\*@param url          请求的地址
\*@param httpCallBack 请求的结果的处理
\*@param files    要上传的的文件路径列表
\*@param context 建议用ApplicationContext
 */
public void uploadFile(Context context,String url, HttpCallBack httpCallBack,  Map<String, String> params,File...files )

</pre></code>

2.工程的导入在工程中的build.gradle

<pre><code>
allprojects {

repositories {
    jcenter()
    maven {
        url "https://raw.githubusercontent.com/zhihaoliang/LibOkHttpUtil/master/libokhttp/repository"
    }
}

}


</pre></code>
在app中的build.gradle 加入


compile 'com.zhihaoliang.util.http:libokhttp:1.0'

compile 'com.zhihaoliang.util.http:libokhttp:1.0'

3.使用方法

<1>实现OkHttpClientUtil
<pre><code>
package com.zhihaoliang.util.http.zokhttpsimple;

import android.util.Log;

import com.zhihaoliang.util.http.OkHttpClientUtil;

/**
\*Created by Administrator on 2016/4/7 0007.
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
\* 是否是debug模式，debug模式不进行联网直接返回成功
\* @return
*/
@Override
public boolean isDebug() {
return false;
}

/**
\* 当可以联网但是服务器没返回的错误
\* 返回值暂时没有用到
*/
@Override
public boolean onDeError() {
return false;
}

/**
\* 对url的处理，比如加上服务器地址
*/
@Override
public String getUrl(String url) {
return url;
}

/**
\*对返回值的打印
*/
@Override
public void doLog(String response) {
Log.e("==",response);
}
}

</pre></code>

<2>实现HttpCallBack

<pre><code>
package com.zhihaoliang.util.http;

import okhttp3.Call;
import okhttp3.Request;

/**
\*Created by Intellij IDEA
\*User:haoliangzhi
\*DATA:2016/3/11
\*TIME:23:48
 */
public abstract class HttpCallBack {
    /**
    \*如果不符合联网条件
     */
    public abstract void onConnnectFaile(int state);
    /**
    \*符合联网条件
     */
    public abstract void onConnnectSucess();

    /**
    \*联网之前的UI操作
     */
    public void onBefore(Request request) {
    }

    /**
    \*联网之后的操作
     */
    public void onAfter() {
    }

    /**
    \*联网错误的操作
    \*@param call
    \*@param e
     */
    public abstract void onError(Call call, Exception e);

    /**
    \*联网成功的操作
    \*@param response
     */
    public abstract void onResponse(String response);

    /**
    \*联网进度的展示
    \*@param progress
     */
    public void inProgress(float progress) {
    }
}
</pre></code>
