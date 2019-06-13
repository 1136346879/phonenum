package com.hexun.market;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

/***
 * 支持设置标题和跳转的url
 * 基本的webview功能页（支持没有交互的h5页面）
 */

public class WebviewActivity extends Activity implements View.OnClickListener {

    private String toUrl;
    private String mTitle;

    private WebView webView;


    private boolean isRootPage;
    private String title;
    private TextView tetle;

    @SuppressLint({"CheckResult", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate( Bundle savedInstanceState) {

        this.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);

        webView = (WebView) findViewById(R.id.my_webview);
        tetle = (TextView) findViewById(R.id.title);
        webView.requestFocus();
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(false);
        //noinspection deprecation
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setBlockNetworkImage(false);//解决图片不显示
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        webView.setWebViewClient(new WebViewClient() {

            private String errorUrl;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                errorUrl = null;
                isRootPage = toUrl.startsWith(url);
                toUrl = url;
                isFinishing();
                super.onPageStarted(view, url, favicon);
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url != null && !url.equals(errorUrl)) {
                    //android4.4.4加载错误界面data:text/html,chromewebdata
                    if (url.startsWith("http") || url.startsWith("https")) {
                        webView.setVisibility(View.VISIBLE);
                    }
                }
                isFinishing();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                errorUrl = failingUrl;
                super.onReceivedError(view, errorCode, description, failingUrl);
                webView.setVisibility(View.INVISIBLE);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                } else {
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

        });
        isRootPage = true;

        initData(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initData(intent);
    }

    protected void initData(Intent intent) {
        toUrl = intent.getStringExtra("url");
        title = intent.getStringExtra("title");
        if (toUrl != null && !TextUtils.isEmpty(toUrl)) {
            webView.loadUrl(toUrl);
        }
        tetle.setText(title);
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.setVisibility(View.GONE);
            webView.setWebViewClient(null);
            webView.setWebChromeClient(null);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!isRootPage && keyCode == KeyEvent.KEYCODE_BACK && webView != null && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 跳转到基本的webview界面
     * @param s
     * @param url   跳转的url
     */
    public static void toWebActivity(Activity mActivity,  String url,String s) {
            Intent intent = new Intent();
            intent.putExtra("url", url);
            intent.putExtra("title", s);
            intent.setClass(mActivity, WebviewActivity.class);
            mActivity.startActivity(intent);
    }
}
