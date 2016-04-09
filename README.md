1.����һ����OkHttp�����ϰ����ķ�����Ҫ�У�

/**Get ����
* @param url          ����ĵ�ַ
* @param httpCallBack ����Ľ���Ĵ���
* @param context ������ApplicationContext
*/
public void httpGet(Context context,String url, HttpCallBack httpCallBack) 

/**Post Json ����
* @param url          ����ĵ�ַ
* @param httpCallBack ����Ľ���Ĵ���
* @param object       ���ݵ�Bean��
* @param context ������ApplicationContext
*/
public void httpPostJson(Context context,String url, HttpCallBack httpCallBack, Object object) 

/**Post Form�� ����
*
* @param url          ����ĵ�ַ
* @param httpCallBack ����Ľ���Ĵ���
* @param object       ���ݵ�Bean��
* @param context ������ApplicationContext
*/
public void httpPostForm(Context context,String url, HttpCallBack httpCallBack, Object object) 

/**Post Form�� ����
*
* @param url          ����ĵ�ַ
* @param httpCallBack ����Ľ���Ĵ���
* @param params      ���ݵ�
* @param context ������ApplicationContext
*/
public void httpPostForm(Context context,String url, HttpCallBack httpCallBack, Map<String, String> params) 

/**�ļ��ϴ� ����
*
* @param url          ����ĵ�ַ
* @param httpCallBack ����Ľ���Ĵ���
* @param files    Ҫ�ϴ��ĵ��ļ�·���б�
* @param context ������ApplicationContext
*/

public void uploadFile(Context context,String url, HttpCallBack httpCallBack,  Map<String, String> params,File...files )

2.���̵ĵ���

�ڹ����е�build.gradle����
allprojects {
repositories {
jcenter()
maven {
	url "https://raw.githubusercontent.com/zhihaoliang/LibOkHttpUtil/master/libokhttp/repository"
}
}

}

��app�е�build.gradle ����
compile 'com.zhihaoliang.util.http:libokhttp:1.0'

3.ʹ�÷���

<1>ʵ��OkHttpClientUtil
package com.zhihaoliang.util.http.zokhttpsimple;

import android.util.Log;

import com.zhihaoliang.util.http.OkHttpClientUtil;

/**Created by Administrator on 2016/4/7 0007.*/
public class MyOkHttpClientUtil extends OkHttpClientUtil {

    private static OkHttpClientUtil sInstance;

    /**
     * ����ģʽ�е�������
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
     * �Ƿ���debugģʽ��debugģʽ����������ֱ�ӷ��سɹ�
     * @return
     */
    @Override
    public boolean isDebug() {
        return false;
    }

    /**
     * �������������Ƿ�����û���صĴ���
     * ����ֵ��ʱû���õ�
     */
    @Override
    public boolean onDeError() {
        return false;
    }

    /**
     * ��url�Ĵ���������Ϸ�������ַ
     */
    @Override
    public String getUrl(String url) {
        return url;
    }

    /**
     *�Է���ֵ�Ĵ�ӡ
     */
    @Override
    public void doLog(String response) {
        Log.e("==",response);
    }
}

<2>ʵ��HttpCallBack
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
     * �����������������
     */
    public abstract void onConnnectFaile(int state);
    /**
     * ������������
     */
    public abstract void onConnnectSucess();

    /**
     * ����֮ǰ��UI����
     */
    public void onBefore(Request request) {
    }

    /**
     * ����֮��Ĳ���
     */
    public void onAfter() {
    }

    /**
     * ��������Ĳ���
     * @param call
     * @param e
     */
    public abstract void onError(Call call, Exception e);

    /**
     * �����ɹ��Ĳ���
     * @param response
     */
    public abstract void onResponse(String response);

    /**
     * �������ȵ�չʾ
     * @param progress
     */
    public void inProgress(float progress) {
    }
}


