package com.liuhui.newsinfo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    /**
     * 进度条
     */
    private ProgressDialog pb;
    private String url;
    private WebSettings settings;
    private String where;
    private boolean isShowing;
    private ArrayList<String> listimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.wb_news);
        initTopbar(this,true,"新闻资讯");
        listimg = new ArrayList<>();
        if (UtilTool.isNetworkAvailable(this)) {
            show();
        } else {
            ToastShow.shortMessage(this,"暂无网络连接");
            finish();
        }
    }

    @Override
    protected void onResume() {
        isShowing = true;
        super.onResume();
    }

    @Override
    protected void onStop() {
        isShowing = false;
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        webView.setVisibility(View.GONE);
        super.onDestroy();
    }

    private void show() {
        pb = new ProgressDialog(this);
        pb.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置风格为圆形进度条
        pb.setCanceledOnTouchOutside(false);
        pb.setMessage("正在加载...");
        pb.setIndeterminate(false);// 设置进度条是否为不明确的
        pb.show();
        // 警务资讯
//        url = "http://portal.smu.edu.cn/e/action/ShowInfo2.php?classid=1415&id=63663&tlpid=98";
        url = "http://172.16.10.112:48080/InfoCheck/thirdwebinfo/index.html";
        if (TextUtils.isEmpty(url)){
            ToastShow.shortMessage(this,"请先去登录页面设置ip地址");
            pb.dismiss();
            return;
        }
        settings = webView.getSettings();
        settings.setSupportZoom(true);
        // 设置WebView属性，能够执行Javascript脚本
        settings.setDefaultFontSize(17);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JavascriptInterface(this), "imagelistner");//绑定javasrcipt接口，imagelistner为接口的别名
        // 控制缩放
        settings.setLoadWithOverviewMode(true);
        webView.loadUrl(url);
        /** 设置web的进度条style */
        webView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                //点击webView中的键接，依然在此webview中显示，而不跳转到别的浏览器
                webView.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                addImageListner();
                //当页面加载完成，就调用我们的addImageListener()函数
                if (pb != null && isShowing) {
                    pb.dismiss();
                }
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (pb != null && isShowing) {
                    pb.show();
                }
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    if (pb != null) {
                        pb.dismiss();
                    }
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }

    // 监听 所有点击的链接，如果拦截到我们需要的， 就跳转到相对应的页面。


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();// 返回上一页面
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void addImageListner() {

        //遍历页面中所有img的节点，因为节点里面的图片的url即objs[i].src，保存所有图片的src.
        //为每个图片设置点击事件，objs[i].onclick
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{" +
                "window.imagelistner.readImageUrl(objs[i].src);  "  +
                " objs[i].onclick=function()  " +
                " {  "+
                " window.imagelistner.openImage(this.src);  " +
                "  }  " +
                "}" +
                "})()");
    }

    class JavascriptInterface {
        private Context context;
        public JavascriptInterface(Context context) {
            this.context = context;
        }
        @android.webkit.JavascriptInterface
        public void readImageUrl(String img) {     //把所有图片的url保存在ArrayList<String>中
            listimg.add(img);
        }
        @android.webkit.JavascriptInterface  //对于targetSdkVersion>=17的，要加这个声明
        public void openImage(String clickimg)//点击图片所调用到的函数
        {
            int index = 0;
            for(String url:listimg)
                if(url.equals(clickimg)) index = listimg.indexOf(clickimg);//获取点击图片在整个页面图片中的位置
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("list_image",listimg);
            bundle.putInt("index", index);
            intent.putExtra("bundle", bundle);//将所有图片的url以及点击图片的位置作为参数传给启动的activity
            intent.setClass(context, ViewPagerActivity.class);
            context.startActivity(intent);//启动ViewPagerActivity,用于显示图片
        }
    }

    public void initTopbar(final Activity activity, boolean isShow, String title){
        if (isShow) {
            LinearLayout iv_top_left1 = (LinearLayout)activity.findViewById(R.id.iv_top_left1);
            iv_top_left1.setVisibility(View.VISIBLE);
            iv_top_left1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    activity.finish();
                }
            });
        }
        TextView tv_title_center1 = (TextView) findViewById(R.id.tv_title_center1);
        tv_title_center1.setText(title);

    }

}
