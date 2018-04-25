package com.liuhui.newsinfo;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 作者：liuhui on 2017/3/27 09:41
 * 邮箱：liu594545591@126.com
 * 描述：HackyViewPager
 */

public class HackyViewPager extends ViewPager {
    private boolean isLocked;
    public HackyViewPager(Context context) {
        super(context);
        isLocked = false;
    }
    public HackyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        isLocked = false;
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {//用于处理Touch事件
        if (!isLocked) {
            try {
                return super.onInterceptTouchEvent(ev);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return !isLocked && super.onTouchEvent(event);
    }
}