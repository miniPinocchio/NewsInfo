package com.liuhui.newsinfo;

import android.app.Application;
import android.content.Context;

import com.lzy.okhttputils.OkHttpUtils;
import com.orhanobut.hawk.Hawk;

import java.util.Map;

/**
 * 作者：liuhui on 2017/3/14 14:59
 * 邮箱：liu594545591@126.com
 * 描述：NewsApplication
 */

public class NewsApplication extends Application {
    private static NewsApplication instance;
    private static Context mContext;
    private static Map<String, String> map;

    //天气
    public static final boolean DEBUG = true;
    public static final boolean USE_SAMPLE_DATA = true;


    public static NewsApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = getApplicationContext();
        initSomeThirdLib();
    }

    private void initSomeThirdLib() {
        Hawk.init(instance).build();
        initOkHttp();
    }

    private void initOkHttp() {

        OkHttpUtils.init(this);
        // 以下都不是必须的，根据需要自行选择
        OkHttpUtils.getInstance()//
                .debug("OkHttpUtils") // 是否打开调试
                .setConnectTimeout(OkHttpUtils.DEFAULT_MILLISECONDS) // 全局的连接超时时间
                .setReadTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS) // 全局的读取超时时间
                .setWriteTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS);// 全局的写入超时时间
        // .addCommonHeaders(headers) // 设置全局公共头
        // .addCommonParams(params);//添加公共参数
    }

    public static Context getAppCtx() {
        return instance.getApplicationContext();
    }


}
