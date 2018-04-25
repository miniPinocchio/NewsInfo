package com.liuhui.newsinfo;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * 作者：liuhui on 2017/2/12 13:54
 * 邮箱：liu594545591@126.com
 * 描述：ToastShow
 */

public class ToastShow {
    private static Context context;

    public ToastShow(Context context) {
        super();
        this.context = context;
    }

    public void setShowToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 短显示Toast
     *
     * @param context
     *            上下文
     * @param msg
     *            显示的信息
     */
    static public void shortMessage(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /***
     * 短显示Toast
     *
     * @param msg
     *            显示的信息
     */
    public static void shortMessage(String msg) {
        if (msg != null){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "无返回值", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 长显示Toast
     *
     * @param context
     *            上下文
     * @param msg
     *            显示的信息
     */
    static public void longMessage(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void makeToast(final Activity act, final String s) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(act.getWindow().getContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void makeToast(final Activity act, final int resId) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(act, resId, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

